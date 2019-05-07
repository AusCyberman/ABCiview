package com.abc.iview.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abc.iview.Content;
import com.abc.iview.R;

import com.abc.iview.WatchData;
import com.abc.iview.adapters.EpisodeTVShowAdapter;
import com.abc.iview.activities.OnSwipeTouchListener;
import java.util.ArrayList;

import static com.abc.iview.activities.ChannelActivity.returnfrom;

public class TVShowActivity extends AppCompatActivity {



    Content.TVShow tvShow;
    Boolean newchannel = true;
    BottomNavigationView navigationView;
    RecyclerView episodes;
    public void navbar(Integer varname){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(returnfrom,varname);
        startActivity(intent);

    }

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        navbar(R.id.navigation_home);
                        finish();
                        return true;
                    case R.id.navigation_channels:

                            navbar(R.id.navigation_channels);
                            finish();

                        newchannel=true;
                        return true;
                    case R.id.navigation_favourites:
                        navbar(R.id.navigation_favourites);
                        finish();
                        return true;
                    case R.id.navigation_search:
                        navbar(R.id.navigation_favourites);
                        finish();
                        break;
                    case R.id.navigation_settings:
                        navbar(R.id.navigation_settings);
                        finish();
                        return true;
                }


                return false;
            };



    //Vars


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_tvshow);

        Slide slideright = new Slide();
        slideright.setSlideEdge(Gravity.RIGHT);
        slideright.excludeTarget(R.id.navigation, true);
        slideright.excludeTarget(android.R.id.statusBarBackground, true);
        slideright.excludeTarget(android.R.id.navigationBarBackground, true);
        Slide slideleft = new Slide();
        slideleft.setSlideEdge(Gravity.LEFT);
        slideleft.excludeTarget(R.id.navigation, true);
        slideleft.excludeTarget(android.R.id.statusBarBackground, true);
        slideleft.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(slideright);
        getWindow().setExitTransition(slideleft);
        final ActivityOptions options  = ActivityOptions.makeSceneTransitionAnimation(this,findViewById(R.id.channel_image),"tvshowtochannel");

        navigationView = findViewById(R.id.navigation);
        navigationView.setSelectedItemId((Integer) getIntent().getExtras().get("Fragment"));
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        System.out.println("Activity started");
        final Intent intent = getIntent();
        ArrayList<Content.TVShow> tvshows = MainActivity.tvshows;
        tvShow = tvshows.get((Integer) intent.getExtras().get("TVShowPosition"));
        ImageView play = findViewById(R.id.play);
        play.setOnClickListener(v -> {
            Intent aintent = new Intent(v.getContext(),PlayerActivity.class);
            aintent.putExtra("show", (Integer) intent.getExtras().get("TVShowPosition"));
            System.out.println(intent.getExtras().get("TVShowPosition"));
            if(WatchData.getMostRecentSession(tvShow)==null||WatchData.getMostRecentSession(tvShow).didFinish()){
                if(tvShow.getTrailer().equals(null)){
                    aintent.putExtra("episode", tvShow.getEpisode(0).getId());
                }else {
                    aintent.putExtra("isTrailer", true);
                }
            }else{
                aintent.putExtra("isTrailer",false);
                aintent.putExtra("episode",WatchData.getMostRecentSession(tvShow).getVideo());
            }

            aintent.putExtra("tvextras",intent.getExtras());
            startActivity(aintent);
        });

        ConstraintLayout trailer = findViewById(R.id.trailer);
        trailer.setOnClickListener(v->{
            Intent aintent = new Intent(v.getContext(),PlayerActivity.class);
            aintent.putExtra("show", (Integer) intent.getExtras().get("TVShowPosition"));
            System.out.println(intent.getExtras().get("TVShowPosition"));

                aintent.putExtra("isTrailer",true);


            aintent.putExtra("tvextras",intent.getExtras());
            startActivity(aintent);
        });
        if(tvShow.getTrailer()==null){

            trailer.setVisibility(View.GONE);
            if(WatchData.getMostRecentSession(tvShow)==null) {
            play.setVisibility(View.GONE);
            }

        }else{
            trailer.setVisibility(View.VISIBLE);
        }
        ImageView trailerimage = findViewById(R.id.trailer_image);
        trailerimage.setImageResource(tvShow.getImage());

       ImageView backbutton = findViewById(R.id.tv_show_back);
       backbutton.setImageResource(R.drawable.ic_arrow_back_white_24dp);
        Activity activity = this;
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getExtras().get("Fragment")!=null) {
                    Integer fragment = (Integer) getIntent().getExtras().get("Fragment");
                    if(fragment>10) {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.putExtra(ChannelActivity.returnfrom, fragment);

                        Pair<View,String> image = Pair.create(((Activity) v.getContext()).findViewById(R.id.tvshowImage),"opentvshow");
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(),image);
                        startActivity(intent);
                    }else if(fragment==0){
                        Intent intent = new Intent(v.getContext(),ChannelActivity.class);
                        Pair<View,String> channel = Pair.create(findViewById(R.id.channel_image),"tvshowchannel");

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(),channel);

                        intent.putExtra(MainActivity.channelNameVar,tvShow.getChannel());
                        startActivity(intent,options.toBundle());
                    }


                }
                finish();
                return;
            }
        });

        if(tvShow !=null) {
            ImageView tvshowimage = findViewById(R.id.tvshowImage);
            tvshowimage.setImageResource(tvShow.getImage());
            TextView description = findViewById(R.id.tvshowdescription);
            description.setText(tvShow.getDescription());
            ImageView classification = findViewById(R.id.classification);
            if (tvShow.getClassificationImage() == null) {
                classification.setVisibility(View.GONE);
            } else {
                classification.setVisibility(View.VISIBLE);
                classification.setImageResource(tvShow.getClassificationImage());
            }

            TextView title = findViewById(R.id.tvshowtitle);
            title.setText(tvShow.getName());
            ImageView channelimage = findViewById(R.id.channel_image);
            final ImageView favourites = findViewById(R.id.favourite_star);
            if(MainActivity.favourites.contains(MainActivity.tvshows.indexOf(tvShow))){
                System.out.println("It does contain it");
                favourites.setImageResource(R.drawable.ic_star_white_24dp);

            }else{
                favourites.setImageResource(R.drawable.ic_star_border_white_24dp);
            }
            final Content.TVShow tvs = tvShow;
            favourites.setOnClickListener(v -> {
                if(MainActivity.favourites.contains(MainActivity.tvshows.indexOf(tvs))){
                    favourites.setImageResource(R.drawable.ic_star_border_white_24dp);
                    MainActivity.favourites.remove(MainActivity.favourites.indexOf(MainActivity.tvshows.indexOf(tvs)));
                    Snackbar.make(findViewById(R.id.mainTVLayout), "Removed it from favourites",
                            Snackbar.LENGTH_SHORT)
                            .show();
                }else{
                    MainActivity.favourites.add(MainActivity.tvshows.indexOf(tvs));

                    favourites.setImageResource(R.drawable.ic_star_white_24dp);
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.mainTVLayout), "Added it to favourites",
                            Snackbar.LENGTH_SHORT);
                            snackbar.setAction(R.string.view_favourites, v1 -> navbar(R.id.navigation_favourites));
                            snackbar.show();
                }
            });


            channelimage.setImageResource(tvShow.getChannelImage());
        }


        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView=findViewById(R.id.tvshow_episode_recycler);
        recyclerView.setAdapter(new EpisodeTVShowAdapter(tvShow.getEpisodes(),this,false,getIntent().getExtras()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        Context context = this;


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :

                return true;
            case (MotionEvent.ACTION_MOVE) :
                //Log.d(DEBUG_TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                //Log.d(DEBUG_TAG,"Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                //Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                //Log.d(DEBUG_TAG,"Movement occurred outside bounds " +

                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

//TV Show



}

package com.abc.iview.activities;

import android.content.Intent;
import android.os.Bundle;

import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.abc.iview.content.Content;
import com.abc.iview.R;

import com.abc.iview.adapters.MainMenuChannelListAdapter;
import com.abc.iview.adapters.TVShowChannelAdapter;
import com.abc.iview.content.TVShow;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static com.abc.iview.content.Content.categories;

public class ChannelActivity extends AppCompatActivity {


    ImageView channelLogo;
    Boolean newchannel = true;
    ArrayList<TVShow> alltvshows = MainActivity.tvshows;
    ArrayList<TVShow> listTVshows = new ArrayList<>();

    ArrayList<TVShow> populartvshows = new ArrayList<>();





public static final String returnfrom = "com.abc.iview.ReturnFrom";


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
                                    if(newchannel=false) {
                                        navbar(R.id.navigation_channels);
                                        finish();
                                    }
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
    BottomNavigationView.OnNavigationItemReselectedListener onNavigationItemReselectedListener = item -> navbar(R.id.navigation_channels);

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {


        super.onResume();

        alltvshows= MainActivity.tvshows;

        populartvshows.clear();
        listTVshows.clear();
        Intent intent = getIntent();
        //HashMap<Integer, ImageView> channels = new MainActivity().channelImages;




        String channel;
            if(intent.getExtras().get(MainActivity.channelNameVar).getClass()==String.class){
                channel= (String) intent.getExtras().get(MainActivity.channelNameVar);
            }else{
                channel = getResources().getResourceEntryName((Integer) intent.getExtras().get(MainActivity.channelNameVar));
            }
        Integer channelresource;
        System.out.println(channel);
        switch (channel){
            case "abc":
                channelresource = R.drawable.ic_abc;
                System.out.println("Channel:"+channel);
                channel(channel,channelresource,R.id.tv_show_recycler);
                break;
            case "abcnews":
                channelresource = R.drawable.ic_abcnews;
                System.out.println("Channel:"+channel);
                channel(channel,channelresource,R.id.tv_show_recycler);

                break;
            case "abcme":
                channelresource = R.drawable.ic_abcme;
                System.out.println("Channel:"+channel);
                channel(channel,channelresource,R.id.tv_show_recycler);
                break;
            case "abckids":
                channelresource = R.drawable.ic_abckids;
                System.out.println("Channel:"+channel);
                channel(channel,channelresource,R.id.tv_show_recycler);
                break;
            case "abccomedy":
                channelresource = R.drawable.ic_abccomedy;
                channel(channel,channelresource,R.id.tv_show_recycler);
                System.out.println("Channel:"+channel);
                break;
            case "abcarts":
                channelresource = R.drawable.ic_abcarts;
                System.out.println("Channel:"+channel);
                channel(channel,channelresource,R.id.tv_show_recycler);
                break;
            case "iviewpresents":
                channelresource = R.drawable.ic_iviewpresents;
                System.out.println("Channel:"+channel);
                channel(channel,channelresource,R.id.tv_show_recycler);
                break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_channel);

        Slide slideright = new Slide();
        slideright.setSlideEdge(Gravity.END);
        slideright.excludeTarget(R.id.navigation, true);
        slideright.excludeTarget(android.R.id.statusBarBackground, true);
        slideright.excludeTarget(android.R.id.navigationBarBackground, true);
        Slide slideleft = new Slide();
        slideleft.setSlideEdge(Gravity.START);
        slideleft.excludeTarget(R.id.navigation, true);
        slideleft.excludeTarget(android.R.id.statusBarBackground, true);
        slideleft.excludeTarget(android.R.id.navigationBarBackground, true);
        Fade fade = new Fade();
        fade.excludeTarget(R.id.navigation, true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        //getWindow().setEnterTransition(fade);
        //getWindow().setEnterTransition(new Fade().excludeTarget(R.id.navigation, true).excludeTarget(android.R.id.statusBarBackground, true).excludeTarget(android.R.id.navigationBarBackground, true));

        channelLogo = ((ImageView)findViewById(R.id.channel_image));
    System.out.println(R.id.channel_image);












        super.onCreate(savedInstanceState);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setOnNavigationItemReselectedListener(onNavigationItemReselectedListener);
        navigation.setSelectedItemId(R.id.navigation_channels);


    }
    public void channel(String channel,Integer channelresource,Integer recyclerview){

        RecyclerView allRecyclerView;
        RecyclerView popRecyclerView;
        RecyclerView.Adapter alltvChannelAdapter;
        RecyclerView.Adapter poptvChannelAdapter;


        allRecyclerView = (RecyclerView) findViewById(R.id.tv_show_recycler);
        listTVshows = new ArrayList<TVShow>();
        ArrayList<TVShow> alltvshows = MainActivity.tvshows;
        populartvshows = new ArrayList<TVShow>();





        popRecyclerView = (RecyclerView) findViewById(R.id.popular_tv_show_recycler);



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView


        // use a linear layout manager

        popRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        for(TVShow tvshow : alltvshows) {
            if(tvshow!=null){
            System.out.println("The values are " + tvshow.getChannel() + " " + channel);

            if (tvshow.getChannel().equals(channel)) {
                if (MainActivity.parentcontrols && tvshow.getAdult()) {

                } else {
                    listTVshows.add(tvshow);
                    System.out.println("Found one");


                    if(tvshow.isPopular()){
                        populartvshows.add(tvshow);


                    }
                }
            }
        }}



        //String[][]
        for(String category : categories){
            if(category!=null){
            for(TVShow tvshow : alltvshows) {
                if(tvshow!=null){
                if(tvshow.getChannel().equals(channel)){

                    if(categories.indexOf(category) ==tvshow.getCategory()){

                        if (MainActivity.parentcontrols && tvshow.getAdult()) {

                        } else {

                            System.out.println("Adding category to channel "+category);

                            if(categories.contains(category)==false){
                                categories.add(category);
                            }



                        }}}

            }}}
        }
        LinearLayoutManager llm  = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        allRecyclerView.setLayoutManager(llm);
        System.out.println("Pop Show size "+populartvshows.size());
        // specify an adapter (see also next example)
        alltvChannelAdapter = new MainMenuChannelListAdapter(listTVshows,categories,this,getApplicationContext(),channel,0);
        allRecyclerView.setAdapter(alltvChannelAdapter);
        allRecyclerView.getAdapter().notifyDataSetChanged();
      //  ViewCompat.setNestedScrollingEnabled(allRecyclerView,false);
        poptvChannelAdapter = new TVShowChannelAdapter(populartvshows,R.layout.tvshow_horizontal, this,getApplicationContext(),false,0);
        popRecyclerView.setAdapter(poptvChannelAdapter);
        popRecyclerView.setHasFixedSize(true);


        popRecyclerView.getAdapter().notifyDataSetChanged();
        channelLogo.setImageResource(channelresource);
       /* allRecyclerView = findViewById(R.id.popular_channel_tv_show_recycler);
        mAdapter = new TVShowChannelAdapter(populartvshows,channel,R.layout.tvshow_horizontal,this,getApplicationContext());
        allRecyclerView.setAdapter(mAdapter);*/
    }




}

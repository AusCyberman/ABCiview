package com.abc.iview.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;

import android.view.ActionMode;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.abc.iview.content.Content;
import com.abc.iview.R;
import com.abc.iview.WatchData;
import com.abc.iview.activities.MainActivity;
import com.abc.iview.activities.PlayerActivity;
import com.abc.iview.activities.TVShowActivity;
import com.abc.iview.content.TVShow;
import com.bumptech.glide.Glide;


import java.util.ArrayList;

public class RecentlyWatchedAdapter extends RecyclerView.Adapter<RecentlyWatchedAdapter.RecentWatchedViewHolder> {
    ArrayList<WatchData.WatchSession> sessions;
ActionMode actionMode;
    Integer layout;
    //Activity  fromclass;
    private Context context;
    Boolean isTextBlack;
    Integer fragmentsource;
    public class vertTVShowManager extends RecyclerView.LayoutManager {
     @Override public boolean isAutoMeasureEnabled(){
         return false;
     }

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return null;
        }
    }
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.recently_watched_options, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.recently_watched_remove:

                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };


    @NonNull
    @Override
    public RecentWatchedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recently_watched_layout,parent, false);

        RecentWatchedViewHolder vh = new RecentWatchedViewHolder(v,parent.getContext());



        System.out.println(getItemCount());
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentWatchedViewHolder holder, int position) {
        ((Activity)holder.context).registerForContextMenu(holder.layout);
        /*holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            // Called when the user long-clicks on someView
            public boolean onLongClick(View view) {
                if (actionMode != null) {
                    return false;
                }


                // Start the CAB using the ActionMode.Callback defined above
                actionMode = ((Activity)holder.context).startActionMode(actionModeCallback);

                view.setSelected(true);
                return true;
            }
        });*/
        System.out.println(position);

        final WatchData.WatchSession session = sessions.get(position);
        TVShow tvShow = session.getTvshow();
        assert tvShow != null;
        System.out.println("Adding "+tvShow.getName());
        Glide.with(context).load(tvShow.getImage()).into(holder.imageView);
        holder.tvshowNameView.setText(tvShow.getName());
        TVShow.Episode episode = ((TVShow.Episode)tvShow.getEpisode(session.getVideo()));
        holder.tvDetails.setText(episode.getName());
        final Integer pos = position;
        final Context context = holder.context;

        holder.info.setOnClickListener(v -> {
            Intent intent = new Intent(context, TVShowActivity.class);
            for(int i=1;MainActivity.tvshows.size()>i;i++){
                if(tvShow.getName().equals(MainActivity.tvshows.get(i).getName())){
                        intent.putExtra("TVShowPosition",i);

                        intent.putExtra("Fragment", R.id.navigation_home);


                        v.getContext().startActivity(intent);


                        ((Activity)v.getContext()).finish();


                }
            }

        });


        holder.play.setOnClickListener(v -> {


                    // intent.putExtra("TVShowPosition",);
                    Intent aintent = new Intent(v.getContext(), PlayerActivity.class);
                    aintent.putExtra("TVShowPosition",tvShow.getId());

                    aintent.putExtra("isTrailer",false);
                    aintent.putExtra("episode",session.getVideo());
                    System.out.println("Episode is "+session.getVideo());
                    aintent.putExtra("recent",true);
                    ActivityOptions play = ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(),v,"recentlywatchedtoplayer");
                    v.getContext().startActivity(aintent,play.toBundle());




        });
        holder.bar.setProgress((int) ((WatchData.getMostRecentSession(episode.getId(),tvShow.getId()).getProgress()*100)/WatchData.getMostRecentSession(episode.getId(),tvShow.getId()).getTotalLength()));






    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }
public RecentlyWatchedAdapter(   Context context,ArrayList sessions){
    this.sessions=sessions;
    System.out.println("Items"+getItemCount());
    this.layout = layout;
   // this.fromclass=fromclass;

    this.context = context;

}
    public static class RecentWatchedViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        // each data item is just a string in this case
        public ImageView imageView;
        public TextView tvshowNameView;
        public TextView tvDetails;
        public ImageView classificationView;
        public ImageView play;
        public View layout;
        public Context context;
        ProgressBar bar;
        public ConstraintLayout constraintLayout;
        public ImageView info;
        public RecentWatchedViewHolder(ConstraintLayout view,  Context context) {
            super(view);
            this.layout=view;
            this.context=context;
            view.setOnCreateContextMenuListener(this);

                tvshowNameView = view.findViewById(R.id.tvshowtitle);
                imageView = view.findViewById(R.id.tvshowImage);
                tvDetails= view.findViewById(R.id.episodedetails);
                play= view.findViewById(R.id.exo_play);
                info=view.findViewById(R.id.info);
                bar = view.findViewById(R.id.progressBar);


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater = ((Activity)v.getContext()).getMenuInflater();
            inflater.inflate(R.menu.recently_watched_options, menu);
        }
    }
}

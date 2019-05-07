package com.abc.iview.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abc.iview.Content;
import com.abc.iview.R;

import com.abc.iview.WatchData;
import com.abc.iview.activities.MainActivity;
import com.abc.iview.activities.PlayerActivity;

import java.util.ArrayList;

public class EpisodeTVShowAdapter extends RecyclerView.Adapter<EpisodeTVShowAdapter.EpisodeViewHolder> {
    ArrayList<Content.TVShow.Episode> episodes;

    //Integer layout;
    //Activity  fromclass;
    private Context context;
    Boolean isTextBlack;
    //Integer fragmentsource;

    Bundle bundle;
    public class vertTVShowManager extends RecyclerView.LayoutManager {
     @Override public boolean isAutoMeasureEnabled(){
         return false;
     }

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return null;
        }
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tvshow_episode,parent, false);

        EpisodeViewHolder vh = new EpisodeViewHolder(v,parent.getContext());



        System.out.println(getItemCount());
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {

        System.out.println(position);
        final Content.TVShow.Episode episode = episodes.get(position);

        assert episode != null;
        System.out.println("Adding "+episode.getName());
        //holder.imageView.setImageResource(episode.getImage());
        holder.tvshowNameView.setText(episode.getName());
        final Integer pos = position;
        final Context context = holder.context;
        holder.description.setText(episode.getDescription());
        holder.imageView.setOnClickListener(v -> {
          //  Intent intent = new Intent(context, TVShowActivity.class);
            for(Content.TVShow tvshow : MainActivity.tvshows){
                if(episode.equals(episode)){
                   // intent.putExtra("TVShowPosition",);
                    Intent aintent = new Intent(v.getContext(), PlayerActivity.class);

                    //System.out.println(intent.getExtras().get("TVShowPosition"));
                    aintent.putExtra("isTrailer",false);
                    aintent.putExtra("episode",pos);
                    aintent.putExtra("tvextras",bundle);
                    v.getContext().startActivity(aintent);


                }
            }

        });
        if(episode.getImage()!=null) {
            holder.imageView.setImageResource(episode.getImage());
        }else{
            holder.imageView.setImageResource(episode.getTVShow().getImage());
        }
          if(episode.getClassificationImage()==null){
              holder.classificationView.setVisibility(View.GONE);
          }else{
           holder.classificationView.setImageResource(episode.getClassificationImage());
       }




       if(isTextBlack){
           holder.tvshowNameView.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark ));

       }
       if(WatchData.getMostRecentSession(episode.getId(),episode.getTVShow().getId())!=null){
           if(WatchData.getMostRecentSession(episode.getId(),episode.getTVShow().getId()).didFinish()){
               holder.bar.setVisibility(View.GONE);
           }else {
               holder.bar.setVisibility(View.VISIBLE);
               holder.bar.setProgress((int) ((WatchData.getMostRecentSession(episode.getId(), episode.getTVShow().getId()).getProgress() * 100) / (WatchData.getMostRecentSession(episode.getId(), episode.getTVShow().getId()).getTotalLength())));
           }}else{

           holder.bar.setVisibility(View.GONE);}
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }
public EpisodeTVShowAdapter(ArrayList<Content.TVShow.Episode> episodes, Context context, Boolean makeTextBlack, Bundle bundle){
    this.episodes= episodes;
    this.isTextBlack=makeTextBlack;
   // this.layout = layout;
   // this.fromclass=fromclass;
    //this.fragmentsource=fragment;
    this.context = context;
    this.bundle=bundle;

}
    public static class EpisodeViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageView;
        public TextView tvshowNameView;
        public ImageView classificationView;
        public TextView description;
        //public Integer layout;
        public ProgressBar bar;
        public Context context;
        public ConstraintLayout constraintLayout;
        public EpisodeViewHolder(ConstraintLayout view, Context context) {
            super(view);

            this.context=context;

                tvshowNameView = view.findViewById(R.id.episode_title);
                imageView = view.findViewById(R.id.episode_image);
                description = view.findViewById(R.id.episode_description);
                bar = view.findViewById(R.id.episodebar);



            classificationView = view.findViewById(R.id.classificationView);
        }
    }
}

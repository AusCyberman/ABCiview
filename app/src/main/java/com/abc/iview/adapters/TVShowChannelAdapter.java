package com.abc.iview.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.abc.iview.content.Content;
import com.abc.iview.R;
import com.abc.iview.activities.MainActivity;
import com.abc.iview.activities.TVShowActivity;
import com.abc.iview.content.TVShow;
import com.bumptech.glide.Glide;


import java.util.ArrayList;

public class TVShowChannelAdapter extends RecyclerView.Adapter<TVShowChannelAdapter.TVShowViewHolder> {

    Activity fromclass;
    ArrayList<TVShow> tvshows = MainActivity.tvshows;

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
    private ImageView channel_image;
    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(layout,parent, false);

        TVShowViewHolder vh = new TVShowViewHolder(v,layout,parent.getContext());
    channel_image=parent.findViewById(R.id.channel_image);
        vh.layout=layout;

        System.out.println(getItemCount());
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        System.out.println(position);
        final TVShow tvShow = tvshows.get(position);

        assert tvShow != null;
        System.out.println("Adding "+tvShow.getName());
        Glide.with(context).load(tvShow.getImage()).into(holder.imageView);
        holder.tvshowNameView.setText(tvShow.getName());
        final Integer pos = position;
        final Context context = holder.context;

        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TVShowActivity.class);
            for(TVShow tvshow : MainActivity.tvshows){
                if(tvShow.equals(tvShow)){
                    intent.putExtra("TVShowPosition",MainActivity.tvshows.indexOf(tvShow));
                    if(fragmentsource>10) {
                        intent.putExtra("Fragment", fragmentsource);

                    }else{
                        switch (fragmentsource){
                            case 0:
                                intent.putExtra("Fragment", 0);

                                break;
                            case 1:
                                intent.putExtra("Fragment",1);
                        }

                    }

//                     final ActivityOptions options  = ActivityOptions.makeSceneTransitionAnimation(fromclass,holder.imageView,"opentvshow");
                    if(fragmentsource==0){
                    if(fromclass==null){
                        System.out.println("from class is null");
                    }
                        Pair<View,String> channel = Pair.create(((Activity) context).findViewById(R.id.channel_image),"tvshowchannel");
                        Pair<View,String> image = Pair.create(holder.imageView,"opentvshow");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,channel,image);

                       context.startActivity(intent,options.toBundle());
                       ((Activity) context).finish();
                    }else{
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,holder.imageView,"opentvshow");
                    v.getContext().startActivity(intent,options.toBundle());
                    if(fragmentsource>10) {

                        //((Activity)v.getContext()).finish();
                    }}

                }
            }

        });

          if(tvShow.getClassificationImage()==null){
              holder.classificationView.setVisibility(View.GONE);
          }else{
           holder.classificationView.setImageResource(tvShow.getClassificationImage());
       }




       if(isTextBlack){
           holder.tvshowNameView.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark ));

       }
    }

    @Override
    public int getItemCount() {
        return tvshows.size();
    }

public TVShowChannelAdapter(ArrayList<TVShow> tvshows, Integer layout, Activity fromclass, Context context, Boolean makeTextBlack, Integer fragment){
    this.tvshows=tvshows;
    this.isTextBlack=makeTextBlack;
    this.layout = layout;

   this.fromclass=fromclass;

    this.fragmentsource=fragment;
    this.context = context;

}
    public static class TVShowViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageView;
        public TextView tvshowNameView;
        public ImageView classificationView;
        public Integer layout;
        public Context context;
        public ConstraintLayout constraintLayout;
        public TVShowViewHolder(ConstraintLayout view,Integer layout,Context context) {
            super(view);
            this.layout=layout;
            this.context=context;
            if(layout== R.layout.tvshow_list) {
                tvshowNameView = view.findViewById(R.id.tvshowsubtext);
                imageView = view.findViewById(R.id.tvshowsubimage);
            }else{
                tvshowNameView = view.findViewById(R.id.horizontal_tvshowsubtext);
                imageView = view.findViewById(R.id.tvshowimage);


            }

            classificationView = view.findViewById(R.id.classificationView);
        }
    }
}

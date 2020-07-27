package com.abc.iview.content;

import android.content.Context;

import java.util.ArrayList;

public class TVShow extends Content{

    private ArrayList<Episode> episodes= new ArrayList<>();
    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }
    public Episode getEpisode(Integer id){
        return episodes.get(id);
    }



    public Episode addEpisode(Context context, String name, String description, String classification, String url) {
        Episode episode = new Episode(context,this,url,name,description,classification);
        episode.setId(episodes.size());
        episodes.add(episode);

        return episode;
    }
    public TVShow(String TVShow, Integer category) {


        super(TVShow, category,"TVSHOW");

    }
}




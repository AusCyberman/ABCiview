package com.abc.iview;

import android.media.tv.TvContentRating;
import android.se.omapi.Session;

import com.abc.iview.activities.MainActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public class WatchData {

    public static ArrayList<WatchSession> watchSessions = new ArrayList<>();
    public void clear(){
        watchSessions.clear();

    }
    //public static ArrayList<Integer> validCategories = new ArrayList<>();
    public static ArrayList<Integer> getLikes() {
        ArrayList<Integer> allCategories = new ArrayList<>();

        HashMap<Integer,Integer> categoryCount = new HashMap<>();
        //validCategories.clear();
        ArrayList<Integer> categoryCountList = new ArrayList<>();
        if (watchSessions!=null){

        for (WatchSession session : watchSessions) {
            allCategories.add(session.getTvshow().getCategory());
        }


        for (int i=0;i<Content.categories.size();i++) {
            Integer category = i;
            for (int x=0;x<allCategories.size();x++) {
                Integer allcat = allCategories.get(x);
                if (category == allcat) {

                    if(categoryCount.keySet().contains(category)){

                        categoryCount.put(i, categoryCount.get(category)+1);
                        System.out.println("Found "+Content.categories.get(category));
                    }else{
                        categoryCount.put(i, 1);
                        System.out.println("Found "+Content.categories.get(category));
                    }
                }
            }
        }
        System.out.println("categorycount size is "+categoryCount.keySet().size());



            categoryCount.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                    .forEachOrdered(e -> {
                        categoryCountList.add(e.getKey());
                        System.out.println(e.getKey());
                        System.out.println(e.getValue());

                    });
            ArrayList<Integer> truelist = new ArrayList<>();
            for(int i=0;i<categoryCountList.size();i++){

                Integer value=categoryCountList.get(i);
                System.out.println(value+" "+ Content.categories.get(value));
                System.out.println(value);
                truelist.add(value);
            }



        //Collections.sort(categoryCountList);
        //Collections.sort(categoryCountList,Collections.reverseOrder());

        System.out.println("data stuffs are "+truelist);
            return truelist;
    }
        return null;
    }

    public static class WatchSession{

        private boolean isFinished=false;
        private Content.TVShow tvshow;
        private long progress;
        private long totalLength;

        public void setTotalLength(long totalLength) {
            this.totalLength = totalLength;
        }

        public long getTotalLength() {
            return totalLength;
        }

        public long getProgress() {
            return progress;
        }
        private Integer video;
        public void setProgress(long progress) {
            this.progress = progress;
        }






        public Content.TVShow getTvshow() {
            return tvshow;
        }

        public void setTvshow(Content.TVShow tvshow) {
            this.tvshow = tvshow;
        }


        public void commit(Long progress){
            setProgress(progress);
            watchSessions.add(this);
        }
        public void finish(){
            isFinished=true;
            watchSessions.add(this);
        }
                public boolean didFinish(){
            System.out.println("is finished");
            return isFinished;
                }


        public WatchSession(Integer video, Content.TVShow tvshow){
                 this.tvshow=tvshow;
                 this.video=video;
        }

        public Integer getVideo() {
            return video;
        }
    }

    public static WatchSession getMostRecentSession(Content.TVShow tvshow){
        if(watchSessions.size()!=0){
            for(int i=watchSessions.size()-1;i>=0;i--){
                WatchSession session = watchSessions.get(i);

         if(session!=null){
                if(session.getTvshow().equals(tvshow)){
                return session;
                }
            }
        }}
        return null;
    }
    public static WatchSession getMostRecentSession(Integer video,Integer tvshow){
        if(watchSessions.size()!=0){
        for(int i=watchSessions.size()-1;i>-1;i--){
            WatchSession session = watchSessions.get(i);
            if(session!=null){

                if(session.getVideo()==video&&session.getTvshow().getId()==tvshow) {
                    return session;

                }
            }
        }}
        return null;
    }
    public static ArrayList<WatchSession> getSessionsWithoutDupes(Boolean forRecentlyWatched){
        ArrayList<WatchSession> realSessions = new ArrayList<WatchSession>();
        if(watchSessions.size()!=0){
        System.out.println("WatchSessions size is "+watchSessions.size());
            for(int i=watchSessions.size()-1;i>=0;i--){
                Boolean isnew=true;
                WatchSession session = watchSessions.get(i);

                if(realSessions.size()!=0) {
                    for (int s = 0;s<realSessions.size();s++) {
                        System.out.println("Values are "+session.getTvshow().getId()+" and " +realSessions.get(s).getTvshow().getId());
                        if (session.getTvshow().getId()==realSessions.get(s).getTvshow().getId()) {
                            isnew=false;
                        } else {


                        }
                    }

                }else {
                    isnew=true;
                }
                if(isnew){
                    if (!(MainActivity.parentcontrols &&session.getTvshow().getAdult())) {

                            realSessions.add(session);
                            System.out.println("Adding session " + session.getTvshow().getId());

                    }
                }
            }
        }
        System.out.println("The size is "+realSessions.size());
        ArrayList<WatchSession> truessions = new ArrayList<>();
        for(WatchSession session : realSessions){
            if(forRecentlyWatched & session.didFinish()) {
                System.out.println("Finished");
            }else{
                truessions.add(session);
            }
        }
    return truessions;
    }

}

package com.abc.iview.data;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.abc.iview.BitmapConversion;
import com.abc.iview.Content;
import com.abc.iview.activities.MainActivity;
import com.abc.iview.data.get.HttpJsonParser;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.abc.iview.activities.MainActivity.tvshows;

public class InitData extends AsyncTask<Object,Integer,Void> {
    private static final String TAG_SUCCESS = "success";
    private static final String BASE_URL = "http://192.168.2.5/";
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    HttpURLConnection urlConnection = null;
    private static final String KEY_SUCCESS = "success";


    @Override
    protected Void doInBackground(Object... objects) {
        HttpJsonParser httpJsonParser = new HttpJsonParser();
        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                BASE_URL + "BaseData.php", "GET", null);
        try {
            int success = Integer.parseInt(jsonObject.getString(KEY_SUCCESS));
            JSONArray movies;





            if (success == 1) {
                JSONArray channels = jsonObject.getJSONArray("channels");
                System.out.println(jsonObject);
                System.out.println(channels);
                for(int i=0;i<channels.length();i++){
                    JSONObject channel = channels.getJSONObject(i);
                    Content.channels.add(channel.getString("channel_name"));
                    System.out.println(channel.getString("channel_name"));

                }
                JSONArray categories = jsonObject.getJSONArray("categories");
                for(int i=0;i<categories.length();i++){
                    JSONObject category = categories.getJSONObject(i);
                    Content.categories.add(category.getString("category_name"));
                    System.out.println(category.getString("category_name"));

                }


                JSONArray contents = jsonObject.getJSONArray("content");
                for(int i=0;i<=contents.length();i++){
                    JSONObject contentj = contents.getJSONObject(i);
                    switch (contentj.getInt("content_type")){
                        case 1:
                            tvshows.add(new Content.TVShow(contentj.getString("content_name"),contentj.getInt("category")));

                            tvshows.get(tvshows.size()-1).setChannel(channels.getJSONObject(contentj.getInt("channel")).getString("channel_name"));
                            tvshows.get(tvshows.size()-1).setClassification(contentj.getString("classification"));
                            new BitmapConversion().execute(contentj.getString("image"), String.valueOf(tvshows.size()-1));
                            System.out.print(contentj);
                            if(contentj.getInt("popular")==1){
                                tvshows.get(tvshows.size()-1).makepopular();
                            }
                            tvshows.get(tvshows.size()-1).setDescription(contentj.getString("description"));
                            tvshows.get(tvshows.size()-1).setId(tvshows.size()-1);


                    }
                            contentj.get("content_name");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

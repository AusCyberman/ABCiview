package com.abc.iview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import com.abc.iview.activities.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapConversion extends AsyncTask<String, Void, Bitmap> {

    private Exception exception;

    protected Bitmap doInBackground(String... surl) {
        try {
            URL url = new URL(surl[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            MainActivity.tvshows.get(Integer.parseInt(surl[1])).setImage(BitmapFactory.decodeStream(input));
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(Bitmap feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}

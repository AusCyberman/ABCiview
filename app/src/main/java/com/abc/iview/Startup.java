package com.abc.iview;
import android.app.Application;
import android.content.Context;

import com.abc.iview.activities.MainActivity;

import static com.abc.iview.activities.MainActivity.init;


public class Startup extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
// Place your code here which will be executed only once
        init(getApplicationContext());
    }

}
package com.abc.iview;
import android.app.Application;
import android.content.Context;

import android.content.Intent;
import com.abc.iview.activities.MainActivity;
import com.abc.iview.data.InitData;
import com.bumptech.glide.Glide;


import static com.abc.iview.activities.MainActivity.init;


public class Startup extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
// Place your code here which will be executed only once
    init(getApplicationContext());


    }

}
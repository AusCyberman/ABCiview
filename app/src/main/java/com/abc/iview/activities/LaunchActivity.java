package com.abc.iview.activities;

import android.content.Intent;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.abc.iview.R;
import com.abc.iview.data.InitData;
import com.abc.iview.activities.MainActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        MainActivity.init(getApplicationContext());
        //new InitData().execute(this,constraintLayout);

    }
}

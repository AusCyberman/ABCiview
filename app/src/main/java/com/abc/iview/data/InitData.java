package com.abc.iview.data;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InitData extends AsyncTask<Object,Integer,Void> {
    private static final String TAG_SUCCESS = "success";


    @Override
    protected Void doInBackground(Object... objects) {


        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // products found
                // Getting Array of Products
                products = json.getJSONArray(TAG_PRODUCTS);

                // looping through All Products
                for (int i = 0; i < products.length(); i++) {
                    JSONObject c = products.getJSONObject(i);

                }
            } else {
                // no products found

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

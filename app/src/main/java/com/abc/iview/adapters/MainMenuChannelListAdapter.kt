package com.abc.iview.adapters

import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewCompat
import android.support.v7.widget.ContentFrameLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.println
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.abc.iview.Content
import com.abc.iview.R

import com.abc.iview.activities.MainActivity

import java.util.ArrayList

import android.os.Handler





class MainMenuChannelListAdapter(tvshows: ArrayList<Content.TVShow>, categories: ArrayList<String>, fromclass: Activity, //  Activity  fromclass;
                                 private val context: Context, channel: String, internal var fragment: Int?) : RecyclerView.Adapter<MainMenuChannelListAdapter.MainMenuViewHolder>() {
    internal var tvshows: ArrayList<Content.TVShow> = MainActivity.tvshows
    internal var channel: String? = "abc"
    internal var categories = ArrayList<String>()
    var pool = RecyclerView.RecycledViewPool()



    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MainMenuViewHolder {

        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_recycler_layout, parent, false) as ConstraintLayout
        val innerRv = v.findViewById<RecyclerView>(R.id.main_menu_sub_recycler)
        val vh = MainMenuViewHolder(v, parent.context)
        val innerLLM = LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
        innerRv.apply {
            layoutManager = innerLLM
            setRecycledViewPool(pool)

        }


        return vh
    }


    override fun onBindViewHolder(holder: MainMenuViewHolder, position: Int) {


        val allRecyclerView: RecyclerView
        val popRecyclerView: RecyclerView
        val alltvChannelAdapter: RecyclerView.Adapter<*>
        val poptvChannelAdapter: RecyclerView.Adapter<*>


        allRecyclerView = holder.recyclerView
        val listTVshows = ArrayList<Content.TVShow>()
        val alltvshows = MainActivity.tvshows
        val category = categories[position]
                 System.out.println("Category is $category")



        holder.textView.text = category


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView


        // use a linear layout manager


        for (tvshow in alltvshows) {

            if (tvshow.channel == channel || channel == "") {

                if (Content.categories.indexOf(categories[position]) == tvshow.category) {

                    if (MainActivity.parentcontrols && tvshow.adult!!) {

                    } else {
                        listTVshows.add(tvshow)
                       // println("Found one")


                    }
                }
            }

        }


        // specify an adapter (see also next example)
        alltvChannelAdapter = TVShowChannelAdapter(listTVshows, R.layout.tvshow_horizontal_home_screen, holder.activity, holder.context, false, fragment)
        allRecyclerView.adapter = alltvChannelAdapter

        //allRecyclerView.setVerticalScrollBarEnabled(false);
        //allRecyclerView.setNestedScrollingEnabled(true);
        //allRecyclerView.setHasFixedSize(true);

        val llm = LinearLayoutManager(holder.context, LinearLayoutManager.VERTICAL, false)
        allRecyclerView.layoutManager=llm;

        allRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 20)
        (allRecyclerView.adapter as TVShowChannelAdapter).notifyDataSetChanged()
        Handler().post {
            allRecyclerView.getRecycledViewPool()
                    .setMaxRecycledViews(0, 1)
        }

        /* allRecyclerView = findViewById(R.id.popular_channel_tv_show_recycler);
        mAdapter = new TVShowChannelAdapter(populartvshows,channel,R.layout.tvshow_horizontal,this,getApplicationContext());
        allRecyclerView.setAdapter(mAdapter);*/
        //println("size is$itemCount")
    }

    override fun getItemCount(): Int {

        return categories.size
    }

    init {
            pool.setMaxRecycledViews(0,5)
        this.tvshows = tvshows
        this.channel = channel
        this.categories = categories

    }//this.fromclass=fromclass;

    class MainMenuViewHolder(view: ConstraintLayout, var context: Context) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case

        var recyclerView: RecyclerView
        var textView: TextView
        var activity: Activity
        var constraintLayout: ConstraintLayout? = null

        init {
            this.activity = context as Activity
            textView = view.findViewById(R.id.main_menu_sub_recycler_text)
            recyclerView = view.findViewById(R.id.main_menu_sub_recycler)


        }
    }
}

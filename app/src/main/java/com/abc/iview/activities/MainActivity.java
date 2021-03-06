package com.abc.iview.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.transition.Slide;

import android.util.LruCache;
import android.view.Gravity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.abc.iview.content.Content;
import com.abc.iview.R;
import com.abc.iview.content.TVShow;
import com.abc.iview.fragments.ChannelsFragment;
import com.abc.iview.fragments.FavouritesFragment;
import com.abc.iview.fragments.HomeFragment;
import com.abc.iview.fragments.SearchFragment;
import com.abc.iview.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;




public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, FavouritesFragment.OnFragmentInteractionListener,ChannelsFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener {



    //Vars
    final Fragment homeFragment = new HomeFragment();
    final Fragment channelsFragment = new ChannelsFragment();
    final Fragment favouritesFragment = new FavouritesFragment();
    final Fragment searchFragment = new SearchFragment();
    final Fragment settingsFragment = new SettingsFragment();
    public static Fragment currentFragment;
    final FragmentManager fm = getSupportFragmentManager();

    public boolean hasstarted=false;
    public static Integer colorPrimaryDark = R.color.colorPrimaryDark;
    public static Integer contrastingColor = R.color.contrastingColor;

    public HashMap<Integer, ImageView> channelImages = new HashMap<Integer, ImageView>();
    public ArrayList<String> channelNames= new ArrayList<String>();
    public static ArrayList<TVShow> tvshows = new ArrayList<>();
    //public static ArrayList<Integer> tvshows = new ArrayList<>();
    public static ArrayList<Integer> favourites = new ArrayList<Integer>();
    public static boolean darkmode = false;
    public static boolean parentcontrols = false;




    public static final String channelNameVar = "com.abc.view.Channel";



    ;

    //Bottom Nav Bar

    private ScreenSlidePagerAdapter pagerAdapter;
    private ViewPager mPager;
    private BottomNavigationView navigation;


    @Override
    protected void onRestart() {
        super.onRestart();


    }
    public static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 8;

    MenuItem prevMenuItem;


    //cache
   public static LruCache<String, Bitmap> memoryCache;
    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Set default fragment to homeFragment

        currentFragment= homeFragment;


        if (!hasstarted){

            hasstarted=true;

        }
        //init cache
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);

        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    navigation.getMenu().getItem(0).setChecked(false);

                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem =navigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        //mPager.setOffscreenPageLimit(1);
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = item -> {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            System.out.println("Naved to home");
                            //setContentView(R.layout.activity_main);

                            mPager.setCurrentItem(0);
                            //break;
                            return true;

                        case R.id.navigation_channels:
                            System.out.println("Naved to channels");
                            mPager.setCurrentItem(1);
                            //break;
                            return true;

                        case R.id.navigation_search:
                            System.out.println("Naved to search");
                            mPager.setCurrentItem(2);
                            //break;
                            return true;
                        case R.id.navigation_favourites:
                            System.out.println("Naved to favourites");
                            mPager.setCurrentItem(3);
                            //break;
                            return true;


                        case R.id.navigation_settings:
                            System.out.println("Naved to settings");
                            mPager.setCurrentItem(4);
                            //break;
                            return true;

                    }

                    return true;
                };





        Slide slideright = new Slide();
        slideright.setSlideEdge(Gravity.RIGHT);
        slideright.excludeTarget(R.id.navigation, true);
        slideright.excludeTarget(android.R.id.statusBarBackground, true);
        slideright.excludeTarget(android.R.id.navigationBarBackground, true);
        Slide slideleft = new Slide();
        slideleft.setSlideEdge(Gravity.LEFT);
        slideleft.excludeTarget(R.id.navigation, true);
        slideleft.excludeTarget(android.R.id.statusBarBackground, true);
        slideleft.excludeTarget(android.R.id.navigationBarBackground, true);

        //getWindow().setEnterTransition(slideright);//getWindow().setExitTransition(null);


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //mPager.setOffscreenPageLimit(0);
        Intent intent = getIntent();
        Bundle intentextras= intent.getExtras();

        if(intentextras !=null) {
            Integer channelint = intent.getExtras().getInt(ChannelActivity.returnfrom);
            if (null != channelint) {
                navigation.setSelectedItemId(channelint);
            }
        }



    }

    public void openChannel(View View) {
        Intent intent = new Intent(this, new ChannelActivity().getClass());
        intent.putExtra("fromChannelPage",true);
        intent.putExtra(channelNameVar, View.getId());
        startActivity(intent);

        Integer id = View.getId();


    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    public static Integer newTVShow(String tvshow,String channel,Integer category, String classification,String imageURL,String description,Boolean popularity){
        tvshows.add(new TVShow(tvshow,category));

        tvshows.get(tvshows.size()-1).setChannel(channel);
        tvshows.get(tvshows.size()-1).setClassification(classification);
        tvshows.get(tvshows.size()-1).setImage(imageURL);

        if(popularity){
            tvshows.get(tvshows.size()-1).makepopular();
        }
        tvshows.get(tvshows.size()-1).setDescription(description);
        tvshows.get(tvshows.size()-1).setId(tvshows.size()-1);

        return tvshows.size()-1;


    }
    public void addEpisode(Integer tvshow,String name,String description,String classification,String url ){
        tvshows.get(tvshow).addEpisode(this,name,description,url,classification);
    }

    public static void init(Context context){

        Content.channels.clear();
        Content.channels.add("abc");
        Content.channels.add("abccomedy");
        Content.channels.add("abcme");
        Content.channels.add("abckids");
        Content.channels.add("abcnews");
        Content.channels.add("abcarts");
        Content.channels.add("iviewpresents");





        Content.categories.clear();
        Content.categories.add("Arts & Culture");//0
        Content.categories.add("Comedy");//1
        Content.categories.add("Documentary");//2
        Content.categories.add("Drama");//3
        Content.categories.add("Documentary");//4
        Content.categories.add("Education");//5
        Content.categories.add("Kids Entertainment");//6
        Content.categories.add("Lifestyle");//7
        Content.categories.add("News & Current Affairs");//8
        Content.categories.add("Panel & Discussion");//9
        Content.categories.add("Regional Australia");//10
        Content.categories.add("Sport");//11

        tvshows.clear();

        newTVShow("Deadlock",
                "abc",
                3,
                "m",
                "https://cdn.iview.abc.net.au/thumbs/1200/dr/DR1621H_5b444767c43d0.jpg",
                "A clandestine tunnel party draws crowds of teenagers with the lure of a wild night. When an amateur stunt goes spectacularly wrong it sends the revellers fleeing head-on into a mysterious accident that will change the life of five teenagers forever.",
                true
        );
        tvshows.get(0).setTrailer("http://www.youtube.com/watch?v=K8x7a7ocP_U",context);
        tvshows.get(0).addEpisode(context,"Episode 1 Sadie","Sadie is offered the chance of a lifetime, but it means leaving home and everything she knows. She struggles with the decision, despite her boyfriend's failing promises, her best friend's anger, and her alcoholic mother.","m","http://www.youtube.com/watch?v=gUvcx6tkK7Y");
        //tvshows.get(0).getEpisode(0).setImage(R.drawable.deadlock_ep_1);
        tvshows.get(0).addEpisode(context,"Episode 2 Laila","Unable to face life without her best friend, Laila embarks on a harebrained plan to keep Sadie from leaving. After bribing a pregnant local girl to pee on a pregnancy test stick, she sends it to Sadie's boyfriend.","m","http://www.youtube.com/watch?v=vF-y4dWlizw");
        //tvshows.get(0).getEpisode(1).setImage(R.drawable.deadlock_ep_2);
       tvshows.get(0).addEpisode(context,"Episode 3 Aero","Aero is a handsome indigenous straight-A student. He has worked so hard to destroy stereotypes of what he can achieve. He never goes to parties, but gets emotionally blackmailed into helping his trouble-prone cousin.","m","https://www.youtube.com/watch?v=stWS-ELT-_M");
        //tvshows.get(0).getEpisode(2).setImage(R.drawable.deadlock_ep_3);
        tvshows.get(0).addEpisode(context,"Episode 4 Zai","Zai has more serious secrets to keep than those unearthed by his nosy little sister. He's being eaten alive by guilt and harbours feelings for his best friend Charlie. He should have been with Sadie on the night of the party.","m","https://www.youtube.com/watch?v=azSTGYKoYTY");
        //tvshows.get(0).getEpisode(3).setImage(R.drawable.deadlock_ep_4);
        tvshows.get(0).addEpisode(context,"Episode 5 Jed","Inseparable thrill-seeking identical twins, Ned and Jed Manos, are local legends known for their maniac stunts. Separated at the party, Ned has not come home, and Jed suspects he had something to do with the car accident.","m","https://www.youtube.com/watch?v=8Mg7030cLhg");
        //tvshows.get(0).getEpisode(4).setImage(R.drawable.deadlock_ep_5);
      //  
        newTVShow("Bluey",
                "abckids",
                6,
                "g",
                "https://cdn.iview.abc.net.au/thumbs/1200/ch/CH1702Q_5d8459f6d7a88_1280.jpg",
                "Bluey is an inexhaustible six year-old Blue Heeler dog, who loves to play and turns everyday family life into extraordinary adventures, developing her imagination as well as her mental, physical and emotional resilience",
                true
        );
       // 
        newTVShow("Melbourne Comedy Festival",
                "abccomedy",
                1,
                "m",
                "https://cdn.iview.abc.net.au/thumbs/1200/le/LE1816V_5c907176b7096.jpg",
                "The Melbourne International Comedy Festival Events include the smash hit festival favourites such The Gala and the Allstar Supershow. Featuring a star-studded line-up of Aussie comedians and international talent.",
                true
        );
     //   
        newTVShow("Hardball",
                "abcme",
                6,
                "g",
                "https://cdn.iview.abc.net.au/thumbs/1200/ch/CH1719H_5ca6e43600baa_1280.jpg",
                "Hardball follows fish out of water Mikey and his two misfit mates, Salwa and Jerry. Their goal - Make Mikey the sweetest-bestest-acest handball champ Western Sydney's ever seen.",
                true
        );

      tvshows.get(3).setTrailer("https://www.youtube.com/watch?v=EW3bwU5zU48",context);
       
        newTVShow("Planet America",
                "abcnews",
                9,
                "norating",
                "https://cdn.iview.abc.net.au/thumbs/1200/nc/NC1914H_5c412986c0c90.jpg",
                "John Barron and Chas Licciardello check in on the State of the United States, separating fake news from facts as President Trump wrangles a Democratic House, investigations, turbulent global affairs and the 2020 campaign.",
                true
        );
        tvshows.get(4).addEpisode(context,"Friday 12/4/2019","As the US awaits his extradition, what's next for Wikileaks founder Julian Assange? Also, did American intelligence agencies really spy on Donald Trump? And where did it all go wrong? A Deep Dive into the crisis in Venezuela.","norating","http://www.youtube.com/watch?v=KAh1Ni7vhL8");
       // tvshows.get(4).getEpisode(0).setImage(R.drawable.planetamerica_fri_12);
        tvshows.get(4).addEpisode(context,"Friday 5/4/2019","Presidential frontrunner Joe Biden faces his own #MeToo moment. Plus NATOs uncertain future in the era of Trump, and the battle to protect free speech on college campuses.","norating","http://www.youtube.com/watch?v=aVsfNZwRGwE");
        
        newTVShow("Rewind",
                "abcarts",
                0,
                "norating",
                "https://cdn.iview.abc.net.au/thumbs/1200/ac/AC1826H_5c4fdf7bab34f_1280.jpg",
                "This archive-based series is a kaleidoscope of high art and pop culture programming from the 1960s to the noughties.",
                true
        );
        
        newTVShow("Good Game",
                "iviewpresents",
                0,
                "m",
                "https://cdn.iview.abc.net.au/thumbs/1200/ch/CH1861H_5c80bb9744c98.jpg",
                "A digital-only series on the latest in gaming for adults. Join Goose for hands-on impressions of the biggest upcoming titles, analysis of the latest trends, and the occasional rant!",
                true);

        tvshows.get(6).addEpisode(context,"Sekiro Starter's Guide","Diving into the latest game from the developers behind Dark Souls and Bloodborne can be more than a bit daunting... So here's a quick guide with some tips to help you start out as a sneaky, stabby shinobi in Sekiro.","m","https://www.youtube.com/watch?v=q8c_EdW6I5s");
       // tvshows.get(6).getEpisode(0).setImage(R.drawable.goodgame_ep1);
        
        newTVShow("Killing Eve",
                "abc",
                3,

                "ma",
                "https://cdn.iview.abc.net.au/thumbs/1200/ch/CH1861H_5c80bb9744c98.jpg",
                "Bound by their obsession and a brutal act; Eve is reeling and Villanelle has disappeared and both of them are in deep trouble. Eve's hunt for Villanelle begins, but this time she's not the only one looking for her.",
                true);
        

        newTVShow("Secret Life of Boys",
                "abcme",
                6,
                "g",
                "https://cdn.iview.abc.net.au/thumbs/1200/zw/ZW1676A_5c872e8b29519.jpg",
                "Ginger moves from her home in Sydney into the Hughes house, full of boys! She must learn how to fit in and how to live the summer with all the boys.",
                true);
        tvshows.get(8).addEpisode(context,"Ep 1 Just One Of The Boys","Ginger makes the long trip from Australia to the UK, so she can spend the summer with her cousins. After making a horrible first impression, Ginger finds it difficult to fit in with the four boys","g","https://www.youtube.com/watch?v=CkYSFxEJE54");
        tvshows.get(8).addEpisode(context,"Ep 2 Make Yourself At Home","The boys find out that Ginger is keeping a secret about her dad. After their parents leave for the afternoon, the boys use a game of Truth or Dare to try to coax the information out of her.","g","https://www.youtube.com/watch?v=0wnsAMpz7L0");
        
        newTVShow("PJ Masks",
                "abckids",
                6,
                "g",
                "https://cdn.iview.abc.net.au/thumbs/1200/zw/ZW1866A_5c5cd4ba92e91_1280.jpg",
                "The adventure continues for the PJ Masks as they embark on new missions with some brand-new superpowers. The old villians are back as well as new ones creating chaos but the PJ Masks are here to save the day!",
                true);
        tvshows.get(9).setTrailer("https://www.youtube.com/watch?v=kmUx9cGUHzg",context);
        
        newTVShow("Insiders",
                "abcnews",
                9,
                "norating",
                "https://cdn.iview.abc.net.au/thumbs/1200/nc/NC1909V_5d02e4403fb87_1280.jpg",
                "Barrie Cassidy presents Australia's most popular political program. Insiders speaks with the key players, providing analysis, opinion and robust debate from the country's leading political commentators.",
                true);
        

    newTVShow("Peppa Pig",
            "abckids",
            6,
            "g",
            "https://cdn.iview.abc.net.au/thumbs/1200/zw/ZW0924A_5def2a42a781b_1280.jpg",
            "The pre-school adventures of a cheeky and slightly bossy little pig called Peppa, who lives with her mummy and daddy and little brother George. Sometimes these adventures involve a few tears but they always end happily.",true);
    tvshows.get(11).addEpisode(context,"The Dentist",
            "Peppa and George are going to the dentist for a check-up. Peppa and Georges's teeth are lovely and clean but George's plastic toy \"Mr Dinosaur\" teeth are a bit yellow.\n" +
                    "\n",
            "g",
            "https://www.youtube.com/watch?v=O_Ccbrka0_0");
        

    //System.out.println(gson.toJson(tvshows));

    }
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        System.out.println(position);
            switch (position) {
                case 0:


                    if(!currentFragment.equals(homeFragment)){
                        currentFragment=homeFragment;

                        System.out.println("navved to home");
                        return homeFragment;
                    }

                    break;
                case 1:

                    if(!currentFragment.equals(channelsFragment)){
                        currentFragment=channelsFragment;

                        System.out.println("navved to channels");
                        return channelsFragment;
                    }


                    break;
                case 2:

                    if(!currentFragment.equals(searchFragment)){

                        currentFragment=searchFragment;

                        System.out.println("navved to search");
                        return searchFragment;
                    }

                    break;
                case 3:

                    if(!currentFragment.equals(favouritesFragment)){
                        currentFragment=favouritesFragment;

                        System.out.println("navved to faves");
                        return favouritesFragment;
                    }
                    break;

                case 4:

                    if(!currentFragment.equals(settingsFragment)){


                        System.out.println("navved to settings");
                        return settingsFragment;
                    }
                     break;


            }
            return currentFragment;

        }

        @Override
        public int getCount() {
            return 5;
        }
    }
    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

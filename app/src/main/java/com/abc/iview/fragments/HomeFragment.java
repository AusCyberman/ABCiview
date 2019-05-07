package com.abc.iview.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.abc.iview.Content;
import com.abc.iview.Content.TVShow;
import com.abc.iview.R;

import com.abc.iview.WatchData;
import com.abc.iview.activities.MainActivity;
import com.abc.iview.adapters.MainMenuChannelListAdapter;
import com.abc.iview.adapters.RecentlyWatchedAdapter;
import com.abc.iview.adapters.TVShowChannelAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    View mview;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = ((Activity)getContext()).getMenuInflater();
        inflater.inflate(R.menu.recently_watched_options, menu);
    }
    @Override
    public void onResume() {
        super.onResume();

        ((RecyclerView)mview.findViewById(R.id.recently_watched_recycler)).getAdapter().notifyDataSetChanged();
        if(getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0)!=null){
        if(getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0).equals(this)) {
            ((BottomNavigationView) ((Activity) mview.getContext()).findViewById(R.id.navigation)).setSelectedItemId(R.id.navigation_home);
        }}
    }

    ArrayList<Integer> likes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         mview = inflater.inflate(R.layout.fragment_home, container, false);
        //((BottomNavigationView)((Activity)mview.getContext()).findViewById(R.id.navigation)).setSelectedItemId(R.id.navigation_home);
        Boolean recommended =false;
        Boolean recentlywatched=false;

        RecyclerView recentlyWatchedRecycler;
        RecyclerView recommendedRecycler;
        RecyclerView popRecyclerView;
        RecyclerView allReyclerView;

        RecyclerView.Adapter alltvChannelAdapter;
        RecyclerView.Adapter poptvChannelAdapter;


        recentlyWatchedRecycler = mview.findViewById(R.id.recently_watched_recycler);

        recommendedRecycler=mview.findViewById(R.id.recommended_recycler);

        ArrayList<TVShow>listTVshows = new ArrayList<TVShow>();
        ArrayList<TVShow> alltvshows = MainActivity.tvshows;
        ArrayList<TVShow> populartvshows = new ArrayList<TVShow>();

        likes = WatchData.getLikes();
        ArrayList<TVShow> likedtvshows = new ArrayList<>();
        for(Integer count : likes){
            Integer catgory = count;
            System.out.println(TVShow.categories.get(catgory));
            for (TVShow tvhow : MainActivity.tvshows) {
                if(tvhow.getCategory()==catgory){
                    if(WatchData.getMostRecentSession(tvhow)==null) {
                        if(!(MainActivity.parentcontrols&&tvhow.getAdult())) {
                            likedtvshows.add(tvhow);
                        }
                    }
                }
            }
        }

        recentlyWatchedRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recentlyWatchedRecycler.setAdapter(new RecentlyWatchedAdapter(getContext(),WatchData.getSessionsWithoutDupes(true)));

        if (recentlyWatchedRecycler.getAdapter().getItemCount()!=0){
            recentlywatched=true;
        }


        recommendedRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recommendedRecycler.setAdapter(new TVShowChannelAdapter(likedtvshows,R.layout.tvshow_horizontal_home_screen,getActivity(),getContext(),false,R.id.navigation_home));
        if(recommendedRecycler.getAdapter().getItemCount()!=0){
           recommended=true;

        }


        popRecyclerView = (RecyclerView) mview.findViewById(R.id.home_popular);



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView


        // use a linear layout manager

        popRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));







        for(TVShow tvshow : alltvshows) {



            if (MainActivity.parentcontrols && tvshow.getAdult()) {

            } else {
                listTVshows.add(tvshow);
                System.out.println("Found one");


                if(tvshow.isPopular()){
                    populartvshows.add(tvshow);


                }
            }

        }

        System.out.println("Pop Show size "+populartvshows.size());
        // specify an adapter (see also next example)

       // recentlyWatchedRecycler.setVerticalScrollBarEnabled(false);

        //  ViewCompat.setNestedScrollingEnabled(recentlyWatchedRecycler,false);
        poptvChannelAdapter = new TVShowChannelAdapter(populartvshows,R.layout.tvshow_horizontal,getActivity(),getContext(),false,R.id.navigation_home);
        popRecyclerView.setAdapter(poptvChannelAdapter);
        popRecyclerView.setHasFixedSize(true);


        popRecyclerView.getAdapter().notifyDataSetChanged();

        ArrayList<String> categories = new ArrayList<>();
        for(String category : TVShow.categories){

            for(TVShow tvshow : alltvshows) {



                    if(TVShow.categories.indexOf(category) ==tvshow.getCategory()){

                        if (MainActivity.parentcontrols && tvshow.getAdult()) {

                        } else {

                            System.out.println("Adding category to channel "+category);

                            if(categories.contains(category)==false){
                                categories.add(category);
                            }



                        }}

            }
        }



        allReyclerView=mview.findViewById(R.id.all_tv_recycler);
        allReyclerView.setNestedScrollingEnabled(false);
        allReyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        allReyclerView.setAdapter(new MainMenuChannelListAdapter(MainActivity.tvshows,categories,getActivity(),getContext(),"",R.id.navigation_home));
        //allReyclerView.setHasFixedSize(true);
        //((LinearLayoutManager)allReyclerView.getLayoutManager()).setInitialPrefetchItemCount(2);


        if(recommended || recentlywatched){
            allReyclerView.setVisibility(View.GONE);
            if (recentlyWatchedRecycler.getAdapter().getItemCount()==0){
                mview.findViewById(R.id.recently_watched).setVisibility(View.GONE);
            }else{
                mview.findViewById(R.id.recently_watched).setVisibility(View.VISIBLE);
            }
            if(recommendedRecycler.getAdapter().getItemCount()==0){
                mview.findViewById(R.id.recommended).setVisibility(View.GONE);

            }else{
                mview.findViewById(R.id.recommended).setVisibility(View.VISIBLE);

            }

        }else{
        allReyclerView.setVisibility(View.VISIBLE);
            mview.findViewById(R.id.recommended).setVisibility(View.GONE);
            mview.findViewById(R.id.recently_watched).setVisibility(View.GONE);
        }
        return mview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

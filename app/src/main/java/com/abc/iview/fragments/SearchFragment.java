package com.abc.iview.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.abc.iview.content.Content;
import com.abc.iview.R;

import com.abc.iview.activities.MainActivity;
import com.abc.iview.adapters.TVShowChannelAdapter;
import com.abc.iview.content.TVShow;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    View v;

    @Override
    public void onResume() {

        super.onResume();
        ((SearchView)v.findViewById(R.id.searchbar)).setQuery("",false);
        if(getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0)!=null){
        if(getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0).equals(this)) {
            ((BottomNavigationView) ((Activity) getContext()).findViewById(R.id.navigation)).setSelectedItemId(R.id.navigation_search);
        }}
    }

    final ArrayList<TVShow> tvShowsQuery = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_search, container, false);

        //((BottomNavigationView)((Activity)v.getContext()).findViewById(R.id.navigation)).setSelectedItemId(R.id.navigation_search);
        // Inflate the layout for this fragment
        final GridLayoutManager llm  = new GridLayoutManager(getContext(),2);




        SearchView searchView = v.findViewById(R.id.searchbar);
        searchView.setIconifiedByDefault(false);
        ((SearchView)v.findViewById(R.id.searchbar)).setQuery("",false);


        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tvShowsQuery.clear();
                for(TVShow tvshow : MainActivity.tvshows){
                    if(!(MainActivity.parentcontrols&&tvshow.getAdult())) {
                        if (tvshow.getName().toLowerCase().contains(query.toLowerCase()) || tvshow.getDescription().toLowerCase().contains(query.toLowerCase())) {
                            tvShowsQuery.add(tvshow);

                            System.out.println("found one for search query");
                        }
                    }
                }
                if(tvShowsQuery.size()!=0){
                    RecyclerView recyclerView = v.findViewById(R.id.search_recycler);
                    RecyclerView.Adapter adapter =  new TVShowChannelAdapter(tvShowsQuery,R.layout.tvshow_horizontal_home_screen,getActivity(),getContext(),false,1);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tvShowsQuery.clear();
                for(TVShow tvshow : MainActivity.tvshows){
                    if(!(MainActivity.parentcontrols&&tvshow.getAdult())) {
                        if (tvshow.getName().toLowerCase().contains(newText.toLowerCase()) || tvshow.getDescription().toLowerCase().contains(newText.toLowerCase())) {
                            tvShowsQuery.add(tvshow);

                            System.out.println("found one for search query");
                        }
                    }
                }
                RecyclerView recyclerView = v.findViewById(R.id.search_recycler);
                if(tvShowsQuery.size()!=0){

                    RecyclerView.Adapter adapter =  new TVShowChannelAdapter(tvShowsQuery,R.layout.tvshow_horizontal_home_screen,getActivity(),getContext(),false,1);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(adapter);

                }else{
                    recyclerView.removeAllViews();
                }
                return true;


            }
        });
        //searchView.setOnQueryTextListener(this);
        return v;
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

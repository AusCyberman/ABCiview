package com.abc.iview.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.abc.iview.Content;
import com.abc.iview.Content.TVShow;
import com.abc.iview.R;
import com.abc.iview.activities.ChannelActivity;
import com.abc.iview.activities.MainActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.abc.iview.activities.MainActivity.channelNameVar;
import static com.abc.iview.activities.MainActivity.parentcontrols;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChannelsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChannelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChannelsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ChannelsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChannelsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChannelsFragment newInstance(String param1, String param2) {
        ChannelsFragment fragment = new ChannelsFragment();
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

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0)!=null){
        if(getActivity().getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0).equals(this)) {
            ((BottomNavigationView) ((Activity) getContext()).findViewById(R.id.navigation)).setSelectedItemId(R.id.navigation_channels);
        }}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channels, container, false);
        //((BottomNavigationView)((Activity)view.getContext()).findViewById(R.id.navigation)).setSelectedItemId(R.id.navigation_channels);
        ArrayList<String> channels = new ArrayList<>();
        for(TVShow tvshow : MainActivity.tvshows){
            if(!(parentcontrols&tvshow.getAdult())){
                channels.add(tvshow.getChannel());
            }
        }
        view.findViewById(R.id.abc).setVisibility(View.GONE);
        view.findViewById(R.id.abccomedy).setVisibility(View.GONE);
        view.findViewById(R.id.abcme).setVisibility(View.GONE);
        view.findViewById(R.id.abckids).setVisibility(View.GONE);
        view.findViewById(R.id.abcnews).setVisibility(View.GONE);
        view.findViewById(R.id.abcarts).setVisibility(View.GONE);
        view.findViewById(R.id.iviewpresents).setVisibility(View.GONE);
        for(String channel : channels){
            switch(channel){
                case "abc":
                    view.findViewById(R.id.abc).setVisibility(View.VISIBLE);
                    break;
                case "abccomedy":
                    view.findViewById(R.id.abccomedy).setVisibility(View.VISIBLE);
                    break;
                case "abcme":
                    view.findViewById(R.id.abcme).setVisibility(View.VISIBLE);
                    break;
                case "abckids":
                    view.findViewById(R.id.abckids).setVisibility(View.VISIBLE);
                    break;
                case "abcnews":
                    view.findViewById(R.id.abcnews).setVisibility(View.VISIBLE);
                    break;
                case "abcarts":
                    view.findViewById(R.id.abcarts).setVisibility(View.VISIBLE);
                    break;
                case "iviewpresents":
                    view.findViewById(R.id.iviewpresents).setVisibility(View.VISIBLE);
                    break;
            }

        }

        return view;
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

    public void openChannel(View View) {
        Intent intent = new Intent(new MainActivity(), new ChannelActivity().getClass());
        String channel = getResources().getResourceEntryName(View.getId());

        System.out.println(channel);
        intent.putExtra(MainActivity.channelNameVar, getView().getId());
        startActivity(intent);

        Integer id = View.getId();



    }

    }

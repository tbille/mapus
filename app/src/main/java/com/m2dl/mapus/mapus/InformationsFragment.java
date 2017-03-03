package com.m2dl.mapus.mapus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Display app informations
 */
public class InformationsFragment extends Fragment {

    public InformationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InformationsFragment.
     */
    public static InformationsFragment newInstance() {
        InformationsFragment fragment = new InformationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_fragment_informations);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informations, container, false);
    }
}

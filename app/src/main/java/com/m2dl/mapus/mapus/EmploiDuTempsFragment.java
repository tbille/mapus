package com.m2dl.mapus.mapus;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.m2dl.mapus.mapus.database.EventDataSource;
import com.m2dl.mapus.mapus.edt.EmploiDuTempsAdapter;
import com.m2dl.mapus.mapus.model.DatePickerDialogFragment;
import com.m2dl.mapus.mapus.model.Event;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmploiDuTempsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmploiDuTempsFragment extends Fragment {

    private static final String ARG_PARAM1 = "date";

    // TODO: Rename and change types of parameters
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEEE d MMMMMM yyyy");
    private String date;


    public EmploiDuTempsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment EmploiDuTempsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmploiDuTempsFragment newInstance(String date) {
        EmploiDuTempsFragment fragment = new EmploiDuTempsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_fragment_edt);
        View view = inflater.inflate(R.layout.fragment_emploi_du_temps, container, false);
        TextView jour = (TextView) view.findViewById(R.id.jour);
        Date trueDate = new Date(date);
        jour.setText(dateFormat.format(trueDate));
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialogFragment popup = new DatePickerDialogFragment();
                popup.show(getActivity().getFragmentManager(), "calendar");
            }
        };
        jour.setOnClickListener(listener);
        ImageView flecheDown = (ImageView) view.findViewById(R.id.arrow_down_button);
        flecheDown.setOnClickListener(listener);

        ListView list = (ListView) view.findViewById(R.id.list);
        EventDataSource eventDataSource = new EventDataSource(getContext());
        eventDataSource.open();
        ArrayList<Event> dataModels = eventDataSource.getEventByDate(trueDate);
        EmploiDuTempsAdapter adapter= new EmploiDuTempsAdapter(dataModels,getContext());
        list.setAdapter(adapter);
        return view;
    }
}

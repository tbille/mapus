package com.m2dl.mapus.mapus;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.m2dl.mapus.mapus.firebase.AnomalieDataSource;
import com.m2dl.mapus.mapus.model.Anomalie;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnomalieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnomalieFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String imgUri;
    private String imgNom;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;

    public AnomalieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnomalieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnomalieFragment newInstance(String param1, String param2) {
        AnomalieFragment fragment = new AnomalieFragment();
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
            imgUri = getArguments().getString(ARG_PARAM1);
            imgNom = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_fragment_anomalie);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_anomalie, container, false);
        ImageView photo = (ImageView) view.findViewById(R.id.previewAnomaly);

        Button retour = (Button) view.findViewById(R.id.anomaly_cancel);
        retour.setOnClickListener(this);
        Button envoyer = (Button) view.findViewById(R.id.anomaly_confirm);
        envoyer.setOnClickListener(this);
        Uri uri = Uri.parse(imgUri);
        photo.setImageURI(uri);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.anomaly_confirm:
                confirm();
                break;
            case R.id.anomaly_cancel:
                goBack();
                break;
        }
    }

    private void goBack() {
        Log.d("FIRE", "goback: BIEN RETOURNE");
        ((MainActivity)getActivity()).changeFragment(GeolocalisationFragment.newInstance("",""));
    }

    private void confirm() {
        Log.d("FIRE", "confirm: BIEN ENVOYE");
        RadioGroup level = (RadioGroup) this.getView().findViewById(R.id.radio_group);
        String gravite = (String)((RadioButton) this.getView().findViewById(level.getCheckedRadioButtonId())).getText();
        //TODO récupérer position utilisateur
        Uri uri = Uri.parse(imgUri);
        Anomalie anomalie = new Anomalie(imgNom, 1., 1., gravite);
        AnomalieDataSource sender = new AnomalieDataSource();
        sender.addNewAnomalie(uri, anomalie);
        goBack();
    }
}

package com.m2dl.mapus.mapus;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.m2dl.mapus.mapus.database.EventDataSource;
import com.m2dl.mapus.mapus.database.FormationDataSource;
import com.m2dl.mapus.mapus.edt.Classes;
import com.m2dl.mapus.mapus.edt.Formations;
import com.m2dl.mapus.mapus.edt.Timetable;
import com.m2dl.mapus.mapus.model.Formation;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private Spinner licenceFormationSpinner;
    private Spinner masterFormationSpinner;
    private Spinner groupSpinner;
    private FormationSpinAdapter licenceFormationSpinnerAdapter;
    private FormationSpinAdapter masterFormationSpinnerAdapter;
    private FormationSpinAdapter classesFormationSpinnerAdapter;

    private ArrayList<Formation> licenceFormations = new ArrayList<>();
    private ArrayList<Formation> masterFormations = new ArrayList<>();
    private ArrayList<Formation> classes = new ArrayList<>();
    private Formation selectedFormation;
    private ProgressDialog progressDialog;


    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_fragment_settings);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        licenceFormationSpinner = (Spinner) view.findViewById(R.id.edt_licence_formation_spinner);
        masterFormationSpinner = (Spinner) view.findViewById(R.id.edt_master_formation_spinner);
        groupSpinner = (Spinner) view.findViewById(R.id.edt_group_spinner);

        licenceFormationSpinner.setOnItemSelectedListener(new LicenceFormationSpinnerListener());
        masterFormationSpinner.setOnItemSelectedListener(new MasterFormationSpinnerListener());
        groupSpinner.setOnItemSelectedListener(new GroupSpinnerListener());

        Button refreshButton = (Button) view.findViewById(R.id.refresh_btn);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                refreshEDT();
            }
        });

        new Formations().execute(this);
    }

    public void setLicences(ArrayList<Formation> licences) {
        this.licenceFormations = licences;
        this.licenceFormations.add(0, new Formation("", getResources().getString(R.string.licence_placeholder)));

        licenceFormationSpinnerAdapter = new FormationSpinAdapter(getContext(), android.R.layout.simple_spinner_item, this.licenceFormations, false);
        licenceFormationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        licenceFormationSpinner.setAdapter(licenceFormationSpinnerAdapter);
    }

    public void setMasters(ArrayList<Formation> masters) {
        this.masterFormations = masters;
        this.masterFormations.add(0, new Formation("", getResources().getString(R.string.master_placeholder)));

        masterFormationSpinnerAdapter = new FormationSpinAdapter(getContext(), android.R.layout.simple_spinner_item, this.masterFormations, false);
        masterFormationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        masterFormationSpinner.setAdapter(masterFormationSpinnerAdapter);
    }

    public void setClasses(ArrayList<Formation> classes) {
        if (classes == null) {
            return;
        }
        this.classes = classes;
        this.classes.add(0, new Formation("", "", "", getResources().getString(R.string.groupe_placeholder)));

        classesFormationSpinnerAdapter = new FormationSpinAdapter(getContext(), android.R.layout.simple_spinner_item, this.classes, true);
        classesFormationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        groupSpinner.setAdapter(classesFormationSpinnerAdapter);
    }

    private void showGroupBlock() {
        groupSpinner.setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.group_label).setVisibility(View.VISIBLE);
    }

    private void hideGroupBlock() {
        groupSpinner.setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.group_label).setVisibility(View.INVISIBLE);
    }

    public void refreshEDT() {
        if (selectedFormation == null) {
            return;
        }

        progressDialog = ProgressDialog.show(getContext(), "Veuillez patienter", "Téléchargement de l'emploi du temps en cours");
        EventDataSource eventDataSource = new EventDataSource(getContext());
        new Timetable().execute(this, selectedFormation, eventDataSource);
    }

    public void edtIsDownload() {
        progressDialog.dismiss();
        ((MainActivity) getActivity()).edtIsDownload();
    }

    private class LicenceFormationSpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                if (masterFormationSpinner.getSelectedItemPosition() == 0) {
                    hideGroupBlock();
                }
                return;
            }
            masterFormationSpinner.setSelection(0);
            Formation formation = (Formation) parent.getItemAtPosition(position);
            Log.d("Debug", "onItemSelected: " + formation);
            new Classes().execute(SettingsFragment.this, formation);
            showGroupBlock();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class MasterFormationSpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                if (licenceFormationSpinner.getSelectedItemPosition() == 0) {
                    hideGroupBlock();
                }
                return;
            }
            licenceFormationSpinner.setSelection(0);
            Formation formation = (Formation) parent.getItemAtPosition(position);
            Log.d("Debug", "onItemSelected: " + formation);
            new Classes().execute(SettingsFragment.this, formation);
            showGroupBlock();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class GroupSpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                getActivity().findViewById(R.id.refresh_btn).setVisibility(View.INVISIBLE);
                selectedFormation = null;
                return;
            }
            getActivity().findViewById(R.id.refresh_btn).setVisibility(View.VISIBLE);
            selectedFormation = (Formation) parent.getItemAtPosition(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public class FormationSpinAdapter extends ArrayAdapter<Formation>{

        private final boolean isGroupe;
        private Context context;
        // Your custom values for the spinner (User)
        private ArrayList<Formation> values = new ArrayList<>();

        public FormationSpinAdapter(Context context, int textViewResourceId,
                                    ArrayList<Formation> values, boolean isGroupe) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
            this.isGroupe = isGroupe;
        }

        public void insert(@Nullable Formation formation) {
            this.values.add(formation);
        }

        public int getCount(){
            return values.size();
        }

        public Formation getItem(int position){
            return values.get(position);
        }

        public long getItemId(int position){
            return position;
        }


        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView label = new TextView(context);
            label.setTextAppearance(R.style.TextAppearance_AppCompat_Caption);
            label.setTextSize(20f);
            if (isGroupe) {
                label.setText(values.get(position).getGroupe());
            } else {
                label.setText(values.get(position).getFormation());
            }

            return label;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            TextView label = new TextView(context);
            label.setTextAppearance(R.style.TextAppearance_AppCompat_Caption);
            label.setPadding(10, 10, 10, 10);
            label.setTextSize(20f);
            if (isGroupe) {
                label.setText(values.get(position).getGroupe());
            } else {
                label.setText(values.get(position).getFormation());
            }

            return label;
        }
    }
}

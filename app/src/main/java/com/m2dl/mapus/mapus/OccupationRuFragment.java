package com.m2dl.mapus.mapus;


import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.m2dl.mapus.mapus.firebase.RUDataSource;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OccupationRuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class OccupationRuFragment extends Fragment {

    // Eléments graphiques
    private TextView ru1ValueTextView;
    private TextView ru2ValueTextView;
    private TextView ruDateTextView;
    private GraphView graph;

    // Attributs
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    private int peopleRu1;
    private int peopleRu2;
    private RUDataSource ruDataSource;


    public OccupationRuFragment() {
        // Required empty public constructor
    }

    public static OccupationRuFragment newInstance() {
        OccupationRuFragment fragment = new OccupationRuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ruDataSource = new RUDataSource(this);
    }


    public void addRUData(int nbPeopleRu1, int nbPeopleRu2) {
        refreshLastActualisationDate();
        setPeopleRu1(nbPeopleRu1);
        setPeopleRu2(nbPeopleRu2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_fragment_ru);
        return inflater.inflate(R.layout.fragment_occupation_ru, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initGraphView();
        initGraphData();
    }

    private void initGraphView() {
        this.graph = (GraphView) getActivity().findViewById(R.id.ru_graph);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"11h30", "12h", "12h30", "13h", "13h30", "14h"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(9);
        graph.getViewport().setMaxY(28);

        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
    }

    private void initGraphData() {
        // TODO Calculer les valeurs du graph en fonction des données Firebase
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0.5, 20),
                new DataPoint(1.5, 25),
                new DataPoint(2.5, 26),
                new DataPoint(3.5, 22),
                new DataPoint(4.5, 18),
                new DataPoint(5.5, 10),
        });

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                int red = (int) (255 - data.getY() * 102 / 26);
                return Color.rgb(red, 0, 0);
            }
        });

        series.setSpacing(5);
        series.setAnimated(true);

        graph.addSeries(series);
    }


    public void refreshLastActualisationDate() {
        if (ruDateTextView == null) {
            ruDateTextView = (TextView) getActivity().findViewById(R.id.ru_date);
        }
        ruDateTextView.setText("Dernière mise à jour à " + dateFormat.format(new Date()));
    }

    public void setPeopleRu1(int peopleRu1) {
        this.peopleRu1 = peopleRu1;

        if (ru1ValueTextView == null) {
            ru1ValueTextView = (TextView) getActivity().findViewById(R.id.ru1_value);
        }
        ru1ValueTextView.setText(Integer.toString(this.peopleRu1));
    }

    public void setPeopleRu2(int peopleRu2) {
        this.peopleRu2 = peopleRu2;

        if (ru2ValueTextView == null) {
            ru2ValueTextView = (TextView) getActivity().findViewById(R.id.ru2_value);
        }
        ru2ValueTextView.setText(Integer.toString(this.peopleRu2));
    }


}

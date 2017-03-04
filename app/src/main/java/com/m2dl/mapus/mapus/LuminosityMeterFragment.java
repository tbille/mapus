package com.m2dl.mapus.mapus;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LuminosityMeterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LuminosityMeterFragment extends Fragment implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mLight;

    private TextView luminosityTextview;

    public LuminosityMeterFragment() {
        // Required empty public constructor
    }

    public static LuminosityMeterFragment newInstance() {
        LuminosityMeterFragment fragment = new LuminosityMeterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_luminosity_meter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        luminosityTextview = (TextView) view.findViewById(R.id.sensors_luminosity_value);
    }

    @Override
    public void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int lightValue = (int) event.values[0];

        if (luminosityTextview == null) {
            luminosityTextview = (TextView) getActivity().findViewById(R.id.sensors_luminosity_value);
        }
        luminosityTextview.setText(Integer.toString(lightValue));

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

}

package com.m2dl.mapus.mapus;


import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SoundMeterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoundMeterFragment extends Fragment {

    final int REFRESH_SOUND_DURATION = 50;

    private SoundMeter soundMeter = new SoundMeter();

    private TextView soundTextview;
    private ProgressBar soundBar;


    private int maxSound = 0;

    private Handler mHandler = new Handler();

    public SoundMeterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SoundMeterFragment.
     */
    public static SoundMeterFragment newInstance() {
        SoundMeterFragment fragment = new SoundMeterFragment();
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

        mHandler = new Handler();
        mHandler.postDelayed(soundUpdater, REFRESH_SOUND_DURATION);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sound_meter, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        soundMeter.startRecorder();
    }

    @Override
    public void onPause() {
        super.onPause();
        soundMeter.stopRecorder();
    }


    final Runnable soundUpdater = new Runnable() {

        public void run() {
            updateSoundMeterDisplay();
            mHandler.postDelayed(this, REFRESH_SOUND_DURATION);
        }
    };

    public int getMaxSound() {
        return maxSound;
    }

    public void setMaxSound(int maxSound) {
        this.maxSound = maxSound;
        if (soundTextview == null) {
            soundTextview = (TextView) getActivity().findViewById(R.id.max_sound_text);
        }
        soundTextview.setText("Max. : " + this.maxSound + "dB");
        soundBar.setMax(this.maxSound);
    }


    public void updateSoundMeterDisplay() {
        int dbValue = ((int) soundMeter.soundDb(1));
        if (soundBar == null) {
            soundBar = (ProgressBar) getActivity().findViewById(R.id.sound_bar);
        }
        if (dbValue > maxSound) {
            setMaxSound(dbValue);
        }
        soundBar.setProgress(dbValue);
    }

}

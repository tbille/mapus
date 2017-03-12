package com.m2dl.mapus.mapus;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * Use the {@link QrCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QrCodeFragment extends Fragment {

    private boolean permissionCamera = false;
    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;

    public QrCodeFragment() {
        // Required empty public constructor
    }

    public static QrCodeFragment newInstance() {
        QrCodeFragment fragment = new QrCodeFragment();
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
        getActivity().setTitle(R.string.title_fragment_qrcode);
        View view=inflater.inflate(R.layout.fragment_qr_code, container, false);

        cameraView = (SurfaceView) view.findViewById(R.id.cameraView);
        cameraView.setZOrderMediaOverlay(true);
        holder = cameraView.getHolder();
        barcode = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        if(!barcode.isOperational()){
            Toast.makeText(getContext(), "Sorry, Couldn't setup the detector", Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
        cameraSource = new CameraSource.Builder(getActivity(), barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920,1024)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        cameraSource.start(cameraView.getHolder());
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (cameraSource != null) {
                    cameraSource.release();
                    cameraSource = null;
                }

            }
        });
        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes =  detections.getDetectedItems();
                if(barcodes.size() > 0){
                    if(barcodes.valueAt(0).valueFormat == Barcode.URL){
                        openIntent(barcodes.valueAt(0).displayValue);

                    }else{
                        if(barcodes.valueAt(0).displayValue.contains("mapus:"))
                            openDatabase(barcodes.valueAt(0).displayValue);
                    }

                }
            }
        });

        return view;
    }

    private void openDatabase(String target){
        target = target.substring(target.indexOf(":")+1);
        ((MainActivity)getActivity()).changeFragment(QrCodeResult.newInstance(target));
    }

    private void openIntent(String url){
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
        ((MainActivity)getActivity()).changeFragment(GeolocalisationFragment.newInstance("e", "e"));
    }
}

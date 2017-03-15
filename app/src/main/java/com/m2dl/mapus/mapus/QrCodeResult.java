package com.m2dl.mapus.mapus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Use the {@link QrCodeResult#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QrCodeResult extends Fragment implements View.OnClickListener {
    private static final String RESULT = "result";

    private String result;


    public QrCodeResult() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param result Resultat de la lecture du QR code.
     * @return A new instance of fragment QrCodeResult.
     */
    public static QrCodeResult newInstance(String result) {
        QrCodeResult fragment = new QrCodeResult();
        Bundle args = new Bundle();
        args.putString(RESULT, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            result = getArguments().getString(RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code_result, container, false);
        //((TextView)view.findViewById(R.id.Entree)).setText(this.resultatFirebase.getEntree());
        //((TextView)view.findViewById(R.id.Plat)).setText(this.resultatFirebase.getEntree());
        //((TextView)view.findViewById(R.id.Dessert)).setText(this.resultatFirebase.getEntree());
        ((TextView)view.findViewById(R.id.contenuQRCode)).setText(result);
        FloatingActionButton b = (FloatingActionButton) view.findViewById(R.id.goBack);
        b.setOnClickListener(this);
        return view;
    }

    public void goBack(){
        ((MainActivity)getActivity()).changeFragment(QrCodeFragment.newInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goBack:
                goBack();
                break;
        }
    }

}

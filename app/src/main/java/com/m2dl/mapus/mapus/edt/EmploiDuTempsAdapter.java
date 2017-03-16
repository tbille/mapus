package com.m2dl.mapus.mapus.edt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.m2dl.mapus.mapus.R;
import com.m2dl.mapus.mapus.model.Event;

import java.util.ArrayList;

/**
 * Created by Clement on 16/03/2017.
 */

public class EmploiDuTempsAdapter extends ArrayAdapter<Event> implements View.OnClickListener{

    private ArrayList<Event> ListeCours;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }

    public EmploiDuTempsAdapter(ArrayList<Event> data, Context context) {
        super(context, R.layout.emploi_du_temps_row, data);
        this.ListeCours = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Event dataModel=(Event)object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event cours = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        TextView coursName = new TextView(mContext);
        TextView salle  = new TextView(mContext);
        TextView startTime = new TextView(mContext);
        TextView endTime = new TextView(mContext);

        final View result;

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.emploi_du_temps_row, parent, false);
            coursName = (TextView) convertView.findViewById(R.id.matiere);
            salle = (TextView) convertView.findViewById(R.id.salle);
            startTime = (TextView) convertView.findViewById(R.id.start_date);
            endTime = (TextView) convertView.findViewById(R.id.end_date);

            result=convertView;

        } else {
            result=convertView;
        }
        lastPosition = position;

        coursName.setText(cours.getMatiere());
        salle.setText(cours.getSalle());
        startTime.setText(cours.getStartTime());
        endTime.setText(cours.getEndTime());
        // Return the completed view to render on screen
        return convertView;
    }
}
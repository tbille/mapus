package com.m2dl.mapus.mapus.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.m2dl.mapus.mapus.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Clement on 16/03/2017.
 */

public class DatePickerDialogFragment extends DialogFragment{

        public interface DatePickerDialogListener {
            public void onDialogPositiveClick(DatePickerDialogFragment dialog);
        }

    // Use this instance of the interface to deliver action events
    DatePickerDialogListener mListener;

    DatePicker datePicker;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DatePickerDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement DatePickerDialogFragment");
        }
    }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            View view = inflater.inflate(R.layout.dialog_calendar, null);
            datePicker = (DatePicker) view.findViewById(R.id.datePicker);

            builder.setView(view)
                    // Add action buttons
                    .setPositiveButton(R.string.ok_calendar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            mListener.onDialogPositiveClick(DatePickerDialogFragment.this);
                        }
                    })
                    .setNegativeButton(R.string.back_calendar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DatePickerDialogFragment.this.getDialog().cancel();
                        }
                    });
            return builder.create();
        }

    public Date getDate(){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}

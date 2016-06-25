package com.codepath.nytimessearch1.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by ramyarao on 6/21/16.
 */
public class DatePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //get SearchActivity's default date
        final Calendar c = Calendar.getInstance();
        SearchActivity searchActivity = (SearchActivity) getActivity();
        String dateString = searchActivity.dateString;
        int year;
        int month;
        int day;
        if (dateString != null && dateString != "" && dateString.length() > 4) {

            String defaultYear = "";
            for (int i = 0; i < 4; i++) {
                defaultYear += dateString.charAt(i);
            }

            String defaultMonth = "";
            for (int i = 4; i < 6; i++) {
                defaultMonth += dateString.charAt(i);
            }

            String defaultDay = "";
            for (int i = 6; i < 8; i++) {
                defaultDay += dateString.charAt(i);
            }
            // Use the current time as the default values for the picker
            //final Calendar c = Calendar.getInstance();
            year = Integer.parseInt(defaultYear);
            month = Integer.parseInt(defaultMonth) - 1;
            day = Integer.parseInt(defaultDay);
        } else {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        // Activity needs to implement this interface
        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getActivity();

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }
}
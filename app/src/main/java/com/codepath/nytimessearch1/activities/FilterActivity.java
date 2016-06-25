package com.codepath.nytimessearch1.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.codepath.nytimessearch1.R;

import java.util.Calendar;

public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String beginDay;
    String beginMonth;
    String dateString1;
    private final int REQUEST_CODE = 20;
    boolean fashion;
    boolean arts;
    boolean sports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        String [] values =
                {"newest", "oldest",};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if (dayOfMonth <= 10) {
            beginDay = "0" + (1 + dayOfMonth);
        }
        else {
            beginDay = "" + dayOfMonth;
        }
        if (monthOfYear <= 10) {
            beginMonth = "0" + (1 + monthOfYear);
        }
        else {
            beginMonth = "" + monthOfYear;
        }
        dateString1 = year + beginMonth + beginDay;
    }

    public void onSubmit(View v) {
        //fashion checkbox
        fashion = ((CheckBox) findViewById(R.id.cbFashion)).isChecked();
        arts = ((CheckBox) findViewById(R.id.cbArts)).isChecked();
        sports = ((CheckBox) findViewById(R.id.cbSports)).isChecked();
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        if (dateString1 != null) {
            data.putExtra("dateString", dateString1);
        }
        else {
            data.putExtra("dateString", "HI");
        }
        //data for checkboxes, news_desk categories
        data.putExtra("fashion", fashion);
        data.putExtra("sports", sports);
        data.putExtra("arts", arts);


        Spinner mySpinner=(Spinner) findViewById(R.id.spinner);
        String text = mySpinner.getSelectedItem().toString();
        //if ()
        //Log.d("date", dateString1);
        data.putExtra("sort", text);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}

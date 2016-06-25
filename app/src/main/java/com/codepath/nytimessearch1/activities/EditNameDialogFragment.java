package com.codepath.nytimessearch1.activities;

/**
 * Created by ramyarao on 6/21/16.
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.nytimessearch1.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

// ...
// ...

public class EditNameDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    //private EditText mEditText;
    //public boolean fashion;
    //@BindView(R.id.button1)
    @BindView(R.id.spinner) Spinner spinner;

    @BindView(R.id.cbFashion) CheckBox cbFashion;
    @BindView(R.id.cbArts) CheckBox cbArts;
    @BindView(R.id.cbSports) CheckBox cbSports;

    boolean fashion;
    boolean arts;
    boolean sports;

    String[] valsOfOrder;

    String beginDay;
    String beginMonth;
    String dateString;
    private final int REQUEST_CODE = 20;

    public EditNameDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }
    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText, String inputText2, boolean fshn,
                                boolean arts, boolean sports);
    }






    public static EditNameDialogFragment newInstance(String title) {
        EditNameDialogFragment frag = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;

    }

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }



//    public void dismiss(View view) {
//        dialog.dismiss();
//    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_name, container);
        ButterKnife.bind(this, v);
        String [] values =
                {"newest", "oldest",};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        return v;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view

        SearchActivity searchActivity = (SearchActivity) getActivity();
        cbFashion.setChecked(searchActivity.fashion);
        cbArts.setChecked(searchActivity.arts);
        cbSports.setChecked(searchActivity.sports);
        ArrayList<String> vals = new ArrayList<>();
        vals.add("newest");
        vals.add("oldest");
        spinner.setSelection ( vals.indexOf(searchActivity.sort));
        //mEditText.setOnEditorActionListener(this);

//        mEditText.setOnEditorActionListener(this);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        //mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void sendData(View view) {
//        MainActivity.dismiss();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        EditNameDialogListener listener = (EditNameDialogListener) getActivity();
        fashion = cbFashion.isChecked();
        sports = cbSports.isChecked();
        arts = cbArts.isChecked();


        String text = spinner.getSelectedItem().toString();
//        listener.onFinishEditDialog(mEditText.getText().toString(), text, fashion, arts, sports);
        listener.onFinishEditDialog("sup", text, fashion, arts, sports);
        super.onDismiss(dialog);
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Toast.makeText(getContext(), "HI", Toast.LENGTH_LONG).show();
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            EditNameDialogListener listener = (EditNameDialogListener) getActivity();
            //listener.onFinishEditDialog(mEditText.getText().toString());

            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }



}

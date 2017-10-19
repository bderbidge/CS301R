package com.example.brandonderbidge.myapplication.sell;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.brandonderbidge.myapplication.R;
import com.example.brandonderbidge.myapplication.main.MainController;
import com.example.brandonderbidge.myapplication.model.Contract;
import com.example.brandonderbidge.myapplication.model.Model;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by justinbrunner on 10/16/17.
 */

public class NewContractFragment extends Fragment {
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Apartments");

    private String TAG = "NewContract";
    private MainController mainController;
    Spinner maritalStatusSpinner;
    Spinner sexSpinner;
    Button addImage;
    EditText sellBy;
    EditText dateAvailable;
    Button saveButton;
    boolean isSellByDateFrag;

    private static int RESULT_LOAD_IMAGE = 1;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_contract, container, false);
        setHasOptionsMenu(true);
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        view.setBackgroundColor(Color.WHITE);

        maritalStatusSpinner = view.findViewById(R.id.marital_status_spinner);
        sexSpinner = view.findViewById(R.id.sex_spinner);
        addImage = view.findViewById(R.id.add_image);
        sellBy = view.findViewById(R.id.sell_by);
        dateAvailable = view.findViewById(R.id.date_available);
        saveButton = view.findViewById(R.id.save_bttn);

        ArrayAdapter<CharSequence> maritalAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.marital_status_list, R.layout.spinner_item);
        maritalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maritalStatusSpinner.setAdapter(maritalAdapter);

        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sex_list, R.layout.spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(sexAdapter);

        maritalStatusSpinner.setSelection(1);

        sellBy.setShowSoftInputOnFocus(false);
        dateAvailable.setShowSoftInputOnFocus(false);

        sellBy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    closeKeyboard();
                    isSellByDateFrag = true;
                    showDateDialog(getString(R.string.sell_by));
                }
            }
        });

        dateAvailable.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    closeKeyboard();
                    isSellByDateFrag = false;
                    showDateDialog(getString(R.string.date_available));
                }
            }
        });

        maritalStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (maritalStatusSpinner.getSelectedItem().toString().equals("Single")) {
                    sexSpinner.setEnabled(true);
                } else {
                    sexSpinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Contract contract = new Contract("a;sldfjk", "Centenial", "Brandon Derbidge",
                        "1000N", 1, -1, "12/1/2017", "Provo", "UT", 91205, 200.00,  "Single",
                        "male","I need to sell!", "8018575056", "brander81@gmail.com");
                Model.instance().getIdToContracts().put("1_key", contract);
                myRef.setValue(Model.instance().getIdToContracts());
            }
        });

        getActivity().setTitle(R.string.new_contract);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false);
        }

        Fragment newContractFragment = getFragmentManager().findFragmentByTag(getString(R.string.TAG_datedialog));

        if (newContractFragment != null) {
            getFragmentManager().beginTransaction().remove(newContractFragment).commit();
        }

        getActivity().setTitle(R.string.sell_contracts);

        getFragmentManager().popBackStack();
    }

    public void showDateDialog(String dateField) {

        if (getActivity().findViewById(R.id.datepicker_dialog_container) == null) {
            FragmentManager fragmentManager = getFragmentManager();
            DatePickerDialog datePickerDialog = new DatePickerDialog();

            datePickerDialog.setMainController(mainController);
            datePickerDialog.setFieldHint(dateField);

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(android.R.id.content, datePickerDialog, getString(R.string.TAG_datedialog))
                    .commit();
        }
    }

    public void setDate(String date) {
        if (isSellByDateFrag) {
            sellBy.setText(date);
        } else {
            dateAvailable.setText(date);
        }
    }

    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus() == null ? null : getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

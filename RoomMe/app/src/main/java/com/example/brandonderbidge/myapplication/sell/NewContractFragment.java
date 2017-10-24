package com.example.brandonderbidge.myapplication.sell;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import com.google.firebase.database.Query;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by justinbrunner on 10/16/17.
 */

public class NewContractFragment extends Fragment {
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Apartments");
    DatabaseReference myUser = database.getReference("Users");

    private String TAG = "NewContract";
    private MainController mainController;
    private Spinner maritalStatusSpinner;
    private Spinner sexSpinner;
    private Button addImage;
    private EditText sellBy;
    private EditText dateAvailable;
    private Button saveButton;
    private boolean isSellByDateFrag;
    private EditText apartmentName;
    private EditText apartmentNumber;
    private EditText address;
    private EditText address2;
    private EditText city;
    private EditText state;
    private EditText postal;
    private EditText price;
    private EditText additionalInfo;

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
        saveButton = view.findViewById(R.id.create_contract_btn);
        apartmentName = view.findViewById(R.id.apartment_name);
        apartmentNumber = view.findViewById(R.id.apartment_number);
        address = view.findViewById(R.id.address_line_1);
        address2 = view.findViewById(R.id.address_line_2);
        city = view.findViewById(R.id.city);
        state = view.findViewById(R.id.state);
        postal = view.findViewById(R.id.postal_code);
        price = view.findViewById(R.id.price);
        additionalInfo = view.findViewById(R.id.additional_info);

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


            String temp = price.getText().toString();
            int price1 = Integer.parseInt(temp);
            double price2 = (double)price1;
                String ID = UUID.randomUUID().toString();
                Contract contract = new Contract(ID, apartmentName.getText().toString(),
                        Model.instance().getCurrentUser().getFirstName() + " " + Model.instance().getCurrentUser().getLastName(),
                        address.getText().toString() + address2.getText().toString(), apartmentNumber.getText().toString(), -1, sellBy.getText().toString(),
                        city.getText().toString(), state.getText().toString(), postal.getText().toString(), price2
                        , maritalStatusSpinner.getSelectedItem().toString(),
                        sexSpinner.getSelectedItem().toString(),additionalInfo.getText().toString(), Model.instance().getCurrentUser().getPhoneNumber(),
                        Model.instance().getCurrentUser().getEmail());

                Model.instance().getAllContracts().put(ID, contract);
                Model.instance().getCurrentUser().getMyContractsToSell().add(contract);

                myRef.setValue(Model.instance().getAllContracts());
                myUser.child(Model.instance().getCurrentUser().getID()).setValue(Model.instance().getCurrentUser());

                if(getActivity().getSupportFragmentManager().findFragmentByTag(getString(R.string.TAG_sell)) != null)
                    ((SellFragment) getActivity().getSupportFragmentManager().findFragmentByTag(getString(R.string.TAG_sell))).loadSellContracts();
                getFragmentManager().popBackStack();


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

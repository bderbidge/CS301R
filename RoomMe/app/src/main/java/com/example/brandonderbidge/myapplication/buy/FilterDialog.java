package com.example.brandonderbidge.myapplication.buy;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.FilterModel;
import com.example.brandonderbidge.myapplication.R;

/**
 * Created by justinbrunner on 10/12/17.
 */

public class FilterDialog extends DialogFragment {
    ConstraintLayout filterLayout;
    ImageButton closeBtn;
    EditText priceLow;
    EditText priceHigh;
    Button maleBtn;
    Button femaleBtn;
    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_buy, container, false);

        filterLayout = view.findViewById(R.id.filter_layout);
        closeBtn = view.findViewById(R.id.close_btn);
        priceLow = view.findViewById(R.id.price_low);
        priceHigh = view.findViewById(R.id.price_high);
        maleBtn = view.findViewById(R.id.male_btn);
        femaleBtn = view.findViewById(R.id.female_btn);

        filterLayout.setBackground(new ColorDrawable(Color.WHITE));

        String priceLowText = "";
        priceLowText = String.format(priceLowText, FilterModel.getInstance().getPriceLow());
        priceLow.setText(priceLowText);

        String priceHighText = "";
        priceHighText = String.format(priceHighText, FilterModel.getInstance().getPriceLow());
        priceLow.setText(priceHighText);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterModel.getInstance().setSex("M");
                maleBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.White));
                femaleBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.greyedText));

                maleBtn.setBackground(getResources().getDrawable(R.drawable.toggle_button_left_clicked, null));
                femaleBtn.setBackground(getResources().getDrawable(R.drawable.toggle_button_right, null));

                ((BuyActivity) getActivity()).loadFakeData();
            }
        });

        femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterModel.getInstance().setSex("F");
                femaleBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.White));
                maleBtn.setTextColor(ContextCompat.getColor(getContext(), R.color.greyedText));

                femaleBtn.setBackground(getResources().getDrawable(R.drawable.toggle_button_right_clicked, null));
                maleBtn.setBackground(getResources().getDrawable(R.drawable.toggle_button_left, null));

                ((BuyActivity) getActivity()).loadFakeData();
            }
        });

        priceLow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatePriceLow();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        priceHigh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatePriceHigh();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return view;
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.close_btn) {
            dismiss();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updatePriceLow() {
        String priceLowText = (priceLow.getText().toString().equals("")) ? "0" : priceLow.getText().toString();
        FilterModel.getInstance().setPriceHigh(Double.parseDouble(priceLowText));

        ((BuyActivity) getActivity()).loadFakeData();
    }

    private void updatePriceHigh() {
        String priceHighText = (priceHigh.getText().toString().equals("")) ? "100000" : priceHigh.getText().toString();
        FilterModel.getInstance().setPriceHigh(Double.parseDouble(priceHighText));

        ((BuyActivity) getActivity()).loadFakeData();
    }
}
package com.example.brandonderbidge.myapplication.sell;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.R;
import com.example.brandonderbidge.myapplication.buy.BuyFragment;
import com.example.brandonderbidge.myapplication.buy.FilterDialog;
import com.example.brandonderbidge.myapplication.main.MainController;

/**
 * Created by justinbrunner on 10/12/17.
 */

public class DatePickerDialog extends DialogFragment {
    private String TAG = "DatePickerDialog";
    private DatePicker datePicker;
    private MainController mainController;
    private Button cancelBtn;
    private Button okBtn;
    private TextView dateField;
    private String fieldHint;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setFieldHint(String fieldHint) {
        this.fieldHint = fieldHint;
    }

    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_datepicker, container, false);

        view.setBackgroundColor(getResources().getColor(R.color.White));

        datePicker = view.findViewById(R.id.date_picker);
        okBtn = view.findViewById(R.id.ok_datepicker_btn);
        cancelBtn = view.findViewById(R.id.cancel_datepicker_btn);
        dateField = view.findViewById(R.id.date_field);

        dateField.setText(fieldHint);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = (datePicker.getMonth() + 1) + "/" + datePicker.getDayOfMonth() + "/" + datePicker.getYear();

                if (getActivity().getSupportFragmentManager().findFragmentByTag(getString(R.string.TAG_new_contract)) != null) {
                    ((NewContractFragment) getActivity().getSupportFragmentManager().findFragmentByTag(getString(R.string.TAG_new_contract)))
                            .setDate(date);
                }else if (getActivity().getSupportFragmentManager().findFragmentByTag(getString(R.string.TAG_buy)) != null) {
                    ((FilterDialog) getActivity().getSupportFragmentManager().findFragmentByTag(getString(R.string.TAG_filter)))
                            .setDateAvailableFilter(date);
                }
                dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
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

    private void changeBtnStyle(Button btn, int drawable, int  textColor) {
        btn.setTextColor(ContextCompat.getColor(getContext(), textColor));
        btn.setBackground(getResources().getDrawable(drawable, null));
    }
}
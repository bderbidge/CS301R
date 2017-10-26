package com.example.brandonderbidge.myapplication.buy;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.brandonderbidge.myapplication.model.FilterModel;
import com.example.brandonderbidge.myapplication.R;
import com.example.brandonderbidge.myapplication.sell.DatePickerDialog;

/**
 * Created by justinbrunner on 10/12/17.
 */

public class FilterDialog extends DialogFragment {
    private String TAG = "FilterDialog";
    private ConstraintLayout filterLayout;
    private ImageButton closeBtn;
    private EditText priceLow;
    private EditText priceHigh;
    private Button maleBtn;
    private Button femaleBtn;
    private Button singleBtn;
    private Button marriedBtn;
    private EditText availableBy;
    private LinearLayout selectSexContainer;
    private Button clearBtn;
    private Button applyBtn;

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
        singleBtn = view.findViewById(R.id.single);
        marriedBtn = view.findViewById(R.id.married);
        availableBy = view.findViewById(R.id.available_by);
        selectSexContainer = view.findViewById(R.id.select_sex_container);
        clearBtn = view.findViewById(R.id.clear_filter);
        applyBtn = view.findViewById(R.id.apply_filter);

        filterLayout.setBackground(new ColorDrawable(Color.WHITE));

        final String priceLowText = FilterModel.getInstance().getPriceLow() == null ? "" : Double.toString(FilterModel.getInstance().getPriceLow());
        priceLow.setText(priceLowText);

        String priceHighText = FilterModel.getInstance().getPriceHigh() == null ? "" : Double.toString(FilterModel.getInstance().getPriceHigh());
        priceHigh.setText(priceHighText);

        String availableByText = FilterModel.getInstance().getAvailableBy();
        availableBy.setText(availableByText);

        selectSexContainer.setVisibility(View.GONE);
        availableBy.setShowSoftInputOnFocus(false);

        availableBy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    closeKeyboard();
                    showDateDialog(getString(R.string.available_by));
                }
            }
        });
        availableBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                showDateDialog(getString(R.string.available_by));
            }
        });

        if (FilterModel.getInstance().getMaritalStatus() != null) {
            if (FilterModel.getInstance().getMaritalStatus().equalsIgnoreCase("single")) {
                changeBtnStyle(singleBtn, R.drawable.toggle_button_left_clicked, R.color.White);
                selectSexContainer.setVisibility(View.VISIBLE);
            } else if (FilterModel.getInstance().getMaritalStatus().equalsIgnoreCase("married")) {
                changeBtnStyle(marriedBtn, R.drawable.toggle_button_left_clicked, R.color.White);
            }

            if (FilterModel.getInstance().getSex() != null) {
                if (FilterModel.getInstance().getSex().equalsIgnoreCase("male")) {
                    changeBtnStyle(maleBtn, R.drawable.toggle_button_left_clicked, R.color.White);
                } else if (FilterModel.getInstance().getSex().equalsIgnoreCase("female")) {
                    changeBtnStyle(femaleBtn, R.drawable.toggle_button_left_clicked, R.color.White);
                }
            }
        }

        singleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FilterModel.getInstance().getMaritalStatus() != null
                        && FilterModel.getInstance().getMaritalStatus().equalsIgnoreCase("single")) {
                    FilterModel.getInstance().setMaritalStatus(null);
                    changeBtnStyle(singleBtn, R.drawable.toggle_button_left, R.color.greyedText);
                    selectSexContainer.setVisibility(View.GONE);
                } else {
                    FilterModel.getInstance().setMaritalStatus("single");
                    changeBtnStyle(singleBtn, R.drawable.toggle_button_left_clicked, R.color.White);
                    changeBtnStyle(marriedBtn, R.drawable.toggle_button_right, R.color.greyedText);
                    selectSexContainer.setVisibility(View.VISIBLE);
                }

                if (FilterModel.getInstance().getSex() != null) {
                    if (FilterModel.getInstance().getSex().equalsIgnoreCase("male")) {
                        changeBtnStyle(maleBtn, R.drawable.toggle_button_left_clicked, R.color.White);
                        changeBtnStyle(femaleBtn, R.drawable.toggle_button_right, R.color.greyedText);

                    } else if (FilterModel.getInstance().getSex().equalsIgnoreCase("female")) {
                        changeBtnStyle(femaleBtn, R.drawable.toggle_button_right_clicked, R.color.White);
                        changeBtnStyle(maleBtn, R.drawable.toggle_button_left, R.color.greyedText);
                    }
                }
            }
        });

        marriedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FilterModel.getInstance().getMaritalStatus() != null
                        && FilterModel.getInstance().getMaritalStatus().equalsIgnoreCase("married")) {
                    FilterModel.getInstance().setMaritalStatus(null);
                    changeBtnStyle(marriedBtn, R.drawable.toggle_button_right, R.color.greyedText);
                } else {
                    FilterModel.getInstance().setMaritalStatus("married");
                    changeBtnStyle(marriedBtn, R.drawable.toggle_button_right_clicked, R.color.White);
                    changeBtnStyle(singleBtn, R.drawable.toggle_button_left, R.color.greyedText);
                    selectSexContainer.setVisibility(View.GONE);

                }
            }
        });

        maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FilterModel.getInstance().getSex() != null && FilterModel.getInstance().getSex().equalsIgnoreCase("male")) {
                    FilterModel.getInstance().setSex(null);
                    changeBtnStyle(maleBtn, R.drawable.toggle_button_left, R.color.greyedText);
                } else {
                    FilterModel.getInstance().setSex("male");
                    changeBtnStyle(maleBtn, R.drawable.toggle_button_left_clicked, R.color.White);
                    changeBtnStyle(femaleBtn, R.drawable.toggle_button_right, R.color.greyedText);
                }
            }
        });

        femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FilterModel.getInstance().getSex() != null && FilterModel.getInstance().getSex().equalsIgnoreCase("female")) {
                    FilterModel.getInstance().setSex(null);
                    changeBtnStyle(femaleBtn, R.drawable.toggle_button_right, R.color.greyedText);
                } else {
                    FilterModel.getInstance().setSex("female");
                    changeBtnStyle(femaleBtn, R.drawable.toggle_button_right_clicked, R.color.White);
                    changeBtnStyle(maleBtn, R.drawable.toggle_button_left, R.color.greyedText);
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFilters();
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Applying Filter");
                Double priceLowText = (priceLow.getText().toString().equals("")) ? null : Double.parseDouble(priceLow.getText().toString());
                FilterModel.getInstance().setPriceLow(priceLowText);

                Double priceHighText = (priceHigh.getText().toString().equals("")) ? null : Double.parseDouble(priceHigh.getText().toString());
                FilterModel.getInstance().setPriceHigh(priceHighText);

                FilterModel.getInstance().setPrevMaritalStatus(FilterModel.getInstance().getMaritalStatus());
                FilterModel.getInstance().setPrevSex(FilterModel.getInstance().getSex());

                FilterModel.getInstance().setAvailableBy(availableBy.getText().toString());

                if ((getActivity().getSupportFragmentManager()).findFragmentByTag(getString(R.string.TAG_buy)) != null) {
                    ((BuyFragment) (getActivity().getSupportFragmentManager()).findFragmentByTag(getString(R.string.TAG_buy))).loadContracts();
                } else {
                    Log.e(TAG, "Could not find Buy Fragment");
                    Toast.makeText(getContext(), "There was an error applying your filter settings. Please contect support", Toast.LENGTH_LONG).show();
                }

                dismiss();
            }
        });



        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Dismissing Dialog");
                FilterModel.getInstance().setMaritalStatus(FilterModel.getInstance().getPrevMaritalStatus());
                FilterModel.getInstance().setSex(FilterModel.getInstance().getPrevSex());

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

    public void setDateAvailableFilter(String date) {
        availableBy.setText(date);
    }

    public void showDateDialog(String dateField) {

        if (getActivity().findViewById(R.id.datepicker_dialog_container) == null) {
            FragmentManager fragmentManager = getFragmentManager();
            DatePickerDialog datePickerDialog = new DatePickerDialog();

            datePickerDialog.setFieldHint(dateField);

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.add(android.R.id.content, datePickerDialog, getString(R.string.TAG_datedialog))
                    .commit();
        }
    }

    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus() == null ? null : getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void clearFilters() {
        priceHigh.setText("");
        priceLow.setText("");
        changeBtnStyle(singleBtn, R.drawable.toggle_button_left, R.color.greyedText);
        changeBtnStyle(marriedBtn, R.drawable.toggle_button_right, R.color.greyedText);
        selectSexContainer.setVisibility(View.GONE);
        availableBy.setText("");

        FilterModel.getInstance().clear();

        if ((getActivity().getSupportFragmentManager()).findFragmentByTag(getString(R.string.TAG_buy)) != null) {
            ((BuyFragment) (getActivity().getSupportFragmentManager()).findFragmentByTag(getString(R.string.TAG_buy))).loadContracts();
        } else {
            Log.e(TAG, "Could not find Buy Fragment");
            Toast.makeText(getContext(), "There was an error clearing your filter settings. Please contect support", Toast.LENGTH_LONG).show();
        }
    }
}
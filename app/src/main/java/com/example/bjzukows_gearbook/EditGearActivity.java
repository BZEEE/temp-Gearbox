package com.example.bjzukows_gearbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class EditGearActivity extends AppCompatActivity {
    private Button backButton;
    private Button addGearButton;
    private Button updateGearButton;
    private TextView gearTitle;
    private EditText gearMakerEditText;
    private EditText gearDateEditText;
    private EditText gearPriceEditText;
    private EditText gearDescriptionEditText;
    private EditText gearCommentEditText;
    private TextView gearMakerErrorBox;
    private TextView gearDateErrorBox;
    private TextView gearPriceErrorBox;
    private TextView gearDescriptionErrorBox;
    private TextView gearCommentErrorBox;
    private int gearIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gear);

        Intent intent = getIntent();
        // reference specific gear item that was seletced
        gearIndex = intent.getIntExtra(MainActivity.SELECTED_GEAR_TAG, 0);
        // get whether we are in 'ADD' or 'EDIT' mode
        String mode = intent.getStringExtra(MainActivity.ADD_EDIT_GEAR_MODE);

        // initialize componenents
        backButton = findViewById(R.id.backButton);
        addGearButton = findViewById(R.id.addGearButton);
        updateGearButton = findViewById(R.id.updateGearButton);
        gearTitle = findViewById(R.id.gearTitle);
        gearMakerEditText = findViewById(R.id.gearMakerEditText);
        gearDateEditText = findViewById(R.id.gearDateEditText);
        gearPriceEditText = findViewById(R.id.gearPriceEditText);
        gearDescriptionEditText = findViewById(R.id.gearDescriptionEditText);
        gearCommentEditText = findViewById(R.id.gearCommentEditText);
        gearMakerErrorBox = findViewById(R.id.gearMakerErrorBox);
        gearDateErrorBox = findViewById(R.id.gearDateErrorBox);
        gearPriceErrorBox = findViewById(R.id.gearPriceErrorBox);
        gearDescriptionErrorBox = findViewById(R.id.gearDescriptionErrorBox);
        gearCommentErrorBox = findViewById(R.id.gearCommentErrorBox);
        this.createOnClickListeners();

        if (mode.equals("EDIT")) {
            // fill the values with the Gear object since we are editing an existing item
            Gear gear = GearStore.getInstance().getSpecificGearItem(gearIndex);
            gearTitle.setText("Gear Item #" + Integer.toString(gearIndex + 1));
            gearMakerEditText.setText(gear.getGearMaker());
            gearDateEditText.setText(gear.getGearDate());
            gearPriceEditText.setText( Double.toString(gear.getGearPrice()));
            gearDescriptionEditText.setText(gear.getGearDescription());
            gearCommentEditText.setText(gear.getGearComment());
            addGearButton.setVisibility(View.GONE);
        } else {
            // we are adding a gear object so leave all the fields blank initially
            gearTitle.setText("Gear Item #" + Integer.toString(GearStore.getInstance().gearCount() + 1));
            gearMakerEditText.setText("");
            gearDateEditText.setText("");
            gearPriceEditText.setText("");
            gearDescriptionEditText.setText("");
            gearCommentEditText.setText("");
            updateGearButton.setVisibility(View.GONE);
        }
    }

    private void createOnClickListeners() {
        // attach event listeners
        addGearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (infoValidated()) {
                    Gear gear = new Gear(
                            gearDateEditText.getText().toString(),
                            gearMakerEditText.getText().toString(),
                            gearDescriptionEditText.getText().toString(),
                            Float.parseFloat(gearPriceEditText.getText().toString()),
                            gearCommentEditText.getText().toString()
                    );
                    // ensure info was validated, if successful add to gear store
                    GearStore.getInstance().addGear(gear);
                    // destroy current activity
                    finish();
                }
            }
        });
        updateGearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (infoValidated()) {
                    Gear gear = new Gear(
                            gearDateEditText.getText().toString(),
                            gearMakerEditText.getText().toString(),
                            gearDescriptionEditText.getText().toString(),
                            Float.parseFloat(gearPriceEditText.getText().toString()),
                            gearCommentEditText.getText().toString()
                    );
                    // ensure info was validated, if successful update existing gear item in store
                    GearStore.getInstance().updateGear(gearIndex, gear);
                    // destroy current activity
                    finish();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gearDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get current calender date
                // ensure we always have correct format by using android date picker widget
                // set the current date as the default date
                Calendar c = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditGearActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                gearDateEditText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    private boolean infoValidated() {
        // info and validation checks for all form fields
        boolean t1 = notEmpty(gearDateEditText, gearDateErrorBox);
        boolean t2 = notEmpty(gearMakerEditText, gearMakerErrorBox);
        boolean t3 = notEmpty(gearDescriptionEditText, gearDescriptionErrorBox);
        boolean t4 = notEmpty(gearPriceEditText, gearPriceErrorBox);
        boolean t5 = belowMaxLength(gearMakerEditText, 20, gearMakerErrorBox);
        boolean t6 = belowMaxLength(gearDescriptionEditText, 40, gearDescriptionErrorBox);
        boolean t7 = belowMaxLength(gearCommentEditText, 20, gearCommentErrorBox);
        boolean t8 = isNumber(gearPriceEditText, gearPriceErrorBox);
        // successfull if all tests pass
        return t1 && t2 && t3 && t4 && t5 && t6 && t7 && t8;
    }

    private boolean isNumber(EditText box, TextView errorBox) {
        String priceRegex = "^[0-9]+(\\.[0-9]+)?$";
        boolean test = box.getText().toString().matches(priceRegex);
        if (!test) {
            // if empty, set error message to warn the user
            errorBox.setText("field must be a number");
        }
        return test;
    }

    private boolean notEmpty(EditText box, TextView errorBox) {
        // check to see if form field is empty
        boolean test = box.getText().toString().trim().length() != 0;
        if (!test) {
            // if empty, set error message to warn the user
            errorBox.setText("field is required");
        }
        return test;
    }

    private boolean belowMaxLength(EditText box, int length, TextView errorBox) {
        // check to see if form field is greater than the allowed max length
        boolean test = box.getText().toString().trim().length() <= length;
        if (!test) {
            // if greater than max length, set error message to warn the user
            errorBox.setText("field must be max " + Integer.toString(length) + " characters");
        }
        return test;
    }
}
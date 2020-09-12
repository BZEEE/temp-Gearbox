package com.example.bjzukows_gearbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;

public class ViewGearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gear);
        Intent intent = getIntent();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        // receieve the incoming intent, and the Gear index that was select to be viewed
        int gearNumber = intent.getIntExtra(MainActivity.SELECTED_GEAR_TAG, 0);
        // get instance of singleton GearStore
        Gear gear = GearStore.getInstance().getSpecificGearItem(gearNumber);

        // get reference to all view objects that need to have set values
        TextView title = (TextView) findViewById(R.id.gearTitle);
        TextView maker = (TextView) findViewById(R.id.gearMakerTextView);
        TextView date = (TextView) findViewById(R.id.gearDateTextView);
        TextView price = (TextView) findViewById(R.id.gearPriceTextView);
        TextView description = (TextView) findViewById(R.id.gearDescriptionTextView);
        TextView comment = (TextView) findViewById(R.id.gearCommentTextView);
        Button backButton = (Button) findViewById(R.id.backButton);

        // set values of views and any event listeners
        title.setText("Gear Item #" + Integer.toString(gearNumber + 1));
        maker.setText("Maker: " + gear.getGearMaker());
        date.setText("Date: " + gear.getGearDate());
        price.setText("Price: " + formatter.format(gear.getGearPrice()));
        description.setText("Description: " + gear.getGearDescription());
        comment.setText("Comment: " + gear.getGearComment());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
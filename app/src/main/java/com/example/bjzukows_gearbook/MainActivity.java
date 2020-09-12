package com.example.bjzukows_gearbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private int selectedIndex;
    public static final String SELECTED_GEAR_TAG = "com.example.myfirstapp.SELECTED_GEAR_TAG";
    public static final String ADD_EDIT_GEAR_MODE = "com.example.myfirstapp.ADD_EDIT_GEAR_MODE";
    private GearArrayAdapter gearAdapter;
    private TextView gearTotalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ListView is the widget that will hold our gear items
        ListView gearListView = findViewById(R.id.gearListView);
        gearTotalTextView = findViewById(R.id.gearTotalTextView);
        // adapter maps a XML template to each specific object in an array that we treat as a data source for the list of gear
        gearAdapter = new GearArrayAdapter(this,
                R.layout.gear_list_view, GearStore.getInstance().getGearList());
        gearListView.setAdapter(gearAdapter);

        // set a listener for the gear that is selected
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create pop up menu displaying actions available
                selectedIndex = position;
                showGearOptionsMenu(view);

            }
        };
        gearListView.setOnItemClickListener(listener);

        // set on click listener for add gear button
        Button addGearButton = (Button) findViewById(R.id.addGearButton);
        addGearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddGearActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // on return to the main activity, we should inform the view (ListView) that the data has changed
        // force it to refresh
        refreshList();
        // recalculate total of all gear, format as currency
        refreshGearTotal();
    }

    private void refreshGearTotal() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        gearTotalTextView.setText("Gear Total: " + formatter.format(GearStore.getInstance().calculateGearTotal()));
    }

    private void refreshList() {
        // use the ArrayAdapters public method to notify the view that the data has changed, and that it should refresh
        gearAdapter.notifyDataSetChanged();
    }

    private void startViewGearActivity() {
        // navigate to our View Gear Activity Page
        Intent intent = new Intent(this, ViewGearActivity.class);
        intent.putExtra(SELECTED_GEAR_TAG, selectedIndex);
        startActivity(intent);
    }

    private void startAddGearActivity() {
        // navigate to our Edit Gear Activity Page, used for both adding and editing gear
        Intent intent = new Intent(this, EditGearActivity.class);
        intent.putExtra(SELECTED_GEAR_TAG, selectedIndex);
        intent.putExtra(ADD_EDIT_GEAR_MODE, "ADD");
        startActivity(intent);
    }

    private void startEditGearActivity() {
        // navigate to our Edit Gear Activity Page, used for both adding and editing gear
        Intent intent = new Intent(this, EditGearActivity.class);
        intent.putExtra(SELECTED_GEAR_TAG, selectedIndex);
        intent.putExtra(ADD_EDIT_GEAR_MODE, "EDIT");
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // user has selected to View, Edit, or Delete that piece of gear from their list
        // user also has ability to cancel action by clicking away
        switch (item.getItemId()) {
            case R.id.viewGear:
                startViewGearActivity();
                return true;
            case R.id.editGear:
                startEditGearActivity();
                return true;
            case R.id.deleteGear:
                GearStore.getInstance().deleteGear(selectedIndex);
                refreshList();
                refreshGearTotal();
                return true;
            default:
                return false;
        }
    }

    private void showGearOptionsMenu(View v) {
        // show pop up options for gear selected
        // should be View, Edit, and Delete
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.gear_options_menu);
        popup.show();
    }


}
package com.example.bjzukows_gearbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.NumberFormat;
import java.util.ArrayList;

public class GearArrayAdapter extends ArrayAdapter<Gear> {

    private ArrayList<Gear> gearList;

    public GearArrayAdapter(@NonNull Context context, int resource, ArrayList<Gear> gearList) {
        super(context, resource, gearList);
        this.gearList = gearList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // custom array adapter for formatting each item in our list
        // inflate our custom layout (R.layout.gear_list_view) instead of the default view
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        View view = inflater.inflate(R.layout.gear_list_view, null);

        // get specific gear item from gear store singleton
        Gear gear = gearList.get(position);
        // get custom view items in layout and set gear details for every item in list
        // chose to show Maker, Price, and Date as the most important initial features for that gear
        TextView maker = (TextView) view.findViewById(R.id.gearMakerTextView);
        TextView price = (TextView) view.findViewById(R.id.gearPriceTextView);
        TextView date = (TextView) view.findViewById(R.id.gearDateTextView);
        TextView description = (TextView) view.findViewById(R.id.gearDescriptionTextView);
        maker.setText(gear.getGearMaker());
        price.setText(formatter.format(gear.getGearPrice()));
        date.setText(gear.getGearDate());
        description.setText(gear.getGearDescription());
        return view;
    }
}

package com.example.bjzukows_gearbook;

import java.util.ArrayList;
import java.util.List;

public class GearStore {
    // singleton instance that other classes can use without having to instantiate
    // ensures we are referencing the same instance every time
    // class acts as our container/ source of truth for gear data
    private static GearStore instance = null;
    private ArrayList<Gear> gearList = new ArrayList<Gear>();

    public static GearStore getInstance() {
        // ensure we only have one gear storing our gear objects
        // using singleton pattern to accomplish this
        if (instance == null) {
            instance = new GearStore();
        }
        return instance;
    }

    // methods available to access gear store
    public void addGear(Gear gear) {
        gearList.add(gear);
    }

    public void updateGear(int index, Gear gear) {
        gearList.set(index, gear);
    }

    public void deleteGear(int index) {
        gearList.remove(index);
    }

    public ArrayList<Gear> getGearList() {
        return gearList;
    }

    public Gear getSpecificGearItem(int index) {
        return gearList.get(index);
    }

    public int gearCount() {
        return gearList.size();
    }

    public double calculateGearTotal() {
        double total = 0;
        for (Gear gear : gearList) {
            total += gear.getGearPrice();
        }
        return total;
    }
}

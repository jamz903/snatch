package sg.edu.np.mad.snatch;

import java.util.ArrayList;

public class FoodCourt {

    public int noOfPpl;

    public int capacity;

    public String name;

    public ArrayList<FoodItem> popularDishes;

    public boolean expanded;

    public FoodCourt(int aNoOfPpl, int aCapacity, String aName) {
        this.noOfPpl = aNoOfPpl;
        this.capacity = aCapacity;
        this.name = aName;
    }

    public int getNoOfPpl() {
        return noOfPpl;
    }

    public void setNoOfPpl(int noOfPpl) {
        this.noOfPpl = noOfPpl;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FoodItem> getPopularDishes() {
        return popularDishes;
    }

    public void setPopularDishes(ArrayList<FoodItem> popularDishes) {
        this.popularDishes = popularDishes;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}

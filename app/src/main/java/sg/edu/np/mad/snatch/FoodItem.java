package sg.edu.np.mad.snatch;

import java.util.Comparator;

public class FoodItem implements Comparable {
    public String foodName;
    public String foodDesc;
    public double price;
    public int upVotes;
    public int imageID;
    public String foodCourt;
    public String stallName;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public double getPrice() {
        return price;
    }

    public String getFoodCourt() {
        return foodCourt;
    }

    public void setFoodCourt(String foodCourt) {
        this.foodCourt = foodCourt;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public FoodItem(String aFoodName, String aFoodDesc, double aPrice, int aImageID, int aUpVotes, String aFoodCourt, String aStallName) {
        foodName = aFoodName;
        foodDesc = aFoodDesc;
        price = aPrice;
        imageID = aImageID;
        upVotes = aUpVotes;
        foodCourt = aFoodCourt;
        stallName = aStallName;
    }

    public FoodItem(String aFoodName, int aUpVotes) {
        this.foodName = aFoodName;
        this.upVotes = aUpVotes;
    }

    //sorting system for upvote system, it sorts the upvotes in the list in descending order
    //called using Collections.sort()
    @Override
    public int compareTo(Object o) {
        int compareUpVotes = ((FoodItem)o).getUpVotes();
        //returns in descending order
        return compareUpVotes-this.upVotes;
    }
}

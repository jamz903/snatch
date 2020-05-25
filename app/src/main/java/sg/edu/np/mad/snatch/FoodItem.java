package sg.edu.np.mad.snatch;

import java.util.Comparator;

public class FoodItem implements Comparable {
    public String foodName;
    public String foodDesc;
    public double price;
    public int upVotes;
    public int imageID;

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

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public FoodItem(String aFoodName, String aFoodDesc, double aPrice, int aImageID, int aUpVotes) {
        foodName = aFoodName;
        foodDesc = aFoodDesc;
        price = aPrice;
        imageID = aImageID;
        upVotes = aUpVotes;
    }

    @Override
    public int compareTo(Object o) {
        int compareUpVotes = ((FoodItem)o).getUpVotes();
        //returns in descending order
        return compareUpVotes-this.upVotes;
    }
}

package sg.edu.np.mad.snatch;

import java.util.Comparator;

public class FoodItem implements Comparator<FoodItem> {
    public String foodName;
    public String foodDesc;
    public double price;
    public int upVotes;

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

    public FoodItem(String aFoodName, String aFoodDesc, double aPrice, int aUpVotes) {
        foodName = aFoodName;
        foodDesc = aFoodDesc;
        price = aPrice;
        upVotes = aUpVotes;
    }

    @Override
    public int compare(FoodItem o1, FoodItem o2) {
        if(o1.getUpVotes() > o2.getUpVotes()){
            return 1;
        }
        else{
            return 0;
        }
    }

}

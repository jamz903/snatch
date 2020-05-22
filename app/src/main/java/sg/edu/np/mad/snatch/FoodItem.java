package sg.edu.np.mad.snatch;

public class FoodItem {
    public String foodName;
    public String foodDesc;
    public double price;

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

    public FoodItem(String aFoodName, String aFoodDesc, double aPrice) {
        foodName = aFoodName;
        foodDesc = aFoodDesc;
        price = aPrice;
    }
}

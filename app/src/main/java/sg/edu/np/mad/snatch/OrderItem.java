package sg.edu.np.mad.snatch;

import java.io.Serializable;

public class OrderItem implements Serializable {
    public String foodName;
    public int quantity;
    public double foodPrice;
    public double subtotal;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public OrderItem(String aFoodName, int aQuantity, double aFoodPrice, double aSubtotal) {
        foodName = aFoodName;
        quantity = aQuantity;
        foodPrice = aFoodPrice;
        subtotal = aSubtotal;
    }
}

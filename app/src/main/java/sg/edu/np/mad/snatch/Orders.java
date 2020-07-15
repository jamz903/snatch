package sg.edu.np.mad.snatch;

public class Orders {
    private String FoodCourt;
    private String Stall;
    private int OrderNumber;
    private String OrderFufilled;
    private String DateTime;

    private Orders(){};

    public Orders(String foodCourt, String stall, int orderNumber, String orderFufilled, String dateTime){
        this.FoodCourt = foodCourt;
        this.Stall = stall;
        this.OrderNumber = orderNumber;
        this.OrderFufilled = orderFufilled;
        this.DateTime = dateTime;
    }

    public String getFoodCourt() {
        return FoodCourt;
    }

    public void setFoodCourt(String foodCourt) {
        FoodCourt = foodCourt;
    }

    public String getStall() {
        return Stall;
    }

    public void setStall(String stall) {
        Stall = stall;
    }

    public int getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getOrderFufilled() {
        return OrderFufilled;
    }

    public void setOrderFufilled(String orderFufilled) {
        OrderFufilled = orderFufilled;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}

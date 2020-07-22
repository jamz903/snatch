package sg.edu.np.mad.snatch;

public class Orders {
    private String FoodCourt;
    private String Stall;
    private String OrderNumber;
    private String OrderFufilled;
    private String DateTime;
    private double TotalCost;

    private Orders(){};

    public Orders(String foodCourt, String stall, String orderNumber, String orderFufilled, String dateTime, double totalCost){
        this.FoodCourt = foodCourt;
        this.Stall = stall;
        this.OrderNumber = orderNumber;
        this.OrderFufilled = orderFufilled;
        this.DateTime = dateTime;
        this.TotalCost = totalCost;
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

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
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

    public double getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(double totalCost) {
        TotalCost = totalCost;
    }
}

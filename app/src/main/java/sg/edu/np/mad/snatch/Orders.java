package sg.edu.np.mad.snatch;

public class Orders {
    private String Stall;
    private int OrderNumber;
    private String OrderFufilled;

    private Orders(){};

    public Orders(String stall, int orderNumber, String orderFufilled){
        this.Stall = stall;
        this.OrderNumber = orderNumber;
        this.OrderFufilled = orderFufilled;
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
}

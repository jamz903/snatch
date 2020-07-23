package sg.edu.np.mad.snatch;

public class Rewards {
    private String RewardsID;
    private String Price;
    private String Redeemed;

    private Rewards(){};

    public Rewards(String rewardsID, String price, String redeemed){
        this.RewardsID = rewardsID;
        this.Price = price;
        this.Redeemed = redeemed;
    }

    public String getRewardsID(){return RewardsID;}

    public void setRewardsID(String rewardsID){RewardsID = rewardsID;}

    public String getRedeemed(){return Redeemed;}

    public void setRedeemed(String redeemed){Redeemed = redeemed;}

    public String getPrice(){return Price;}

    public void setPrice(String price){Price = price;}

    @Override
    public String toString(){return RewardsID + " " + Price;}

}

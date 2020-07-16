package sg.edu.np.mad.snatch;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodCourt {

    DatabaseReference reference;

    public int noOfPpl;

    public int capacity;

    public String name;

    public ArrayList<FoodItem> popularDishes;

    public boolean expanded;

    public FoodCourt(int aNoOfPpl, int aCapacity, String aName) {
        this.noOfPpl = aNoOfPpl;
        this.capacity = aCapacity;
        this.name = aName;
        Log.d("snatch", "aName IS " + aName);
        this.reference = FirebaseDatabase.getInstance().getReference().child("FoodCourt").child(aName.replaceAll("\\s+",""));
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

    public ArrayList<String> getAllStalls(String foodCourt) {
        ArrayList<String> listOfStores = new ArrayList<>();
        if (foodCourt.equals("FoodClub")) {
            listOfStores.add("BakKutTeh");
            listOfStores.add("BanMian");
            listOfStores.add("ChickenRice");
            listOfStores.add("DrinksStall");
            listOfStores.add("EconomicalRice");
            listOfStores.add("FCBakery");
            listOfStores.add("Indonesian");
            listOfStores.add("JapaneseFood");
            listOfStores.add("Mala");
            listOfStores.add("MiniWok");
            listOfStores.add("Thai");
            listOfStores.add("Western");
            listOfStores.add("Yogurt");
        }
        else if(foodCourt.equals("Makan Place")) {
            listOfStores.add("ChickenRice");
            listOfStores.add("DrinksStall");
            listOfStores.add("JapaneseFood");
            listOfStores.add("Mala");
        }
        else if(foodCourt.equals("Poolside")) {
            listOfStores.add("Henry'sWestern");
        }
        else {
            listOfStores.add("Japanese");
            listOfStores.add("Mala(Halal");
            listOfStores.add("Western");
        }
        return listOfStores;
    }

    public ArrayList<FoodItem> getAllDishes(ArrayList<String> stallNames) {
        final ArrayList<FoodItem> listOfDishes = new ArrayList<>();

        for (String foodStall : stallNames) {
            DatabaseReference reference2 = reference.child(foodStall);
            final String[] dishName = {""};
            final int[] upVoteAmt = {0};
            final int[] count = {0};

            reference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
                        FoodItem dish;
                        boolean isUpvote = count[0] % 2 == 0;
                        String value = postSnapshot.getValue().toString();
                        String key = postSnapshot.getKey().toString();

                        if(isUpvote) {
                            upVoteAmt[0] = Integer.parseInt(value);
                            /*Log.d("snatch", "KEY IS " + key);*/
                            Log.d("snatch", "DISH UPVOTE IS " + upVoteAmt[0]);
                            /*Log.d("snatch","COUNT IS "+ count[0]);*/
                        }
                        else {
                            dishName[0] = value;
                            Log.d("snatch", "DISH NAME IS " + dishName[0]);
                            dish = new FoodItem(dishName[0], upVoteAmt[0]);
                            listOfDishes.add(dish);
                        }
                        count[0]++;
                    }

                    for (FoodItem item :listOfDishes) {
                        Log.d("snatch", "DISH NAME IS " + item.foodName + "AND AND AND UPVOTE IS " + item.upVotes);
                    }
                    Log.d("snatch", "TESTTEST");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }




        return listOfDishes;
    }

    /*public ArrayList<FoodItem> getAllDishes(String aFoodCourt) {
        ArrayList<FoodItem> listOfDishes = new ArrayList<>();
        if (aFoodCourt.equals("FoodClub")) {
            listOfDishes = initDishesFC();
        }
        else if (aFoodCourt.equals("Makan Place")) {

        }
        else if (aFoodCourt.equals("Poolside")) {

        }
        else {

        }
        return listOfDishes;
    }*/

    /*public ArrayList<FoodItem> initDishesFC() {
        ArrayList<FoodItem> listOfDishes = new ArrayList<>();
        String aFoodCourt = "FoodClub";
        listOfDishes.add(new FoodItem("Lemon Chicken Rice", "Lemon Chicken Rice description", 3, R.drawable.chicken_rice1,0,aFoodCourt, "Chicken Rice"));
        listOfDishes.add(new FoodItem("Roasted Chicken Rice", "Roasted Chicken Rice description", 2.5, R.drawable.chicken_rice2,0, aFoodCourt, "Chicken Rice"));
        listOfDishes.add(new FoodItem("Steam Chicken Rice", "Steam Chicken Rice description", 2.5, R.drawable.chicken_rice3, 0, aFoodCourt, "Chicken Rice"));
        listOfDishes.add(new FoodItem("Sausage", "1 stick of hotdog", 1, R.drawable.sausage,0, aFoodCourt, "Mala"));
        listOfDishes.add(new FoodItem("Taiwan Sausage", "1 stick of Taiwan Sausage", 1, R.drawable.taiwan_sausage,0, aFoodCourt, "Mala"));
        listOfDishes.add(new FoodItem("Rice", "1 bowl of rice", 0.5, R.drawable.rice,0, aFoodCourt, "Mala"));
        listOfDishes.add(new FoodItem("Noodles", "1 packet of Instant Noodles", 1.5, R.drawable.noodle,0, aFoodCourt, "Mala"));
        listOfDishes.add(new FoodItem("Chicken Chop", "Chicken Chop with Mushroom Sauce", 5, R.drawable.chicken_chop,0, aFoodCourt, "Western"));
        listOfDishes.add(new FoodItem("Fish and Chips", "Fish and Chips with tartar sauce", 6, R.drawable.fish_and_chips,0, aFoodCourt, "Western"));
        listOfDishes.add(new FoodItem("Cheezy Fries", "Cheese Fries with Mayo", 3, R.drawable.cheese_fries,0, aFoodCourt, "Western"));
        listOfDishes.add(new FoodItem("Japanese Curry Chicken Katsu", "Chicken Katsu served with Japanese Curry and Rice", 4.5, R.drawable.chicken_katsu_curry,0, aFoodCourt, "Japanese Food"));
        listOfDishes.add(new FoodItem("Salmon Don", "Salmon with Japanese Rice", 4, R.drawable.salmon_don, 0, aFoodCourt, "Japanese Food"));
        listOfDishes.add(new FoodItem("Chawanmushi", "Bowl of Chawanmushi", 1, R.drawable.chawanmushi, 0, aFoodCourt, "Japanese Food"));

    }*/
}

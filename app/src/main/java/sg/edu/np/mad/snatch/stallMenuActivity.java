package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class stallMenuActivity extends AppCompatActivity implements menuItemAdapterCallback {

    //Create variables and lists for item
    ArrayList<FoodItem> foodMenu;
    ArrayList<OrderItem> shoppingCart;
    FloatingActionButton menuFAB;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("FoodCourt").child(HomescreenActivity.firebaseStall).child(StoresAdapter.firebaseStoreName);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stall_menu);
        shoppingCart = new ArrayList<>();
        menuFAB = (FloatingActionButton) findViewById(R.id.menuFAB);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Create a new list for ech food menu
        foodMenu = new ArrayList<>();

        //Create recycler view
        RecyclerView rv = findViewById(R.id.recyclerViewMenu);

        //change line below for adapter if is other food stall

        menuItemAdapter adapter = new menuItemAdapter(foodMenu, this);
        determineFoodStall(adapter);
        rv.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setItemAnimator(new DefaultItemAnimator());

        //adds a divider inbetween items
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

        menuFAB.bringToFront();
        menuFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(stallMenuActivity.this, OrderActivity.class);
                in.putExtra("OrderList", shoppingCart);
                startActivity(in);
            }
        });
    }



    @Override
    public void onBackPressed() {
        //prevents alertDialog from closing immediately when backbutton is pressed
        //super.onBackPressed();
        if (shoppingCart.size() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Warning! Clearing cart?")
                    .setCancelable(false)
                    .setMessage("Going back to see other stalls will clear current items in cart. Would you like to proceed?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            shoppingCart.clear();
                            stallMenuActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        else {
            stallMenuActivity.super.onBackPressed();
        }
    }

    //incomplete, need continue for remaining stalls
    //Check for each singular stall in food court
    public void determineFoodStall(menuItemAdapter adapter) {
        String choice = getIntent().getStringExtra("Stall");
        if (choice.equals("Chicken Rice")) {
            initChickenRice(adapter);
        }
        else if (choice.equals("Mala")) {
            initMala(adapter);
            adapter.notifyDataSetChanged();
        }
        else if(choice.equals("Western")) {
            initWestern(adapter);
            adapter.notifyDataSetChanged();
        }
        else if (choice.equals("Japanese Food")) {
            initJap(adapter);
            adapter.notifyDataSetChanged();
        }
        else if (choice.equals("Bak Kut Teh")) {
            initBKT(adapter);
            adapter.notifyDataSetChanged();
        }
        else if (choice.equals("Ban Mian")) {
            initBanMian(adapter);
            adapter.notifyDataSetChanged();
        }
        else if (choice.equals("Indonesian")) {
            initIndo(adapter);
            adapter.notifyDataSetChanged();
        }
        else if (choice.equals("Drinks Stall")) {
            initDrinks(adapter);
            adapter.notifyDataSetChanged();
        }
        else if (choice.equals("Yogurt")) {
            initYogurt(adapter);
            adapter.notifyDataSetChanged();
        }
        else if (choice.equals("Mini Wok")) {
            initMiniWok(adapter);
            adapter.notifyDataSetChanged();
        }
        else if (choice.equals("Thai")) {
            initThai(adapter);
            adapter.notifyDataSetChanged();
        }
        else if (choice.equals("Economical Rice")) {
            initEconRice(adapter);
            adapter.notifyDataSetChanged();
        }
        else if (choice.equals("FC Bakery")) {
            initBakery(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public void initChickenRice(menuItemAdapter aAdapter) {
        foodMenu.add(new FoodItem("Lemon Chicken Rice", "Lemon Chicken Rice description", 3, R.drawable.chicken_rice1,0));
        foodMenu.add(new FoodItem("Roasted Chicken Rice", "Roasted Chicken Rice description", 2.5, R.drawable.chicken_rice2,0));
        foodMenu.add(new FoodItem("Steam Chicken Rice", "Steam Chicken Rice description", 2.5, R.drawable.chicken_rice3, 0));
        getUpvote(foodMenu, aAdapter);
    }


    public void initMala(menuItemAdapter aAdapter) {
        foodMenu.add(new FoodItem("Sausage", "1 stick of hotdog", 1, R.drawable.sausage,0));
        foodMenu.add(new FoodItem("Taiwan Sausage", "1 stick of Taiwan Sausage", 1, R.drawable.taiwan_sausage,0));
        foodMenu.add(new FoodItem("Rice", "1 bowl of rice", 0.5, R.drawable.rice,0));
        foodMenu.add(new FoodItem("Noodles", "1 packet of Instant Noodles", 1.5, R.drawable.noodle,0));
        getUpvote(foodMenu, aAdapter);
    }

    public void initWestern(menuItemAdapter aAdapter) {
        foodMenu.add(new FoodItem("Chicken Chop", "Chicken Chop with Mushroom Sauce", 5, R.drawable.chicken_chop,0));
        foodMenu.add(new FoodItem("Fish and Chips", "Fish and Chips with tartar sauce", 6, R.drawable.fish_and_chips,0));
        foodMenu.add(new FoodItem("Cheezy Fries", "Cheese Fries with Mayo", 3, R.drawable.cheese_fries,0));
        getUpvote(foodMenu, aAdapter);
    }


    public void initJap(menuItemAdapter aAdapter) {
        //final int[] upvote = {0};
        foodMenu.add(new FoodItem("Japanese Curry Chicken Katsu", "Chicken Katsu served with Japanese Curry and Rice", 4.5, R.drawable.chicken_katsu_curry,0));
        foodMenu.add(new FoodItem("Salmon Don", "Salmon with Japanese Rice", 4, R.drawable.salmon_don, 0));
        foodMenu.add(new FoodItem("Chawanmushi", "Bowl of Chawanmushi", 1, R.drawable.chawanmushi, 0));
        getUpvote(foodMenu, aAdapter);
        /*for (FoodItem food : foodMenu) {
            String dishName = food.getFoodName().replaceAll("\\s+","");
            getUpvote(dishName,food,aAdapter);
            Log.d("snatchwork", food.getFoodName() + " has " + food.getUpVotes());
        }*/
        //Collections.sort(foodMenu);
    }

    public void initBKT(menuItemAdapter aAdapter) {
        foodMenu.add(new FoodItem("Bak Kut Teh", "1 bowl of Bak Kut Teh with Rice", 4, R.drawable.bakkutteh,0));
        foodMenu.add(new FoodItem("You Tiao (5pcs)", "5 sticks of You Tiao", 1.5, R.drawable.youtiao, 0));
        foodMenu.add(new FoodItem("Vinegar Braised Pork", "Braised Pork in Vinegar sauce", 4, R.drawable.vinegar_braised_pork,0));
        getUpvote(foodMenu, aAdapter);
    }

    public void initBanMian(menuItemAdapter aAdapter) {
        foodMenu.add(new FoodItem("Sliced Fish Noodles Soup", "Fish slices with Noodles", 5, R.drawable.sliced_fish_noodle_soup,0));
        foodMenu.add(new FoodItem("Ban Mian", "Ban Mian with egg", 4, R.drawable.banmian,0));
        foodMenu.add(new FoodItem("Fish Soup", "Sliced Fish in Soup", 3.5, R.drawable.fish_soup,0));
        getUpvote(foodMenu, aAdapter);
    }

    public void initIndo(menuItemAdapter aAdapter) {
        foodMenu.add(new FoodItem("Ayam Penyet", "Fried Chicken with Rice", 3.5, R.drawable.ayam_penyet,0));
        foodMenu.add(new FoodItem("Mee Soto", "Noodles in Chicken broth", 3, R.drawable.mee_soto,0));
        foodMenu.add(new FoodItem("Papadoms (3pcs)", "3 pieces of crispy papadoms", 0.1, R.drawable.papadom,0));
        getUpvote(foodMenu, aAdapter);
    }

    public void initDrinks(menuItemAdapter aAdapter) {
        foodMenu.add(new FoodItem("Iced Milo", "Cup of Iced Milo", 1.5, R.drawable.iced_milo,0));
        foodMenu.add(new FoodItem("Hot Milo", "Cup of Hot Milo", 0.7, R.drawable.hot_milo,0));
        foodMenu.add(new FoodItem("Hot Coffee", "Cup of Hot Coffee", 0.7, R.drawable.hot_coffee,0));
        foodMenu.add(new FoodItem("Iced Lemon Tea", "Cup of Iced Lemon Tea", 1.5, R.drawable.iced_lemon_tea,0));
        getUpvote(foodMenu, aAdapter);
    }

    public void initYogurt(menuItemAdapter aAdapter) {
        foodMenu.add(new FoodItem("Yogurt (Small)", "Small cup of Yogurt", 3.9, R.drawable.yogurt,0));
        foodMenu.add(new FoodItem("Yogurt (Med)", "Medium cup of Yogurt", 4.9, R.drawable.yogurt,0));
        foodMenu.add(new FoodItem("Yogurt (Large)", "Large cup of Yogurt", 5.9, R.drawable.yogurt,0));
        getUpvote(foodMenu, aAdapter);
    }

    public void initMiniWok(menuItemAdapter aAdapter) {
        foodMenu.add(new FoodItem("Gong Bao Chicken Rice", "Diced chicken cubes in Gong Bao sauce", 4, R.drawable.gongbao_chicken,0));
        foodMenu.add(new FoodItem("Hor Fun", "Sliced Fish Hor Fun with Prawns", 4, R.drawable.horfun,0));
        foodMenu.add(new FoodItem("Salted Egg Rice", "Salted Egg Chicken with Rice", 3.5, R.drawable.salted_egg_rice,0));
        getUpvote(foodMenu, aAdapter);
    }

    public void initThai(menuItemAdapter aAdapter) {
        foodMenu.add(new FoodItem("Basil Pork Rice", "Basil Pork Rice with Egg (Spicy)", 5, R.drawable.basil_pork_rice,0));
        foodMenu.add(new FoodItem("Pad Thai", "Pad Thai Noodles with Prawns", 4.5, R.drawable.padthai,0));
        foodMenu.add(new FoodItem("Mango Salad", "Green Mango Salad", 3, R.drawable.mangosalad,0));
        getUpvote(foodMenu, aAdapter);
    }

    public void initEconRice(menuItemAdapter aAdapter) {
        foodMenu.add(new FoodItem("Rice", "1 bowl of Rice", 0.5, R.drawable.rice,0));
        foodMenu.add(new FoodItem("Bee Hoon", "1 bowl of Bee Hoon", 0.7, R.drawable.beehoon,3));
        foodMenu.add(new FoodItem("Sweet and Sour Pork", "1 portion of Sweet and Sour Pork", 0.8, R.drawable.sweet_sour_pork,0));
        foodMenu.add(new FoodItem("Fried Egg", "1 slice of Fried Egg", 0.5, R.drawable.fried_egg,0));
        getUpvote(foodMenu, aAdapter);
    }

    public void initBakery(menuItemAdapter aAdapter) {
        foodMenu.add(new FoodItem("Hot Dog Bun", "Sausage in a Bun", 1, R.drawable.hotdog_bun,0));
        foodMenu.add(new FoodItem("Cream Puff (1pc)", "1 piece of Cream Puff", 0.8, R.drawable.cream_puff,0));
        foodMenu.add(new FoodItem("Floss Bun", "Chicken Floss Bun", 1,R.drawable.floss_bun,0));
        getUpvote(foodMenu, aAdapter);
    }

    @Override
    public void promptAddItem(final int aPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Go to Food Stall?")
                .setCancelable(true)
                .setMessage("Would you like to add " + foodMenu.get(aPosition).foodName + " to cart?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!checkSame(shoppingCart, foodMenu.get(aPosition).foodName)) {
                            shoppingCart.add(new OrderItem(foodMenu.get(aPosition).foodName, 1, foodMenu.get(aPosition).price, foodMenu.get(aPosition).price));
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean checkSame(ArrayList<OrderItem> aShoppingCart, String aFoodName) {
        boolean isSame = false;
        for (OrderItem food : aShoppingCart) {
            if (aFoodName.equals(food.foodName)) {
                food.quantity += 1;
                food.subtotal += food.foodPrice;
                isSame = true;
                break;
            }
        }
        return isSame;
    }

    public void getUpvote(final ArrayList<FoodItem> foodMenu, final menuItemAdapter aAdapter){
        //final int[] upVotes = {0};
        for (final FoodItem food : foodMenu) {
            final String dishName = food.getFoodName().replaceAll("\\s+","");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Long upvotes = (Long) dataSnapshot.child(dishName).getValue();
                    Log.d("snatchwork", "upvote here is " + String.valueOf(upvotes));
                    //upvote[0] = Integer.parseInt(String.valueOf(upvotes));
                    food.setUpVotes(Integer.parseInt(String.valueOf(upvotes)));
                    Collections.sort(foodMenu);
                    aAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Log.d("snatchwork", food.getFoodName() + " has " + food.getUpVotes());
        }
    }

}

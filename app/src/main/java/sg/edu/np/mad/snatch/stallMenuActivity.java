package sg.edu.np.mad.snatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class stallMenuActivity extends AppCompatActivity implements menuItemAdapterCallback {

    ArrayList<FoodItem> foodMenu;
    ArrayList<Integer> imageIDs;
    ArrayList<OrderItem> shoppingCart;
    FloatingActionButton menuFAB;
    Button upvote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stall_menu);
        shoppingCart = new ArrayList<>();
        menuFAB = (FloatingActionButton) findViewById(R.id.menuFAB);

        //upvote button
        /*Button upvote = (Button)findViewById(R.id.upvote);
        upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String dish = getIntent().getStringExtra("foodName");
                //String text = dish + "succesfully upvoted.";
                //Toast.makeText(stallMenuActivity.this, text, Toast.LENGTH_SHORT).show();

            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        foodMenu = new ArrayList<>();
        imageIDs = new ArrayList<>();

        determineFoodStall();

        RecyclerView rv = findViewById(R.id.recyclerViewMenu);

        //change line below for adapter if is other food stall
        menuItemAdapter adapter = new menuItemAdapter(foodMenu, imageIDs, this);
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
    public void determineFoodStall() {
        String choice = getIntent().getStringExtra("Stall");
        if (choice.equals("Chicken Rice")) {
            initChickenRice();
            initChickenRiceIDs();
        }
        else if (choice.equals("Mala")) {
            initMala();
            initMalaIDs();
        }
        else if(choice.equals("Western")) {
            initWestern();
            initWesternIDs();
        }
        else if (choice.equals("Japanese Food")) {
            initJap();
            initJapIDs();
        }
        else if (choice.equals("Bak Kut Teh")) {
            initBKT();
            initBKTIDs();
        }
        else if (choice.equals("Ban Mian")) {
            initBanMian();
            initBanMianIDs();
        }
        else if (choice.equals("Indonesian")) {
            initIndo();
            initIndoIDs();
        }
        else if (choice.equals("Drinks Stall")) {
            initDrinks();
            initDrinksIDs();
        }
        else if (choice.equals("Yogurt")) {
            initYogurt();
            initYogurtIDs();
        }
        else if (choice.equals("Mini Wok")) {
            initMiniWok();
            initMiniWokIDs();
        }
        else if (choice.equals("Thai")) {
            initThai();
            initThaiIDs();
        }
        else if (choice.equals("Economical Rice")) {
            initEconRice();
            initEconRiceIDs();
        }
        else if (choice.equals("FC Bakery")) {
            initBakery();
            initBakeryIDs();
        }
    }

    public void initChickenRice() {
        foodMenu.add(new FoodItem("Lemon Chicken Rice", "Lemon Chicken Rice description", 3));
        foodMenu.add(new FoodItem("Roasted Chicken Rice", "Roasted Chicken Rice description", 2.5));
        foodMenu.add(new FoodItem("Steam Chicken Rice", "Steam Chicken Rice description", 2.5));
    }

    public void initChickenRiceIDs() {
        imageIDs.add(R.drawable.chicken_rice1);
        imageIDs.add(R.drawable.chicken_rice2);
        imageIDs.add(R.drawable.chicken_rice3);
    }

    public void initMala() {
        foodMenu.add(new FoodItem("Sausage", "1 stick of hotdog", 1));
        foodMenu.add(new FoodItem("Taiwan Sausage", "1 stick of Taiwan Sausage", 1));
        foodMenu.add(new FoodItem("Rice", "1 bowl of rice", 0.5));
        foodMenu.add(new FoodItem("Noodles", "1 packet of Instant Noodles", 1.5));
    }

    public void initMalaIDs() {
        imageIDs.add(R.drawable.sausage);
        imageIDs.add(R.drawable.taiwan_sausage);
        imageIDs.add(R.drawable.rice);
        imageIDs.add(R.drawable.noodle);
    }

    public void initWesternIDs() {
        imageIDs.add(R.drawable.chicken_chop);
        imageIDs.add(R.drawable.fish_and_chips);
        imageIDs.add(R.drawable.cheese_fries);
    }

    public void initWestern() {
        foodMenu.add(new FoodItem("Chicken Chop", "Chicken Chop with Mushroom Sauce", 5));
        foodMenu.add(new FoodItem("Fish and Chips", "Fish and Chips with tartar sauce", 6));
        foodMenu.add(new FoodItem("Cheezy Fries", "Cheese Fries with Mayo", 3));
    }

    public void initJapIDs() {
        imageIDs.add(R.drawable.chicken_katsu_curry);
        imageIDs.add(R.drawable.salmon_don);
        imageIDs.add(R.drawable.chawanmushi);
    }

    public void initJap() {
        foodMenu.add(new FoodItem("Japanese Curry Chicken Katsu", "Chicken Katsu served with Japanese Curry and Rice", 4.5));
        foodMenu.add(new FoodItem("Salmon Don", "Salmon with Japanese Rice", 4));
        foodMenu.add(new FoodItem("Chawanmushi", "Bowl of Chawanmushi", 1));
    }

    public void initBKTIDs() {
        imageIDs.add(R.drawable.bakkutteh);
        imageIDs.add(R.drawable.youtiao);
        imageIDs.add(R.drawable.vinegar_braised_pork);
    }

    public void initBKT() {
        foodMenu.add(new FoodItem("Bak Kut Teh", "1 bowl of Bak Kut Teh with Rice", 4));
        foodMenu.add(new FoodItem("You Tiao (5pcs)", "5 sticks of You Tiao", 1.5));
        foodMenu.add(new FoodItem("Vinegar Braised Pork", "Braised Pork in Vinegar sauce", 4));
    }

    public void initBanMianIDs() {
        imageIDs.add(R.drawable.sliced_fish_noodle_soup);
        imageIDs.add(R.drawable.banmian);
        imageIDs.add(R.drawable.fish_soup);
    }

    public void initBanMian() {
        foodMenu.add(new FoodItem("Sliced Fish Noodles Soup", "Fish slices with Noodles", 5));
        foodMenu.add(new FoodItem("Ban Mian", "Ban Mian with egg", 4));
        foodMenu.add(new FoodItem("Fish Soup", "Sliced Fish in Soup", 3.5));
    }

    public void initIndoIDs() {
        imageIDs.add(R.drawable.ayam_penyet);
        imageIDs.add(R.drawable.mee_soto);
        imageIDs.add(R.drawable.papadom);
    }

    public void initIndo() {
        foodMenu.add(new FoodItem("Ayam Penyet", "Fried Chicken with Rice", 3.5));
        foodMenu.add(new FoodItem("Mee Soto", "Noodles in Chicken broth", 3));
        foodMenu.add(new FoodItem("Papadoms (3pcs)", "3 pieces of crispy papadoms", 0.1));
    }

    public void initDrinksIDs() {
        imageIDs.add(R.drawable.iced_milo);
        imageIDs.add(R.drawable.hot_milo);
        imageIDs.add(R.drawable.hot_coffee);
        imageIDs.add(R.drawable.iced_lemon_tea);
    }

    public void initDrinks() {
        foodMenu.add(new FoodItem("Iced Milo", "Cup of Iced Milo", 1.5));
        foodMenu.add(new FoodItem("Hot Milo", "Cup of Hot Milo", 0.7));
        foodMenu.add(new FoodItem("Hot Coffee", "Cup of Hot Coffee", 0.7));
        foodMenu.add(new FoodItem("Iced Lemon Tea", "Cup of Iced Lemon Tea", 1.5));
    }

    public void initYogurtIDs() {
        imageIDs.add(R.drawable.yogurt);
        imageIDs.add(R.drawable.yogurt);
        imageIDs.add(R.drawable.yogurt);
    }

    public void initYogurt() {
        foodMenu.add(new FoodItem("Yogurt (Small)", "Small cup of Yogurt", 3.9));
        foodMenu.add(new FoodItem("Yogurt (Med)", "Medium cup of Yogurt", 4.9));
        foodMenu.add(new FoodItem("Yogurt (Large)", "Large cup of Yogurt", 5.9));
    }

    public void initMiniWokIDs() {
        imageIDs.add(R.drawable.gongbao_chicken);
        imageIDs.add(R.drawable.horfun);
        imageIDs.add(R.drawable.salted_egg_rice);
    }

    public void initMiniWok() {
        foodMenu.add(new FoodItem("Gong Bao Chicken Rice", "Diced chicken cubes in Gong Bao sauce", 4));
        foodMenu.add(new FoodItem("Hor Fun", "Sliced Fish Hor Fun with Prawns", 4));
        foodMenu.add(new FoodItem("Salted Egg Rice", "Salted Egg Chicken with Rice", 3.5));
    }

    public void initThaiIDs() {
        imageIDs.add(R.drawable.basil_pork_rice);
        imageIDs.add(R.drawable.padthai);
        imageIDs.add(R.drawable.mangosalad);
    }

    public void initThai() {
        foodMenu.add(new FoodItem("Basil Pork Rice", "Basil Pork Rice with Egg (Spicy)", 5));
        foodMenu.add(new FoodItem("Pad Thai", "Pad Thai Noodles with Prawns", 4.5));
        foodMenu.add(new FoodItem("Mango Salad", "Green Mango Salad", 3));
    }

    public void initEconRiceIDs() {
        imageIDs.add(R.drawable.rice);
        imageIDs.add(R.drawable.beehoon);
        imageIDs.add(R.drawable.sweet_sour_pork);
        imageIDs.add(R.drawable.fried_egg);
    }

    public void initEconRice() {
        foodMenu.add(new FoodItem("Rice", "1 bowl of Rice", 0.5));
        foodMenu.add(new FoodItem("Bee Hoon", "1 bowl of Bee Hoon", 0.7));
        foodMenu.add(new FoodItem("Sweet and Sour Pork", "1 portion of Sweet and Sour Pork", 0.8));
        foodMenu.add(new FoodItem("Fried Egg", "1 slice of Fried Egg", 0.5));
    }

    public void initBakeryIDs() {
        imageIDs.add(R.drawable.hotdog_bun);
        imageIDs.add(R.drawable.cream_puff);
        imageIDs.add(R.drawable.floss_bun);
    }

    public void initBakery() {
        foodMenu.add(new FoodItem("Hot Dog Bun", "Sausage in a Bun", 1));
        foodMenu.add(new FoodItem("Cream Puff (1pc)", "1 piece of Cream Puff", 0.8));
        foodMenu.add(new FoodItem("Floss Bun", "Chicken Floss Bun", 1));
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


}

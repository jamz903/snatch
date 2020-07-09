package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    String foodCourtChoice;
    String foodStallChoice;
    int LAUNCH_SECOND_ACTIVITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stall_menu);
        shoppingCart = new ArrayList<>();
        menuFAB = (FloatingActionButton) findViewById(R.id.menuFAB);
        foodCourtChoice = getIntent().getStringExtra("FoodCourt");
        foodStallChoice = getIntent().getStringExtra("Stall");
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
        determineFoodCourt(foodCourtChoice, foodStallChoice);
        getUpvote(foodMenu, adapter);
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
                LAUNCH_SECOND_ACTIVITY = 1;
                startActivityForResult(in, LAUNCH_SECOND_ACTIVITY);
            }
        });
    }



    @Override
    public void onBackPressed() {
        //prevents alertDialog from closing immediately when back button is pressed
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

    public void determineFoodCourt(String aFoodCourtChoice, String aFoodStallChoice) {
        if (aFoodCourtChoice.equals("FoodClub")) {
            determineFoodStallFoodClub(aFoodStallChoice);
        }
        else if(aFoodCourtChoice.equals("MKP")) {
            determineFoodStallMKP(aFoodStallChoice);
        }
        else if(aFoodCourtChoice.equals("Poolside")) {
            determineFoodStallPoolside(aFoodStallChoice);
        }
        else if(aFoodCourtChoice.equals("Munch")) {
            determineFoodStallMunch(aFoodStallChoice);
        }

    }

    public void determineFoodStallMKP(String aFoodStallChoice) {
        if (aFoodStallChoice.equals("Chicken Rice")) {
            initChickenRiceMKP();
        }
        else if (aFoodStallChoice.equals("Drinks Stall")) {
            initDrinksStallMKP();
        }
        else if (aFoodStallChoice.equals("Japanese Food")) {
            initJapStallMKP();
        }
        else if (aFoodStallChoice.equals("Mala")) {
            initMalaMKP();
        }
    }

    public void determineFoodStallPoolside(String aFoodStallChoice) {
        if (aFoodStallChoice.equals("Henry's Western")) {
            initHenrysWesternPoolside();
        }
    }

    public void determineFoodStallMunch(String aFoodStallChoice) {
        if (aFoodStallChoice.equals("Japanese")) {
            initJapMunch();
        }
        else if (aFoodStallChoice.equals("Mala (Halal)")) {
            initMalaMunch();
        }
        else if (aFoodStallChoice.equals("Western")) {
            initWesternMunch();
        }
    }

    //incomplete, need continue for remaining stalls
    //Check for each singular stall in food court
    public void determineFoodStallFoodClub( String aFoodStallChoice) {
        if (aFoodStallChoice.equals("Chicken Rice")) {
            initChickenRiceFC();
        }
        else if (aFoodStallChoice.equals("Mala")) {
            initMalaFC();
        }
        else if(aFoodStallChoice.equals("Western")) {
            initWesternFC();
        }
        else if (aFoodStallChoice.equals("Japanese Food")) {
            initJapFC();
        }
        else if (aFoodStallChoice.equals("Bak Kut Teh")) {
            initBKTFC();
        }
        else if (aFoodStallChoice.equals("Ban Mian")) {
            initBanMianFC();
        }
        else if (aFoodStallChoice.equals("Indonesian")) {
            initIndoFC();
        }
        else if (aFoodStallChoice.equals("Drinks Stall")) {
            initDrinksFC();
        }
        else if (aFoodStallChoice.equals("Yogurt")) {
            initYogurtFC();
        }
        else if (aFoodStallChoice.equals("Mini Wok")) {
            initMiniWokFC();
        }
        else if (aFoodStallChoice.equals("Thai")) {
            initThaiFC();
        }
        else if (aFoodStallChoice.equals("Economical Rice")) {
            initEconRiceFC();
        }
        else if (aFoodStallChoice.equals("FC Bakery")) {
            initBakeryFC();
        }
    }

    //Food Stalls for FC (Chicken Rice, Mala, Western, Jap, BKT, Ban Mian, Indo, Drinks Stall, Yogurt, MiniWok, Thai, etc.)
    public void initChickenRiceFC() {
        foodMenu.add(new FoodItem("Lemon Chicken Rice", "Lemon Chicken Rice description", 3, R.drawable.chicken_rice1,0));
        foodMenu.add(new FoodItem("Roasted Chicken Rice", "Roasted Chicken Rice description", 2.5, R.drawable.chicken_rice2,0));
        foodMenu.add(new FoodItem("Steam Chicken Rice", "Steam Chicken Rice description", 2.5, R.drawable.chicken_rice3, 0));
    }


    public void initMalaFC() {
        foodMenu.add(new FoodItem("Sausage", "1 stick of hotdog", 1, R.drawable.sausage,0));
        foodMenu.add(new FoodItem("Taiwan Sausage", "1 stick of Taiwan Sausage", 1, R.drawable.taiwan_sausage,0));
        foodMenu.add(new FoodItem("Rice", "1 bowl of rice", 0.5, R.drawable.rice,0));
        foodMenu.add(new FoodItem("Noodles", "1 packet of Instant Noodles", 1.5, R.drawable.noodle,0));
    }

    public void initWesternFC() {
        foodMenu.add(new FoodItem("Chicken Chop", "Chicken Chop with Mushroom Sauce", 5, R.drawable.chicken_chop,0));
        foodMenu.add(new FoodItem("Fish and Chips", "Fish and Chips with tartar sauce", 6, R.drawable.fish_and_chips,0));
        foodMenu.add(new FoodItem("Cheezy Fries", "Cheese Fries with Mayo", 3, R.drawable.cheese_fries,0));
    }


    public void initJapFC() {
        foodMenu.add(new FoodItem("Japanese Curry Chicken Katsu", "Chicken Katsu served with Japanese Curry and Rice", 4.5, R.drawable.chicken_katsu_curry,0));
        foodMenu.add(new FoodItem("Salmon Don", "Salmon with Japanese Rice", 4, R.drawable.salmon_don, 0));
        foodMenu.add(new FoodItem("Chawanmushi", "Bowl of Chawanmushi", 1, R.drawable.chawanmushi, 0));
        foodMenu.add(new FoodItem("Gyoza (5 pcs)", "A plate of Fried Gyoza", 2, R.drawable.gyoza, 0));
        foodMenu.add(new FoodItem("Ramen Bowl", "A bowl of Ramen with Cha Shu", 5, R.drawable.ramen, 0));
        foodMenu.add(new FoodItem("Sushi Platter", "A plate of Sushi", 4, R.drawable.sushi_platter, 0));
        foodMenu.add(new FoodItem("Takoyaki (3pcs)", "Takoyaki", 2.1, R.drawable.takoyaki, 0));
    }

    public void initBKTFC() {
        foodMenu.add(new FoodItem("Bak Kut Teh", "1 bowl of Bak Kut Teh with Rice", 4, R.drawable.bakkutteh,0));
        foodMenu.add(new FoodItem("You Tiao (5pcs)", "5 sticks of You Tiao", 1.5, R.drawable.youtiao, 0));
        foodMenu.add(new FoodItem("Vinegar Braised Pork", "Braised Pork in Vinegar sauce", 4, R.drawable.vinegar_braised_pork,0));
    }

    public void initBanMianFC() {
        foodMenu.add(new FoodItem("Sliced Fish Noodles Soup", "Fish slices with Noodles", 5, R.drawable.sliced_fish_noodle_soup,0));
        foodMenu.add(new FoodItem("Ban Mian", "Ban Mian with egg", 4, R.drawable.banmian,0));
        foodMenu.add(new FoodItem("Fish Soup", "Sliced Fish in Soup", 3.5, R.drawable.fish_soup,0));
    }

    public void initIndoFC() {
        foodMenu.add(new FoodItem("Ayam Penyet", "Fried Chicken with Rice", 3.5, R.drawable.ayam_penyet,0));
        foodMenu.add(new FoodItem("Mee Soto", "Noodles in Chicken broth", 3, R.drawable.mee_soto,0));
        foodMenu.add(new FoodItem("Papadoms (3pcs)", "3 pieces of crispy papadoms", 0.1, R.drawable.papadom,0));
    }

    public void initDrinksFC() {
        foodMenu.add(new FoodItem("Iced Milo", "Cup of Iced Milo", 1.5, R.drawable.iced_milo,0));
        foodMenu.add(new FoodItem("Hot Milo", "Cup of Hot Milo", 0.7, R.drawable.hot_milo,0));
        foodMenu.add(new FoodItem("Hot Coffee", "Cup of Hot Coffee", 0.7, R.drawable.hot_coffee,0));
        foodMenu.add(new FoodItem("Iced Lemon Tea", "Cup of Iced Lemon Tea", 1.5, R.drawable.iced_lemon_tea,0));
    }

    public void initYogurtFC() {
        foodMenu.add(new FoodItem("Yogurt (Small)", "Small cup of Yogurt", 3.9, R.drawable.yogurt,0));
        foodMenu.add(new FoodItem("Yogurt (Med)", "Medium cup of Yogurt", 4.9, R.drawable.yogurt,0));
        foodMenu.add(new FoodItem("Yogurt (Large)", "Large cup of Yogurt", 5.9, R.drawable.yogurt,0));
    }

    public void initMiniWokFC() {
        foodMenu.add(new FoodItem("Gong Bao Chicken Rice", "Diced chicken cubes in Gong Bao sauce", 4, R.drawable.gongbao_chicken,0));
        foodMenu.add(new FoodItem("Hor Fun", "Sliced Fish Hor Fun with Prawns", 4, R.drawable.horfun,0));
        foodMenu.add(new FoodItem("Salted Egg Rice", "Salted Egg Chicken with Rice", 3.5, R.drawable.salted_egg_rice,0));
    }

    public void initThaiFC() {
        foodMenu.add(new FoodItem("Basil Pork Rice", "Basil Pork Rice with Egg (Spicy)", 5, R.drawable.basil_pork_rice,0));
        foodMenu.add(new FoodItem("Pad Thai", "Pad Thai Noodles with Prawns", 4.5, R.drawable.padthai,0));
        foodMenu.add(new FoodItem("Mango Salad", "Green Mango Salad", 3, R.drawable.mangosalad,0));
    }

    public void initEconRiceFC() {
        foodMenu.add(new FoodItem("Rice", "1 bowl of Rice", 0.5, R.drawable.rice,0));
        foodMenu.add(new FoodItem("Bee Hoon", "1 bowl of Bee Hoon", 0.7, R.drawable.beehoon,0));
        foodMenu.add(new FoodItem("Sweet and Sour Pork", "1 portion of Sweet and Sour Pork", 0.8, R.drawable.sweet_sour_pork,0));
        foodMenu.add(new FoodItem("Fried Egg", "1 slice of Fried Egg", 0.5, R.drawable.fried_egg,0));
    }

    public void initBakeryFC() {
        foodMenu.add(new FoodItem("Hot Dog Bun", "Sausage in a Bun", 1, R.drawable.hotdog_bun,0));
        foodMenu.add(new FoodItem("Cream Puff (1pc)", "1 piece of Cream Puff", 0.8, R.drawable.cream_puff,0));
        foodMenu.add(new FoodItem("Floss Bun", "Chicken Floss Bun", 1,R.drawable.floss_bun,0));
    }

    //Food stalls for MKP (Chicken Rice, Drinks Stall, Jap Stall, Mala Stall)
    public void initChickenRiceMKP() {
        foodMenu.add(new FoodItem("Lemon Chicken Rice", "Lemon Chicken Rice Description", 3.5, R.drawable.chicken_rice1, 0));
        foodMenu.add(new FoodItem("Roasted Chicken Rice", "Roasted Chicken Rice Description", 3, R.drawable.chicken_rice2, 0));
        foodMenu.add(new FoodItem("Steam Chicken Rice", "Steam Chicken Rice Description", 3, R.drawable.chicken_rice3, 0));
    }

    public void initDrinksStallMKP() {
        foodMenu.add(new FoodItem("Bandung", "Iced Bandung Drink", 1.3, R.drawable.bandung, 0));
        foodMenu.add(new FoodItem("Hot Milo", "One cup of Hot Milo", 1, R.drawable.hot_milo, 0));
        foodMenu.add(new FoodItem("Iced Milo", "One cup of Iced Milo", 1.5, R.drawable.iced_milo, 0));
    }

    public void initJapStallMKP() {
        foodMenu.add(new FoodItem("Chawanmushi", "One bowl of Chawanmushi", 1, R.drawable.chawanmushi, 0));
        foodMenu.add(new FoodItem("Chicken Fuyong", "Chicken Fuyong Omelette", 3.3, R.drawable.chicken_fuyong, 0));
        foodMenu.add(new FoodItem("Japanese Curry Chicken Katsu", "Japanese Curry with Chicken Cutlet", 5, R.drawable.chicken_katsu_curry, 0));
    }

    public void initMalaMKP() {
        foodMenu.add(new FoodItem("Noodles", "One packet of Instant Noodles", 1, R.drawable.noodle, 0));
        foodMenu.add(new FoodItem("Rice", "One bowl of Rice", 0.5, R.drawable.rice, 0));
        foodMenu.add(new FoodItem("Sausage", "One slice of Hotdog", 0.5, R.drawable.sausage, 0));
        foodMenu.add(new FoodItem("Taiwanese Sausage", "One slice of Taiwan Sausage", 0.7, R.drawable.taiwan_sausage, 0));
    }

    //Food stalls for Munch (Japanese, Mala, Western)
    public void initJapMunch() {
        foodMenu.add(new FoodItem("Chawanmushi", "One bowl of Japanese Steam Egg", 0.8, R.drawable.chawanmushi, 0));
        foodMenu.add(new FoodItem("Japanese Curry Chicken Katsu", "Japanese Curry with Chicken Cutlet and Rice", 5.5, R.drawable.chicken_katsu_curry, 0));
        foodMenu.add(new FoodItem("Salmon Don", "Salmon with Egg and Rice", 6, R.drawable.salmon_don, 0));
    }

    public void initMalaMunch() {
        foodMenu.add(new FoodItem("Chicken", "100g of chicken", 2, R.drawable.chicken, 0));
        foodMenu.add(new FoodItem("Noodles", "One packet of Instant Noodles", 1.5, R.drawable.noodle, 0));
        foodMenu.add(new FoodItem("Rice", "One bowl of Rice", 0.5, R.drawable.rice, 0));
    }

    public void initWesternMunch() {
        foodMenu.add(new FoodItem("Cheezy Fries", "Cheese Fries", 2, R.drawable.cheese_fries, 0));
        foodMenu.add(new FoodItem("Chicken Chop", "Grilled Chicken Chop", 4, R.drawable.chicken_chop, 0));
        foodMenu.add(new FoodItem("Fish and Chips", "Fried Dory with Fries", 5, R.drawable.fish_and_chips, 0));
    }

    //Food stalls for Poolside
    public void initHenrysWesternPoolside() {
        foodMenu.add(new FoodItem("Cheezy Fries", "Cheese Fries", 2, R.drawable.cheese_fries, 0));
        foodMenu.add(new FoodItem("Chicken Chop", "Grilled Chicken Chop", 4, R.drawable.chicken_chop, 0));
        foodMenu.add(new FoodItem("Fish and Chips", "Fried Dory with Fries", 5, R.drawable.fish_and_chips, 0));
    }

    //adds item to cart
    @Override
    public void promptAddItem(final int aPosition) {
        //alert dialog to check with user
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add item to cart?")
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
        for (final FoodItem food : foodMenu) {
            final String dishName = food.getFoodName().replaceAll("\\s+","");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Long upvotes = (Long) dataSnapshot.child(dishName).getValue();
                    Log.d("snatchwork", "upvote here is " + String.valueOf(upvotes));
                    food.setUpVotes(Integer.parseInt(String.valueOf(upvotes)));
                    //sorts items based on UpvoteNo (sorted based on CompareTo in FoodItem class)
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /*if(item.getItemId() == R.id.credits_option){
            to be implemented later on in phase 2
        }*/

        //Food courts' vacancy button on kebab icon on top right corner of the app
        //brings user to see the number of people in each food court
        if (item.getItemId() == R.id.vacancy_option) {
            Intent in = new Intent(this, FoodCourtVacancyActivity.class);
            startActivity(in);
        }

        //logout button on kebab icon on top right corner of the app
        //brings user to log in page
        if(item.getItemId() == R.id.logout_option){
            Intent signIn = new Intent(stallMenuActivity.this,MainActivity.class);
            //clears backstack so user cannot click back to go back to main page of application after logging out
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    //to get the updated list of cart items from OrderActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                ArrayList<OrderItem> result = (ArrayList<OrderItem>) data.getSerializableExtra("result");
                shoppingCart = result;
            }
        }
    }

}

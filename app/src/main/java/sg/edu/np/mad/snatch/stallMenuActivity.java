package sg.edu.np.mad.snatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class stallMenuActivity extends AppCompatActivity {

    ArrayList<FoodItem> foodMenu;
    ArrayList<Integer> imageIDs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stall_menu);

    }

    @Override
    protected void onResume() {
        super.onResume();

        foodMenu = new ArrayList<>();
        imageIDs = new ArrayList<>();

        determineFoodStall();

        RecyclerView rv = findViewById(R.id.recyclerViewMenu);

        //change line below for adapter if is other food stall
        menuItemAdapter adapter = new menuItemAdapter(foodMenu, imageIDs);
        rv.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setItemAnimator(new DefaultItemAnimator());

        //adds a divider inbetween items
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

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
}

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

    public int capacity;

    public String name;

    public ArrayList<FoodItem> popularDishes;

    public boolean expanded;

    public FoodCourt(int aCapacity, String aName) {
        this.capacity = aCapacity;
        this.name = aName;
        Log.d("snatch", "aName IS " + aName);
        this.reference = FirebaseDatabase.getInstance().getReference().child("FoodCourt").child(aName.replaceAll("\\s+",""));
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

    public ArrayList<FoodItem> getAllDishes(String aFoodCourt) {
        ArrayList<FoodItem> listOfDishes = new ArrayList<>();
        if (aFoodCourt.equals("FoodClub")) {
            listOfDishes = initDishesFC();
        }
        else if (aFoodCourt.equals("Makan Place")) {
            listOfDishes = initDishesMKP();
        }
        else if (aFoodCourt.equals("Poolside")) {
            listOfDishes = initDishesPoolside();
        }
        else {
            listOfDishes = initDishesMunch();
        }
        return listOfDishes;
    }

    public ArrayList<FoodItem> initDishesFC() {
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
        listOfDishes.add(new FoodItem("Gyoza (5 pcs)", "A plate of Fried Gyoza", 2, R.drawable.gyoza, 0, aFoodCourt, "Japanese Food"));
        listOfDishes.add(new FoodItem("Ramen Bowl", "A bowl of Ramen with Cha Shu", 5, R.drawable.ramen, 0, aFoodCourt, "Japanese Food"));
        listOfDishes.add(new FoodItem("Sushi Platter", "A plate of Sushi", 4, R.drawable.sushi_platter, 0, aFoodCourt, "Japanese Food"));
        listOfDishes.add(new FoodItem("Takoyaki (3pcs)", "Takoyaki", 2.1, R.drawable.takoyaki, 0, aFoodCourt, "Japanese Food"));
        listOfDishes.add(new FoodItem("Bak Kut Teh", "1 bowl of Bak Kut Teh with Rice", 4, R.drawable.bakkutteh,0, aFoodCourt, "Bak Kut Teh"));
        listOfDishes.add(new FoodItem("You Tiao (5pcs)", "5 sticks of You Tiao", 1.5, R.drawable.youtiao, 0, aFoodCourt, "Bak Kut Teh"));
        listOfDishes.add(new FoodItem("Vinegar Braised Pork", "Braised Pork in Vinegar sauce", 4, R.drawable.vinegar_braised_pork,0, aFoodCourt, "Bak Kut Teh"));
        listOfDishes.add(new FoodItem("Sliced Fish Noodles Soup", "Fish slices with Noodles", 5, R.drawable.sliced_fish_noodle_soup,0, aFoodCourt, "Ban Mian"));
        listOfDishes.add(new FoodItem("Ban Mian", "Ban Mian with egg", 4, R.drawable.banmian,0, aFoodCourt, "Ban Mian"));
        listOfDishes.add(new FoodItem("Fish Soup", "Sliced Fish in Soup", 3.5, R.drawable.fish_soup,0, aFoodCourt, "Ban Mian"));
        listOfDishes.add(new FoodItem("Ayam Penyet", "Fried Chicken with Rice", 3.5, R.drawable.ayam_penyet,0, aFoodCourt, "Indonesian"));
        listOfDishes.add(new FoodItem("Mee Soto", "Noodles in Chicken broth", 3, R.drawable.mee_soto,0, aFoodCourt, "Indonesian"));
        listOfDishes.add(new FoodItem("Papadoms (3pcs)", "3 pieces of crispy papadoms", 0.1, R.drawable.papadom,0, aFoodCourt, "Indonesian"));
        listOfDishes.add(new FoodItem("Iced Milo", "Cup of Iced Milo", 1.5, R.drawable.iced_milo,0, aFoodCourt, "Drinks Stall"));
        listOfDishes.add(new FoodItem("Hot Milo", "Cup of Hot Milo", 0.7, R.drawable.hot_milo,0, aFoodCourt, "Drinks Stall"));
        listOfDishes.add(new FoodItem("Hot Coffee", "Cup of Hot Coffee", 0.7, R.drawable.hot_coffee,0, aFoodCourt, "Drinks Stall"));
        listOfDishes.add(new FoodItem("Iced Lemon Tea", "Cup of Iced Lemon Tea", 1.5, R.drawable.iced_lemon_tea,0, aFoodCourt, "Drinks Stall"));
        listOfDishes.add(new FoodItem("Yogurt (Small)", "Small cup of Yogurt", 3.9, R.drawable.yogurt,0, aFoodCourt, "Yogurt"));
        listOfDishes.add(new FoodItem("Yogurt (Medium)", "Medium cup of Yogurt", 4.9, R.drawable.yogurt,0, aFoodCourt, "Yogurt"));
        listOfDishes.add(new FoodItem("Yogurt (Large)", "Large cup of Yogurt", 5.9, R.drawable.yogurt,0, aFoodCourt, "Yogurt"));
        listOfDishes.add(new FoodItem("Gong Bao Chicken Rice", "Diced chicken cubes in Gong Bao sauce", 4, R.drawable.gongbao_chicken,0, aFoodCourt, "Mini Wok"));
        listOfDishes.add(new FoodItem("Hor Fun", "Sliced Fish Hor Fun with Prawns", 4, R.drawable.horfun,0, aFoodCourt, "Mini Wok"));
        listOfDishes.add(new FoodItem("Salted Egg Rice", "Salted Egg Chicken with Rice", 3.5, R.drawable.salted_egg_rice,0, aFoodCourt, "Mini Wok"));
        listOfDishes.add(new FoodItem("Basil Pork Rice", "Basil Pork Rice with Egg (Spicy)", 5, R.drawable.basil_pork_rice,0, aFoodCourt, "Thai"));
        listOfDishes.add(new FoodItem("Pad Thai", "Pad Thai Noodles with Prawns", 4.5, R.drawable.padthai,0, aFoodCourt, "Thai"));
        listOfDishes.add(new FoodItem("Mango Salad", "Green Mango Salad", 3, R.drawable.mangosalad,0, aFoodCourt, "Thai"));
        listOfDishes.add(new FoodItem("Rice", "1 bowl of Rice", 0.5, R.drawable.rice,0, aFoodCourt, "Economical Rice"));
        listOfDishes.add(new FoodItem("Bee Hoon", "1 bowl of Bee Hoon", 0.7, R.drawable.beehoon,0, aFoodCourt, "Economical Rice"));
        listOfDishes.add(new FoodItem("Sweet and Sour Pork", "1 portion of Sweet and Sour Pork", 0.8, R.drawable.sweet_sour_pork,0, aFoodCourt, "Economical Rice"));
        listOfDishes.add(new FoodItem("Fried Egg", "1 slice of Fried Egg", 0.5, R.drawable.fried_egg,0, aFoodCourt, "Economical Rice"));
        listOfDishes.add(new FoodItem("Hot Dog Bun", "Sausage in a Bun", 1, R.drawable.hotdog_bun,0, aFoodCourt, "FC Bakery"));
        listOfDishes.add(new FoodItem("Cream Puff (1pc)", "1 piece of Cream Puff", 0.8, R.drawable.cream_puff,0, aFoodCourt, "FC Bakery"));
        listOfDishes.add(new FoodItem("Floss Bun", "Chicken Floss Bun", 1,R.drawable.floss_bun,0, aFoodCourt, "FC Bakery"));
        return listOfDishes;
    }

    public ArrayList<FoodItem> initDishesMKP() {
        ArrayList<FoodItem> listOfDishes = new ArrayList<>();
        String aFoodCourt = "Makan Place";
        listOfDishes.add(new FoodItem("Lemon Chicken Rice", "Lemon Chicken Rice Description", 3.5, R.drawable.chicken_rice1, 0, aFoodCourt, "Chicken Rice"));
        listOfDishes.add(new FoodItem("Roasted Chicken Rice", "Roasted Chicken Rice Description", 3, R.drawable.chicken_rice2, 0, aFoodCourt, "Chicken Rice"));
        listOfDishes.add(new FoodItem("Steam Chicken Rice", "Steam Chicken Rice Description", 3, R.drawable.chicken_rice3, 0, aFoodCourt, "Chicken Rice"));
        listOfDishes.add(new FoodItem("Bandung", "Iced Bandung Drink", 1.3, R.drawable.bandung, 0, aFoodCourt, "Drinks Stall"));
        listOfDishes.add(new FoodItem("Hot Milo", "One cup of Hot Milo", 1, R.drawable.hot_milo, 0, aFoodCourt, "Drinks Stall"));
        listOfDishes.add(new FoodItem("Iced Milo", "One cup of Iced Milo", 1.5, R.drawable.iced_milo, 0, aFoodCourt, "Drinks Stall"));
        listOfDishes.add(new FoodItem("Chawanmushi", "One bowl of Chawanmushi", 1, R.drawable.chawanmushi, 0, aFoodCourt, "Japanese Food"));
        listOfDishes.add(new FoodItem("Chicken Fuyong", "Chicken Fuyong Omelette", 3.3, R.drawable.chicken_fuyong, 0, aFoodCourt, "Japanese Food"));
        listOfDishes.add(new FoodItem("Japanese Curry Chicken Katsu", "Japanese Curry with Chicken Cutlet", 5, R.drawable.chicken_katsu_curry, 0, aFoodCourt, "Japanese Food"));
        listOfDishes.add(new FoodItem("Noodles", "One packet of Instant Noodles", 1, R.drawable.noodle, 0, aFoodCourt, "Mala"));
        listOfDishes.add(new FoodItem("Rice", "One bowl of Rice", 0.5, R.drawable.rice, 0, aFoodCourt, "Mala"));
        listOfDishes.add(new FoodItem("Sausage", "One slice of Hotdog", 0.5, R.drawable.sausage, 0, aFoodCourt, "Mala"));
        listOfDishes.add(new FoodItem("Taiwanese Sausage", "One slice of Taiwan Sausage", 0.7, R.drawable.taiwan_sausage, 0, aFoodCourt, "Mala"));
        return listOfDishes;
    }

    public ArrayList<FoodItem> initDishesMunch() {
        ArrayList<FoodItem> listOfDishes = new ArrayList<>();
        String aFoodCourt = "Munch";
        listOfDishes.add(new FoodItem("Chawanmushi", "One bowl of Japanese Steam Egg", 0.8, R.drawable.chawanmushi, 0, aFoodCourt, "Japanese"));
        listOfDishes.add(new FoodItem("Japanese Curry Chicken Katsu", "Japanese Curry with Chicken Cutlet and Rice", 5.5, R.drawable.chicken_katsu_curry, 0, aFoodCourt, "Japanese"));
        listOfDishes.add(new FoodItem("Salmon Don", "Salmon with Egg and Rice", 6, R.drawable.salmon_don, 0, aFoodCourt, "Japanese"));
        listOfDishes.add(new FoodItem("Chicken", "100g of chicken", 2, R.drawable.chicken, 0, aFoodCourt, "Mala (Halal)"));
        listOfDishes.add(new FoodItem("Noodles", "One packet of Instant Noodles", 1.5, R.drawable.noodle, 0, aFoodCourt, "Mala (Halal)"));
        listOfDishes.add(new FoodItem("Rice", "One bowl of Rice", 0.5, R.drawable.rice, 0, aFoodCourt, "Mala (Halal)"));
        listOfDishes.add(new FoodItem("Cheezy Fries", "Cheese Fries", 2, R.drawable.cheese_fries, 0, aFoodCourt, "Western"));
        listOfDishes.add(new FoodItem("Chicken Chop", "Grilled Chicken Chop", 4, R.drawable.chicken_chop, 0, aFoodCourt, "Western"));
        listOfDishes.add(new FoodItem("Fish and Chips", "Fried Dory with Fries", 5, R.drawable.fish_and_chips, 0, aFoodCourt, "Western"));
        return listOfDishes;
    }

    public ArrayList<FoodItem> initDishesPoolside() {
        ArrayList<FoodItem> listOfDishes = new ArrayList<>();
        String aFoodCourt = "Poolside";
        listOfDishes.add(new FoodItem("Cheezy Fries", "Cheese Fries", 2, R.drawable.cheese_fries, 0, aFoodCourt, "Henry's Western"));
        listOfDishes.add(new FoodItem("Chicken Chop", "Grilled Chicken Chop", 4, R.drawable.chicken_chop, 0, aFoodCourt, "Henry's Western"));
        listOfDishes.add(new FoodItem("Fish and Chips", "Fried Dory with Fries", 5, R.drawable.fish_and_chips, 0, aFoodCourt, "Henry's Western"));
        return listOfDishes;
    }
}

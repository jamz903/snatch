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
        if (getIntent().getStringExtra("Stall").equals("Chicken Rice")) {
            initChickenRice();
            initChickenRiceIDs();
        }
        else if (getIntent().getStringExtra("Stall").equals("Mala")) {
            initMala();
            initMalaIDs();
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
}

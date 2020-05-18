package sg.edu.np.mad.snatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class FoodClubActivity extends AppCompatActivity {
    ArrayList<String> storeName = new ArrayList<>();
    ArrayList<String> storeDesc = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_club);

        storeNames(storeName);
        storeDescs(storeDesc);

        RecyclerView rv = findViewById(R.id.recyclerView1);

        StoresAdapter adapter = new StoresAdapter(storeName,storeDesc,this);
        rv.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setItemAnimator(new DefaultItemAnimator());

        //adds a divider inbetween items
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

    }

    private static void storeNames(ArrayList<String> storeName){
        storeName.add("Japanese Food");
        storeName.add("Bak Ku Teh");
        storeName.add("Ban Mian");
        storeName.add("Western");
        storeName.add("Indonesian");
        storeName.add("Drinks Stall");
        storeName.add("Yogurt");
        storeName.add("Mini Wok");
        storeName.add("Mala");
        storeName.add("Thai");
        storeName.add("Chicken Rice");
        storeName.add("Economical Rice");
        storeName.add("FC Bakery");
    }

    private static void storeDescs(ArrayList<String> storeDesc){
        storeDesc.add("This is the Japanese Stall");
        storeDesc.add("This is the Bak Ku Teh Stall");
        storeDesc.add("This is the Ban Mian Stall");
        storeDesc.add("This is the Western Stall");
        storeDesc.add("This is the Indonesian Stall");
        storeDesc.add("This is the Drinks Stall");
        storeDesc.add("This is the Yogurt Stall");
        storeDesc.add("This is the Mini Wok Stall");
        storeDesc.add("This is the Mala Stall");
        storeDesc.add("This is the Thai Stall");
        storeDesc.add("This is the Chicken Rice Stall");
        storeDesc.add("This is the Economical Rice Stall");
        storeDesc.add("This is the FC Bakery Stall");
    }


}

package sg.edu.np.mad.snatch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RewardsActivity  extends AppCompatActivity {
    DatabaseReference reff;
    RewardsAdapter adapter;
    private RecyclerView recyclerView;
    List<Rewards> rewardsList = new ArrayList<>();
    List<HashMap> hashList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);

        setContentView(R.layout.activity_rewards);
        reff = FirebaseDatabase.getInstance().getReference().child("Rewards");

        //add all rewards to list from firebase
        addExistingDeals();



    }


    @Override
    protected void onStart(){
        super.onStart();
        Log.d("Reward", "Started");
    }

    @Override
    protected void onResume(){
        super.onResume();
    }



    //add all current available arewards to list
    protected void addExistingDeals(){
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                String a = null;
                String b = null;
                String c = null;
                hashList = (List<HashMap>) dataSnapshot.getValue();

                Log.d("Rewards", " "+ hashList.get(1).get("ID"));

                for(int i = 1; i<hashList.size(); i++ ){
                    a = hashList.get(i).get("ID").toString();
                    b = hashList.get(i).get("Redeemed").toString();
                    c = hashList.get(i).get("Price").toString();
                }

                Rewards reward = new Rewards(a,c,b);
                rewardsList.add(reward);
                Log.d("Reward", rewardsList.get(0).getRewardsID());

                RecyclerView rv = findViewById(R.id.recyclerViewRewards);
                adapter = new RewardsAdapter(RewardsActivity.this , (ArrayList<Rewards>) rewardsList);
                rv.setAdapter(adapter);
                LinearLayoutManager layout = new LinearLayoutManager(RewardsActivity.this);
                rv.setLayoutManager(layout);
                rv.setItemAnimator(new DefaultItemAnimator());

                rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));





            };

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Reward","FIAL", databaseError.toException());
            }
        });
    }
}

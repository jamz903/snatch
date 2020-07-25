package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class OwnerOrdersActivity extends AppCompatActivity {

    private static final String TAG = "SNATCH";
    DatabaseReference reference;
    final ArrayList<Orders> ordersList = new ArrayList();
    ArrayList<String> parent = new ArrayList<>();
    OwnerOrdersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_orders);

        reference = FirebaseDatabase.getInstance().getReference().child("Orders");
        getOrderNumber();
        addUncompletedOrders();
        for (Orders o : ordersList){
            Log.d(TAG,"Order " + o.getOrderNumber());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView rv = findViewById(R.id.ordersRecyclerView);
        adapter = new OwnerOrdersAdapter(this, ordersList);
        rv.setAdapter(adapter);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    protected void getOrderNumber(){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    parent.add(childSnapshot.getKey());
                    Log.d(TAG,"order number is" + childSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void addUncompletedOrders(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                ordersList.clear();
                //add to list
                Iterator orderList = map.entrySet().iterator();
                String a = null;
                String b = null;
                String c = null;
                String d = null;
                String e = null;
                double f = 0;

                //iterate through database for all child items
                while(orderList.hasNext()){
                    Map.Entry mapElement = (Map.Entry)orderList.next();
                    Log.d(TAG,"map key is" + mapElement.getValue());
                    HashMap hash = (HashMap) mapElement.getValue();
                    Iterator IDLIST = hash.entrySet().iterator();
                    while (IDLIST.hasNext()) {

                        Map.Entry hashElement = (Map.Entry)IDLIST.next();

                        Log.d(TAG,"map key" + hashElement);
                        String details = (((String)hashElement.getValue().toString()));

                        if (hashElement.getKey().equals("foodCourt")){
                            a = (String) details;
                        }
                        else if (hashElement.getKey().equals("stall")){
                            b = (String) details;
                        }
                        else if(hashElement.getKey().equals("orderNumber")){
                            c = (String) details;
                        }
                        else if(hashElement.getKey().equals("orderFufilled")){
                            d = (String) details;
                        }
                        else if(hashElement.getKey().equals("dateTime")){
                            e = (String) details;
                        }
                        else if(hashElement.getKey().equals("totalCost")){
                            f = Double.parseDouble(details);
                        }
                        else if(hashElement.getKey().equals("OrderItems")){

                        }
                        else{
                            Log.d(TAG,"fail");
                        }
                        Random random = new Random();
                        Orders order = new Orders(a,b,String.valueOf(random.nextInt(10000)),d,e,f);
                        ordersList.add(order);
                        adapter.notifyDataSetChanged();
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG,"failed" , databaseError.toException());
            }
        });
    }
}

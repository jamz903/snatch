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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OwnerOrdersActivity extends AppCompatActivity {

    private static final String TAG = "SNATCH";
    DatabaseReference reference;
    final ArrayList<Orders> ordersList = new ArrayList();
    final ArrayList<Orders> orderListNoDuplicate = new ArrayList();
    OwnerOrdersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_orders);

        //Reference for firebase to get studentList
        reference = FirebaseDatabase.getInstance().getReference().child("Orders");
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
                int c = 0;
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
                            c = Integer.parseInt(details);
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
                        else{
                            Log.d(TAG,"fail");
                        }

                        Orders order = new Orders(a,b,c,d,e,f);
                        ordersList.add(order);
                        break;
                        /*for (Orders o : ordersList){
                            if(o.getOrderNumber() == order.getOrderNumber()){

                            }
                            else{
                                ordersList.add(order);
                            }
                            if(o.getOrderNumber() == 0){
                                ordersList.remove(o);
                            }
                            Log.d(TAG, "order number: " + o.getOrderNumber());
                        }*/

                        /*if(d.equals("no")){
                            Orders order = new Orders(a,b,c,d,e,f);
                            //add student to student list
                            ordersList.add(order);
                            Log.d(TAG, " " + ordersList.get(0).getOrderNumber());
                        }
                        else{
                            Log.d(TAG, "order completed, not added to list");
                        }*/
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

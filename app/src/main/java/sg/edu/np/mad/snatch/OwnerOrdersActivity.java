package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    final List<Orders> ordersList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_orders);

        //Reference for firebase to get studentList
        reference = FirebaseDatabase.getInstance().getReference().child("Orders");
        addUncompletedOrders();
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

                //iterate through database for all child items
                while(orderList.hasNext()){
                    Map.Entry mapElement = (Map.Entry)orderList.next();
                    Log.d(TAG,"map key is" + mapElement.getValue());


                    HashMap hash = (HashMap) mapElement.getValue();
                    Iterator IDLIST = hash.entrySet().iterator();
                    while (IDLIST.hasNext()) {

                        Map.Entry hashElement = (Map.Entry)IDLIST.next();

                        Log.d(TAG,"map key s" + hashElement);
                        String details = (((String)hashElement.getValue().toString()));

                        //check if item is an ID or PW before adding to list
                        if (hashElement.getKey().equals("FoodCourt")){
                            a = (String) details;
                        }
                        else if (hashElement.getKey().equals("Stall")){
                            b = (String) details;
                        }
                        else if(hashElement.getKey().equals("OrderNumber")){
                            c = Integer.parseInt(details);
                        }
                        else if(hashElement.getKey().equals("OrderFufilled")){
                            d = (String) details;
                        }
                        else if(hashElement.getKey().equals("DateTime")){
                            e = (String) details;
                        }
                        else{
                            Log.d(TAG,"fail");
                        }

                        Orders order = new Orders(a,b,c,d,e);
                        //add student to student list
                        ordersList.add(order);
                        Log.d(TAG, " " + ordersList.get(0).getOrderNumber());

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

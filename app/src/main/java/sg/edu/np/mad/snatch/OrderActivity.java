package sg.edu.np.mad.snatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ArrayList<OrderItem> shoppingCart;
    TextView grandTotalTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //create array list for shopping cart objects
        shoppingCart = (ArrayList<OrderItem>) getIntent().getSerializableExtra("OrderList");
        if (shoppingCart.size() == 0) {
            setContentView(R.layout.empty_order);
        }
        else {
            setContentView(R.layout.activity_order);
            grandTotalTextView = (TextView) findViewById(R.id.grandTotalTextView);
            initRecyclerView();
            double amount = calculateGrandTotal(shoppingCart);
            grandTotalTextView.setText("$" + String.format("%.2f", amount));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initRecyclerView() {
        RecyclerView rv = findViewById(R.id.recyclerViewOrder);

        //change line below for adapter if is other food stall
        OrderItemAdapter adapter = new OrderItemAdapter(this, shoppingCart);
        rv.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setItemAnimator(new DefaultItemAnimator());

        //adds a divider inbetween items
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
    }

    public double calculateGrandTotal(ArrayList<OrderItem> aShoppingCart) {
        double total = 0;
        for (OrderItem item : aShoppingCart) {
            total += item.getSubtotal();
        }
        return total;
    }
}

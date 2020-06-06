package sg.edu.np.mad.snatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class ReceiptActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<OrderItem> orderList;
    TextView receiptGrandTotalTextView;
    ReceiptAdapter adapter;
    TextView orderDateTimeTextView;
    TextView orderNoTextView;
    Button okBtn;
    boolean doubleClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        orderDateTimeTextView = findViewById(R.id.orderDateTimeTextView);
        TimeZone tz = TimeZone.getTimeZone("Asia/Singapore");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        sdf.setTimeZone(tz);
        String currentDateTime =  sdf.format(new Date());
        orderDateTimeTextView.setText(currentDateTime);

        orderNoTextView = findViewById(R.id.orderNoTextView);
        Random random = new Random();
        String orderNo = String.format("Order Number %04d", random.nextInt(10000));
        orderNoTextView.setText(orderNo);

        Intent receivingEnd = getIntent();
        orderList = (ArrayList<OrderItem>) receivingEnd.getSerializableExtra("orders");

        receiptGrandTotalTextView = findViewById(R.id.receiptGrandTotalTextView);
        receiptGrandTotalTextView.setText(String.format("Grand Total: $%.2f", calculateGrandTotal(orderList)));

        okBtn = findViewById(R.id.okBtn);
        okBtn.getBackground().setColorFilter(0xFF2a8cd6, PorterDuff.Mode.MULTIPLY);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView rv = findViewById(R.id.receiptsRecyclerView);

        adapter = new ReceiptAdapter(this, orderList);
        rv.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
    }

    public double calculateGrandTotal(ArrayList<OrderItem> aOrderList) {
        double total = 0;
        for (OrderItem item : aOrderList) {
            total += item.subtotal;
        }
        return total;
    }

    @Override
    public void onClick(View v) {
        Intent in = new Intent(this, HomescreenActivity.class);
        in.putExtra("Ordered", "Ordered");
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
        ReceiptActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        //users cannot go back to cart after placing order
        if(doubleClick){
            //to notify them that it's an app function
            AlertDialog.Builder builder = new AlertDialog.Builder(ReceiptActivity.this);
            builder.setTitle("You cannot go back to cart at this stage")
                    .setCancelable(false)
                    .setMessage("Please click back to continue viewing receipt or click go to homepage to continue")
                    .setPositiveButton("Go to Homepage", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent in = new Intent(ReceiptActivity.this, HomescreenActivity.class);
                            in.putExtra("Ordered", "Ordered");
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(in);
                            ReceiptActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
            this.doubleClick = false;
        }
        else{
            this.doubleClick = true;
        }
    }

}

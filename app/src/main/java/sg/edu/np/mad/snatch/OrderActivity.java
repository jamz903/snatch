package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements orderItemAdapterCallback, View.OnClickListener {

    ArrayList<OrderItem> shoppingCart;
    TextView grandTotalTextView;
    OrderItemAdapter adapter;
    Button placeOrderBtn;
    //Help pop-up dialog
    Dialog helpDialog;
    ImageView close;
    Button getHelpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create array list for shopping cart objects
        shoppingCart = (ArrayList<OrderItem>) getIntent().getSerializableExtra("OrderList");

        //checks if cart is empty and sets layout
        if (shoppingCart.size() == 0) {
            setContentView(R.layout.empty_order);
        }
        else { //lists the cart items through recyclerview
            setContentView(R.layout.activity_order);
            grandTotalTextView = (TextView) findViewById(R.id.grandTotalTextView);
            initRecyclerView();
            double amount = calculateGrandTotal(shoppingCart);
            grandTotalTextView.setText("$" + String.format("%.2f", amount));
            placeOrderBtn = findViewById(R.id.placeOrderBtn);
            placeOrderBtn.getBackground().setColorFilter(0xFF2a8cd6, PorterDuff.Mode.MULTIPLY);
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
        adapter = new OrderItemAdapter(this, shoppingCart, this);
        rv.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setItemAnimator(new DefaultItemAnimator());

        //adds a divider inbetween items
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
    }

    //calculates total for items in cart
    public double calculateGrandTotal(ArrayList<OrderItem> aShoppingCart) {
        double total = 0;
        for (OrderItem item : aShoppingCart) {
            total += item.getSubtotal();
        }
        return total;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /*if(item.getItemId() == R.id.credits_option){
            to be implemented later on in phase 2
        }*/

        //Food courts' vacancy button on kebab icon on top right corner of the app
        //brings user to see the number of people in each food court
        if (item.getItemId() == R.id.vacancy_option) {
            Intent in = new Intent(this, FoodCourtVacancyActivity.class);
            startActivity(in);
        }

        //logout button on kebab icon on top right corner of the app
        //brings user to log in page
        else if(item.getItemId() == R.id.logout_option){
            //shared preferences
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "false");
            editor.apply();

            Intent signIn = new Intent(OrderActivity.this,MainActivity.class);
            //clears backstack so user cannot click back to go back to main page of application after logging out
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
            finish();
        }

        else if(item.getItemId() == R.id.help_option){
            helpDialog = new Dialog(this);
            ShowPopUp();
        }

        return super.onOptionsItemSelected(item);
    }

    //to remove order item from cart and from layout
    @Override
    public void removeOrderItem(OrderItem item, int position) {
        //"deduct" item quantity
        if (item.quantity > 1) {
            double oldSubtotal = item.subtotal;
            item.subtotal = oldSubtotal / item.quantity * (item.quantity - 1);
            item.quantity -= 1;
        }
        else {
            shoppingCart.remove(position);
        }
        TextView grandTotalTextView = (TextView) findViewById(R.id.grandTotalTextView);
        grandTotalTextView.setText("$" + String.format("%.2f", calculateGrandTotal(shoppingCart)));
        adapter.notifyDataSetChanged();
        if (shoppingCart.size() == 0) {
            setContentView(R.layout.empty_order);
        }
    }

    @Override
    public void removeAllOrderItems(int position) {
        shoppingCart.remove(position);
        TextView grandTotalTextView = (TextView) findViewById(R.id.grandTotalTextView);
        grandTotalTextView.setText("$" + String.format("%.2f", calculateGrandTotal(shoppingCart)));
        adapter.notifyDataSetChanged();
        if (shoppingCart.size() == 0) {
            setContentView(R.layout.empty_order);
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", shoppingCart);
        setResult(Activity.RESULT_OK, returnIntent);
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {
        //checks if there is no internet connection, if no internet, informs user that login is not possible without an internet connection
        if(getConnectionType(OrderActivity.this)){
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
            builder.setTitle("No Internet Connection")
                    .setCancelable(false)
                    .setMessage("You currently have no internet connection. Internet is required to proceed.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        else{
            //starts new activity
            Intent in = new Intent(this, ReceiptActivity.class);
            in.putExtra("orders", shoppingCart);
            startActivity(in);
        }
    }

    public static boolean getConnectionType(Context context) {
        boolean result = true; // If there is no internet connection, bool returns true
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                // if there is internet connection, regardless of what type (wifi, cellular, vpn) return false
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = false;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = false;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                        result = false;
                    }
                }
            }
            else { //for older devices
                if (cm != null) {
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    if (activeNetwork != null) {
                        // connected to the internet
                        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                            result = false;
                        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                            result = false;
                        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_VPN) {
                            result = false;
                        }
                    }
                }
            }
        }
        return result;
    }

    public void ShowPopUp(){
        helpDialog.setContentView(R.layout.help_layout);
        close = (ImageView) helpDialog.findViewById(R.id.close);
        getHelpButton = (Button) helpDialog.findViewById(R.id.getHelpButton);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.dismiss();
            }
        });

        getHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: create a google form to link
                //sends user to google form to give feedback
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/gsgmyWWp17vvxF7e8"));
                startActivity(in);
            }
        });
        //sets the background to 'blur' so that the pop up dialog is clearer
        helpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        helpDialog.show();
    }
}

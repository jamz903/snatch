package sg.edu.np.mad.snatch;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    //created necessary variables
    Context context;
    ArrayList<OrderItem> shoppingCart;
    orderItemAdapterCallback listener;

    public OrderItemAdapter(Context aContext, ArrayList<OrderItem> aShoppingCart, orderItemAdapterCallback aListener) {
        this.context = aContext;
        this.shoppingCart = aShoppingCart;
        this.listener = aListener;
    }


    //View holder for recycler view
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_listitem, parent, false);

        return new OrderViewHolder(v);
    }

    //Assign variables for shopping cart items.
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, final int position) {
        if (position < shoppingCart.size()) {
            OrderItem order = shoppingCart.get(position);
            int information1 = order.quantity;
            holder.quantityTextView.setText(String.valueOf(information1));

            String information2 = order.foodName;
            holder.foodOrderNameTextView.setText(information2);

            Double information3 = order.subtotal;
            holder.subtotalTextView.setText(String.format("$%.2f", information3));
        }
        holder.parentLayoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Remove item from cart?")
                        .setMessage("Would you like to remove " + shoppingCart.get(position).foodName + " from your cart?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.removeOrderItem(shoppingCart.get(position), position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingCart.size();
    }
}

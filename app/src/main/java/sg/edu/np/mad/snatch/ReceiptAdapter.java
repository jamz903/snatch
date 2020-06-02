package sg.edu.np.mad.snatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptViewholder> {

    Context context;
    ArrayList<OrderItem> shoppingCart;
    public ReceiptAdapter(Context aContext, ArrayList<OrderItem> aShoppingCart) {
        this.context = aContext;
        this.shoppingCart = aShoppingCart;
    }

    @NonNull
    @Override
    public ReceiptViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_listitem, parent, false);

        return new ReceiptViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptViewholder holder, int position) {
        if (position < shoppingCart.size()) {
            OrderItem order = shoppingCart.get(position);
            int information1 = order.quantity;
            holder.quantityTextView.setText(String.valueOf(information1));

            String information2 = order.foodName;
            holder.foodNameTextView.setText(information2);

            Double information3 = order.subtotal;
            holder.subtotalTextView.setText(String.format("$%.2f", information3));
        }

    }

    @Override
    public int getItemCount() {
        return shoppingCart.size();
    }
}

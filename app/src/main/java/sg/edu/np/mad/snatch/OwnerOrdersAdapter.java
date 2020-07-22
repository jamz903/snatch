package sg.edu.np.mad.snatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OwnerOrdersAdapter extends RecyclerView.Adapter<OwnerOrdersViewholder> {

    Context context;
    ArrayList<Orders> ordersArrayList;
    public OwnerOrdersAdapter(Context aContext, ArrayList<Orders> aOrdersArrayList){
        this.context = aContext;
        this.ordersArrayList = aOrdersArrayList;
    }
    @NonNull
    @Override
    public OwnerOrdersViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_orders_listitem, parent, false);
        return new OwnerOrdersViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerOrdersViewholder holder, int position) {

        if (position < ordersArrayList.size()){
            Orders order = ordersArrayList.get(position);
            String info1 = order.getOrderNumber();
            String orderNumber = "Order #" + info1;
            holder.orderNumberTextView.setText(orderNumber);

            String info2 = order.getDateTime();
            holder.dateTextView.setText(info2);
        }
    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }
}

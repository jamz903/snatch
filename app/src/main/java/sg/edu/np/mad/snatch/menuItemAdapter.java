package sg.edu.np.mad.snatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class menuItemAdapter extends RecyclerView.Adapter<menuItemViewHolder>{
    ArrayList<FoodItem> menuItems;
    ArrayList<Integer> itemImageIDs;
    menuItemAdapterCallback listener;

    public menuItemAdapter(ArrayList<FoodItem> aMenuItems, ArrayList<Integer> aItemImageIDs, menuItemAdapterCallback aListener) {
        this.itemImageIDs = aItemImageIDs;
        this.menuItems = aMenuItems;
        this.listener = aListener;
    }

    @NonNull
    @Override
    public menuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menus_listitem, parent, false);

        return new menuItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull menuItemViewHolder holder, final int position) {

        if (position < menuItems.size()) {
            FoodItem dish = menuItems.get(position);
            String information1 = dish.foodName;
            holder.foodNameTextView.setText(information1);

            String information2 = dish.foodDesc;
            holder.foodDescTextView.setText(information2);

            Double information3 = dish.price;
            holder.priceTextView.setText(String.format("$%.2f", information3));

            int imageID = itemImageIDs.get(position);
            holder.foodImageView.setImageResource(imageID);
        }

        holder.parentLayoutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.promptAddItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}

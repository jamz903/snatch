package sg.edu.np.mad.snatch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class vacancyRVAdapter extends RecyclerView.Adapter<vacancyViewHolder> {

    Context context;
    ArrayList<FoodCourt> foodCourtList;
    vacancyRVAdapterCallback listener;

    public vacancyRVAdapter(Context context, ArrayList<FoodCourt> aFoodCourtList, vacancyRVAdapterCallback aListener) {
        this.context = context;
        this.foodCourtList = aFoodCourtList;
        this.listener = aListener;
    }

    @NonNull
    @Override
    public vacancyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vacancy_food_court_listitem, parent, false);

        return new vacancyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vacancyViewHolder holder, final int position) {

        final FoodCourt foodCourt = foodCourtList.get(position);
        holder.bind(foodCourt);

        ArrayList<FoodItem> top3Dishes = foodCourt.popularDishes;
        Collections.sort(top3Dishes);

        if (foodCourt.getName().equals("FoodClub")) {
            holder.listitem_foodCourtNameTextView.setText("Food Club");
        }
        else if (foodCourt.getName().equals("Makan Place")) {
            holder.listitem_foodCourtNameTextView.setText("Makan Place");
        }
        else if (foodCourt.getName().equals("Poolside")) {
            holder.listitem_foodCourtNameTextView.setText("Poolside");
        }
        else {
            holder.listitem_foodCourtNameTextView.setText("Munch");
        }

        // Display the Food Court seating capacity
        holder.capacityTextView.setText("Seating Capacity: " + String.valueOf(foodCourt.capacity));

        // Retrieving the top 3 dishes and displaying in the Food Court list item
        final FoodItem dish1 = top3Dishes.get(0);
        String visitText = "Visit Store";
        holder.foodNameTextView1.setText(dish1.foodName);
        holder.visitTextView1.setText(visitText);
        holder.dishImageView1.setImageResource(dish1.imageID);

        final FoodItem dish2 = top3Dishes.get(1);
        holder.foodNameTextView2.setText(dish2.foodName);
        holder.visitTextView2.setText(visitText);
        holder.dishImageView2.setImageResource(dish2.imageID);

        final FoodItem dish3 = top3Dishes.get(2);
        holder.foodNameTextView3.setText(dish3.foodName);
        holder.visitTextView3.setText(visitText);
        holder.dishImageView3.setImageResource(dish3.imageID);

        // To expand/un-expand the row in Recycler View when it is clicked
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean expanded = foodCourt.isExpanded();
                foodCourt.setExpanded(!expanded);
                notifyItemChanged(position);
            }
        });

        // Brings user to the food stall's menu when user selects "Visit Food Store" option
        holder.visitTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.visitFoodStall(dish1.foodCourt, dish1.stallName);
            }
        });

        holder.visitTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.visitFoodStall(dish2.foodCourt, dish2.stallName);
            }
        });

        holder.visitTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.visitFoodStall(dish3.foodCourt, dish3.stallName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodCourtList.size();
    }

}

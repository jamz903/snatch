package sg.edu.np.mad.snatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

public class menuItemAdapter extends RecyclerView.Adapter<menuItemViewHolder>{

    //creating required lists
    ArrayList<FoodItem> menuItems;
    menuItemAdapterCallback listener;
    Button upvote;
    DatabaseReference reff2 = FirebaseDatabase.getInstance().getReference().child("FoodCourt").child(HomescreenActivity.firebaseStall).child(StoresAdapter.firebaseStoreName);

    //Assigning items
    public menuItemAdapter(ArrayList<FoodItem> aMenuItems, menuItemAdapterCallback aListener) {
        this.menuItems = aMenuItems;
        this.listener = aListener;
    }

    @NonNull
    @Override


    public menuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //get View holder for recycler
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menus_listitem, parent, false);

        return new menuItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final menuItemViewHolder holder, final int position) {


        //set format for menu items
        if (position < menuItems.size()) {
            FoodItem dish = menuItems.get(position);
            String information1 = dish.foodName;
            holder.foodNameTextView.setText(information1);

            String information2 = dish.foodDesc;
            holder.foodDescTextView.setText(information2);

            Double information3 = dish.price;
            holder.priceTextView.setText(String.format("$%.2f", information3));

            //pretty sure we need to change smth (TBD)
            int information4 = dish.imageID;
            holder.foodImageView.setImageResource(information4);

        }

        holder.parentLayoutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.promptAddItem(position);
            }
        });


        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                String string = "Are you sure you want to upvote " + holder.foodNameTextView.getText() + "?";
                builder.setTitle(string);
                builder.setMessage("You will not be able to retract this vote.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String dish = String.valueOf(holder.foodNameTextView.getText());
                                checkDish(dish);
                                String text = dish + " succesfully upvoted.";
                                Toast.makeText(v.getContext(), text, Toast.LENGTH_SHORT).show();
                                menuItems.get(position).upVotes++;
                                Collections.sort(menuItems);
                                notifyDataSetChanged();
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
        return menuItems.size();
    }

    public void checkDish(final String dish){
        String firebaseDish = dish.replaceAll("\\s+","");
        updateUpvote(firebaseDish);
    }

    public void updateUpvote(final String firebaseDish){
        reff2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dishName = dataSnapshot.child(firebaseDish);
                long upVotes = (long)dishName.getValue();
                long upVotesUpdated = upVotes + 1;
                Object upVotesObject = (Object) upVotesUpdated;
                reff2.child(firebaseDish).setValue(upVotesObject);
                String string = "Updated value to " + upVotes;
                Log.d("value",string);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

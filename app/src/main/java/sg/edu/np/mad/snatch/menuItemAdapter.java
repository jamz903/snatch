package sg.edu.np.mad.snatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
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
    DatabaseReference reff2;

    //Assigning items
    public menuItemAdapter(ArrayList<FoodItem> aMenuItems, menuItemAdapterCallback aListener) {
        this.menuItems = aMenuItems;
        this.listener = aListener;

        if (HomescreenActivity.firebaseStall == null && StoresAdapter.firebaseStoreName == null) {
            String foodCourtString = aMenuItems.get(0).foodCourt.replaceAll("\\s+", "");
            String foodStallString = aMenuItems.get(0).stallName.replaceAll("\\s+", "");

            reff2 = FirebaseDatabase.getInstance().getReference().child("FoodCourt").child(foodCourtString).child(foodStallString);
        }
        else {
            reff2 = FirebaseDatabase.getInstance().getReference().child("FoodCourt").child(HomescreenActivity.firebaseStall).child(StoresAdapter.firebaseStoreName);
        }
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

        holder.upvote.getBackground().setColorFilter(0xFF2a8cd6, PorterDuff.Mode.MULTIPLY);
        //set format for menu items
        if (position < menuItems.size()) {
            FoodItem dish = menuItems.get(position);
            String information1 = dish.foodName;
            holder.foodNameTextView.setText(information1);

            String information2 = dish.foodDesc;
            holder.foodDescTextView.setText(information2);

            Double information3 = dish.price;
            holder.priceTextView.setText(String.format("$%.2f", information3));

            int information4 = dish.imageID;
            holder.foodImageView.setImageResource(information4);

            int information5 = dish.getUpVotes();
            holder.numUpvotesTextView.setText(String.valueOf(information5));

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
                //checks if there is no internet connection, if no internet, informs user that login is not possible without an internet connection
                if(getConnectionType(holder.upvote.getContext())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.upvote.getContext());
                    builder.setTitle("No Internet Connection")
                            .setCancelable(false)
                            .setMessage("You currently have no internet connection. Internet is required to proceed. Upvote Scores currently displayed may also be inaccurate.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
                else{
                    //confirms with user about upvoting dish
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
                                    //sorts items based on upvoteNo (sorted based on CompareTo in FoodItem class)
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

            }
        });





    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    //gets the name of dish that user is upvoting and updates upvoteNo of dish in firebase
    public void checkDish(final String dish){
        String firebaseDish = dish.replaceAll("\\s+","");
        updateUpvote(firebaseDish);
    }

    //method to be called to update upvoteNo of dish in firebase
    public void updateUpvote(final String firebaseDish){
        reff2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //obtains dish name
                DataSnapshot dishName = dataSnapshot.child(firebaseDish);
                long upVotes = (long)dishName.getValue();
                long upVotesUpdated = upVotes + 1;
                Object upVotesObject = (Object) upVotesUpdated;
                //sets value in firebase
                reff2.child(firebaseDish).setValue(upVotesObject);
                String string = "Updated value to " + upVotes;
                Log.d("value",string);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
}

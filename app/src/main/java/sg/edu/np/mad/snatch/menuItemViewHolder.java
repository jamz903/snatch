package sg.edu.np.mad.snatch;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class menuItemViewHolder extends RecyclerView.ViewHolder {
    TextView foodNameTextView;
    TextView foodDescTextView;
    TextView priceTextView;
    ImageView foodImageView;
    ConstraintLayout parentLayoutMenu;
    Button upvote;
    DatabaseReference reff2;
    public menuItemViewHolder(@NonNull View itemView) {
        super(itemView);
        foodNameTextView = itemView.findViewById(R.id.foodNameTextView);
        foodDescTextView = itemView.findViewById(R.id.foodDescTextView);
        priceTextView = itemView.findViewById(R.id.priceTextView);
        foodImageView = itemView.findViewById(R.id.foodImageView);
        parentLayoutMenu = itemView.findViewById(R.id.parentLayoutMenu);
        upvote = itemView.findViewById(R.id.upvote);
        upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dish = String.valueOf(foodNameTextView.getText());
                checkDish(dish);
                String text = dish + " succesfully upvoted.";
                Toast.makeText(v.getContext(), text, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void checkDish(final String dish){
        reff2 = FirebaseDatabase.getInstance().getReference().child("FoodCourt").child("FoodClub").child("JapaneseFood");//Set to foodClub for now
        final int[] urmom = {0};
        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dish.equals("Japanese Curry Chicken Katsu") && urmom[0] == 0){
                    DataSnapshot dishName = dataSnapshot.child("JapChickenKatsu");
                    long upVotes = (long)dishName.getValue();
                    long upVotesUpdated = upVotes + 1;
                    Object upVotesObject = (Object) upVotesUpdated;
                    reff2.child("JapChickenKatsu").setValue(upVotesObject);
                    String string = "Updated value to " + upVotes;
                    Log.d("value",string);
                    urmom[0]++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("eh","this was cancelled");
            }
        });
    }
}

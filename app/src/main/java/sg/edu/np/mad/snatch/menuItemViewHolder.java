package sg.edu.np.mad.snatch;

import android.util.Log;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.List;

public class menuItemViewHolder extends RecyclerView.ViewHolder {

    //Assigning required widgets

    TextView foodNameTextView;
    TextView foodDescTextView;
    TextView priceTextView;
    ImageView foodImageView;
    ConstraintLayout parentLayoutMenu;
    Button upvote;

    public menuItemViewHolder(@NonNull View itemView) {
        super(itemView);

        //Assign view items to variables
        foodNameTextView = itemView.findViewById(R.id.foodNameTextView);
        foodDescTextView = itemView.findViewById(R.id.foodDescTextView);
        priceTextView = itemView.findViewById(R.id.priceTextView);
        foodImageView = itemView.findViewById(R.id.foodImageView);
        parentLayoutMenu = itemView.findViewById(R.id.parentLayoutMenu);
        upvote = itemView.findViewById(R.id.upvote);
    }
}

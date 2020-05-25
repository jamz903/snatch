package sg.edu.np.mad.snatch;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class menuItemViewHolder extends RecyclerView.ViewHolder {
    TextView foodNameTextView;
    TextView foodDescTextView;
    TextView priceTextView;
    ImageView foodImageView;
    ConstraintLayout parentLayoutMenu;
    Button upvote;
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
                String text = dish + " succesfully upvoted.";
                Toast.makeText(v.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

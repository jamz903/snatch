package sg.edu.np.mad.snatch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class vacancyViewHolder extends RecyclerView.ViewHolder {

    TextView foodNameTextView1;
    ImageView listitem_foodCourtImageView;
    TextView listitem_foodCourtNameTextView;
    TextView capacityTextView;
    ImageView dishImageView1;
    ImageView dishImageView2;
    ImageView dishImageView3;
    ConstraintLayout parentLayout;
    ConstraintLayout expandLayout;
    TextView foodNameTextView2;
    TextView foodNameTextView3;
    TextView visitTextView1;
    TextView visitTextView2;
    TextView visitTextView3;

    public vacancyViewHolder(@NonNull View itemView) {
        super(itemView);

        foodNameTextView1 = itemView.findViewById(R.id.foodNameTextView1);
        listitem_foodCourtImageView = itemView.findViewById(R.id.listitem_foodCourtImageView);
        listitem_foodCourtNameTextView = itemView.findViewById(R.id.listitem_foodCourtNameTextView);
        capacityTextView = itemView.findViewById(R.id.capacityTextView);
        dishImageView1 = itemView.findViewById(R.id.dishImageView1);
        dishImageView2 = itemView.findViewById(R.id.dishImageView2);
        dishImageView3 = itemView.findViewById(R.id.dishImageView3);
        parentLayout = itemView.findViewById(R.id.parentLayout);
        expandLayout = itemView.findViewById(R.id.expandLayout);
        foodNameTextView1 = itemView.findViewById(R.id.foodNameTextView1);
        foodNameTextView2 = itemView.findViewById(R.id.foodNameTextView2);
        foodNameTextView3 = itemView.findViewById(R.id.foodNameTextView3);
        visitTextView1 = itemView.findViewById(R.id.visitTextView1);
        visitTextView2 = itemView.findViewById(R.id.visitTextView2);
        visitTextView3 = itemView.findViewById(R.id.visitTextView3);
    }

    public void bind(FoodCourt foodCourt) {
        // Get the state
        boolean expanded = foodCourt.isExpanded();
        // Set the visibility based on state
        expandLayout.setVisibility(expanded ? View.VISIBLE : View.GONE);
    }
}

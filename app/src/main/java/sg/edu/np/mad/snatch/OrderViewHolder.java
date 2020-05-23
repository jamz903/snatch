package sg.edu.np.mad.snatch;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    TextView foodOrderNameTextView;
    TextView subtotalTextView;
    TextView quantityTextView;
    ConstraintLayout parentLayoutOrder;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        foodOrderNameTextView = itemView.findViewById(R.id.foodOrderNameTextView);
        subtotalTextView = itemView.findViewById(R.id.subtotalTextView);
        quantityTextView = itemView.findViewById(R.id.quantityTextView);
        parentLayoutOrder = itemView.findViewById(R.id.parentLayoutOrder);
    }
}

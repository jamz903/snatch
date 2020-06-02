package sg.edu.np.mad.snatch;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiptViewholder extends RecyclerView.ViewHolder {

    TextView quantityTextView;
    TextView foodNameTextView;
    TextView subtotalTextView;
    ConstraintLayout parentLayout;

    public ReceiptViewholder(@NonNull View itemView) {
        super(itemView);

        quantityTextView = itemView.findViewById(R.id.quantityTextView);
        foodNameTextView = itemView.findViewById(R.id.foodOrderNameTextView);
        subtotalTextView = itemView.findViewById(R.id.subtotalTextView);
        parentLayout = itemView.findViewById(R.id.parentLayoutOrder);
    }
}

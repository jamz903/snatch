package sg.edu.np.mad.snatch;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OwnerOrdersViewholder extends RecyclerView.ViewHolder {
    TextView orderNumberTextView;
    TextView dateTextView;

    public OwnerOrdersViewholder(@NonNull View itemView) {
        super(itemView);
        orderNumberTextView = itemView.findViewById(R.id.orderNumberTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
    }
}

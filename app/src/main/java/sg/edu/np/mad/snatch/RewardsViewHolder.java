package sg.edu.np.mad.snatch;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RewardsViewHolder extends RecyclerView.ViewHolder {
    TextView RewardstxtV;
    TextView PricetxtV;

    public RewardsViewHolder(@NonNull View itemView){
        super(itemView);
        RewardstxtV = itemView.findViewById(R.id.rewardsTextView);
        PricetxtV = itemView.findViewById(R.id.priceTextView);

    }

}

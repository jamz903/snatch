package sg.edu.np.mad.snatch;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RewardsViewHolder extends RecyclerView.ViewHolder {
    TextView RewardstxtV;
    TextView PricetxtV;
    Button CfmRedeem;

    public RewardsViewHolder(@NonNull View itemView){
        super(itemView);
        RewardstxtV = itemView.findViewById(R.id.rewardsTextView);
        PricetxtV = itemView.findViewById(R.id.priceTextView);
        CfmRedeem = itemView.findViewById(R.id.cfmredeem);

    }

}

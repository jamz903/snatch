package sg.edu.np.mad.snatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsViewHolder> {

    Context context;
    ArrayList<Rewards> RewardsArrayList;
    public RewardsAdapter(Context context, ArrayList<Rewards> RewardsArrayList){
        this.context = context;
        this.RewardsArrayList = RewardsArrayList;
    }

    @NonNull
    @Override
    public RewardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_option,parent,false);
        return new RewardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsViewHolder holder, int position) {
        if(position< RewardsArrayList.size()){
            Rewards rewards = RewardsArrayList.get(position);
            String info1 = rewards.getRewardsID();
            String rewardNumber = "Order #" + info1; //use this to change to names later on(rhsy)
            holder.RewardstxtV.setText(rewardNumber);

            String info2 = rewards.getPrice();
            holder.PricetxtV.setText(info2);

        }
    }

    @Override
    public int getItemCount() {
        return RewardsArrayList.size();
    }
}

package sg.edu.np.mad.snatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

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
            final Rewards rewards = RewardsArrayList.get(position);
            final String info1 = rewards.getRewardsID();
            String rewardNumber = rewards.getDesc(); //use this to change to names later on(rhsy)
            holder.RewardstxtV.setText(rewardNumber);

            final String info2 = rewards.getPrice();
            holder.PricetxtV.setText(info2);

            holder.CfmRedeem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(MainActivity.userpoints < Integer.parseInt(info2)){
                        Log.d("rewards", "User does not have enough points");
                    }
                    else{
                        SharedPreferences preferences = context.getSharedPreferences("checkbox", MODE_PRIVATE);
                        final String checkbox = preferences.getString("remember","");
                        final String studentID = preferences.getString("studentID","");

                        DatabaseReference updatePoints;
                        if (checkbox.equals("false")){
                            updatePoints = FirebaseDatabase.getInstance().getReference().child("Students").child(MainActivity.usingID).child("studentPoints");
                        }
                        else{
                            updatePoints = FirebaseDatabase.getInstance().getReference().child("Students").child(studentID).child("studentPoints");
                        }


                        updatePoints.setValue(MainActivity.userpoints - (Integer.parseInt(info2)));
                        Log.d("rewards", "deduction sueccessfulk");

                        //delete from database
                        FirebaseDatabase.getInstance().getReference().child("Rewards").child(info1).removeValue();

                        AlertDialog.Builder codealert = new AlertDialog.Builder(context);
                        codealert.setTitle("Heres your code! Remember to screenshot it for future use!");
                        codealert.setMessage(rewards.getRedeemed());
                        codealert.setCancelable(false);
                        codealert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                context.startActivity(new Intent(context,HomescreenActivity.class));

                            }
                        });

                        AlertDialog showDialog = codealert.create();
                        showDialog.show();


                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return RewardsArrayList.size();
    }
}

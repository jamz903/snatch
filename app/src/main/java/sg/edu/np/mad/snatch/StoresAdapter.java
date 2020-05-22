package sg.edu.np.mad.snatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoresAdapter extends RecyclerView.Adapter<StoresViewHolder>{
    Context context;
    ArrayList<String> storeName;
    ArrayList<String> storeDesc;
    StoresAdapterCallback listener;

    public StoresAdapter(ArrayList<String> aStoreName, ArrayList<String> aStoreDesc, Context context, StoresAdapterCallback aListener){
        storeName = aStoreName;
        storeDesc = aStoreDesc;
        this.context = context;
        listener = aListener;
    }
    @NonNull
    @Override
    public StoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stores_listitem,null);

        return new StoresViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StoresViewHolder holder, final int position) {
        String information1 = storeName.get(position);
        holder.storeName.setText(information1);

        String information2 = storeDesc.get(position);
        holder.storeDesc.setText(information2);

        //clickable list view of stalls
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String string = storeName.get(position); //show store name
                //Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
                listener.promptFoodStore(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeName.size();
    }
}

package sg.edu.np.mad.snatch;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class StoresViewHolder extends RecyclerView.ViewHolder{
    TextView storeName;
    TextView storeDesc;
    ConstraintLayout parentLayoutStores;
    public StoresViewHolder(@NonNull View itemView) {
        super(itemView);
        storeName = itemView.findViewById(R.id.stallNameTextView);
        storeDesc = itemView.findViewById(R.id.stallDescTextView);
        parentLayoutStores = itemView.findViewById(R.id.parentLayoutStores); //constraint layout of each row item (stores_listitem.xml)
    }
}

package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;


public class FoodClubActivity extends AppCompatActivity implements StoresAdapterCallback {

    //Lists for Store activities
    ArrayList<String> storeName = new ArrayList<>(); //stores store name
    ArrayList<String> storeDesc = new ArrayList<>(); //stores store description
    String foodCourt;
    Intent receivingEnd;
    //Help pop-up dialog
    Dialog helpDialog;
    ImageView close;
    Button getHelpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_club); //sets the custom created layout

        //receives foodcourt name (using intent string putExtra) from HomescreenActivity
        receivingEnd = getIntent();
        foodCourt = receivingEnd.getStringExtra("FoodCourt");
        storeNames(storeName, foodCourt);
        storeDescs(storeDesc, foodCourt);

        RecyclerView rv = findViewById(R.id.recyclerView1);

        //Recycler view adapter
        StoresAdapter adapter = new StoresAdapter(storeName,storeDesc,this, this);
        rv.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setItemAnimator(new DefaultItemAnimator());

        //adds a divider in-between items
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

    }

    private static void storeNames(ArrayList<String> storeName, String aFoodCourt){
        //store names
        if (aFoodCourt.equals("FoodClub")) {
            storeName.add("Japanese Food");
            storeName.add("Bak Kut Teh");
            storeName.add("Ban Mian");
            storeName.add("Western");
            storeName.add("Indonesian");
            storeName.add("Drinks Stall");
            storeName.add("Yogurt");
            storeName.add("Mini Wok");
            storeName.add("Mala");
            storeName.add("Thai");
            storeName.add("Chicken Rice");
            storeName.add("Economical Rice");
            storeName.add("FC Bakery");
        }
        else if (aFoodCourt.equals("MKP")) {
            storeName.add("Mala");
            storeName.add("Chicken Rice");
            storeName.add("Drinks Stall");
            storeName.add("Japanese Food");
        }
        else if (aFoodCourt.equals("Munch")) {
            storeName.add("Japanese");
            storeName.add("Mala (Halal)");
            storeName.add("Western");
        }
        else if (aFoodCourt.equals("Poolside")) {
            storeName.add("Henry's Western");
        }
    }

    private static void storeDescs(ArrayList<String> storeDesc, String aFoodCourt){
        //store descriptions
        if (aFoodCourt.equals("FoodClub")) {
            storeDesc.add("This is the Japanese Stall");
            storeDesc.add("This is the Bak Kut Teh Stall");
            storeDesc.add("This is the Ban Mian Stall");
            storeDesc.add("This is the Western Stall");
            storeDesc.add("This is the Indonesian Stall");
            storeDesc.add("This is the Drinks Stall");
            storeDesc.add("This is the Yogurt Stall");
            storeDesc.add("This is the Mini Wok Stall");
            storeDesc.add("This is the Mala Stall");
            storeDesc.add("This is the Thai Stall");
            storeDesc.add("This is the Chicken Rice Stall");
            storeDesc.add("This is the Economical Rice Stall");
            storeDesc.add("This is the FC Bakery Stall");
        }
        else if (aFoodCourt.equals("MKP")) {
            storeDesc.add("This is the Mala Stall");
            storeDesc.add("This is the Chicken Rice Stall");
            storeDesc.add("This is the Drinks Stall");
            storeDesc.add("This is the Japanese Food Stall");
        }
        else if (aFoodCourt.equals("Munch")) {
            storeDesc.add("This is the Japanese Stall");
            storeDesc.add("This is the Mala (Halal) Stall");
            storeDesc.add("This is the Western Stall");
        }
        else if (aFoodCourt.equals("Poolside")) {
            storeDesc.add("This is Henry's Western Stall");
        }
    }


    @Override
    public void promptFoodStore(final int aPosition) {

        //prompt when user clicks on name of food store

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Go to Food Stall?")
                .setCancelable(true)
                .setMessage("Would you like to see " + storeName.get(aPosition) + " menu?")

                // Yes/No options and actions
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent in = new Intent(FoodClubActivity.this, stallMenuActivity.class);
                        in.putExtra("FoodCourt", foodCourt);
                        in.putExtra("Stall", storeName.get(aPosition));
                        startActivity(in);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        //Show alert
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /*if(item.getItemId() == R.id.credits_option){
            to be implemented later on in phase 2
        }*/

        //Food courts' vacancy button on kebab icon on top right corner of the app
        //brings user to see the number of people in each food court
        if (item.getItemId() == R.id.vacancy_option) {
            Intent in = new Intent(this, FoodCourtVacancyActivity.class);
            startActivity(in);
        }

        //logout button on kebab icon on top right corner of the app
        //brings user to log in page
        else if(item.getItemId() == R.id.logout_option){
            //shared preferences
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "false");
            editor.apply();

            Intent signIn = new Intent(FoodClubActivity.this,MainActivity.class);
            //clears backstack so user cannot click back to go back to main page of application after logging out
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
            finish();
        }

        else if(item.getItemId() == R.id.help_option){
            helpDialog = new Dialog(this);
            ShowPopUp();
        }

        return super.onOptionsItemSelected(item);
    }

    public void ShowPopUp(){
        helpDialog.setContentView(R.layout.help_layout);
        close = (ImageView) helpDialog.findViewById(R.id.close);
        getHelpButton = (Button) helpDialog.findViewById(R.id.getHelpButton);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.dismiss();
            }
        });

        getHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: create a google form to link
                //sends user to google form to give feedback
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.sg"));
                startActivity(in);
            }
        });
        //sets the background to 'blur' so that the pop up dialog is clearer
        helpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        helpDialog.show();
    }
}

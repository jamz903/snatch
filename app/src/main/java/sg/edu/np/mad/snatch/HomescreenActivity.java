package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HomescreenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner dropdownList;
    ArrayAdapter<CharSequence> adapter;
    public static String firebaseStall;
    boolean doubleClickToExit = false;
    TextView welcomeMessage;
    private int[] mImages = new int[]{R.drawable.steak, R.drawable.fastfood, R.drawable.desert};
    TextView pointsTextView;
    //Help pop-up dialog
    Dialog helpDialog;
    ImageView close;
    Button getHelpButton;

    DatabaseReference reff;
    final List<Students> studentsList = new ArrayList();
    private static final String TAG = "snatchworks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        //Reference for firebase to get studentList
        reff = FirebaseDatabase.getInstance().getReference().child("Students");
        if (getConnectionType(HomescreenActivity.this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(HomescreenActivity.this);
            builder.setTitle("No Internet Connection")
                    .setCancelable(false)
                    .setMessage("You currently have no internet connection. Information displayed may be inaccurate.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }

        //find and assign views
        dropdownList = findViewById(R.id.dropdownList);
        adapter = ArrayAdapter.createFromResource(this, R.array.food_court, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //sets the dropdown list

        //Shared Preferences
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        final String checkbox = preferences.getString("remember","");
        final String username = preferences.getString("studentUsername","");
        final String studentID = preferences.getString("studentID","");
        final String studentPoints = preferences.getString("studentPoints","");

        //sets custom message for different users, depending on their username
        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        //show current points
        pointsTextView = (TextView) findViewById(R.id.PointsTextView);
        String message;
        String pointsText;
        int points = 0;
        if (checkbox.equals("true")){
            message = "Welcome, " + username + "!";
            if (getConnectionType(HomescreenActivity.this)){
                Log.d("snatchworks", "Student ID Is " + studentID);
                points = 0;
            }
            else{
                points = Integer.parseInt(studentPoints);
            }
            pointsText = "Points: " + points;
        }
        else{
            message = "Welcome, " + SignUpActivity.username + "!";
            Log.d("Points", "Points = " + MainActivity.userpoints);
            pointsText = "Points: " + MainActivity.userpoints;
        }
        welcomeMessage.setText(message);
        pointsTextView.setText(pointsText);




        //sets carousel view
        CarouselView carouselView = findViewById(R.id.carousel);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

        dropdownList.setAdapter(adapter);
        dropdownList.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Intent in;

        //position 0 is a label "select food court" and thus the if statement
        //Actions when food court is chosen
        if (position != 0){
            String foodCourt = parent.getItemAtPosition(position).toString();
            //storing the selected food court from the drop down list without spaces to extract data from firebase in other activites
            firebaseStall = foodCourt.replaceAll("\\s+","");

            Log.d("snatch","Store court " + firebaseStall);
            //when user selects food court from drop down list/spinner, new activity is started
            in = new Intent(HomescreenActivity.this, FoodClubActivity.class);
            //putExtra to send the foodcourt name to FoodClubActivity.java
            if (foodCourt.equals("Food Club")){
                in.putExtra("FoodCourt", "FoodClub");
            }
            else if (foodCourt.equals("Makan Place")) {
                in.putExtra("FoodCourt", "Makan Place");
            }
            else if (foodCourt.equals("Munch")) {
                in.putExtra("FoodCourt", "Munch");
            }
            else if (foodCourt.equals("Poolside")) {
                in.putExtra("FoodCourt", "Poolside");
            }
            startActivity(in);
        }
        else{
            //to guide user
            Toast.makeText(HomescreenActivity.this, "Please select a Food Court", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.profile_option){
            Intent in = new Intent(this, ProfileActivity.class);
            startActivity(in);
        }

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

            Intent signIn = new Intent(HomescreenActivity.this,MainActivity.class);
            //clears backstack so user cannot click back to go back to main page of application after logging out
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
            finish();
        }

        //shows cardview popup for users to get help
        else if(item.getItemId() == R.id.help_option){
            helpDialog = new Dialog(this);
            ShowPopUp();
        }

        return super.onOptionsItemSelected(item);
    }

    //prevents user from clicking back from homepage to go back to login activity
    //when user clicks the back button twice, he is sent back to phone's homescreen
    @Override
    public void onBackPressed() {
        //when back button is double clicked
        if(doubleClickToExit){
            super.onBackPressed();
            Intent exit = new Intent(HomescreenActivity.this, FinishActivity.class);
            //clears backstack
            exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(exit);
        }

        //on first click, set doubleClick to true
        this.doubleClickToExit = true;
        //to inform user after first click
        Toast.makeText(this,"Click again to exit application", Toast.LENGTH_SHORT).show();

        //to reset click after 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClickToExit = false;
            }
        },2000);
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
                //sends user to activity with webview to open google form
                Intent in = new Intent(HomescreenActivity.this, FormActivity.class);
                startActivity(in);
            }
        });
        //sets the background to 'blur' so that the pop up dialog is clearer
        helpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        helpDialog.show();
    }


    public static boolean getConnectionType(Context context) {
        boolean result = true; // If there is no internet connection, bool returns true
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                // if there is internet connection, regardless of what type (wifi, cellular, vpn) return false
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = false;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = false;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                        result = false;
                    }
                }
            }
            else { //for older devices
                if (cm != null) {
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    if (activeNetwork != null) {
                        // connected to the internet
                        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                            result = false;
                        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                            result = false;
                        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_VPN) {
                            result = false;
                        }
                    }
                }
            }
        }
        return result;
    }
}

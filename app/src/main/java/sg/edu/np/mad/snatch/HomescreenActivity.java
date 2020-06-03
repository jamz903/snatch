package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HomescreenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner dropdownList;
    ArrayAdapter<CharSequence> adapter;
    public static String firebaseStall;
    boolean doubleClickToExit = false;
    TextView welcomeMessage;
    private int[] mImages = new int[]{R.drawable.steak, R.drawable.fastfood, R.drawable.desert};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        //find and assign views
        dropdownList = findViewById(R.id.dropdownList);
        adapter = ArrayAdapter.createFromResource(this, R.array.food_court, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Toast to show successful login
        Toast.makeText(HomescreenActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        String message = "Welcome, " + SignUpActivity.username + "!";
        welcomeMessage.setText(message);

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

        //Actions when food court is chosen

        if (position != 0){
            String foodCourt = parent.getItemAtPosition(position).toString();
            firebaseStall = foodCourt.replaceAll("\\s+","");
            in = new Intent(HomescreenActivity.this, FoodClubActivity.class);
            if (foodCourt.equals("Food Club")){
                in.putExtra("FoodCourt", "FoodClub");
            }
            else if (foodCourt.equals("Makan Place")) {
                in.putExtra("FoodCourt", "MKP");
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

        if(item.getItemId() == R.id.credits_option){

        }

        else if(item.getItemId() == R.id.logout_option){
            Intent signIn = new Intent(HomescreenActivity.this,MainActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(doubleClickToExit){
            super.onBackPressed();
            Intent exit = new Intent(HomescreenActivity.this, FinishActivity.class);
            exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(exit);
        }

        this.doubleClickToExit = true;
        Toast.makeText(this,"Click again to exit application", Toast.LENGTH_SHORT).show();

        //to reset click after 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClickToExit = false;
            }
        },2000);
    }
}

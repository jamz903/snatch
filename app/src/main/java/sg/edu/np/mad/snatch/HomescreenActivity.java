package sg.edu.np.mad.snatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class HomescreenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner dropdownList;
    ArrayAdapter<CharSequence> adapter;

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
            if (foodCourt.equals("Food Club")){
                in = new Intent(HomescreenActivity.this, FoodClubActivity.class);
                startActivity(in);
            }
        }
        else{
            Toast.makeText(HomescreenActivity.this, "Please select a Food Court", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

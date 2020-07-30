package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProfileActivity extends AppCompatActivity{

    EditText profileName;
    EditText profilePW;
    TextView profilePoints;
    Button updateButton;
    Button redeemButton;


    String password;
    String message;
    int points;

    DatabaseReference reff;

    //Help pop-up dialog
    Dialog helpDialog;
    ImageView close;
    Button getHelpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        //set the hints of the current users UN and PW

            //connect the text and edit views
        profileName = (EditText) findViewById(R.id.profileName);
        profilePW = (EditText) findViewById(R.id.profilePassword);
        profilePoints = (TextView) findViewById(R.id.profilePoints);
        updateButton = (Button) findViewById(R.id.UpdateButton);
        redeemButton = (Button) findViewById(R.id.RedeemPointsButtonProfile);

        //Shared Preferences
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        final String checkbox = preferences.getString("remember","");
        final String username = preferences.getString("studentUsername","");
        final String pw = preferences.getString("studentPW", "");

        //set hints of current user name and pw

        if (checkbox.equals("true")){
            message = username;
            password = pw;
        }
        else{
            message = MainActivity.usingName;
            password = MainActivity.usingPW;
        }
        profileName.setText(message);
        profilePW.setText(password);
    }
    @Override
    protected void onStart() {
        super.onStart();
        int value = 0;
        reff = FirebaseDatabase.getInstance().getReference().child("Students");
        //Shared Preferences
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        final String checkbox = preferences.getString("remember","");
        final String studentPoints = preferences.getString("studentPoints","");
        //usingID is a variable taken from MainActivity(login page)
        //"remember me" function skips login page, and thus must check to see which variables to use
        if (checkbox.equals("true")){
            profilePoints.setText("Points: " + studentPoints);
        }
        else{
            reff.child(MainActivity.usingID).child("studentPoints").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int value = Integer.parseInt( dataSnapshot.getValue().toString());
                    profilePoints.setText("Points: " + value);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
    @Override
    protected void onResume() {
        super.onResume();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileName.getText().toString() != message|| profilePW.getText().toString() != password){
                    //means that the user has updated sth here
                    //update database

                    //shared pref
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    final String checkbox = preferences.getString("remember","");
                    final String studentID = preferences.getString("studentID","");
                    reff = FirebaseDatabase.getInstance().getReference().child("Students");
                    //usingID is a variable taken from MainActivity(login page)
                    //"remember me" function skips login page, and thus must check to see which variables to use
                    if (checkbox.equals("true")){
                        reff.child(studentID).child("studentName").setValue(profileName.getText().toString());
                        reff.child(studentID).child("studentPW").setValue(profilePW.getText().toString());
                    }
                    else{
                        reff.child(MainActivity.usingID).child("studentName").setValue(profileName.getText().toString());
                        reff.child(MainActivity.usingID).child("studentPW").setValue(profilePW.getText().toString());
                    }

                };
            }
        });


        redeemButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent in = new Intent(ProfileActivity.this, RewardsActivity.class);
                startActivity(in);
            }
        });


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

            Intent signIn = new Intent(ProfileActivity.this,MainActivity.class);
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
                Intent in = new Intent(ProfileActivity.this, FormActivity.class);
                startActivity(in);
            }
        });
        //sets the background to 'blur' so that the pop up dialog is clearer
        helpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        helpDialog.show();
    }
}

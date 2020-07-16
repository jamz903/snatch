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


    String password;
    String message;

    DatabaseReference reff;

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

        //Shared Preferences
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        final String checkbox = preferences.getString("remember","");
        final String username = preferences.getString("studentUsername","");
        final String pw = preferences.getString("studentPW", "");

        //set hints of current user name and pw

        if (checkbox.equals("true")){
            message = username ;
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
        profilePoints.setText("Points: " + MainActivity.userpoints);
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
                    reff = FirebaseDatabase.getInstance().getReference().child("Students");
                    reff.child(MainActivity.usingID).child("studentName").setValue(profileName.getText().toString());
                    reff.child(MainActivity.usingID).child("studentPW").setValue(profilePW.getText().toString());
                };
            }
        });


    }
}

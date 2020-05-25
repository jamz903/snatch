package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity {


    //TAG and variables
    private static final String TAG = "snatch";
    Button loginBtn;
    Button signUpBtn;
    EditText emailEditText;
    EditText pwEditText;
    TextView errorMsgTextView;

    DatabaseReference reff;
    Students student;


    RequestQueue requestQueue;
    final List<Students> studentsList = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Created");


        //Assign Buttons to variables
        loginBtn = (Button) findViewById(R.id.loginBtn);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        pwEditText = (EditText) findViewById(R.id.pwEditText);
        errorMsgTextView = (TextView) findViewById(R.id.errorMsgTextView);


        //Reference for firebase to get studentList
        reff = FirebaseDatabase.getInstance().getReference().child("Students");

        //Function to add all Students to studentsLIst from firebase
        addExistingMembers();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Started");


    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Resumed");

        //assign values to studentsList incase of change
        addExistingMembers();


        //When is login button is pressed check students List with input info
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String pw = pwEditText.getText().toString();
                if (email.equals("s1234567") && pw.equals("12345678")) {
                    Log.d(TAG, "Login successful!");
                    Intent in = new Intent(MainActivity.this, HomescreenActivity.class);
                    startActivity(in);
                }


                /*for(int i = 0; i<studentsList.size(); i++)
                {

                    if (studentsList.get(i).getStudentID().equals(email) && studentsList.get(i).getStudentPW().equals(pw)){
                        Log.d(TAG, "Login correct");
                        Intent in = new Intent(MainActivity.this, HomescreenActivity.class);
                        startActivity(in);

                    }
                    else{
                        Log.d(TAG, "Login Wrong");
                        errorMsgTextView.setText("Incorrect email/password! Please try again");
                        continue;
                    }


                }*/

            }
        });

        //Wem sign up button is pressed send input info to new firebase child
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = emailEditText.getText().toString();
                String pw = pwEditText.getText().toString();
                
                Students students = new Students(id,pw);
                //students.setStudentID(id);
                //students.setStudentPW(pw);


                reff.child(id).setValue(students);
                Toast.makeText(getApplicationContext(), "New Account registered", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //add members of database to studentsList
    protected void addExistingMembers(){
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                studentsList.clear();


                Log.d(TAG,"connection Success");
                Log.d(TAG,"Value is" + map);
                Log.d(TAG,"is a " + map.getClass().getName());

                //add to list
                Iterator StuList = map.entrySet().iterator();
                String a = null;
                String b = null;

                //iterate through database for all child items
                while(StuList.hasNext()){
                    Map.Entry mapElement = (Map.Entry)StuList.next();
                    Log.d(TAG,"map key is" + mapElement.getValue());


                    HashMap hash = (HashMap) mapElement.getValue();
                    Iterator IDLIST = hash.entrySet().iterator();
                    while (IDLIST.hasNext()) {

                        Map.Entry hashElement = (Map.Entry)IDLIST.next();

                        Log.d(TAG,"map key s" + hashElement);
                        String details = (((String)hashElement.getValue()));

                        //check if item is an ID or PW before adding to list
                        if (hashElement.getKey().equals("studentID")){
                            a = (String) details;
                        }
                        else if (hashElement.getKey().equals("studentPW")){
                            b = (String) details;
                        }
                        else{
                            Log.d(TAG,"Assignment failure");
                        }



                        //make student obkect to add to list
                        Students student = new Students(a,b);
                        //add student to student list
                        studentsList.add(student);
                        Log.d(TAG, " " + studentsList.get(0).getStudentID());


                    }


                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG,"FAILL" , databaseError.toException());
            }
        });
    }

}




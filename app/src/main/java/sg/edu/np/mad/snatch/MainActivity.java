package sg.edu.np.mad.snatch;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
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
        //errorMsgTextView = (TextView) findViewById(R.id.errorMsgTextView);


        //Reference for firebase to get studentList
        reff = FirebaseDatabase.getInstance().getReference().child("Students");

        //Function to add all Students to studentsLIst from firebase
        addExistingMembers();

        loginBtn.getBackground().setColorFilter(0xFF2a8cd6, PorterDuff.Mode.MULTIPLY);
        signUpBtn.getBackground().setColorFilter(0xFF2a8cd6, PorterDuff.Mode.MULTIPLY);

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
                addExistingMembers();

                closeKeyboard();
                String studentID = emailEditText.getText().toString().toUpperCase(); //.toUpperCase() makes Student ID not case sensitive
                String pw = pwEditText.getText().toString();
                /*if (email.equals("s1234567") && pw.equals("12345678")) {
                    Log.d(TAG, "Login successful!");
                    Intent in = new Intent(MainActivity.this, HomescreenActivity.class);
                    startActivity(in);
                }*/
                if(getConnectionType(getApplicationContext())){
                    //errorMsgTextView.setText("You have no internet connection. Please try again when you have access to the internet");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("No Internet Connection")
                            .setCancelable(false)
                            .setMessage("You currently have no internet connection. Internet is required to proceed.")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }

                else if (studentID.length() == 0){
                    //errorMsgTextView.setText("Empty email/password! Please try again");
                    emailEditText.setError("Enter Student ID");
                }

                else if (pw.length() == 0){
                    pwEditText.setError("Enter Password");
                }

                else{
                    boolean matchFound = false;
                    for(int i = 0; i<studentsList.size(); i++)
                    {
                        if ((studentsList.get(i).getStudentID()).equalsIgnoreCase(studentID.toString())){
                            try{
                                if (studentsList.get(i).getStudentPW().equalsIgnoreCase(pw.toString()) == true){
                                    Log.d(TAG, "Login correct");
                                    matchFound = true;
                                    Intent in = new Intent(MainActivity.this, HomescreenActivity.class);
                                    startActivity(in);
                                    SignUpActivity.username = studentsList.get(i).getStudentName();

                                }
                            } catch (NullPointerException e) {
                                //errorMsgTextView.setText("Wrong password");
                                pwEditText.setError("Invalid Login Credentials");
                            }

                        }

                    }
                    if (!matchFound) {
                        Log.d(TAG, "Login Wrong");
                        emailEditText.setError("Invalid Login Credentials");
                        pwEditText.setError("Invalid Login Credentials");

                    }
                }

            }
        });

        //Wem sign up button is pressed send input info to new firebase child
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(in);

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
                String c = null;

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
                        else if(hashElement.getKey().equals("studentName")){
                            c = (String) details;
                        }
                        else{
                            Log.d(TAG,"Assignment failure");
                        }



                        //make student obkect to add to list
                        Students student = new Students(a,b,c);
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

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
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




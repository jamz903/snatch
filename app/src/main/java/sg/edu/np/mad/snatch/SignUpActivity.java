package sg.edu.np.mad.snatch;

import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "pain";
    //Create variables
    EditText signUpUser;
    EditText signUpStuID;
    EditText signUpPassword;
    Button cfmSignUp;
    DatabaseReference reff;
    final List<Students> studentsList = new ArrayList();
    public static String username;
    public static int userpoints;
    TextInputLayout signUpLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //find and assign buttons/EditText to variables
        signUpUser = (EditText) findViewById(R.id.signUpUsername);
        signUpStuID = (EditText) findViewById(R.id.signUpStuID);
        signUpPassword = (EditText) findViewById(R.id.signUpPassword);
        cfmSignUp = (Button) findViewById(R.id.signUpPgBtn);
        signUpLayout = (TextInputLayout) findViewById(R.id.signUpLayout);
        //set button colour to blue
        cfmSignUp.getBackground().setColorFilter(0xFF2a8cd6, PorterDuff.Mode.MULTIPLY);

        //firebase reference to obtain data
        reff = FirebaseDatabase.getInstance().getReference().child("Students");

        //add members of database to studentsList
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
        //add members of database to studentsList
        addExistingMembers();

        cfmSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser.setError(null);
                signUpLayout.setError(null);
                signUpStuID.setError(null);
                addExistingMembers();
                String id = signUpStuID.getText().toString().toUpperCase(); //.toUpperCase() makes Student ID not case sensitive
                String pw = signUpPassword.getText().toString();
                String un = signUpUser.getText().toString();

                //if username field is not filled in
                if (un.length() == 0){
                    signUpUser.setError("Enter Username");
                }

                //if studentID field not filled in
                else if (id.length() == 0){
                    signUpStuID.setError("Enter Student ID");
                }

                //if password field not filled in
                else if (pw.length() == 0){
                    signUpLayout.setError("Enter Password");
                }

                //checks if there is no internet connection, if no internet, informs user that login is not possible without an internet connection
                else if(getConnectionType(SignUpActivity.this)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setTitle("No Internet Connection")
                            .setCancelable(false)
                            .setMessage("You currently have no internet connection. Internet is required to proceed.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
                else{
                    boolean matchFound = false;

                    for(int i = 0; i<studentsList.size() && matchFound == false; i++)
                    {
                        if ((studentsList.get(i).getStudentID()).equalsIgnoreCase(id.toString())) {
                            matchFound = true;
                        }
                    }

                    if (!matchFound) {
                        Log.d(TAG, "Sign up successful");
                        if((id.toUpperCase().matches("S[0-9]{8}") && id.length() == 9) ){
                            username = un;
                            String newUser = "yes";
                            Students students = new Students(id.toUpperCase(),pw,un,newUser,0);
                            reff.child(id).setValue(students);
                            Toast.makeText(getApplicationContext(), "New Account registered", Toast.LENGTH_SHORT).show();
                            addExistingMembers();
                            Intent in = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(in);
                        }
                        else{
                            signUpStuID.setError("Invalid Student ID");

                        }

                    }
                    else{
                        signUpStuID.setError("Student ID already registered");
                    }
                }


            }
        });

    }
    protected void addExistingMembers(){
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                studentsList.clear();


                //add to list
                Iterator StuList = map.entrySet().iterator();
                String a = null;
                String b = null;
                String c = null;
                String d = null;
                int e = 0;

                //iterate through database for all child items
                while(StuList.hasNext()){
                    Map.Entry mapElement = (Map.Entry)StuList.next();


                    HashMap hash = (HashMap) mapElement.getValue();
                    Iterator IDLIST = hash.entrySet().iterator();
                    while (IDLIST.hasNext()) {

                        Map.Entry hashElement = (Map.Entry)IDLIST.next();


                        String details = (((String)hashElement.getValue().toString()));

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
                        else if(hashElement.getKey().equals("newUser")){
                            d = (String) details;
                        }
                        else if(hashElement.getKey().equals("studentPoints")){
                            e = Integer.parseInt(details);
                        }
                        else{
                            Log.d(TAG,"Assignment failure");
                        }



                        //make student obkect to add to list
                        Students student = new Students(a,b,c,d,e);
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

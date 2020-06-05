package sg.edu.np.mad.snatch;

import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
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
    Students student;
    MainActivity main = new MainActivity();
    final List<Students> studentsList = new ArrayList();
    public static String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpUser = (EditText) findViewById(R.id.signUpUsername);
        signUpStuID = (EditText) findViewById(R.id.signUpStuID);
        signUpPassword = (EditText) findViewById(R.id.signUpPassword);
        cfmSignUp = (Button) findViewById(R.id.signUpPgBtn);
        cfmSignUp.getBackground().setColorFilter(0xFF2a8cd6, PorterDuff.Mode.MULTIPLY);


        reff = FirebaseDatabase.getInstance().getReference().child("Students");


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
        addExistingMembers();

        cfmSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExistingMembers();
                String id = signUpStuID.getText().toString().toUpperCase();
                String pw = signUpPassword.getText().toString();
                String un = signUpUser.getText().toString();

                if (un.length() == 0){
                    signUpUser.setError("Enter Username");
                }

                else if (id.length() == 0){
                    signUpStuID.setError("Enter Student ID");
                }

                else if (pw.length() == 0){
                    signUpPassword.setError("Enter Password");
                }
                else if(getConnectionType(SignUpActivity.this)){
                    //errorMsgTextView.setText("You have no internet connection. Please try again when you have access to the internet");
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
                        Log.d(TAG, "Sign up safe");
                        if((id.toUpperCase().matches("S[0-9]{8}") && id.length() == 9) ){
                            username = un;
                            Students students = new Students(id.toUpperCase(),pw,un);
                            reff.child(id).setValue(students);
                            Toast.makeText(getApplicationContext(), "New Account registered", Toast.LENGTH_SHORT).show();
                            addExistingMembers();
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

                //iterate through database for all child items
                while(StuList.hasNext()){
                    Map.Entry mapElement = (Map.Entry)StuList.next();


                    HashMap hash = (HashMap) mapElement.getValue();
                    Iterator IDLIST = hash.entrySet().iterator();
                    while (IDLIST.hasNext()) {

                        Map.Entry hashElement = (Map.Entry)IDLIST.next();


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

package sg.edu.np.mad.snatch;

import android.app.VoiceInteractor;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpUser = (EditText) findViewById(R.id.signUpUsername);
        signUpStuID = (EditText) findViewById(R.id.signUpStuID);
        signUpPassword = (EditText) findViewById(R.id.signUpPassword);
        cfmSignUp = (Button) findViewById(R.id.signUpPgBtn);


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

                if (id == "" || pw == "" ||un ==""){
                    Toast.makeText(getApplicationContext(),"Empty email/password! Please try again",Toast.LENGTH_SHORT).show();
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
                        if((id.matches("S[0-9]{8}") && id.length() == 9) ){
                            Students students = new Students(id,pw,un);
                            reff.child(id).setValue(students);
                            Toast.makeText(getApplicationContext(), "New Account registered", Toast.LENGTH_SHORT).show();
                            addExistingMembers();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "invalid student ID", Toast.LENGTH_SHORT).show();

                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "ID already registered", Toast.LENGTH_SHORT).show();
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
}

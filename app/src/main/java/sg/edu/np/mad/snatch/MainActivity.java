package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "snatch";
    Button loginBtn;
    Button signUpBtn;
    EditText emailEditText;
    EditText pwEditText;
    TextView errorMsgTextView;

    DatabaseReference reff;
    Students student;

    List<Students> studentsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Created");

        loginBtn = (Button) findViewById(R.id.loginBtn);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        pwEditText = (EditText) findViewById(R.id.pwEditText);
        errorMsgTextView = (TextView) findViewById(R.id.errorMsgTextView);

        student = new Students("","");
        studentsList = new ArrayList<>();
        reff = FirebaseDatabase.getInstance().getReference();



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Started");

        getStudents();

        for( Students student : studentsList){
            System.out.println(student.getStudentID());
            System.out.println(student.getStudentPW());
        }
        System.out.println("OWF");
    }

    public void getStudents(){
        studentsList.clear();

        DatabaseReference getDatabase;
        getDatabase = FirebaseDatabase.getInstance().getReference();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                Students student = dataSnapshot.getValue(Students.class);

                studentsList.add(student);
                if (studentsList.size() > 0){
                    System.out.println("YAY" + studentsList.size());
                    System.out.println(studentsList.get(0));
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                Students students = dataSnapshot.getValue(Students.class);



            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(MainActivity.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        getDatabase.addChildEventListener(childEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Resumed");
        final List<Students> studentsList = new ArrayList();





        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String pw = pwEditText.getText().toString();







                if (email.equals("s1234567") && pw.equals("12345678")) {
                    Intent in = new Intent(MainActivity.this, HomescreenActivity.class);
                    startActivity(in);
                }
                else {
                    errorMsgTextView.setText("Incorrect email/password! Please try again");
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = emailEditText.getText().toString();
                String pw = pwEditText.getText().toString();

                student.setStudentID(id);
                student.setStudentPW(pw);


                reff.child("Students").child(id).setValue(student);
                Toast.makeText(getApplicationContext(),"New Account registered",Toast.LENGTH_SHORT).show();
            }
        });
    }



}

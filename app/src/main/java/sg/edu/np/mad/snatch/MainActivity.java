package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        student = new Students();

        reff = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Resumed");
        final List<Students> studentsList = new ArrayList();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Students");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Students x = dataSnapshot.getValue(Students.class);
                System.out.println(x);
                System.out.println("pain");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Failed");

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String pw = pwEditText.getText().toString();







                /*if (email.equals("s1234567") && pw.equals("12345678")) {
                    Intent in = new Intent(MainActivity.this, HomescreenActivity.class);
                    startActivity(in);
                }
                else {
                    errorMsgTextView.setText("Incorrect email/password! Please try again");
                }*/
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = emailEditText.getText().toString();
                String pw = pwEditText.getText().toString();

                student.setStudentID(id);
                student.setStudentPW(pw);


                reff.child("Students").child(id).child("Password").setValue(pw);
                Toast.makeText(getApplicationContext(),"New Account registered",Toast.LENGTH_SHORT).show();
            }
        });
    }



}

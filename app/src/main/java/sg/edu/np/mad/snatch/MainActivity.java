package sg.edu.np.mad.snatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "snatch";
    Button loginBtn;
    Button signUpBtn;
    EditText emailEditText;
    EditText pwEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Created");

        loginBtn = (Button) findViewById(R.id.loginBtn);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        pwEditText = (EditText) findViewById(R.id.pwEditText);
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

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String pw = pwEditText.getText().toString();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

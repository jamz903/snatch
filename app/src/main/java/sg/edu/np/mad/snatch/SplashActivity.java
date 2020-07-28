package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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

public class SplashActivity extends AppCompatActivity {
    private TextView splashTextView;
    private ImageView splashImageView;
    Animation splashAnimation;

    DatabaseReference reff;
    final List<Students> studentsList = new ArrayList();
    private static final String TAG = "snatchworks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make the activity fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        //hide action bar
        getSupportActionBar().hide();

        splashTextView = (TextView) findViewById(R.id.splashTextView);
        splashImageView = (ImageView) findViewById(R.id.splashImageView);
        splashAnimation = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_animation);

        //Shared Preferences
        final SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        final String checkbox = preferences.getString("remember","");
        final String studentID = preferences.getString("studentID","");
        Log.d("snatchworks","StudentID: "+studentID);

        //Reference for firebase to get studentList
        reff = FirebaseDatabase.getInstance().getReference().child("Students");
        if (!getConnectionType(SplashActivity.this)){
            Log.d(TAG, "RUNNING");
            addExistingMembers();
        }

        splashTextView.startAnimation(splashAnimation);
        splashImageView.startAnimation(splashAnimation);
        Thread timer = new Thread(){
            public void run () {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    Intent in;
                    Log.d("snatchworks","Checked:" + checkbox);
                    if (checkbox.equals("true")){
                        Log.d("snatchworks", "it works");
                        in = new Intent(SplashActivity.this,HomescreenActivity.class);
                        int points = 0;
                        String password = null;
                        Log.d("snatchworks","Size: " + studentsList.size());
                        /*for(Students student: studentsList){
                            if (student.getStudentID().equals(studentID)){
                                points = student.getStudentPoints();
                                password = student.getStudentPW();
                                Log.d("snatchworks", "Points HERE is " + points);
                            }
                        }*/
                        for(int i = 0; i<studentsList.size(); i++){
                            if (studentsList.get(i).getStudentID().equalsIgnoreCase(studentID)){
                                points = studentsList.get(i).getStudentPoints();
                                password = studentsList.get(i).getStudentPW();
                                Log.d("snatchworks", "Points HERE is " + points);
                            }
                        }
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("studentPoints", String.valueOf(points));
                        editor.putString("studentPW", password);
                        editor.apply();
                        Log.d("snatchworks","applied");
                        Log.d("snatchworks",preferences.getString("studentPoints",""));
                        Log.d("snatchworks",preferences.getString("studentPW",""));
                    }
                    else{
                        in = new Intent(SplashActivity.this,MainActivity.class);
                    }
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(SplashActivity.this,android.R.anim.fade_in, android.R.anim.fade_out);
                    startActivity(in,options.toBundle());
                    //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
            }
        };
        timer.start();
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
                String a = null ;
                String b = null;
                String c = null;
                String d = null;
                int e = 0;

                //iterate through database for all child items
                while(StuList.hasNext()){
                    Map.Entry mapElement = (Map.Entry)StuList.next();
                    Log.d(TAG,"map key is" + mapElement.getValue());


                    HashMap hash = (HashMap) mapElement.getValue();
                    Iterator IDLIST = hash.entrySet().iterator();
                    while (IDLIST.hasNext()) {

                        Map.Entry hashElement = (Map.Entry)IDLIST.next();

                        Log.d(TAG,"map key s" + hashElement);
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

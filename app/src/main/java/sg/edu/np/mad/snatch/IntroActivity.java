package sg.edu.np.mad.snatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button nextButton;
    int position;
    Button btnGetStarted;
    Animation btnAnimation;
    TextView skip;
    boolean doubleClickToExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make the activity fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //setting the content
        setContentView(R.layout.activity_intro);

        //hide action bar
        getSupportActionBar().hide();

        //find Views
        nextButton = (Button) findViewById(R.id.nextButton);
        tabIndicator = (TabLayout) findViewById(R.id.tabIndicator);
        btnGetStarted = (Button) findViewById(R.id.getStarted);
        btnAnimation = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        skip = (TextView) findViewById(R.id.tv_skip);

        //fill item list (for screen)
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Welcome","Welcome to Snatch! This is an app created for and by Ngee Ann Students to make purchasing food an efficient and fun process for all." ,R.drawable.intro_micdrop));
        mList.add(new ScreenItem("Food for All","Offering food from all 4 food courts, our wide selection of food will definitely satisfy all your cravings.",R.drawable.intro_burger));
        mList.add(new ScreenItem("Fast Delivery","Rushing an Assignment? Not to worry, our delivery service will deliver your favourite foods to you- wherever you may be at Ngee Ann.",R.drawable.intro_delivery));
        mList.add(new ScreenItem("Pay with ease","Cashless payment options are available to make the payment process seamless and easy. (Also just in case you forget to bring your wallet to school!)",R.drawable.intro_payment));

        //setup viewpager
        screenPager = findViewById(R.id.viewPager1);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        //setup tabLayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        //button onClick listener
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                else if (position == mList.size()-1){
                    loadLastScreen();
                }
            }
        });

        //tabLayout change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //get started button onclick listener, goes to home activity when this is clicked
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //only update variable to "no" once user has clicked get started, if not intro screen will continue to appear even if app is cleared
                updateUserStatus(MainActivity.FirebaseStudentID);
                Intent in = new Intent(IntroActivity.this, HomescreenActivity.class);
                startActivity(in);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //skip button onclick listener, goes to last slide when clicked
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });

    }


    //show get started button and hide indicator and next button
    private void loadLastScreen() {
        nextButton.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        //button animation
        btnGetStarted.setAnimation(btnAnimation);
    }

    //prevents user from clicking back from homepage to go back to login activity
    //when user clicks the back button twice, he is sent back to phone's homescreen
    @Override
    public void onBackPressed() {
        //when back button is double clicked
        if(doubleClickToExit){
            super.onBackPressed();
            Intent exit = new Intent(IntroActivity.this, FinishActivity.class);
            //clears backstack
            exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(exit);
        }

        //on first click, set doubleClick to true
        this.doubleClickToExit = true;
        //to inform user after first click
        Toast.makeText(this,"Click again to exit application", Toast.LENGTH_SHORT).show();

        //to reset click after 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClickToExit = false;
            }
        },2000);
    }

    //changes new user variable to "no" so that introscreen does not appear again
    public void updateUserStatus(String studentID){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Students").child(studentID).child("newUser");
        reference.setValue("no");
        Log.d("Snatch","Updated User Status");
    }
}

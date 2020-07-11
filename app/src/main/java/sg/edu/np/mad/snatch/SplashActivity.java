package sg.edu.np.mad.snatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private TextView splashTextView;
    private ImageView splashImageView;
    Animation splashAnimation;

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
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        final String checkbox = preferences.getString("remember","");

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
                    if (checkbox.equals("true")){
                        in = new Intent(SplashActivity.this,HomescreenActivity.class);
                    }
                    else{
                        in = new Intent(SplashActivity.this,MainActivity.class);
                    }
                    startActivity(in);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
            }
        };
        timer.start();
    }
}

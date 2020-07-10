package sg.edu.np.mad.snatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        splashTextView.startAnimation(splashAnimation);
        splashImageView.startAnimation(splashAnimation);
        final Intent in = new Intent(this,MainActivity.class);
        Thread timer = new Thread(){
            public void run () {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    startActivity(in);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
            }
        };
        timer.start();
    }
}

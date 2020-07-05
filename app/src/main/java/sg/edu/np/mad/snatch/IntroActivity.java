package sg.edu.np.mad.snatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //fill item list (for screen)
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("For Ngee Ann Students","Welcome to Snatch, Created for and by Ngee Ann Students",R.drawable.intro_micdrop));
        mList.add(new ScreenItem("Food for All","Offering food from all 4 food courts, our wide selection of food will definitely satisfy all your cravings.",R.drawable.intro_burger));
        mList.add(new ScreenItem("Fast Delivery","Rushing an Assignment? Not to worry, our delivery service will deliver your favourite foods to you- wherever you are at Ngee Ann.",R.drawable.intro_delivery));
        mList.add(new ScreenItem("Pay with ease","Cashless payment options available to make payment seamless and easy. (Also just in case you forget to bring your wallet to school!)",R.drawable.intro_payment));
        //setup viewpager
        screenPager = findViewById(R.id.viewPager1);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);
    }
}

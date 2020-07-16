package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class StallOwnerActivity extends AppCompatActivity {
    //cardviews
    CardView ordersCardView;
    CardView receiptsCardView;
    CardView transactionCardView;

    boolean doubleClickToExit = false;
    //Help pop-up dialog
    Dialog helpDialog;
    ImageView close;
    Button getHelpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stall_owner);

        ordersCardView = (CardView) findViewById(R.id.ordersCardView);
        receiptsCardView = (CardView) findViewById(R.id.receiptsCardView);
        transactionCardView = (CardView) findViewById(R.id.transactionCardView);

        ordersCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(StallOwnerActivity.this, OwnerOrdersActivity.class);
                startActivity(in);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.owner_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /*if(item.getItemId() == R.id.credits_option){
            to be implemented later on in phase 2
        }*/

        //logout button on kebab icon on top right corner of the app
        //brings user to log in page
        if(item.getItemId() == R.id.logout_option){
            Intent signIn = new Intent(StallOwnerActivity.this,MainActivity.class);
            //clears backstack so user cannot click back to go back to main page of application after logging out
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
            finish();
        }

        else if(item.getItemId() == R.id.help_option){
            helpDialog = new Dialog(this);
            ShowPopUp();
        }

        return super.onOptionsItemSelected(item);
    }

    //prevents user from clicking back from homepage to go back to login activity
    //when user clicks the back button twice, he is sent back to phone's homescreen
    @Override
    public void onBackPressed() {
        //when back button is double clicked
        if(doubleClickToExit){
            super.onBackPressed();
            Intent exit = new Intent(StallOwnerActivity.this, FinishActivity.class);
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

    public void ShowPopUp(){
        helpDialog.setContentView(R.layout.help_layout);
        close = (ImageView) helpDialog.findViewById(R.id.close);
        getHelpButton = (Button) helpDialog.findViewById(R.id.getHelpButton);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.dismiss();
            }
        });

        getHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: create a google form to link
                //sends user to google form to give feedback
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/gsgmyWWp17vvxF7e8"));
                startActivity(in);
            }
        });
        //sets the background to 'blur' so that the pop up dialog is clearer
        helpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        helpDialog.show();
    }
}


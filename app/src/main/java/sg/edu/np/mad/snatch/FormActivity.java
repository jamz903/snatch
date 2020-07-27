package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FormActivity extends AppCompatActivity {

    //WebView
    private WebView webView;
    private ProgressDialog progDialog;

    //@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        String url = "https://docs.google.com/forms/d/e/1FAIpQLSchJ97PFehjmgivoQy5_5PGQZe1jqKffK6RNo4i0UZejzsViA/viewform";

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);// set drawable icon
        //to set the drawable icon to be the "back" equivalent, goes back to previous activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //sets progress dialog
        progDialog = ProgressDialog.show(FormActivity.this, "Loading","Please wait...", true);
        progDialog.setCancelable(false);

        webView = (WebView) findViewById(R.id.webView);

        //enables javascript so that the page can load
        webView.getSettings().setJavaScriptEnabled(true);
        //loads the webview completely zoomed out
        webView.getSettings().setLoadWithOverviewMode(true);
        //makes the webview have a normal viewport (e.g. like a normal desktop browser), instead of one that adjusts to the size of webview
        webView.getSettings().setUseWideViewPort(true);

        //checks if the url has loaded, if not loaded display the progress dialog, if not cancel the progress dialog.
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDialog.show();
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url){
                progDialog.dismiss();
            }

        });
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSchJ97PFehjmgivoQy5_5PGQZe1jqKffK6RNo4i0UZejzsViA/viewform");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //allows user to open the webview in chrome instead for easier access
        if(item.getItemId() == R.id.chrome_option){
            Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/gsgmyWWp17vvxF7e8"));
            startActivity(in);
        }
        else{
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //if the user has clicked another page in the browser, if it can go back to its previous page, allow it to
        if (webView.canGoBack()){
            webView.goBack();
        }
        //if not, go back to previous activity
        else{
            super.onBackPressed();
        }
    }
}



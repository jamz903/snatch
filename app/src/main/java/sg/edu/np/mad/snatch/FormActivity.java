package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        String url = "https://docs.google.com/forms/d/e/1FAIpQLSchJ97PFehjmgivoQy5_5PGQZe1jqKffK6RNo4i0UZejzsViA/viewform";

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( URLUtil.isNetworkUrl(url) ) {
                    return false;
                }
                if (appInstalledOrNot(url)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity( intent );
                } else {
                    // do something if app is not installed
                }
                return true;
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

        if (webView.canGoBack()){
            webView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
}



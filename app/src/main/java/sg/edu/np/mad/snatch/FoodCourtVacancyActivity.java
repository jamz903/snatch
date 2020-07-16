package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FoodCourtVacancyActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, vacancyRVAdapterCallback {

    MapView mMapView;
    //Help pop-up dialog
    Dialog helpDialog;
    ImageView close;
    Button getHelpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_court_vacancy);

        mMapView = findViewById(R.id.vacancyMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        initLocations(googleMap);
    }

    public void initLocations(final GoogleMap googleMap) {
        LatLng makanPlaceLocation = new LatLng(1.332251, 103.774441);
        LatLng foodClubLocation = new LatLng(1.334217,  103.775498);
        LatLng poolsideLocation = new LatLng(1.335179, 103.77629);
        LatLng munchLocation = new LatLng(1.331992, 103.776518);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(makanPlaceLocation, 15);
        googleMap.addMarker(new MarkerOptions().position(makanPlaceLocation).title("Makan Place"));
        googleMap.addMarker(new MarkerOptions().position(foodClubLocation).title("Food Club"));
        googleMap.addMarker(new MarkerOptions().position(poolsideLocation).title("Poolside"));
        googleMap.addMarker(new MarkerOptions().position(munchLocation).title("Munch"));
        googleMap.animateCamera(cameraUpdate);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                //Retrieves the position of the selected marker
                LatLng location = marker.getPosition();
                //Ensure that after zoom, the full info window can be read and is not cropped off
                LatLng zoomLocation = new LatLng(location.latitude+0.0025, location.longitude);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(zoomLocation, 15);
                //Animates Google Maps to zoom to the selected marker
                googleMap.animateCamera(cameraUpdate);
                googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(FoodCourtVacancyActivity.this, marker.getTitle()));
                marker.showInfoWindow();
                return true;
            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent in = new Intent(FoodCourtVacancyActivity.this, Pop.class);
                in.putExtra("FoodCourt", marker.getTitle());
                startActivity(in);
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();

        //Below is test values for the recycler view, just leave it here for now
        FoodCourt foodClub = new FoodCourt(25,400,"FoodClub");
        ArrayList<FoodItem> foodList = new ArrayList<>();
        foodList.add(new FoodItem("Sausage", "1 stick of hotdog", 1, 0,0, "FoodClub", "Mala"));
        foodList.add(new FoodItem("Sausage1", "1 stick of hotdog", 1, 0,0, "FoodClub", "Mala"));
        foodList.add(new FoodItem("Sausage2", "1 stick of hotdog", 1, 0,0, "FoodClub", "Mala"));
        foodClub.popularDishes = foodList;

        FoodCourt foodClub2 = new FoodCourt(25,400,"FoodClub");
        ArrayList<FoodItem> foodList2 = new ArrayList<>();
        foodList2.add(new FoodItem("Sausage", "1 stick of hotdog", 1, 0,0, "FoodClub", "Mala"));
        foodList2.add(new FoodItem("Sausage1", "1 stick of hotdog", 1, 0,0, "FoodClub", "Mala"));
        foodList2.add(new FoodItem("Sausage2", "1 stick of hotdog", 1, 0,0, "FoodClub", "Mala"));
        foodClub2.popularDishes = foodList2;

        ArrayList<FoodCourt> foodCourtList = new ArrayList<>();
        foodCourtList.add(foodClub);
        foodCourtList.add(foodClub2);


        RecyclerView rv = findViewById(R.id.vacancyRecyclerView);
        vacancyRVAdapter adapter = new vacancyRVAdapter(this,foodCourtList,this);
        rv.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setItemAnimator(new DefaultItemAnimator());

        //adds a divider in-between items
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /*if(item.getItemId() == R.id.credits_option){
            to be implemented later on in phase 2
        }*/
        if(item.getItemId() == R.id.profile_option){
            Intent in = new Intent(this, ProfileActivity.class);
            startActivity(in);
        }
        //logout button on kebab icon on top right corner of the app
        //brings user to log in page
        if(item.getItemId() == R.id.logout_option){
            //shared preferences
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "false");
            editor.apply();

            Intent signIn = new Intent(this, MainActivity.class);
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

    @Override
    public void visitFoodStall(String foodCourt, String foodStall) {
        Intent in = new Intent(FoodCourtVacancyActivity.this, stallMenuActivity.class);
        in.putExtra("FoodCourt", foodCourt);
        in.putExtra("Stall", foodStall);
        in.putExtra("prevActivity", "FoodCourtVacancyActivity");
        startActivity(in);
    }
}
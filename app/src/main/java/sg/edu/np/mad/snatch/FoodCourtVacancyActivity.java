package sg.edu.np.mad.snatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
import java.util.Collections;
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
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_court_vacancy);

        if(getConnectionType(FoodCourtVacancyActivity.this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(FoodCourtVacancyActivity.this);
            builder.setTitle("No Internet Connection")
                    .setCancelable(false)
                    .setMessage("You currently have no internet connection. Internet is required to proceed. Popular dishes displayed for each stall may also be inaccurate")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }

        reference = FirebaseDatabase.getInstance().getReference().child("FoodCourt");
        mMapView = findViewById(R.id.vacancyMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        FoodCourt foodClub = new FoodCourt(400, "FoodClub");
        foodClub.setPopularDishes(foodClub.getAllDishes(foodClub.name));

        //Below is test values for the recycler view, just leave it here for now
        FoodCourt makanPlace = new FoodCourt(500,"Makan Place");
        makanPlace.setPopularDishes(makanPlace.getAllDishes(makanPlace.name));

        FoodCourt poolside = new FoodCourt(250, "Poolside");
        poolside.setPopularDishes(poolside.getAllDishes(poolside.name));

        FoodCourt munch = new FoodCourt(200, "Munch");
        munch.setPopularDishes(munch.getAllDishes(munch.name));

        ArrayList<FoodCourt> foodCourtList = new ArrayList<>();
        foodCourtList.add(foodClub);
        foodCourtList.add(makanPlace);
        foodCourtList.add(poolside);
        foodCourtList.add(munch);

        RecyclerView rv = findViewById(R.id.vacancyRecyclerView);
        vacancyRVAdapter adapter = new vacancyRVAdapter(this,foodCourtList,this);
        for (FoodCourt fc : foodCourtList) {
            Log.d("snatchwork", "NEW FOOD COURT IS " + fc.name);
            fc.popularDishes = fc.getAllDishes(fc.name);

            getUpvote(fc.popularDishes, adapter);
        }
        rv.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);
        rv.setItemAnimator(new DefaultItemAnimator());

        //adds a divider in-between items
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
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
                Intent in = new Intent(FoodCourtVacancyActivity.this, FormActivity.class);
                //Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/gsgmyWWp17vvxF7e8"));
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
        //store foodcourt in sharedpreferences
        SharedPreferences preferences = getSharedPreferences("store", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("foodCourt", foodCourt.replaceAll("\\s+",""));
        editor.putString("stall", foodStall.replaceAll("\\s",""));
        startActivity(in);
    }

    public void getUpvote(final ArrayList<FoodItem> listOfDishes, final vacancyRVAdapter aAdapter){
        for (final FoodItem food : listOfDishes) {
            final String dishName = food.getFoodName().replaceAll("\\s+","");
            Log.d("snatchwork", "DISH NAME IS " + dishName);
            DatabaseReference reference2 = reference.child(food.foodCourt.replaceAll("\\s+","")).child(food.stallName.replaceAll("\\s+",""));
            reference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Long upvotes = (Long) dataSnapshot.child(dishName).getValue();
                    Log.d("snatchwork", "KEY IS " + dataSnapshot.child(dishName).getKey() + " FROM " + food.foodCourt);
                    Log.d("snatchwork", "upvote here is " + String.valueOf(upvotes));
                    food.setUpVotes(Integer.parseInt(String.valueOf(upvotes)));
                    food.upVotes = Integer.parseInt(String.valueOf(upvotes));
                    //sorts items based on UpvoteNo (sorted based on CompareTo in FoodItem class)
                    Collections.sort(listOfDishes);
                    aAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
/*            Log.d("snatchwork", food.getFoodName() + " has " + food.getUpVotes());*/
        }
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
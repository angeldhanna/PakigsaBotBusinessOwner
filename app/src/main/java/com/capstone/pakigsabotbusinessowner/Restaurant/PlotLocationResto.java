package com.capstone.pakigsabotbusinessowner.Restaurant;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.pakigsabotbusinessowner.EstablishmentRules.DentalClinic.AddEstRulesDentalClinic;
import com.capstone.pakigsabotbusinessowner.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlotLocationResto extends AppCompatActivity implements OnMapReadyCallback {
    private Button setLocationBtn;
    private GoogleMap pakigsabotMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng latLng;
    double longitude, latitude;
    TextView estLocLatitude, estLocLongitude;

    //Firebase
    FirebaseAuth fAuth;
    FirebaseFirestore fStoreRef;
    String userId,estLocationTxt;

    private FusedLocationProviderClient mFusedLocationProviderClient = null;
    private LocationRequest mLocationRequest = null;
    private LocationCallback mLocationCallback = null;
    private long LOCATION_REQUEST_INTERVAL = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_location_resto);

        fAuth = FirebaseAuth.getInstance();
        fStoreRef = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        estLocLatitude = findViewById(R.id.estLocLatitude);
        estLocLongitude = findViewById(R.id.estLocLongitude);
        setLocationBtn = findViewById(R.id.setLocationBtn);

        estLocLatitude.setVisibility(View.GONE);
        estLocLongitude.setVisibility(View.GONE);


     setLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               estLocLatitude.setVisibility(View.VISIBLE);
               estLocLatitude.setVisibility(View.VISIBLE);
               estLocLatitude.setText(String.valueOf(latLng.latitude));
               estLocLongitude.setText(String.valueOf(latLng.longitude));
/*
               //Store promo and deals details
                Map<String, Object> estLocLatLng = new HashMap<>();
                estLocLatLng.put("est_loc_latLng", estLocationTxt);

                //To save inside the document of the userID, under the dental-procedures collection
                fStoreRef.collection("establishments").document(userId).set(estLocLatLng);*/

            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.estMapPlotting);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        mapFragment.getMapAsync(this);
    }

   @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        pakigsabotMap = googleMap;
        pakigsabotMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title("My Establishment");

                // Clears the previously touched position
                pakigsabotMap.clear();

                // Animating to the touched position
                pakigsabotMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                pakigsabotMap.addMarker(markerOptions);
                latitude = markerOptions.getPosition().latitude;
                longitude = markerOptions.getPosition().longitude;
            }
        });
        getMyLocation();
    }

    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        pakigsabotMap.setMyLocationEnabled(true);
        pakigsabotMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(@NonNull Location location) {
               latLng = new LatLng(location.getLatitude(), location.getLongitude());
               CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14f);
               pakigsabotMap.animateCamera(cameraUpdate);
            }
        });

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    longitude= latLng.longitude;
                    latitude = latLng.latitude;
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14f);
                    pakigsabotMap.animateCamera(cameraUpdate);
                }
            }
        });

    }

}
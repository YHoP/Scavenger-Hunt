package com.morgan.yvonne.scavengerhunter.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.morgan.yvonne.scavengerhunter.R;
import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

/*
To make this work in Android Emulator:
1. In Emulator:
    -Go to Settings
    -Go to Apps
    -Select your app!
    -Select Permissions
    -Enable Location !
2. Go back to Settings
    -Go to Apps
    -Select Google Play Services
    -Set the Version in the Gradle App: Module file to the SAME VERSION that is listed here.
    (Our emulator shows "8.1.85", we set it in gradle to "8.1.0"
3. In Android Studio:
    (MAKE SURE THE EMULATOR IS STILL RUNNING)
    -Go to Tools
    -Select Android
    -Select Android Device Monitor
    -Select Emulator (top right of nav bar)
    -Under "Location Controls,"
    -Set Longitude and Latitude to your desired location
    -Our building is: Long: -122.6777365, Lat: 45.520945
    -Press "Send" - this will auto update location inside emulator
4. Finding Lat/Long of other locations:
    -Open web browser
    -Go to maps.google.com
    -Type in address of desired location
    -Right click the pin
    -Click "What's here?"
    -Lat & Long will appear in info box

*/

public class MapsActivity extends FragmentActivity implements android.location.LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    LocationManager locationManager;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {

            onLocationChanged(location);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {

    }

    @Override
    public void onLocationChanged(Location location) {

        Double lat = location.getLatitude();
        Double lng = location.getLongitude();

        mMap.clear();

        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Your location"));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 18));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);

    }

}


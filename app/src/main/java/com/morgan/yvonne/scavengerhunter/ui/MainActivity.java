package com.morgan.yvonne.scavengerhunter.ui;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.morgan.yvonne.scavengerhunter.R;
import com.morgan.yvonne.scavengerhunter.models.Location;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    ParseUser mCurrentuser;

    @Bind(R.id.btnFindHunt)Button mBtnFindHunt;
    @Bind(R.id.btnCreateHunt)Button mBtnCreateHunt;
    @Bind(R.id.txtEnterStartLocation)EditText mTxtEnterStartLocation;
    @Bind(R.id.txtEnterDescription)EditText mTxtEnterDescription;

    private String mStartLocation, mDescription;
    ParseGeoPoint mGeoPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBtnCreateHunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartLocation = mTxtEnterStartLocation.getText().toString();
                mDescription = mTxtEnterDescription.getText().toString();

                try {
                    convertAddressToLatLng(mStartLocation);
                    Location location = new Location(mStartLocation, mDescription, mGeoPoint);

                    location.save(MainActivity.this, new Runnable() {
                        @Override
                        public void run() {
                            goToLocationListActivity();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("Get GeoPoint", "Failed");
                }

            }
        });

        mCurrentuser = ParseUser.getCurrentUser();
        if (mCurrentuser == null) {
            goToLoginActivity();
            finish();
        } else {
            String user = mCurrentuser.getString("username");
            Toast.makeText(this, "Hello " + user, Toast.LENGTH_LONG).show();
        }

    }


    private void convertAddressToLatLng(String location) throws IOException{

        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();

        Double lat = add.getLatitude();
        Double lng = add.getLongitude();
        mGeoPoint = new ParseGeoPoint(lat, lng);
        Log.i("LatLng: ", lat + ", " +  lng );
        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

    }

    private void goToLocationListActivity() {
        Intent intent = new Intent(this, LocationListActivity.class);
        startActivity(intent);
    }


    public void goToLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_logout:
                ParseUser.logOut();
                Intent logoutIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(logoutIntent);
                finish();
                break;
            case R.id.action_map:
                Intent mapIntent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(mapIntent);
        }


        return super.onOptionsItemSelected(item);
    }
}

package com.morgan.yvonne.scavengerhunter.ui;

import android.app.ListActivity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.morgan.yvonne.scavengerhunter.R;
import com.morgan.yvonne.scavengerhunter.models.Location;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationListActivity extends ListActivity{

    @Bind(R.id.btnCreateHunt)Button mBtnCreateHunt;
    @Bind(R.id.txtEnterStartLocation) EditText mTxtEnterStartLocation;
    @Bind(R.id.txtEnterDescription)EditText mTxtEnterDescription;

    private String mStartLocation, mDescription;
    ParseGeoPoint mGeoPoint;

    private ArrayList<String> mLocations;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        ButterKnife.bind(this);

        mBtnCreateHunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartLocation = mTxtEnterStartLocation.getText().toString();
                mDescription = mTxtEnterDescription.getText().toString();

                try {
                    convertAddressToLatLng(mStartLocation);
                    Location location = new Location(mStartLocation, mDescription, mGeoPoint);

                    location.save(LocationListActivity.this, new Runnable() {
                        @Override
                        public void run() {
                            onResume();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("Get GeoPoint", "Failed");
                }

            }
        });

        mLocations = new ArrayList<String>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLocations);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Location");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> allLocations, ParseException e) {
                if (e == null) {

                    for(ParseObject location : allLocations){
                        mLocations.add(location.getString("address"));
                    }
                    setListAdapter(mAdapter);

                } else {
                    Toast.makeText(LocationListActivity.this, "Error - please try again", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });



        setListAdapter(mAdapter);

    }


    private void convertAddressToLatLng(String location) throws IOException{

        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();

        Double lat = add.getLatitude();
        Double lng = add.getLongitude();
        mGeoPoint = new ParseGeoPoint(lat, lng);
        Log.i("LatLng: ", lat + ", " + lng);
        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {

        super.onResume();
        this.onCreate(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.morgan.yvonne.scavengerhunter.models;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.morgan.yvonne.scavengerhunter.ui.LocationListActivity;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.List;

public class Location extends Activity {
    ParseObject mLocationObject;

    Context mContext;

    public Location(String address, String description, ParseGeoPoint geoPoint){
        mLocationObject = new ParseObject("Location");
        mLocationObject.put("address", address);
        mLocationObject.put("description", description);
        mLocationObject.put("geoPoint", geoPoint);

    }


    public String getAddressObject() {
        return mLocationObject.getString("address");
    }

    public String getDescription(){
        return mLocationObject.getString("description");
    }

    public void save(final Activity context, final Runnable runnable){
        mLocationObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(mContext.getApplicationContext(), "Save location succeffully", Toast.LENGTH_LONG).show();
                    context.runOnUiThread(runnable);
                }else{
                    Toast.makeText(mContext.getApplicationContext(), "Save location fail. Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

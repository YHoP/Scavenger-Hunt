package com.morgan.yvonne.scavengerhunter.models;


import android.app.Activity;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class Location extends Activity {
    ParseObject mLocationObject;

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

    public ParseGeoPoint getGeoPoint(){
        return mLocationObject.getParseGeoPoint("geoPoint");
    }

    public void save(final Activity context, final Runnable runnable){
        mLocationObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(context.getApplicationContext(), "Save location succeffully", Toast.LENGTH_LONG).show();
                    context.runOnUiThread(runnable);
                }else{
                    Toast.makeText(context.getApplicationContext(), "Save location fail. Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

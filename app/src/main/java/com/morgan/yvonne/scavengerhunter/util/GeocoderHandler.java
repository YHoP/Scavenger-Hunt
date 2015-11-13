package com.morgan.yvonne.scavengerhunter.util;


import android.os.Bundle;
import android.os.Message;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GeocoderHandler extends Handler {

    public String handleMessage(Message message) {
        String locationAddress;
        switch(message.what) {
            case 1:
                Bundle bundle = message.getData();
                locationAddress = bundle.getString("address");
                break;
            default:
                locationAddress = null;
        }
        return locationAddress;
    }



    @Override
    public void close() {

    }

    @Override
    public void flush() {

    }

    @Override
    public void publish(LogRecord record) {

    }
}

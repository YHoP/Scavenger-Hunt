package com.morgan.yvonne.scavengerhunter.models;

import android.util.Log;

import com.morgan.yvonne.scavengerhunter.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class User{
    ParseUser mParseUser;

    public User(String username, String password) {
        mParseUser = new ParseUser();
        mParseUser.setUsername(username);
        mParseUser.setPassword(password);
    }

    public void signUp() {
        try {
            mParseUser.signUp();
            Log.i("Sign Up: ", "Successful");
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("Sign Up: ", "Fail");
        }
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public static void login(String username, String password) {
        try {
            ParseUser.logIn(username, password);
            Log.i("Log in: ", "Successful");
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("Log in: ", "Fail");

        }
//            @Override
//            public void done(ParseUser user, ParseException e) {
//                if (user != null) {
//
//                } else {
//                }
//            }
//        });
    }

    public String getUserName() {
        return mParseUser.getString("username");
    }

    public void setUserName(String username) {
        mParseUser.setUsername(username);
    }

    public void setPassword(String password) {
        mParseUser.setPassword(password);
    }




}

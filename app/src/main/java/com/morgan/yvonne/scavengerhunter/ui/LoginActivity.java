package com.morgan.yvonne.scavengerhunter.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morgan.yvonne.scavengerhunter.R;
import com.morgan.yvonne.scavengerhunter.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.txtUsername) EditText mTxtUsername;
    @Bind(R.id.txtPassword) EditText mTxtPassword;
    @Bind(R.id.btnSignUp) Button mBtnSignUp;
    @Bind(R.id.imgLogo) ImageView mImgLogo;
    @Bind(R.id.btnLogin) Button mBtnLogin;
    @Bind(R.id.relativeLayout) RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mBtnLogin.setOnClickListener(this);
        mBtnSignUp.setOnClickListener(this);
        mImgLogo.setOnClickListener(this);
        mRelativeLayout.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String username = String.valueOf(mTxtUsername.getText());
        String password = String.valueOf(mTxtPassword.getText());
        if(v.getId() == R.id.btnSignUp){
            Log.i("Sign Up", "Clicked");
            User user = new User(username, password);
            user.signUp();
            goToMainActivity();
        } else if (v.getId() == R.id.btnLogin){
            User.login(username, password);
            goToMainActivity();
            Log.i("Login", "Clicked");
        } else if (v.getId() == R.id.imgLogo || v.getId() == R.id.relativeLayout){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);

        }

    }


    public void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

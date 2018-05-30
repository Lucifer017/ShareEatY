package com.halo.loginui2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activity_Forgot_Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__forgot__password);
    }

    public void SignUp (View v){
        Intent i = new Intent(Activity_Forgot_Password.this,Activity_SignUp.class);
        startActivity(i);
    }

    public void Login (View v){
        Intent i = new Intent(Activity_Forgot_Password.this,Activity_Login.class);
        startActivity(i);
    }
}

package com.halo.loginui2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_Login extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rellay1, rellay2;
    private EditText textEmail;
    private EditText textPassword;
    private ProgressDialog progressDialog;
    private Button buttonLogin;
    private FirebaseAuth firebaseAuth;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);

        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //start home activity
            finish();
            startActivity(new Intent(getApplicationContext(), Activity_Main.class));
        }

        textEmail = (EditText) findViewById(R.id.textEmail) ;
        textPassword = (EditText) findViewById(R.id.textPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        progressDialog = new ProgressDialog(this);

        buttonLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        if(view == buttonLogin){
            userLogin();
        }
    }

    private void userLogin() {
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter your email!",Toast.LENGTH_SHORT).show();
            //stoping the function
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter your password!",Toast.LENGTH_SHORT).show();
            //stoping the function
            return;
        }

        progressDialog.setMessage("Login, please wait..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //start home activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), Activity_Main.class));
                        }else{
                            Toast.makeText(Activity_Login.this, "Wrong email or password!",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    public void SignUp (View v){
        Intent i = new Intent(Activity_Login.this,Activity_SignUp.class);
        startActivity(i);
    }

    public void ForgotPass (View v){
        Intent i = new Intent(Activity_Login.this,Activity_Forgot_Password.class);
        startActivity(i);
    }
}

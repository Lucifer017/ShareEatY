package com.halo.loginui2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class Activity_SignUp extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private Button buttonSignUp;
    private EditText textEmail;
    private EditText textPassword;
    private EditText textRePass;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //start home activity
            finish();
            startActivity(new Intent(getApplicationContext(), Activity_Main.class));
        }

        progressDialog = new ProgressDialog(this);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        textEmail = (EditText) findViewById(R.id.textEmail);
        textPassword = (EditText) findViewById(R.id.textPassword);
        textRePass = (EditText) findViewById(R.id.textRePass);

        buttonSignUp.setOnClickListener(this);

    }

    public void Login (View v){
        Intent i = new Intent(Activity_SignUp.this,Activity_Login.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view){
        if(view == buttonSignUp){
            signUpUser();
        }


    }

    private void signUpUser() {
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();
        String rePass = textRePass.getText().toString().trim();

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

        if(password.equals(rePass)){
            progressDialog.setMessage("Registering User...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //user is successfully registered
                                finish();
                                Toast.makeText(Activity_SignUp.this,"Registered successfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), Activity_Main.class));

                            }else {
                                Toast.makeText(Activity_SignUp.this,"Couldn't register.. please try again",Toast.LENGTH_SHORT).show();

                            }
                            progressDialog.dismiss();
                        }
                    });
        }
        else {
            //password are not the same
            Toast.makeText(this,"Password doesn't match!",Toast.LENGTH_SHORT).show();
            //stoping the function
            return;
        }


    }

}

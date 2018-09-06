package com.example.claudio.splashscreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
//to add on click functionality here for button and textview to sign in we use implements.......
    private Button mButtonRegister;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private TextView mTextViewSignin;

    //progress dialog to show user registering after registering
    private ProgressDialog mProgressDialog;

    //firebase authentication object
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //initialise the progreebar object
        mProgressDialog = new ProgressDialog(this);

        //initialise the firebaseAuth object
        mFirebaseAuth = FirebaseAuth.getInstance();


        //to track if user is already logged in navigate them to profile activity. Last step of verification
        if (mFirebaseAuth.getCurrentUser() !=null){
            finish();
            Intent auth = new Intent(getApplicationContext(), PostingActivity.class);
            startActivity(auth);
        }

        mButtonRegister = findViewById(R.id.btnRegister);

        mEditTextEmail = findViewById(R.id.edtTextEmail);
        mEditTextPassword = findViewById(R.id.edtPassword);

        mTextViewSignin = findViewById(R.id.txtViewSigin);

        mButtonRegister.setOnClickListener(this);
        mTextViewSignin.setOnClickListener(this);
    }

    //method to register user
    private void registerUser(){
        String email = mEditTextEmail.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();

        //to check if string is empty so one cannot proceed if either email or password is empty

        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            //stopping the function execution futher
            return;
        }

        if (TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            //stopping the function execution futher
            return;
        }

        //if validations are ok i.e email and password
        //show a progress dialog

        mProgressDialog.setMessage("Registering User.....");
        mProgressDialog.show(); //to show the progress dialog we use the show method


        //now to register user
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //user is successfully registered and logged in
                            //start the profile activity
                            //to be removed once a profile activity is created
                            //was toast now intent to profile activity
                            finish();
                            Intent created = new Intent(getApplicationContext(), PostingActivity.class);
                            startActivity(created);
                        }else {
                            Toast.makeText(LoginPage.this,
                                    "Could not register..Please try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                });



    }


    @Override
    public void onClick(View v) {  //method derived after implemnets
        //to track which click from above on click listener

        if (v == mButtonRegister){
            registerUser();
        }

        if (v == mTextViewSignin){
            //will open login activity here
            Intent login = new Intent(getApplicationContext(), SignIn.class);
            startActivity(login);

        }

    }
}

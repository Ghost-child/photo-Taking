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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity implements View.OnClickListener { //to add on click functionality

    private Button mButtonSignIn;
    private EditText mEmailSignIn, mPassSignIn;
    private TextView mTextSignIn;

    private ProgressDialog mDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        mButtonSignIn = findViewById(R.id.btnSignIn);
        mEmailSignIn = findViewById(R.id.edtTextEmail);
        mPassSignIn = findViewById(R.id.edtPassword);
        mTextSignIn = findViewById(R.id.txtViewSignUp);

        //initialise the progress dialog object
        mDialog = new ProgressDialog(this);

        //initialise firebase object which is different from the progressdialog one
        firebaseAuth = FirebaseAuth.getInstance();

        //to track if user is already logged in. This is after below NOW TO LOGIN USER
        if (firebaseAuth.getCurrentUser() != null){
            //start activity profile
            finish(); //before starting the next activity we should finish the current one
            Intent profile = new Intent(getApplicationContext(), PostingActivity.class);
            startActivity(profile);


        }


        //made possible via adding implements thus click functionality is enabled
        mButtonSignIn.setOnClickListener(this);
        mTextSignIn.setOnClickListener(this);
    }

    private void UserLogin(){ //method for when button and textview clicked should capture details
        String email = mEmailSignIn.getText().toString().trim();
        String password = mPassSignIn.getText().toString().trim();

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

        mDialog.setMessage("Login user.....");
        mDialog.show(); //to show the progress dialog we use the show method

        //now to loginIn user
        firebaseAuth.signInWithEmailAndPassword(email, password) //called during signin
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            mDialog.dismiss();
                            if (task.isSuccessful()){
                                //start next activity my case the profile activity
                                finish(); //before starting the next activity we should finish the current one
                                Intent profile = new Intent(getApplicationContext(), PostingActivity.class);
                                startActivity(profile);
                            }

                        }
                    });

    }




    @Override
    public void onClick(View v) {
        if (v ==mButtonSignIn) {
            UserLogin(); //new method to be created for user login
        }

        if (v == mTextSignIn){
            finish(); //used old method not familiart with adding finish();
            Intent textSign = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(textSign);
        }
    }
}

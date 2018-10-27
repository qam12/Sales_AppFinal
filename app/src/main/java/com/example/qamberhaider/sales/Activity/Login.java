package com.example.qamberhaider.sales.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qamberhaider.sales.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private  TextView buttonSignuR;
    private EditText Email_lg;
    private EditText Pass_lg;
    private Button buttonSignIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    static String UID;
    private ProgressDialog pDialog;

    String email_,password_;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //status bar
        Window window = this.getWindow();
        window.setBackgroundDrawableResource(R.color.bgwhite);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.TRANSPARENT);

        //firebase Auth
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //profile ativity
            UID =firebaseAuth.getCurrentUser().getUid();
            finish();
            startActivity(new Intent(getApplicationContext(),Dashboard.class));
            //Toast.makeText(this, "Profile Activity", Toast.LENGTH_SHORT).show();
        }

        Email_lg = (EditText) findViewById(R.id.Log_emaiId);
        Pass_lg = (EditText) findViewById(R.id.Log_pass);
        buttonSignIn = (Button) findViewById(R.id.logintnit);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userSignIn();
            }
        });

        buttonSignuR = (TextView) findViewById(R.id.SigPAth);
        buttonSignuR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });

    }

    private void userSignIn() {

        Log.d(TAG, "Login");
        if (validate() == false) {
            onLoginFailed();
            return;
        }
        User_Login();
    }


    private void User_Login(){
        pDialog = new ProgressDialog(Login.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Sign In...");
        pDialog.setCancelable(false);

        showpDialog();

        email_ = Email_lg.getText().toString().trim();
        password_ = Pass_lg.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email_,password_).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressDialog.dismiss();
                if (task.isSuccessful()){
                    //profile ativity
                    finish();
                    try {

                        UID =firebaseAuth.getCurrentUser().getUid();
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        //Toast.makeText(Login.this, "Profile Activity", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Exception applied", Toast.LENGTH_LONG).show();
                        Log.e(e.getClass().getName(), e.getMessage(), e);

                    }
                }
                else{
                    //display some message here
                    // Toast.makeText(Signup.this,"Registration Error",Toast.LENGTH_LONG).show();
                    Toast.makeText(Login.this, "Invalid Email and Password", Toast.LENGTH_SHORT).show();
                }
                hidepDialog();
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        email_= Email_lg.getText().toString();
        password_ = Pass_lg.getText().toString();

        if (email_.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email_).matches()) {
            Email_lg.setError("Invalid Email");
            requestFocus(Email_lg);
            valid = false;
        } else {
            Email_lg.setError(null);
        }

        if (password_.isEmpty()) {
            Pass_lg.setError("Password is empty");
            requestFocus(Pass_lg);
            valid = false;
        } else {
            Pass_lg.setError(null);
        }

        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void onLoginFailed() {
        Log.v("on","SigninFailed");
    }
}

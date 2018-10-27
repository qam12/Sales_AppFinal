package com.example.qamberhaider.sales.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qamberhaider.sales.Fragment.Noteboard;
import com.example.qamberhaider.sales.Model.User;
import com.example.qamberhaider.sales.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signup extends AppCompatActivity {

    public final static String Database_Path = "User"; // Root Database Name for Firebase Database.
    private static final String TAG = "Signup";
    ImageView SelectImage;
    private Uri FilePathUri;                // Creating URI.
    int Image_Request_Code = 7;    // Image request code for onActivityResult()
    static String UID;
    String imageUrl;

    private ProgressDialog pDialog;
    ProgressDialog progressDialog ;

    private EditText Eemail;
    private EditText Epass;
    EditText UserName, Conf_epass;

    private Button Reg_coch;
    private CircleImageView uploadimg;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private StorageReference mStorage;

    String _username,_email,_pass,_re_pass;
    ImageView Imgupload;
    TextView buttonSiginR;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        buttonSiginR = (TextView) findViewById(R.id.UpPAth);
        buttonSiginR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Database_Path);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            //profile ativity
            finish();
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
        }

        Eemail = (EditText) findViewById(R.id.Log_emaiId);
        Epass = (EditText) findViewById(R.id.passAct);
        UserName = (EditText) findViewById(R.id.userAc);
        Conf_epass = (EditText) findViewById(R.id.confirmPass);
        uploadimg = (CircleImageView) findViewById(R.id.profilePic);
        Imgupload = (ImageView) findViewById(R.id.uplobtn);
        Reg_coch = (Button) findViewById(R.id.signupbtn);

        //uploadimage calling
        Imgupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
            }
        });
        Reg_coch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignUp();
            }
        });
        progressDialog = new ProgressDialog(this);
    }

    //open camera activty func and fb app
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                uploadimg.setImageBitmap(bitmap);
                Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //open fb intent
        //callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void userSignUp() {
        Log.d(TAG, "Signup");
        if (validate() == false) {
            onSignupFailed();
            return;
        }
        Authemployee();
    }

    //Registration func()
    private void Authemployee(){
        pDialog = new ProgressDialog(Signup.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();

        _username = UserName.getText().toString().trim();
        _email = Eemail.getText().toString().trim();
        _pass = Epass.getText().toString().trim();
        _re_pass = Conf_epass.getText().toString().trim();
        firebaseAuth.createUserWithEmailAndPassword(_email, _pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            UID = firebaseAuth.getCurrentUser().getUid();
                            UploadImageFileToFirebaseStorage();

                        }
                        catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Exception applied", Toast.LENGTH_LONG).show();
                            Log.e(e.getClass().getName(), e.getMessage(), e);
                            hidepDialog();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Signup.this, "Error in Auth", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //upload image and data in firebase
    public void UploadImageFileToFirebaseStorage() {
        if (FilePathUri != null){
            StorageReference riversRef = mStorage.child("images/"+UID+"/profile.jpg");
            riversRef.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Uri downUrl = taskSnapshot.getDownloadUrl();
                            // Log.d("downUrl" , downUrl.toString());
                            imageUrl = downUrl.toString();
                            if (downUrl != null){
                                SaveData();
                            }
                            else{
                                Toast.makeText(Signup.this, "Please upload image", Toast.LENGTH_SHORT).show();
                            }

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }else {
//            Toast.makeText(Signup.this , "image not uploaded" , Toast.LENGTH_SHORT).show();
            Log.d("yes","image not uploaded");
        }
    }

    public void SaveData(){
        User user  = new User(_username , _email , _pass , imageUrl ,UID);
        mDatabase.child(UID).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseReference.equals(databaseError)){
                    hidepDialog();
//                    Toast.makeText(Signup.this , "Error in Saving" , Toast.LENGTH_SHORT).show();
                    Log.d("yes","Error in Saving");
                }else {
                    hidepDialog();
                    Intent i = new Intent(Signup.this , Dashboard.class);
                    startActivity(i);
                }
            }

        });
    }

//    public void proFile(){
//        ProfileDet  profileDet  = new ProfileDet(naMe , phonE , emaIl , desgin, nic , imageUrl ,UID);
//        mDatabase.child("PROFILE").push().setValue(profileDet, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if (databaseReference.equals(databaseError)){
//                    Toast.makeText(Signup.this , "put error" , Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(Signup.this , "Flag: True" , Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//    }

    public boolean validate() {
        boolean valid = true;
        _username = UserName.getText().toString().trim();
        _email = Eemail.getText().toString().trim();
        _pass = Epass.getText().toString().trim();
        _re_pass = Conf_epass.getText().toString().trim();

        if (_username.isEmpty() || _username.length() < 3){
            UserName.setError("At least 3 characters");
            valid = false;
        }
        else {
            UserName.setError(null);
        }
        if (_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_email).matches()){
            Eemail.setError("Enter a valid email address");
            valid = false;
        }
        else {
            Eemail.setError(null);
        }
        if (_pass.isEmpty() || _pass.length() < 7 || _pass.length() > 10){
            Epass.setError("Between & and 10 alphanumeric characters");
            valid = false;
        }
        else {
            Epass.setError(null);
        }

        if (_re_pass.isEmpty() || _re_pass.length() < 7 || _re_pass.length() > 10 || !(_re_pass.equals(_pass))) {
            Conf_epass.setError("Password Do not match");
            valid = false;
        } else {
            Conf_epass.setError(null);
        }
        return valid;
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void onSignupFailed() {
//        Toast.makeText(getBaseContext(), "Invalid", Toast.LENGTH_LONG).show();
        Log.v("on","SignupFailed");
    }




}

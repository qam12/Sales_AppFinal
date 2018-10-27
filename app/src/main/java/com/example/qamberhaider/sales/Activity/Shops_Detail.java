package com.example.qamberhaider.sales.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.qamberhaider.sales.Adapter.ShopsList;
import com.example.qamberhaider.sales.Model.ShopDetails;
import com.example.qamberhaider.sales.Model.ShopDetails_Retrive;
import com.example.qamberhaider.sales.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Shops_Detail extends AppCompatActivity {

    public final static String Database_Path = "SHOPS"; // Root Database Name for Firebase Database.
    static String UID;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog ;
    List<ShopDetails_Retrive> list;
    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops__detail);

        recyclerview = (RecyclerView) findViewById(R.id.rview);


        mDatabase = FirebaseDatabase.getInstance().getReference(Database_Path);
        firebaseAuth = FirebaseAuth.getInstance();


        UID = firebaseAuth.getCurrentUser().getUid();

        Log.v("Loc", UID);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list = new ArrayList<>();
                // StringBuffer stringbuffer = new StringBuffer();


                try {
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                        ShopDetails shopDetails = dataSnapshot1.getValue(ShopDetails.class);

                        ShopDetails_Retrive shopDetails_retrive = new ShopDetails_Retrive();
                        String title=shopDetails.getShop_Title();
                        String address=shopDetails.getShop_Address();
                        String code=shopDetails.getShop_Code();
                        shopDetails_retrive.setTitle_Shop(title);
                        shopDetails_retrive.setAddress_Shop(address);
                        shopDetails_retrive.setCode_Shop(code);
                        list.add(shopDetails_retrive);
                        progressDialog.dismiss();
                    }


                }
                catch (Exception e) {
                    Toast.makeText(getApplication(), "Exception applied", Toast.LENGTH_LONG).show();
                    Log.e(e.getClass().getName(), e.getMessage(), e);
                    progressDialog.dismiss();
                }

                ShopsList recycler = new ShopsList(getApplicationContext(),list);
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getApplication());
                recyclerview.setLayoutManager(layoutmanager);
                recyclerview.setItemAnimator( new DefaultItemAnimator());
                recyclerview.setAdapter(recycler);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //  Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(getApplication(), "No post Uploaded Yet", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

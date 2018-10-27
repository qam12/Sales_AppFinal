package com.example.qamberhaider.sales.Fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qamberhaider.sales.Activity.Dashboard;
import com.example.qamberhaider.sales.Activity.Login;
import com.example.qamberhaider.sales.Activity.OrderList;
import com.example.qamberhaider.sales.Activity.Shops_Detail;
import com.example.qamberhaider.sales.Activity.Signup;
import com.example.qamberhaider.sales.Model.ShopDetails;
import com.example.qamberhaider.sales.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class Shops extends Fragment {

    //public final static String Storage_Path = "Employee_profile/"; // Folder path for Firebase Storage.
    public final static String Database_Path = "SHOPS"; // Root Database Name for Firebase Database.
    static String UID;

    ProgressDialog progressDialog;
    private Button AddShp,notify;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private StorageReference mStorage;
    EditText _shpTitle, _shp_loc, _shpCode;
    private static final int REQUEST_LOCATION = 1;
    Button button;
    LocationManager locationManager;
    String lattitude,longitude, addres,shoptitle,shopcode;
    TextView Allorder;

    public Shops() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_shops, container, false);

        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Database_Path);
        firebaseAuth = FirebaseAuth.getInstance();

        UID = firebaseAuth.getCurrentUser().getUid();

        Allorder = (TextView) v.findViewById(R.id.listorder);
        Allorder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderList.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(getActivity());

        _shpTitle = (EditText) v.findViewById(R.id.ShopTitle);
        _shp_loc = (EditText) v.findViewById(R.id.shopLocation);
        _shpCode = (EditText) v.findViewById(R.id.shopCode);
        AddShp = (Button)v.findViewById(R.id.addshops);
        notify = (Button)v.findViewById(R.id.shopdetail_);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLOad();
            }
        });


        AddShp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate() == false) {
                    onShopFailed();
                    return;
                }
                    loc();
            }
        });

        return v;
    }

    public  void loc(){

        locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);
            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                System.out.print("Lattitude = " + lattitude);
                System.out.print("Longitude = " + longitude);

                Add_shop();
            }
            else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                System.out.print("Lattitude = " + lattitude);
                System.out.print("Longitude = " + longitude);

                Add_shop();


            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                System.out.print("Lattitude = " + lattitude);
                System.out.print("Longitude = " + longitude);

                Add_shop();


            }else{

                Toast.makeText(getActivity(),"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void Add_shop() {

            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            shoptitle = _shpTitle.getText().toString().trim();
            addres = _shp_loc.getText().toString().trim();
            shopcode = _shpCode.getText().toString().trim();

            try{
                ShopDetails shopDetails  = new ShopDetails(shoptitle,addres,lattitude,longitude,shopcode,UID);
                mDatabase.push().setValue(shopDetails, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        Intent i;
                        if (databaseReference.equals(databaseError)) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Error in Adding", Toast.LENGTH_SHORT).show();
    //                        i = new Intent(getActivity(), Home2.class);
    //                        startActivity(i);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Added Succesfully", Toast.LENGTH_SHORT).show();
                            i = new Intent(getActivity(), Shops_Detail.class);
                            startActivity(i);
                        }
                    }
            });
        }

        catch (Exception e) {
            Toast.makeText(getActivity(), "Exception applied", Toast.LENGTH_LONG).show();
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }

    public boolean validate() {
        boolean valid = true;
         shoptitle = _shpTitle.getText().toString().trim();
         addres = _shp_loc.getText().toString().trim();
         shopcode = _shpCode.getText().toString().trim();

        if (shoptitle.isEmpty() || shoptitle.length() < 10){
            _shpTitle.setError("At least 4 characters");
            valid = false;
        }
        else {
            _shpTitle.setError(null);
        }
         if (addres.isEmpty() || addres.length() < 20){
             _shp_loc.setError("Add full Address");
             valid = false;
         }
         else {
             _shp_loc.setError(null);
         }

        if (shopcode.isEmpty() || shopcode.length() > 4){
            _shpCode.setError("At least 4 characters");
            valid = false;
        }
        else {
            _shpCode.setError(null);
        }
        return valid;
    }

    public void onShopFailed() {
//        Toast.makeText(getBaseContext(), "Invalid", Toast.LENGTH_LONG).show();
        Log.v("on","shopAddedFailed");
     }

    public void onLOad() {
//        Toast.makeText(getBaseContext(), "Invalid", Toast.LENGTH_LONG).show();
        Log.v("on","shopAddedFailed");
        Intent i = new Intent(getActivity(), Shops_Detail.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }



}



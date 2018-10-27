package com.example.qamberhaider.sales.Activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qamberhaider.sales.Fragment.Noteboard;
import com.example.qamberhaider.sales.Fragment.Order;
import com.example.qamberhaider.sales.Fragment.Profile;
import com.example.qamberhaider.sales.Fragment.Shops;
import com.example.qamberhaider.sales.Model.User;
import com.example.qamberhaider.sales.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static String UID;

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private FirebaseAuth firebaseAuth;
    public final static String Database_Path = "User";
    CircleImageView ProfileImg;
    String m_pic,m_name;
    TextView UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Database_Path);
        firebaseAuth = FirebaseAuth.getInstance();

        UID = firebaseAuth.getCurrentUser().getUid();

        progressDialog = new ProgressDialog(this);



        //Main Fragment
        Noteboard fragement = new Noteboard();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameit,fragement);
        fragmentTransaction.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View des = LayoutInflater.from(getBaseContext()).inflate(R.layout.nav_header_dashboard, navigationView);

        UserName = (TextView)des.findViewById(R.id.userPro);
        ProfileImg = (CircleImageView)des.findViewById(R.id.dpPro);

        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        if (UID != null) {
            mDatabase.child(UID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    try {
                        if (user != null) {
                            m_name = user.employee_FirstName;

                            m_pic = user.employee_Profilepicture;
                            //personal data
                            UserName.setText(m_name.toString());
                            Log.d("Name",m_name.toString());
                            Picasso.get().load(m_pic).placeholder(R.mipmap.place_avatar)
                                    .error(R.mipmap.place_avatar)
                                    .into(ProfileImg);
                            Log.d("Url",m_pic.toString());
                            progressDialog.dismiss();
                        }
                    }
                    catch (Exception e) {
                        // This will catch any exception, because they are all descended from Exception
                        System.out.println("Error " + e.getMessage());
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(Dashboard.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Intent i;
        int id = item.getItemId();

        if (id == R.id._noteboard) {

            Noteboard fragement = new Noteboard();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameit,fragement);
            fragmentTransaction.commit();

        } else if (id == R.id._profile) {
            Profile fragement = new Profile();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameit,fragement);
            fragmentTransaction.commit();

        } else if (id == R.id._shops) {

            Shops fragement = new Shops();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameit,fragement);
            fragmentTransaction.commit();

        }
        else if (id == R.id._orders) {

            Order fragement = new Order();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameit,fragement);
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_logout) {

            firebaseAuth.signOut();
            finish();
            i = new Intent(Dashboard.this, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
//                Log.i("test","check");
        }

//        else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
}
}

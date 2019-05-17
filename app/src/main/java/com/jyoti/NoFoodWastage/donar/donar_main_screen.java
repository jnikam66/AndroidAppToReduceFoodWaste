package com.jyoti.NoFoodWastage.donar;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jyoti.NoFoodWastage.CreatePost;
import com.jyoti.NoFoodWastage.Donar_DetailsPage2;
import com.jyoti.NoFoodWastage.Donar_details;
import com.jyoti.NoFoodWastage.LocationFinder;
import com.jyoti.NoFoodWastage.Post;
import com.jyoti.NoFoodWastage.PostAdapter;
import com.jyoti.NoFoodWastage.R;
import com.jyoti.NoFoodWastage.SignInActivity;
import com.jyoti.NoFoodWastage.Thankyou;
import com.jyoti.NoFoodWastage.choose_category;

import java.util.ArrayList;
import java.util.List;

public class donar_main_screen extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private FloatingActionButton addPost;
    private RecyclerView mRecyclerView;
    private DonarPostAdapter mAdapter;
    private List<Post> mProductList;
    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onResume(){
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        addPost = findViewById(R.id.floatingActionButton_AddPosts);
        dl = (DrawerLayout)findViewById(R.id.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        t = new ActionBarDrawerToggle(this, dl, R.string.common_open_on_phone,R.string.common_signin_button_text_long);
        addPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                startActivity(new Intent(donar_main_screen.this, CreatePost.class));
            }
        });
        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Toast.makeText(donar_main_screen.this, "My Account",Toast.LENGTH_SHORT).show();
                  /*  case R.id.settings:
                        Toast.makeText(donar_main_screen.this, "Settings",Toast.LENGTH_SHORT).show();
                    case R.id.mycart:
                        Toast.makeText(donar_main_screen.this, "My Cart",Toast.LENGTH_SHORT).show();
                  */  default:
                        return true;
                }
            }
        });



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.donar_main_screen);
        addPost = findViewById(R.id.floatingActionButton_AddPosts);
        dl = (DrawerLayout)findViewById(R.id.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        t = new ActionBarDrawerToggle(this, dl, R.string.common_open_on_phone,R.string.common_signin_button_text_long);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Toast.makeText(donar_main_screen.this, "My Account",Toast.LENGTH_SHORT).show();
                   /* case R.id.settings:
                        Toast.makeText(donar_main_screen.this, "Settings",Toast.LENGTH_SHORT).show();
                    case R.id.mycart:
                        Toast.makeText(donar_main_screen.this, "My Cart",Toast.LENGTH_SHORT).show();
                   */ default:
                        return true;
                }
            }
        });
        addPost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkLocationPermission();
                }
                startActivity(new Intent(donar_main_screen.this, CreatePost.class));
            }
        });

        LocationFinder finder;
        double longitude = 0.0, latitude = 0.0;
        finder = new LocationFinder(donar_main_screen.this);
        if (finder.canGetLocation()) {
            latitude = finder.getLatitude();
            longitude = finder.getLongitude();
            //Toast.makeText(donar_main_screen.this,"lat-lng" + latitude + " — " + longitude, Toast.LENGTH_LONG).show();
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkLocationPermission();
            }
        } else {
            finder.showSettingsAlert();
        }


       /* mProductList = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("posts");

// Attach a listener to read the data at our posts reference
       // ref.addValueEventListener(listener);
        //ValueEventListener listener =
                ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final DatabaseReference myRef = database.getReference();
                final DatabaseReference postsRef = myRef.child("posts");
                FirebaseStorage storage;
                StorageReference storageReference;
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                mFirebaseAuth = FirebaseAuth.getInstance();

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Post post = ds.getValue(Post.class);
                    String userID =  mFirebaseAuth.getCurrentUser().getUid();
                    if(ds.getKey().toString().contains((userID)))
                        mProductList.add(post);
                    //System.out.println(userID);
                }
                setmAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


      //  super.onCreate(savedInstanceState);
        setContentView(R.layout.donar_main_screen);
        //getting the recyclerview from xml
        mRecyclerView = (RecyclerView) findViewById(R.id.idRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Populate the products

        //  mProductList.add(new Post("dasdasd","breakfast",12,null,null,"dfs","sdf"));
        // mProductList.add(new Post());

        //set adapter to recyclerview
        mAdapter = new DonarPostAdapter(mProductList,this);
        mRecyclerView.setAdapter(mAdapter);
*/
    }
    public void setmAdapter(){
        setContentView(R.layout.donar_main_screen);
        //getting the recyclerview from xml
        mRecyclerView = (RecyclerView) findViewById(R.id.idRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Populate the products

        //  mProductList.add(new Post("dasdasd","breakfast",12,null,null,"dfs","sdf"));
        // mProductList.add(new Post());

        //set adapter to recyclerview
        mAdapter = new DonarPostAdapter(mProductList,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }

            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}

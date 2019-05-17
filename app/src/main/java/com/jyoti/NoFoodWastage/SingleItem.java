package com.jyoti.NoFoodWastage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class SingleItem extends AppCompatActivity implements OnMapReadyCallback {
    ImageView viewImage;
    TextView title;
    TextView postedBy;
    TextView foodType;
    TextView serves;
    TextView description;
    Button callButton;
    private GoogleMap mMap;
   private Post selectedPost;
    private static final String TAG = "SingleItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);
        viewImage=findViewById(R.id.itemImage);
        title =findViewById(R.id.title_value);
        postedBy = findViewById(R.id.postedBy_value);
        foodType = findViewById(R.id.foodtype_value);
        serves = findViewById(R.id.quantity_value);
        description = findViewById(R.id.description_value);
        callButton = findViewById(R.id.call_button);
        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        if(extras!=null){
            selectedPost =(Post) extras.getSerializable("selectedPost");
            //Post selectedPost = (Post)intent.getParcelableExtra("selectedPost");

            // StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            Log.e(TAG, "URI: "+selectedPost.getPostId());
            Picasso.get().load(selectedPost.getImageUrl()).into(viewImage);
            title.setText(selectedPost.getTitle());
            foodType.setText(selectedPost.getFoodCategory());
            serves.setText(selectedPost.getNumOfServes().toString());
            description.setText(selectedPost.getDescription());
            postedBy.setText(selectedPost.getPostedBy());
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.locationmap);

            mapFragment.getMapAsync(this);


            callButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Log.e(TAG, "Call button clicked..");
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:0123456789"));
                    startActivity(intent);


                }
            });
        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        if(selectedPost.getLocation()!=null){
            markerOptions.position(new com.google.android.gms.maps.model.LatLng(selectedPost.getLocation().getLatitude(),selectedPost.getLocation().getLongitude()));
        }


        // Setting the title for the marker.
        // This will be displayed on taping the marker
        //markerOptions.title(latLng.latitude + " : " + latLng.longitude);

       // Placing a marker on the touched position
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new com.google.android.gms.maps.model.LatLng(selectedPost.getLocation().getLatitude(),selectedPost.getLocation().getLongitude())));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

    }
}

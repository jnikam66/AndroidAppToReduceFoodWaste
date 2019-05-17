package com.jyoti.NoFoodWastage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jyoti.NoFoodWastage.donar.donar_main_screen;

public class Donar_DetailsPage2 extends AppCompatActivity {

    private Button mSubmitButton;
    private EditText storePhoneNo;
    private EditText storeWebsite ;
    private static final String TAG = "DonarDetailsPage2";

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar__details_page2);
        mSubmitButton = (Button) findViewById(R.id.button_submit);
        storePhoneNo = (EditText) findViewById(R.id.editText_StorePhoneNo);
        storeWebsite = (EditText) findViewById(R.id.editText_StoreWebsite);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String storephoneno = storePhoneNo.getText().toString();
                String storewebsite = storeWebsite.getText().toString();
                Log.e(TAG, "Submimt button clicked..");
                if (currentUser != null) {
                    if(android.util.Patterns.PHONE.matcher(storephoneno).matches()) {
                        myRef.child("users").child(currentUser.getUid()).child("StoreDetails").child("StorePhoneNo").setValue(storephoneno);
                        myRef.child("users").child(currentUser.getUid()).child("StoreDetails").child("StoreWebsite").setValue(storewebsite);
                        Log.e(TAG, "Store details stored in database..");
                        startActivity(new Intent(Donar_DetailsPage2.this, donar_main_screen.class));

                    }else{
                        Log.e(TAG, "Validation error..");
                        if(storephoneno.length()==0){
                            storePhoneNo.setError("Store Phone No is required");
                        }

                    }
                } else {
                    Log.e(TAG, "Current user is null..");
                    startActivity(new Intent(Donar_DetailsPage2.this, SignInActivity .class));
                }



            }
        });
    }
}

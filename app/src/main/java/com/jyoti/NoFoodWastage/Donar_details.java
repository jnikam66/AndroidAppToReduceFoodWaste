package com.jyoti.NoFoodWastage;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.Locale;
import java.util.ArrayList;



public class Donar_details extends Activity {

    private Button mNextButton;
    private Spinner storeCountry;
    private EditText storeName;
    private EditText storeAddress ;
    private EditText storeZipCode ;
    private static final String TAG = "DonarDetailsPage1";

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_details);
        mNextButton = (Button) findViewById(R.id.button_submit);
        storeCountry = (Spinner) findViewById(R.id.country_spinner);
        storeName = (EditText) findViewById(R.id.editText_StoreName);
        storeAddress = (EditText) findViewById(R.id.editText_StoreAddress);
        storeZipCode = (EditText) findViewById(R.id.editText_ZipCode);

        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        countries.add("Select Country");
        Collections.sort(countries);
        for (String country : countries) {
            System.out.println(country);
        }

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countries);

        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the your spinner
        this.storeCountry.setAdapter(countryAdapter);
        storeCountry.setSelection(186);

        mNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            String storename = storeName.getText().toString();
            String storeaddress = storeAddress.getText().toString();
            String storezipcode = storeZipCode.getText().toString();
            String  storecountry = storeCountry.getSelectedItem().toString();
                Log.e(TAG, "Next button clicked..");
                if (currentUser != null) {
                    if(storename.length()!=0&&storeaddress.length()!=0&&storezipcode.length()!=0&&storecountry.length()!=0) {
                        myRef.child("users").child(currentUser.getUid()).child("StoreDetails").child("StoreName").setValue(storename);
                        myRef.child("users").child(currentUser.getUid()).child("StoreDetails").child("StoreAddress").setValue(storeaddress);
                        myRef.child("users").child(currentUser.getUid()).child("StoreDetails").child("StoreZipCode").setValue(storezipcode);
                        myRef.child("users").child(currentUser.getUid()).child("StoreDetails").child("StoreCountry").setValue(storecountry);
                        Log.e(TAG, "Store details stored in database..");
                        startActivity(new Intent(Donar_details.this,Donar_DetailsPage2.class));

                    }else{
                        Log.e(TAG, "Validation error..");
                        if(storename.length()==0){
                            storeName.setError("Store Name is required");
                        }
                        if(storeaddress.length()==0){
                            storeAddress.setError("Store Address is required");
                        }
                        if(storezipcode.length()==0){
                            storeZipCode.setError("Zip Code is required");
                        }
                        if(storeCountry.getSelectedItemPosition()==186){
                            View selectedView = storeCountry.getSelectedView();
                            if (selectedView != null && selectedView instanceof TextView) {
                                TextView selectedTextView = (TextView) selectedView;

                                //String errorString = selectedTextView.getResources().getString(storeCountry.getItemAtPosition(186).toString());

                                selectedTextView.setError("Please select country");

                            }
                        }
                    }


                } else {
                    Log.e(TAG, "Current user is null..");
                    startActivity(new Intent(Donar_details.this, SignInActivity .class));
                }



            }
        });
    }

}

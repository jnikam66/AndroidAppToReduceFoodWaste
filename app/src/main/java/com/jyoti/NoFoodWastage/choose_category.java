package com.jyoti.NoFoodWastage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class choose_category extends AppCompatActivity {

    private Button donarButton;
    private Button receiverButton;
    private static final String TAG = "ChooseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        donarButton = (Button) findViewById(R.id.button_donor);
        receiverButton = (Button) findViewById(R.id.button_receiver);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference myRef = database.getReference();

        donarButton.setOnClickListener(new View.OnClickListener() {
            User user = new User();
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Donar button clicked..");
                if (currentUser != null) {
                    user.email = currentUser.getEmail();
                    user.userType = "Donar";
                    myRef.child("users").child(currentUser.getUid()).setValue(user);
                    startActivity(new Intent(choose_category.this,Donar_details.class));
                } else {
                    startActivity(new Intent(choose_category.this, SignInActivity .class));
                }


            }
        });

        receiverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                Log.e(TAG, "Receiver button clicked.");
                if (currentUser != null) {
                    user.email = currentUser.getEmail();
                    user.userType = "Receiver";
                    myRef.child("users").child(currentUser.getUid()).setValue(user);
                    startActivity(new Intent(choose_category.this,AvailableDonations.class));
                } else {
                    startActivity(new Intent(choose_category.this, SignInActivity .class));

                }

            }
        });
    }
}

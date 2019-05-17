package com.jyoti.NoFoodWastage;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.jyoti.NoFoodWastage.donar.donar_main_screen;

public class Thankyou extends Activity {
     private Button okay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        okay = (Button) findViewById(R.id.button_ok);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Thankyou.this, donar_main_screen.class));
            }
        });
    }

}

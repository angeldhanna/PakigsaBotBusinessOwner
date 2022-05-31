package com.capstone.pakigsabotbusinessowner.Restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.capstone.pakigsabotbusinessowner.R;
import com.capstone.pakigsabotbusinessowner.Restaurant.PromoAndDeals.PromoAndDealsRestaurant;
import com.capstone.pakigsabotbusinessowner.Services.InputLocation;

public class InEstLocationOrNot extends AppCompatActivity {
    Button yesBtn, noBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_est_location_or_not);

        //References
        refs();

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PlotLocationResto.class);
                startActivity(in);
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), InputLocation.class);
                startActivity(in);
            }
        });
    }

    private void refs() {
        yesBtn = findViewById(R.id.yesEstLocBtn);
        noBtn = findViewById(R.id.noEstLocBtn);
    }


}
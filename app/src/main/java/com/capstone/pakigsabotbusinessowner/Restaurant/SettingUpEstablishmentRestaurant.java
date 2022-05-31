package com.capstone.pakigsabotbusinessowner.Restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.capstone.pakigsabotbusinessowner.DentalClinic.PromoAndDeals.PromoAndDealsDentalClinic;
import com.capstone.pakigsabotbusinessowner.EstablishmentRules.Resto.EstRulesResto;
import com.capstone.pakigsabotbusinessowner.R;
import com.capstone.pakigsabotbusinessowner.Restaurant.PromoAndDeals.PromoAndDealsRestaurant;
import com.capstone.pakigsabotbusinessowner.Services.ServicesDentalClinic;

public class SettingUpEstablishmentRestaurant extends AppCompatActivity {
    Button servicesBtn, promoAndDealsBtn, rulesBtn, estLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_up_establishment_restaurant);

        //References
        servicesBtn = findViewById(R.id.setUpProcBtn);
        promoAndDealsBtn = findViewById(R.id.setUpPromoAndDealsBtn);
        rulesBtn = findViewById(R.id.setUpRulesBtn);
        estLocation = findViewById(R.id.setUpEstLocationBtn);

        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MenuRestaurant.class);
                startActivity(in);
            }
        });
        promoAndDealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PromoAndDealsRestaurant.class);
                startActivity(in);
            }
        });

        rulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), EstRulesResto.class);
                startActivity(in);
            }
        });

        estLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), InEstLocationOrNot.class);
                startActivity(in);
            }
        });
    }
}
package com.capstone.pakigsabotbusinessowner.InternetCafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.capstone.pakigsabotbusinessowner.CoworkingSpace.Rules.RulesCoworkingSpace;
import com.capstone.pakigsabotbusinessowner.InternetCafe.PromosAndDeals.PromosAndDealsIC;
import com.capstone.pakigsabotbusinessowner.InternetCafe.Rules.RulesInternetCafe;
import com.capstone.pakigsabotbusinessowner.Services.ServicesInternetCafe;
import com.capstone.pakigsabotbusinessowner.R;

public class SettingUpEstablishmentIC extends AppCompatActivity {
    Button servicesBtn, promoAndDealsBtn, rulesBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_up_establishment_ic);

        //References
        servicesBtn = findViewById(R.id.setUpServBtn);
        promoAndDealsBtn = findViewById(R.id.setUpPromoAndDealsBtn);
        rulesBtn = findViewById(R.id.setUpRulesBtn);


        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ServicesInternetCafe.class);
                startActivity(in);
            }
        });
        promoAndDealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PromosAndDealsIC.class);
                startActivity(in);
            }
        });

        rulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), RulesInternetCafe.class);
                startActivity(in);
            }
        });

    }
}
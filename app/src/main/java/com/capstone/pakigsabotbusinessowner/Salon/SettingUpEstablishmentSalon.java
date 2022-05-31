package com.capstone.pakigsabotbusinessowner.Salon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.capstone.pakigsabotbusinessowner.InternetCafe.Rules.RulesInternetCafe;
import com.capstone.pakigsabotbusinessowner.Salon.PromosAndDeals.PromosAndDealsSalon;
import com.capstone.pakigsabotbusinessowner.Salon.Rules.RulesSalon;
import com.capstone.pakigsabotbusinessowner.Services.ServicesSalon;
import com.capstone.pakigsabotbusinessowner.R;

public class SettingUpEstablishmentSalon extends AppCompatActivity {
    Button servicesBtn, promoAndDealsBtn, rulesBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_up_establishment_salon);

        //References
        servicesBtn = findViewById(R.id.setUpServBtn);
        promoAndDealsBtn = findViewById(R.id.setUpPromoAndDealsBtn);
        rulesBtn = findViewById(R.id.setUpRulesBtn);

        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ServicesSalon.class);
                startActivity(in);
            }
        });
        promoAndDealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PromosAndDealsSalon.class);
                startActivity(in);
            }
        });

        rulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), RulesSalon.class);
                startActivity(in);
            }
        });

    }
}
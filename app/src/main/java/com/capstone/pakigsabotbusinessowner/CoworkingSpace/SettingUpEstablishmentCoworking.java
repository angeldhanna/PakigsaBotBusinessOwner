package com.capstone.pakigsabotbusinessowner.CoworkingSpace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.capstone.pakigsabotbusinessowner.CoworkingSpace.CancellationPolicies.CancellationPolicyCoworkingSpace;
import com.capstone.pakigsabotbusinessowner.CoworkingSpace.PromosAndDeals.PromosAndDealsCoworking;
import com.capstone.pakigsabotbusinessowner.CoworkingSpace.Rules.RulesCoworkingSpace;
import com.capstone.pakigsabotbusinessowner.Services.ServicesCoworkingSpace;
import com.capstone.pakigsabotbusinessowner.Spa.PromoAndDeals.PromoAndDealsSpa;
import com.capstone.pakigsabotbusinessowner.Services.ServicesSpa;
import com.capstone.pakigsabotbusinessowner.R;

public class SettingUpEstablishmentCoworking extends AppCompatActivity {
    Button servicesBtn, promoAndDealsBtn, rulesBtn, cancelPolicyBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_up_establishment_coworking);

        //References
        servicesBtn = findViewById(R.id.setUpServBtn);
        promoAndDealsBtn = findViewById(R.id.setUpPromoAndDealsBtn);
        rulesBtn = findViewById(R.id.setUpRulesBtn);

        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ServicesCoworkingSpace.class);
                startActivity(in);
            }
        });
        promoAndDealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PromosAndDealsCoworking.class);
                startActivity(in);
            }
        });

        rulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), RulesCoworkingSpace.class);
                startActivity(in);
            }
        });
        cancelPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), CancellationPolicyCoworkingSpace.class);
                startActivity(in);
            }
        });
    }
}
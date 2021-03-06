package com.capstone.pakigsabotbusinessowner.EstablishmentRulesBO.DentalClinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.capstone.pakigsabotbusinessowner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddEstRulesDentalClinic extends AppCompatActivity {
    ImageView backBtn, saveBtn;
    EditText estRulesDesc;
    FirebaseAuth fAuth;
    FirebaseFirestore fStoreRef;
    String userId, autoId, txtEstRulesName, txtEstRulesDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_est_rules_dental_clinic);

        //References
        refs();

        fAuth = FirebaseAuth.getInstance();
        fStoreRef = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        autoId = UUID.randomUUID().toString();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoRules();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRestoEstRules();
            }
        });
    }
    private void refs() {
        estRulesDesc = findViewById(R.id.ruleTxt);

        backBtn = findViewById(R.id.backBtnAddEstRules);
        saveBtn = findViewById(R.id.saveBtnRule);

    }
    private void restoRules() {
        Intent intent = new Intent(getApplicationContext(), EstRulesDentalClinic.class);
        startActivity(intent);
    }

    private void saveRestoEstRules() {
        txtEstRulesDesc = estRulesDesc.getText().toString().trim();


        //Validations::

        if (txtEstRulesDesc.isEmpty()){
            Toast.makeText(AddEstRulesDentalClinic.this, "Please input a rule.", Toast.LENGTH_SHORT).show();
        }


        Toast.makeText(AddEstRulesDentalClinic.this, "Upload Successful", Toast.LENGTH_SHORT).show();

        //Store promo and deals details
        Map<String, Object>dentalEstRules = new HashMap<>();
        dentalEstRules.put("dental_ruleId", autoId);
        dentalEstRules.put("dental_desc", txtEstRulesDesc);
        dentalEstRules.put("estId", userId);

        //To save inside the document of the userID, under the dental-procedures collection
        fStoreRef.collection("establishments").document(userId).collection("dental-est-rules").document(autoId).set(dentalEstRules);

        restoRules();

    }
}
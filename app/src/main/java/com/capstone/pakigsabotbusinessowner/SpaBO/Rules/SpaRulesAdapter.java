package com.capstone.pakigsabotbusinessowner.SpaBO.Rules;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.pakigsabotbusinessowner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpaRulesAdapter extends RecyclerView.Adapter<SpaRulesAdapter.MyViewHolder> {
    //creating variables for Context and ArrayList
    Context context;
    ArrayList<SpaRulesModel> spaRulesArrayList;

    //initializing variables
    ProgressDialog progressDialog;

    //Firebase
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public SpaRulesAdapter() {
        //empty constructor needed
    }

    //creating constructor for the adapter class
    public SpaRulesAdapter(Context context, ArrayList<SpaRulesModel> spaRulesArrayList) {
        this.context = context;
        this.spaRulesArrayList = spaRulesArrayList;
        this.progressDialog = new ProgressDialog(context);
        this.fStore = FirebaseFirestore.getInstance();
        this.fAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //passing layout file to display the items
        View itemView = LayoutInflater.from(context).inflate(R.layout.rules_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull SpaRulesAdapter.MyViewHolder holder, int position) {
        //creating  object of DentalClinicPromoAndDealsModel class and setting data to the textviews from the DentalClinicPromoAndDealsModel class
        SpaRulesModel spaRulesList = spaRulesArrayList.get(position);

        holder.desc.setText(spaRulesList.getSpaRuleDesc());

        //Update an item from the dental procedures
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.desc.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_rules_items_popup))
                        .setExpanded(true, 900)
                        .create();

                dialogPlus.show();

                //References
                View viewRules = dialogPlus.getHolderView();
                TextInputLayout ruleDescLayout = viewRules.findViewById(R.id.updateRuleDescLayout);
                TextInputEditText descTxt = viewRules.findViewById(R.id.rulesDescTxt);
                Button updateBtnPopUp = viewRules.findViewById(R.id.updateBtnPopUp);

                //Retrieving Data
                descTxt.setText(spaRulesList.getSpaRuleDesc());

                updateBtnPopUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Initialization of variables
                        String txtRuleDesc = descTxt.getText().toString();

                        //Validations
                        if (txtRuleDesc.isEmpty()) {
                            ruleDescLayout.setError("Enter Rule Description");
                        } else {
                            ruleDescLayout.setErrorEnabled(false);
                            ruleDescLayout.setError("");
                        }

                        //To save updates to the database
                        Map<String, Object> spaRules = new HashMap<>();
                        spaRules.put("rule_desc", txtRuleDesc);
                        spaRules.put("rule_id", spaRulesList.getSpaRuleID());


                        String userId = fAuth.getCurrentUser().getUid();
                        fStore.collection("establishments").document(userId).collection("spa-rules").document(spaRulesList.getSpaRuleID()).set(spaRules)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, " Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, " Error While Updating!", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                        notifyItemChanged(position);
                    }
                });
            }
        });

        //Delete from rules
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context)
                        .setTitle("DELETE RULE")
                        .setMessage("Do you want to delete this rule?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                progressDialog = new ProgressDialog(context);
                                progressDialog.setCancelable(false);
                                progressDialog.setTitle("Deleting...");
                                progressDialog.show();

                                String userId = fAuth.getCurrentUser().getUid();
                                fStore.collection("establishments").document(userId).collection("spa-rules").document(spaRulesList.getSpaRuleID())
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog.dismiss();
                                                Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                                spaRulesArrayList.remove(position);
                                                notifyItemRemoved(position);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Failed to Delete!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return spaRulesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //refer all elements where data will be populated
        TextView desc;
        ImageView update, delete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.estRulesRVDesc);
            update = itemView.findViewById(R.id.estRulesUpdateBtn);
            delete = itemView.findViewById(R.id.estRulesDeleteBtn);
        }
    }

}


package com.capstone.pakigsabotbusinessowner.ServicesBO;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.capstone.pakigsabotbusinessowner.NavigationFragmentsBO.HistoryFragment;
import com.capstone.pakigsabotbusinessowner.NavigationFragmentsBO.HomeFragment;
import com.capstone.pakigsabotbusinessowner.NavigationFragmentsBO.ReservationsFragment;
import com.capstone.pakigsabotbusinessowner.NavigationFragmentsBO.ServicesFragment;
import com.capstone.pakigsabotbusinessowner.R;
import com.capstone.pakigsabotbusinessowner.SpaBO.AddServiceSpa;
import com.capstone.pakigsabotbusinessowner.SpaBO.Model.SpaServices;
import com.capstone.pakigsabotbusinessowner.SpaBO.Adapter.SpaAdapter;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ServicesSpa extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    ImageView addServBtn, backBtn;
    RecyclerView spaServRecyclerView;
    ArrayList<SpaServices> spaServicesArrayList;
    SpaAdapter spaAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseFirestore fStore;
    FirebaseUser user;
    String userId;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_spa);

        //References:
        refs();

        //SwipeRefreshLayout init
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        //recyclerview initialization
        spaServRecyclerView = findViewById(R.id.spaRecyclerView);
        spaServRecyclerView.setHasFixedSize(true);
        spaServRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adding array list to recyclerview adapter
        fStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        spaServicesArrayList = new ArrayList<>();

        //initializing the arraylist where all data is stored
        spaServicesArrayList = new ArrayList<SpaServices>();

        //initializing the adapter
        spaAdapter = new SpaAdapter(ServicesSpa.this, spaServicesArrayList);
        spaServRecyclerView.setAdapter(spaAdapter);

        //to get the data from Firebase Firestore
        getSpaServices();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFragment();
            }
        });

        addServBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addService();
            }
        });
    }

    public void refs() {

        addServBtn = findViewById(R.id.addSpaServiceBtn);
        backBtn = findViewById(R.id.backSpaServiceBtn);
    }

    private void homeFragment() {
        setContentView(R.layout.activity_bottom_navigation);

        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_nav);

        //Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_reserve_bo));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_services_bo));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_home_24_bo));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_history_bo));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_help));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initialize fragment
                Fragment fragment = null;

                //Check condition
                switch (item.getId()) {
                    case 1: //When id is 1, initialize reservations fragment
                        fragment = new ReservationsFragment();
                        break;

                    case 2: //When id is 2, initialize services fragment
                        fragment = new ServicesFragment();
                        break;

                    case 3: //When id is 3, initialize home fragment
                        fragment = new HomeFragment();
                        break;

                    case 4: //When id is 4, initialize reservations history fragment
                        fragment = new HistoryFragment();
                        break;

                }
                //Load fragment
                loadFragment(fragment);
            }
        });

        /*//Set notification count
        bottomNavigation.setCount(3,"10");*/
        //Set home fragment initially selected
        bottomNavigation.show(3, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });


    }

    private void loadFragment(Fragment fragment) {
        //Replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }




    private void addService(){
        Intent intent = new Intent(getApplicationContext(), AddServiceSpa.class);
        startActivity(intent);
    }

    private void getSpaServices() {
        //Showing progressdialog while fetching the data
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        fStore.collection("establishments").document(userId).collection("spa-services")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        spaServicesArrayList.clear();

                        for(DocumentSnapshot doc : task.getResult()){
                            SpaServices servList = new SpaServices(doc.getString("serv_id"),
                                    doc.getString("serv_name"),
                                    doc.getString("serv_desc"),
                                    doc.getString("serv_rate"),
                                    doc.getString("serv_image"));
                            spaServicesArrayList.add(servList);
                        }
                        spaAdapter = new SpaAdapter(ServicesSpa.this, spaServicesArrayList);
                        // setting adapter to our recycler view.
                        spaServRecyclerView.setAdapter(spaAdapter);
                        spaAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ServicesSpa.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Refresh the recyclerview::
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSpaServices();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
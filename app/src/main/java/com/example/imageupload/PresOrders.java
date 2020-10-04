package com.example.imageupload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PresOrders extends AppCompatActivity implements DImageAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private DImageAdapter mImageAdapter;
    private DatabaseReference mDatabaseReference;
    private List<Prescription> mPrescription;

    private FirebaseStorage mFirebaseStorage;
    private ValueEventListener mDBListener;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pres_orders);

        //Bottom Navigation----------------------------------------------------------------
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.add_pres);

        //Bottom Navigation Listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.add_pres:
                        return true;
                    case R.id.lab_report:
                        //startActivity(new Intent(getApplicationContext(),AdminPanel.class));
                        return true;
                    case R.id.groc_Orders:
                        //startActivity(new Intent(getApplicationContext(),Grocery.class));
                        return true;
                    case R.id.add_Items:
                        //startActivity(new Intent(getApplicationContext(),Account.class));
                        return true;
                    case R.id.customer:
                        //startActivity(new Intent(getApplicationContext(),Account.class));
                        return true;
                }
                return false;
            }
        });
        //-----------------------------------


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPrescription = new ArrayList<>();

        mImageAdapter = new DImageAdapter(PresOrders.this,mPrescription);
        mImageAdapter.setOnItemClickListener(PresOrders.this);
        mRecyclerView.setAdapter(mImageAdapter);

        mFirebaseStorage = FirebaseStorage.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("POrders");
        mDBListener =  mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mPrescription.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Prescription prescription = dataSnapshot.getValue(Prescription.class);
                    prescription.setKey(dataSnapshot.getKey());
                    mPrescription.add(prescription);
                }

                mImageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(PresOrders.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Prescription selectItem = mPrescription.get(position);
        final String selectedKey = selectItem.getKey();
        StorageReference imgRef = mFirebaseStorage.getReferenceFromUrl(selectItem.getUrl());
        String url = selectItem.getUrl();
        Intent toImage = new Intent(getApplicationContext(),ViewImage.class);
        toImage.putExtra("image", url);
        startActivity(toImage);
        //Toast.makeText(this,"Normal Click :"+url,Toast.LENGTH_SHORT).show();
    }
    /*
    @Override
    public void onWhateverClick(int position) {
        Toast.makeText(this,"What ever :"+position,Toast.LENGTH_SHORT).show();
    }
    */
    @Override
    public void onDeleteClick(int position) {
        Prescription selectItem = mPrescription.get(position);
        final String selectedKey = selectItem.getKey();

        StorageReference imgRef = mFirebaseStorage.getReferenceFromUrl(selectItem.getUrl());
        imgRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseReference.child(selectedKey).removeValue();
                Toast.makeText(PresOrders.this, "Item Deleted..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mDBListener);
    }
}
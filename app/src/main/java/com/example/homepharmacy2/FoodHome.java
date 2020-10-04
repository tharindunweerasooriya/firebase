//IT19213972-----------I.K.S.S.Nawarathne
package com.example.homepharmacy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homepharmacy2.ui.Model.Food;
import com.example.homepharmacy2.ui.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodHome extends AppCompatActivity {

    private DatabaseReference ref;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    // Button cart;
    FloatingActionButton cart;


    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_home);

        key = "974532123V";

        if(key.isEmpty()){
            key = getIntent().getStringExtra("key");
        }


        ref = FirebaseDatabase.getInstance().getReference().child("food");
        recyclerView = findViewById(R.id.RV_01);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        cart = findViewById(R.id.floatingActionButton);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodHome.this,Cart.class);
                i.putExtra("key",key);

                startActivity(i);
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();

        if(key.isEmpty()){
            key = getIntent().getStringExtra("key");
        }
        FirebaseRecyclerOptions<Food> options  = new FirebaseRecyclerOptions.Builder<Food>().setQuery(ref , Food.class).build();

        final FirebaseRecyclerAdapter<Food , FoodViewHolder> adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull final Food model) {

                holder.txtFoodName.setText(model.getFoodName());
                holder.txtFoodPrice.setText(model.getFoodPrice());
                holder.txtFoodDescription.setText(model.getFoodDes());
                Picasso.get().load(model.getFoodImage()).into(holder.imageView);

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(FoodHome.this,FoodDetails.class);
                        i.putExtra("name",model.getFoodName());
                        i.putExtra("description",model.getFoodDes());
                        i.putExtra("price",model.getFoodPrice());
                        i.putExtra("image",model.getFoodImage());
                        i.putExtra("id",model.getFoodID());
                        i.putExtra("key",key);



                        startActivity(i);
                    }
                });


            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_food_items_layout , parent, false);
                FoodViewHolder holder = new FoodViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}

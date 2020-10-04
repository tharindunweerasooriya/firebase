//IT19213972-----------I.K.S.S.Nawarathne
package com.example.homepharmacy2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homepharmacy2.ui.Model.CartList;
import com.example.homepharmacy2.ui.ViewHolder.CartListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private Button NextProcessButton;
    private TextView txtTotalAmount , totalview;
    private DatabaseReference ref;
    private  int totalValue;
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        key = getIntent().getStringExtra("key");

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ref= FirebaseDatabase.getInstance().getReference().child("Cart List").child(key);

        NextProcessButton = (Button) findViewById(R.id.next_process_btn);
        NextProcessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this, ConfirmOrder.class);
                intent.putExtra("key" , key);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<CartList> options  = new FirebaseRecyclerOptions.Builder<CartList>().setQuery(ref , CartList.class).build();

        final FirebaseRecyclerAdapter<CartList , CartListViewHolder> adapter = new FirebaseRecyclerAdapter<CartList, CartListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartListViewHolder holder, int position, @NonNull final CartList model) {

                holder.food_name.setText("Name : " +model.getFoodName());
                holder.food_price.setText("Price : "+ model.getFoodPrice());
                holder.quantity.setText("Quantity : "+ model.getQuantity());
                totalValue = totalValue + Integer.parseInt(model.getQuantity()) * Integer.parseInt(model.getFoodPrice());
                totalview = findViewById(R.id.total_price);
                totalview.setText("Total : " + totalValue);

                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Cart.this, EditOrder.class);
                        intent.putExtra("food_id" , model.getFoodID());
                        intent.putExtra("quantity" , model.getQuantity());
                        intent.putExtra("key" , key);
                        startActivity(intent);

                    }
                });

                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
                        builder.setMessage("Are you sure you want to Remove this food item : ")
                                .setTitle("Confirm Delete")
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        ref.child(model.getFoodID()).removeValue();
                                        totalValue = totalValue - Integer.parseInt(model.getQuantity()) * Integer.parseInt(model.getFoodPrice());
                                        totalview.setText("Total : " + totalValue);
                                        Toast.makeText(getApplicationContext() , "Removed Successfully",Toast.LENGTH_LONG).show();


                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // CANCEL
                                    }
                                });
                        // Create the AlertDialog object and return it
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });





            }




            @NonNull
            @Override
            public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart_items , parent, false);
                CartListViewHolder holder = new CartListViewHolder(view);
                return holder;

            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

}
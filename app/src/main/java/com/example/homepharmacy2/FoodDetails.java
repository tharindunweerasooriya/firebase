//IT19213972-----------I.K.S.S.Nawarathne
package com.example.homepharmacy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.homepharmacy2.ui.Model.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class FoodDetails extends AppCompatActivity {

    private Button addToCartButton;
    private ImageView foodImage;
    private ElegantNumberButton numberButton;
    private TextView foodPrice, foodDes, foodName;
    private String foodID="";
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        foodID= getIntent().getStringExtra("id");
        key = getIntent().getStringExtra("key");

        addToCartButton = (Button) findViewById(R.id.fd_add_to_cart_button);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        foodImage = (ImageView) findViewById(R.id.food_image_details);
        foodName = (TextView) findViewById(R.id.food_name_details);
        foodDes = (TextView) findViewById(R.id.food_description_details);
        foodPrice = (TextView) findViewById(R.id.food_price_details);


        getFoodDetails(foodID);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addingToCartList();
            }
        });

    }

    private void addingToCartList() {

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("foodID",foodID);
        cartMap.put("foodName",foodName.getText().toString());
        cartMap.put("foodPrice",foodPrice.getText().toString());
        cartMap.put("quantity",numberButton.getNumber());

        cartListRef.child(key).child(foodID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(FoodDetails.this,"Added to CartList",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(FoodDetails.this,FoodHome.class);
                            intent.putExtra("key",key);
                            startActivity(intent);



                        }

                    }
                });



    }

    private void getFoodDetails(String foodID) {

        DatabaseReference foodref = FirebaseDatabase.getInstance().getReference().child("food");


        foodref.child(foodID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Food food = snapshot.getValue(Food.class);

                foodName.setText(food.getFoodName());
                foodDes.setText(food.getFoodDes());
                foodPrice.setText(food.getFoodPrice());
                Picasso.get().load(food.getFoodImage()).into(foodImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

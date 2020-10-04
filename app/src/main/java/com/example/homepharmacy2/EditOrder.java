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

public class EditOrder extends AppCompatActivity {

    private TextView name , price , description;
    private ElegantNumberButton numberButton;
    private Button save;
    private String foodid;
    private ImageView foodImage;
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        name=findViewById(R.id.edit_food_name_details);
        price=findViewById(R.id.edit_food_price_details);
        description=findViewById(R.id.edit_food_description_details);
        save = findViewById(R.id.edit_fd_add_to_cart_button);
        numberButton=findViewById(R.id.edit_number_btn);
        numberButton.setNumber(getIntent().getStringExtra("quantity"));
        foodImage = findViewById(R.id.edit_food_image_details);

        foodid = getIntent().getStringExtra("food_id");
        key = getIntent().getStringExtra("key");
        getFoodDetails(foodid);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
            }
        });

    }

    private void addingToCartList() {

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("foodID",foodid);
        cartMap.put("foodName",name.getText().toString());
        cartMap.put("foodPrice",price.getText().toString());
        cartMap.put("quantity",numberButton.getNumber());

        cartListRef.child(key).child(foodid)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(EditOrder.this,"Saved to CartList",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(EditOrder.this,Cart.class);
                            intent.putExtra("key" , key);

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

                name.setText(food.getFoodName());
                description.setText(food.getFoodDes());
                price.setText(food.getFoodPrice());
                Picasso.get().load(food.getFoodImage()).into(foodImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

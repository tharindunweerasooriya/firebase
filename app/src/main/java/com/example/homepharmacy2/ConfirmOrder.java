
package com.example.homepharmacy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homepharmacy2.ui.Model.CartList;
import com.example.homepharmacy2.ui.Model.Orders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ConfirmOrder extends AppCompatActivity {

    private EditText user_name , address , contact_number;
    private Button confirm;
    private DatabaseReference orderRef ,ref;
    private int totalValue =0 ;
    private String OrderID;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        user_name=findViewById(R.id.customer_name);
        address = findViewById(R.id.customer_address);
        contact_number = findViewById(R.id.customer_phone);
        confirm = findViewById(R.id.confirm_final_order_btn);
        orderRef = FirebaseDatabase.getInstance().getReference().child("orders");
        ref = FirebaseDatabase.getInstance().getReference().child("Cart List");

        key = getIntent().getStringExtra("key");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrder();
                Intent i = new Intent(ConfirmOrder.this,FoodHome.class);
                i.putExtra("key" , key);

                startActivity(i);
            }
        });




    }

    public void addOrder(){

        ref.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final CartList cart = snapshot.getValue(CartList.class);
                    totalValue=0;
                    Orders order = new Orders();

                    OrderID = Integer.toString(LoadRandom());
                    order.setAddress(address.getText().toString().trim());
                    order.setCustomerName(user_name.getText().toString().trim());
                    order.setMobileNumber(contact_number.getText().toString().trim());
                    order.setOrderId(OrderID);
                    order.setFoodName(cart.getFoodName());
                    totalValue = Integer.parseInt(cart.getQuantity()) * Integer.parseInt(cart.getFoodPrice());
                    order.setTotalPrice(Integer.toString(totalValue));

                    orderRef.child(OrderID).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Order Confirmed" , Toast.LENGTH_LONG).show();
                                ref.child(key).child(cart.getFoodID()).removeValue();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Oops something went wrong " , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



    }


    public int LoadRandom(){

        Random r = new Random();
        int low = 10000;
        int high = 100000;
        int result = r.nextInt(high-low) + low;

        return result;

    }
}

package com.example.homepharmacy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cLogin extends AppCompatActivity {
    private EditText username, password;
    private Button login, signUp;
    Switch active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_c_login);
        username = findViewById(R.id.phoneN);
        password = findViewById(R.id.Password);
        login = findViewById(R.id.buttonLogin);
        active = findViewById(R.id.active);
        signUp = findViewById(R.id.RegL);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cLogin.this, cCreateAccount.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginMethod();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (cPreferences.getDataLogin(this)) {
            if (cPreferences.getDataAs(this).equals("admin")) {
                startActivity(new Intent(cLogin.this, cAdminHome.class));
                finish();
            } else if (cPreferences.getDataAs(this).equals("user")) {
                startActivity(new Intent(cLogin.this, cAdminHome.class));
                finish();
            }
        }
    }

    public void LoginMethod() {
        if (!check1() || !check2() ) {
            //signUp.setEnabled(false);
            return;
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("login").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String input1 = username.getText().toString();
                String input2 = password.getText().toString();

                if (dataSnapshot.child(input1).exists()) {
                    if (dataSnapshot.child(input1).child("password").getValue(String.class).equals(input2)) {
                        if (active.isChecked()) {
                            if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("admin")) {
                                cPreferences.setDataLogin(cLogin.this, true);
                                cPreferences.setDataAs(cLogin.this, "admin");
                                startActivity(new Intent(cLogin.this, cAdminHome.class));

                            } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("user")) {
                                cPreferences.setDataLogin(cLogin.this, true);
                                cPreferences.setDataAs(cLogin.this, "user");
                                startActivity(new Intent(cLogin.this, cHome.class));
                            }
                        } else {
                            if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("admin")) {
                                cPreferences.setDataLogin(cLogin.this, false);
                                startActivity(new Intent(cLogin.this, cAdminHome.class));
                            } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("user")) {
                                cPreferences.setDataLogin(cLogin.this, false);
                                startActivity(new Intent(cLogin.this, cHome.class));
                            }
                        }
                    } else {
                        Toast.makeText(cLogin.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(cLogin.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private Boolean check1() {
        String val = username.getText().toString();
        if (val.isEmpty()) {
            Toast.makeText(this, "Phone Number cannot be Empty", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private Boolean check2() {
        String val = password.getText().toString();
        if (val.isEmpty()) {
            Toast.makeText(this, "Password cannot be Empty", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }
}
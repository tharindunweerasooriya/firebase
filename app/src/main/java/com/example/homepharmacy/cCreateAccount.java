package com.example.homepharmacy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cCreateAccount extends AppCompatActivity {
    private Button login, signUp;
    private EditText name1, phoneNo1, password1;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_create_account);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        login = findViewById(R.id.cktbutton);
        name1 = findViewById(R.id.FirstN);
        phoneNo1 = findViewById(R.id.phoneN);
        password1 = findViewById(R.id.Password);
        signUp = findViewById(R.id.buttonLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cCreateAccount.this, cLogin.class);
                startActivity(intent);
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpMethod(view);

            }
        });
    }

    public void signUpMethod(View view) {

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("login");
        if (!validName() || !validPhone() || !validPassword()) {
            //signUp.setEnabled(false);
            return;
        }
        //signUp.setEnabled(true);
        String name = name1.getText().toString().trim();
        String password = password1.getText().toString().trim();
        String phone = phoneNo1.getText().toString().trim();
        String as = "user";

        Intent intent = new Intent(getApplicationContext(),VerifyPhone.class);
        intent.putExtra("phoneNo" , phone);
        intent.putExtra("type",as);
        intent.putExtra("name",name);
        intent.putExtra("password",password);
        startActivity(intent);



       /* UserHelperClass user = new UserHelperClass(as, name, password, phone);
        reference.child(phone).setValue(user);
        Toast.makeText(this, "Registered Successfully...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), cHome.class);
        startActivity(intent); */

    }

    private Boolean validName() {
        String val = name1.getText().toString();
        if (val.isEmpty()) {
            Toast.makeText(this, "Name cannot be Empty", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private Boolean validPhone() {
        String val = phoneNo1.getText().toString();

        if (val.isEmpty()) {
            Toast.makeText(this, "PhoneNumber cannot be Empty", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private Boolean validPassword() {
        String val = password1.getText().toString();
        String pass = "^"+
                        "(?=.*[a-zA-Z])"+
                        "(?=.*[@#$%^&+=])"+
                        "(?=\\S+$)"+
                        ".{5,}"+
                        "$";

        // "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[$@$!%*#?&])[A-Za-z\\\\d$@$!%*#?&]{8,}$";
        if (val.isEmpty()) {
            Toast.makeText(this, "Password cannot be Empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!val.matches(pass)) {
            Toast.makeText(this, "Password is too week", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

}
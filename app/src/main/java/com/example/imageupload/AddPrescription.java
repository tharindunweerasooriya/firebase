package com.example.imageupload;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPrescription extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    BottomNavigationView bottomNavigationView;
    Button button_camera, button_submit;
    ImageView imageView;
    EditText txt_address, txt_number;
    Prescription prescription;
    DatabaseReference databaseReference;
    String currentPhotoPath;
    StorageReference storageReference;
    //-------
    File newFile;
    Uri contentUri;
    String myurl;
    //-------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);

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
                    case R.id.add_groc:
                        //startActivity(new Intent(getApplicationContext(),Grocery.class));
                        return true;
                }
                return false;
            }
        });
        //-----------------------------------

        imageView = findViewById(R.id.img_Prescrip);
        button_camera = findViewById(R.id.btn_takePhoto);
        txt_address = findViewById(R.id.edit_addterss);
        txt_number = findViewById(R.id.edit_Number);
        button_submit = findViewById(R.id.btn_submit);
        prescription = new Prescription();
        imageView.setImageBitmap(null);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (TextUtils.isEmpty(txt_address.getText().toString())) {
                        Toast.makeText(AddPrescription.this, "Provide the location..", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(txt_number.getText().toString())) {
                        Toast.makeText(AddPrescription.this, "Provide a phone number..", Toast.LENGTH_SHORT).show();
                    }else if(txt_number.getText().toString().length() != 10){
                        Toast.makeText(AddPrescription.this, "Invalid Phone number !", Toast.LENGTH_SHORT).show();
                    }else if(contentUri == null) {
                        Toast.makeText(AddPrescription.this, "Provide the prescription..", Toast.LENGTH_SHORT).show();
                    }else{

                        uploadImage(newFile.getName(),contentUri);
                        prescription.setID(1111);
                        prescription.setLocation(txt_address.getText().toString().trim());
                        prescription.setNumber(txt_number.getText().toString().trim());
                        clearFeilds();
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(AddPrescription.this, "Number format invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });


        storageReference = FirebaseStorage.getInstance().getReference();
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraPermission();
            }
        });

    }

    private void cameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_PERM_CODE);
        }else {
            dispatchTakePictureIntent();
        }
    }
    //---------------------------------

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else{
                Toast.makeText(this, "Camera permission is required.. ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK){
                newFile = new File(currentPhotoPath);
                //imageView.setImageURI(Uri.fromFile(newFile));
                Picasso.get().load(Uri.fromFile(newFile)).fit().into(imageView);
                Log.d("tag","AB >>> Url "+Uri.fromFile(newFile));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                contentUri = Uri.fromFile(newFile);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
            }
        }
    }

    private void uploadImage(String name, Uri contentUri) {
        final StorageReference image = storageReference.child("image/" +name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        myurl = uri.toString();
                        Log.d("tag","OnSuccess : "+ uri);
                        //-------
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("POrders");
                        String id = databaseReference.push().getKey();
                        prescription.setUrl(myurl);
                        Log.d("tag","Image Url :" + myurl);
                        //clearFeilds();
                        databaseReference.child("PID"+id).setValue(prescription);
                        Toast.makeText(AddPrescription.this, "Order Send..", Toast.LENGTH_SHORT).show();
                        //-------
                    }

                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddPrescription.this, "Failed...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private void clearFeilds(){
        txt_address.setText(null);
        txt_number.setText(null);
        imageView.setImageBitmap(null);

    }

    private void clearClass(){
        prescription = null;
    }

}
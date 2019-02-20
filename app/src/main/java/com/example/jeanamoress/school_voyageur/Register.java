package com.example.jeanamoress.school_voyageur;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class Register extends AppCompatActivity implements View.OnClickListener {




    //defining view objects
    private Button buttonregister;
    private EditText textEmailAddress;
    private EditText textPassword;
    private TextView textview;
    private Button btnCamera;
    private ImageView photo;
    private EditText imagename ;
    private Uri mImage = null;

    //private static final  int GALLERY_REQUEST =1;

    private static final int CAMERA_REQUEST_CODE=1;



    private ProgressDialog progressDialog;

    //didefining firebaseAuth object
    private FirebaseAuth firebaseAuth;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //initializing views
        buttonregister=(Button) findViewById(R.id.buttonregister);
        textEmailAddress = (EditText) findViewById(R.id.editTextEmail);
        textPassword= (EditText) findViewById(R.id.editTextPassword);
        imagename= (EditText) findViewById(R.id.imagename);

        textview = (TextView) findViewById(R.id.textview);
        btnCamera = (Button) findViewById(R.id.btnCamera);

        photo= (ImageView) findViewById(R.id.photo);


        buttonregister.setOnClickListener(this);
        textview.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        mStorageRef= FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) { startActivityForResult(intent, CAMERA_REQUEST_CODE); }
            }
        });



        //initializing firebaseAuth object
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //start profile activity


            finish();

            startActivity(new Intent(getApplicationContext(),Assurance.class));

        }
    }





    private void registerUser(){
        String email = textEmailAddress.getText().toString().trim();
        String password = textPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function execution
            return;

        }

        if(TextUtils.isEmpty(password)){
            // password is empty
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function execution
            return;


        }







        //if validation are ok, we will show first a progresssbar
        progressDialog.setMessage("Registrering...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //user is successfully registered
                            // we will start the profil activity here




                            finish();

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));





                            Toast.makeText(Register.this,"Registerd succesfully",Toast.LENGTH_SHORT).show();

                        } else  {

                            Toast.makeText(Register.this,"Could not Register, Please try again",Toast.LENGTH_SHORT).show();

                        }


                    }


                });

    }

    public void onClick(View view){
        if (view == buttonregister){
            registerUser();

        }



        if (view==textview){
            //will open login activity
            finish();

            startActivity( new Intent(this, MainActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CAMERA_REQUEST_CODE && resultCode == RESULT_OK){

            progressDialog.setMessage("Uploading image");
            progressDialog.show();

           // Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            //photo.setImageBitmap(bitmap);


            Uri uri = data.getData();
            StorageReference filepath = mStorageRef.child("Photos").child((uri.getLastPathSegment()));

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    progressDialog.dismiss();
                    Toast.makeText(Register.this, "Upload finished...", Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}

package com.example.jeanamoress.school_voyageur;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    //defining view objects
    private Button buttonsignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textSignup;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing views
        buttonsignin=(Button) findViewById(R.id.buttonsignin);
        editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        editTextPassword= (EditText) findViewById(R.id.editTextPassword);
        textSignup= (TextView) findViewById(R.id.textSignup);

        buttonsignin.setOnClickListener(this);
        textSignup.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() !=null ) {
            //profile activity

            finish();

            startActivity(new Intent(getApplicationContext(), Assurance.class));

        }


    }


    private void userlogin(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checking email and password

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
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //user is successfully registered
                            // we will start the profil activity here

                            finish();

                            startActivity(new Intent(getApplicationContext(), Assurance.class));


                        }


                    }
                });


    }

    @Override
    public void onClick(View view) {

        if (view ==buttonsignin) {

            userlogin();

        }

        if (view== textSignup){

            finish();

            startActivity(new Intent (this, Register.class));

        }

    }
}

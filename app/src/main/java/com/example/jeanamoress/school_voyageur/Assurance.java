package com.example.jeanamoress.school_voyageur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Assurance extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assurance);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() ==null ) {
            //profile activity

            finish();

            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText( user.getEmail());
        buttonlogout = (Button) findViewById(R.id.buttonlogout);

        buttonlogout.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        if (view==buttonlogout){
            firebaseAuth.signOut();
            finish();
            startActivity( new Intent(this, MainActivity.class));
        }

    }
}

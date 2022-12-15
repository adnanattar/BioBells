package com.tkiet.biobells;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Home extends AppCompatActivity {
    CardView dr1, dr2, dr3;
    ImageView loginuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dr1 = findViewById(R.id.dr1);
        dr2 = findViewById(R.id.dr2);
        dr3 = findViewById(R.id.dr3);
        loginuser = findViewById(R.id.Imuser);

        dr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(Home.this, DoctorDetails.class);
//                send.putExtra("cropN", onion.getText().toString());
                startActivity(send);
            }
        });

        dr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(Home.this, DoctorDetails.class);
//                send.putExtra("cropN", onion.getText().toString());
                startActivity(send);
            }
        });

        dr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(Home.this, DoctorDetails.class);
//                send.putExtra("cropN", onion.getText().toString());
                startActivity(send);
            }
        });

        loginuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(Home.this, Prescriptions.class);
//                send.putExtra("cropN", onion.getText().toString());
                startActivity(send);
            }
        });

    }
}
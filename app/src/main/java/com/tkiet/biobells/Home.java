// Package declaration
package com.tkiet.biobells;

// Import statements
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

// Class declaration
public class Home extends AppCompatActivity {
    // Variable declarations
    CardView dr1, dr2, dr3;
    ImageView loginuser;
    // Method called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout for activity
        setContentView(R.layout.activity_home);

        // Assign values to variables based on ids in layout file
        dr1 = findViewById(R.id.dr1);
        dr2 = findViewById(R.id.dr2);
        dr3 = findViewById(R.id.dr3);
        loginuser = findViewById(R.id.Imuser);

        // Set click listeners for each doctor card
        dr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent to open DoctorDetails activity
                Intent send = new Intent(Home.this, DoctorDetails.class);
                // Pass data to DoctorDetails activity (currently commented out)
// send.putExtra("cropN", onion.getText().toString());
                // Start DoctorDetails activity
                startActivity(send);
            }
        });
        dr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent to open DoctorDetails activity
                Intent send = new Intent(Home.this, DoctorDetails.class);
                // Pass data to DoctorDetails activity (currently commented out)
// send.putExtra("cropN", onion.getText().toString());
                // Start DoctorDetails activity
                startActivity(send);
            }
        });
        dr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent to open DoctorDetails activity
                Intent send = new Intent(Home.this, DoctorDetails.class);
                // Pass data to DoctorDetails activity (currently commented out)
// send.putExtra("cropN", onion.getText().toString());
                // Start DoctorDetails activity
                startActivity(send);
            }
        });
        // Set click listener for login user icon
        loginuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent to open Prescriptions activity
                Intent send = new Intent(Home.this, PatientProfile.class);
                // Pass data to Prescriptions activity (currently commented out)
// send.putExtra("cropN", onion.getText().toString());
                // Start Prescriptions activity
                startActivity(send);
            }
        });
    }
}

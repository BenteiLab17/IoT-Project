package com.example.csprojectthree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://test-c8d7d-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button logout = findViewById(R.id.logoutBtn);
        final TextView tur = findViewById(R.id.tur);
        final TextView ph = findViewById(R.id.ph);
        final TextView hardness = findViewById(R.id.hardness);
        final TextView name = findViewById(R.id.name);
        final TextView locationName = findViewById(R.id.locationName);
        final Button gotoMenuBtn = findViewById(R.id.gotoMenuBtn);


//        databaseReference.child("Parameters").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                final int turbidity = snapshot.child("Turbidity").getValue(int.class);
//                final int phtxt = snapshot.child("pH").getValue(int.class);
//                final int hardtxt = snapshot.child("Hardness").getValue(int.class);
//                tur.setText(Integer.toString(turbidity));
//                ph.setText(Integer.toString(phtxt));
//                hardness.setText(Integer.toString(hardtxt));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//
//            }
//        });

        // Collect Bundle from Login Page
        Intent in = getIntent();

        Bundle getCredentials = in.getExtras();

        String location = getCredentials.getString("location");
        String userName = getCredentials.getString("fullname");
        String email = getCredentials.getString("email");
        String root = getCredentials.getString("root");

        name.setText("Welcome " + userName.toUpperCase());
        locationName.setText("Water Parameters of " + location.toUpperCase() + " River");

        // Build bundle to pass back to Menu. Make same variable names for Bundles in every Activity
        Bundle backTo = new Bundle();
        backTo.putString("fullname", userName);
        backTo.putString("email", email);

        databaseReference.child(root).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                final float turbidity = snapshot.child("Parameters").child("Turbidity").getValue(Float.class);
                final float phtxt = snapshot.child("Parameters").child("pH").getValue(Float.class);
                final float hardtxt = snapshot.child("Parameters").child("Hardness").getValue(Float.class);
                tur.setText(Float.toString(turbidity) + " NTU");
                ph.setText(Float.toString(phtxt));
                hardness.setText(Float.toString(hardtxt) + " mg/L");

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        gotoMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Menu.class);
                intent.putExtras(backTo);
                startActivity(intent);
                finish();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
        });


    }
}
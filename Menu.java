package com.example.csprojectthree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Button myntduBtn = findViewById(R.id.muntdu);
        final Button simsangBtn = findViewById(R.id.simsang);
        final Button umkhenBtn = findViewById(R.id.umkhen);
        final Button umsyrpiBtn = findViewById(R.id.umsyrpi);
        final Button umkhrahBtn = findViewById(R.id.umkhrah);
        final TextView fullnameDisplay = findViewById(R.id.name);

        // Collect Bundle from Login Page
        Intent in = getIntent();

        Bundle getCredentials = in.getExtras();


        String email = getCredentials.getString("email");
        String fullname = getCredentials.getString("fullname");

        fullnameDisplay.setText("Welcome " + fullname.toUpperCase());

        // Creating Bundle
        Bundle bundle = new Bundle();

        bundle.putString("fullname", fullname);
        bundle.putString("email", email);


        // for Myndtu River
        myntduBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle.putString("location", "myntdu");
                bundle.putString("root", "Myntdu_River");

               if (email.equals("admin@gmail.com")){
                   Intent intentAdmin = new Intent(Menu.this, Admin.class);
                   intentAdmin.putExtras(bundle);
                   startActivity(intentAdmin);
                   finish();
               }
               else {
                   Intent intentGen = new Intent(Menu.this, MainActivity.class);
                   intentGen.putExtras(bundle);
                   startActivity(intentGen);
                   finish();
               }
            }
        });


        // For Simsang River
        simsangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle.putString("location", "simsang");
                bundle.putString("root", "SimSang_River");

                if (email.equals("admin@gmail.com")){
                    Intent intentAdmin = new Intent(Menu.this, Admin.class);
                    intentAdmin.putExtras(bundle);
                    startActivity(intentAdmin);
                    finish();
                }
                else {
                    Intent intentGen = new Intent(Menu.this, MainActivity.class);
                    intentGen.putExtras(bundle);
                    startActivity(intentGen);
                    finish();
                }
            }
        });


        // For Umkhren River
        umkhenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle.putString("location", "umkhen");
                bundle.putString("root", "Umkhen_River");

                if (email.equals("admin@gmail.com")){
                    Intent intentAdmin = new Intent(Menu.this, Admin.class);
                    intentAdmin.putExtras(bundle);
                    startActivity(intentAdmin);
                    finish();
                }
                else {
                    Intent intentGen = new Intent(Menu.this, MainActivity.class);
                    intentGen.putExtras(bundle);
                    startActivity(intentGen);
                    finish();
                }
            }
        });


        // For Umspyrpi River
        umsyrpiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle.putString("location", "umsyrpi");
                bundle.putString("root", "Umshyrpi_River");

                if (email.equals("admin@gmail.com")){
                    Intent intentAdmin = new Intent(Menu.this, Admin.class);
                    intentAdmin.putExtras(bundle);
                    startActivity(intentAdmin);
                    finish();
                }
                else {
                    Intent intentGen = new Intent(Menu.this, MainActivity.class);
                    intentGen.putExtras(bundle);
                    startActivity(intentGen);
                    finish();
                }
            }
        });


        // For Wahumkhrah River
        umkhrahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle.putString("location", "umkhrah");
                bundle.putString("root", "Wahumkhrah_River");

                if (email.equals("admin@gmail.com")){
                    Intent intentAdmin = new Intent(Menu.this, Admin.class);
                    intentAdmin.putExtras(bundle);
                    startActivity(intentAdmin);
                    finish();
                }
                else {
                    Intent intentGen = new Intent(Menu.this, MainActivity.class);
                    intentGen.putExtras(bundle);
                    startActivity(intentGen);
                    finish();
                }
            }
        });

    }
}
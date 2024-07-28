package com.example.csprojectthree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://test-c8d7d-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        final EditText email = findViewById(R.id.email);
        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.loginBtn);
        final TextView registerNowBtn = findViewById(R.id.registerNowBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final String emailTxt = email.getText().toString();
                final String phonetxt = phone.getText().toString();
                final String passwordTxt = password.getText().toString();

                if(phonetxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter phone number and password", Toast.LENGTH_SHORT ).show();
                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            // check if the email address existed
                            if(snapshot.hasChild(phonetxt)){
                                // get the password from the firebase and check with the entered password
                                final String getPassword = snapshot.child(phonetxt).child("password").getValue(String.class);
                                final String getEmail = snapshot.child(phonetxt).child("email").getValue(String.class);
                                final String getName = snapshot.child(phonetxt).child("fullname").getValue(String.class);

                                if(getPassword.equals(passwordTxt)){
                                    Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                    // Launch Activity Page
//                                    if (getEmail.equals("admin@gmail.com")) {
//                                        startActivity(new Intent(Login.this, Admin.class));
//                                        finish();
//                                    }
//                                    else {
//                                        startActivity(new Intent(Login.this, MainActivity.class));
//                                        finish();
//                                    }

                                    // Createing Bundel
                                    Bundle bundle = new Bundle();

                                    //put string in bundle
                                    bundle.putString("email", getEmail);
                                    bundle.putString("fullname", getName);

                                    Intent intent = new Intent(Login.this, Menu.class);

                                    // Pass bundle
                                    intent.putExtras(bundle);

                                    // Start Activity
                                    startActivity(intent);

                                    finish();
                                }
                                else {
                                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(Login.this, "Wrong Phone Number", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }
            }
        });

        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
}
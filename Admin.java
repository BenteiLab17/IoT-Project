package com.example.csprojectthree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csprojectthree.ml.RandomForest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Admin extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://test-c8d7d-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        final Button logout = findViewById(R.id.logoutBtn);
        final TextView tur = findViewById(R.id.tur);
        final TextView ph = findViewById(R.id.ph);
        final TextView conductivity = findViewById(R.id.conductivity);
        final TextView tds = findViewById(R.id.tds);
        final TextView chloramines = findViewById(R.id.chloramines);
        final TextView hardness = findViewById(R.id.hardness);
        final TextView organicCarbon = findViewById(R.id.organicCarbon);
        final TextView sulfates = findViewById(R.id.sulfates);
        final TextView trihalomethanes = findViewById(R.id.trihalomethanes);
        final TextView status = findViewById(R.id.status);
        final TextView personName = findViewById(R.id.personName);
        final TextView locationName = findViewById(R.id.locationName);
        final Button gotoMenuBtn = findViewById(R.id.gotoMenuBtn);


        // Collect Bundle from Login Page
        Intent in = getIntent();

        Bundle getCredentials = in.getExtras();

        String location = getCredentials.getString("location");
        String userName = getCredentials.getString("fullname");
        String email = getCredentials.getString("email");
        String root = getCredentials.getString("root");

        personName.setText("Welcome " + userName.toUpperCase());
        locationName.setText("Water Parameters of " + location.toUpperCase() + " River");

        // Build bundle to pass back to Menu. Make same variable names for Bundles in every Activity
        Bundle backTo = new Bundle();
        backTo.putString("fullname", userName);
        backTo.putString("email", email);


        // Fetching Data from firebase realtime database in real time
        databaseReference.child(root).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                // Fetching Data from the Firebase
                float turbiditytxt = snapshot.child("Parameters").child("Turbidity").getValue(Float.class);
                float phtxt = snapshot.child("Parameters").child("pH").getValue(Float.class);
                float Conducttxt = snapshot.child("Parameters").child("Conductivity").getValue(Float.class);
                float tdstxt = snapshot.child("Parameters").child("TDS").getValue(Float.class);
                float chloraminestxt = snapshot.child("Parameters").child("Chloramines").getValue(Float.class);
                float hardtxt = snapshot.child("Parameters").child("Hardness").getValue(Float.class);
                float organic_carbontxt = snapshot.child("Parameters").child("Organic Carbon").getValue(Float.class);
                float sulfatestxt = snapshot.child("Parameters").child("Sulfates").getValue(Float.class);
                float trihalomethanestxt = snapshot.child("Parameters").child("Trihalomethanes").getValue(Float.class);

                // Displaying Data
                tur.setText(Float.toString(turbiditytxt) + " NTU");
                ph.setText(Float.toString(phtxt) + "");
                conductivity.setText(Float.toString(Conducttxt) + " µS/cm");
                tds.setText(Float.toString(tdstxt) + " mg/L");
                chloramines.setText(Float.toString(chloraminestxt) + " mg/L");
                hardness.setText(Float.toString(hardtxt) + " mg/L");
                organicCarbon.setText(Float.toString(organic_carbontxt) + " mg/L");
                sulfates.setText(Float.toString(sulfatestxt) + " mg/L");
                trihalomethanes.setText(Float.toString(trihalomethanestxt) + " μg/L");

//                phtxt = "8";


                // Feeding Data to the Model

                ByteBuffer byteBuffer = ByteBuffer.allocate(9 * 4);
                byteBuffer.putFloat(phtxt);
                byteBuffer.putFloat(hardtxt);
                byteBuffer.putFloat(tdstxt);
                byteBuffer.putFloat(chloraminestxt);
                byteBuffer.putFloat(sulfatestxt);
                byteBuffer.putFloat(Conducttxt);
                byteBuffer.putFloat(organic_carbontxt);
                byteBuffer.putFloat(trihalomethanestxt);
                byteBuffer.putFloat(turbiditytxt);

                try {
                    RandomForest model = RandomForest.newInstance(Admin.this);

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 9}, DataType.FLOAT32);
                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    RandomForest.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    float[] data = outputFeature0.getFloatArray();

                    if(data[0] == 1.0f){
                        status.setBackgroundColor(Color.GREEN);
                        status.setText("Water Status: Safe");
                    }
                    else
                    {
                        status.setBackgroundColor(Color.RED);
                        status.setText("Water Status: Danger");
                    }


                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {
                    // TODO Handle the exception
                }


//                if(phtxt < 7) {
//                    status.setText("Water Status: Safe");
//                }
//                else{
//                    status.setBackgroundColor(Color.RED);
//                    status.setText("Water Status: Danger");
//                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        gotoMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, Menu.class);
                intent.putExtras(backTo);
                startActivity(intent);
                finish();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Admin.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Admin.this, Login.class));
                finish();
            }
        });
    }
}
package com.example.medwheels_pat1;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class home extends AppCompatActivity {
    Button uplodeButton;
    public static final String SHARED_PREFS = "sharedPrefs_pat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "PowerButtonService created");
        Intent serviceIntent = new Intent(this, PowerButtonService.class);
        startService(serviceIntent);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        uplodeButton = findViewById(R.id.uplodeFirebase_btn);
        uplodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uplodeToDatabase();

            }
        });

    }

    public void uplodeToDatabase(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://medwheels-4b07d-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference reference = database.getReference("patient");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String mail = sharedPreferences.getString("mail", "");
        String name = sharedPreferences.getString("name", "");
        String pass = sharedPreferences.getString("pass", "");
        String addNotes = sharedPreferences.getString("addNotes", "");
        String add = sharedPreferences.getString("add", "");
        String Phone = sharedPreferences.getString("Phone", "");
        String emName = sharedPreferences.getString("emName", "");
        String emRela = sharedPreferences.getString("emRela", "");
        String med = sharedPreferences.getString("med", "");
        String all = sharedPreferences.getString("all", "");
        String Dob = sharedPreferences.getString("Dob", "");
        String gender_d = sharedPreferences.getString("gender_d", "");
        String blood_d = sharedPreferences.getString("blood_d", "");


        HelperClass helperClass = new HelperClass(mail,pass,name,addNotes,add,Phone,emName,emRela,med,all,Dob,gender_d,blood_d);
        reference.child(mail.replace(".",",")).setValue(helperClass)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Data uploaded successfully: " + helperClass.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error uploading data: " + e.getMessage());
                    }
                });

        Toast.makeText(home.this,"success",Toast.LENGTH_SHORT).show();
    }

}
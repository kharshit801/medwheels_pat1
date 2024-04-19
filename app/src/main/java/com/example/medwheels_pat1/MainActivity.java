package com.example.medwheels_pat1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String BloodGroup,Gender,blood_d,gender_d;
    String [] blood_group_array = {"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    String[] gender_array ={"Male","Female","Non-binary","Others"};
    EditText sname, email, password, additionalNotes,phone, emergencyRelation,emergencyName,medHistory,allergies,dob,address;
    Button finishButton;
AutoCompleteTextView bloodgroupTextView,genderTextView;
ArrayAdapter<String> bloodgroupItems,genderItems;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        sname=findViewById(R.id.name_pat);
        email=findViewById(R.id.email_pat);
        password=findViewById(R.id.pass_pat);
        address=findViewById(R.id.cur_add);
        additionalNotes=findViewById(R.id.additional_notes);
        phone=findViewById(R.id.phone_rela);
        emergencyName=findViewById(R.id.emergency_contact_name);
        emergencyRelation=findViewById(R.id.relation_pat);
        medHistory=findViewById(R.id.medical_conditions);
        allergies=findViewById(R.id.allergies);
        dob=findViewById(R.id.dob);

        bloodgroupTextView = findViewById(R.id.blood_group_pat);

        bloodgroupItems = new ArrayAdapter<String>(this, R.layout.list_item, blood_group_array);
        bloodgroupTextView.setAdapter(bloodgroupItems);

        bloodgroupTextView.setAdapter(bloodgroupItems);

       bloodgroupTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
               blood_d = adapterView.getItemAtPosition(i).toString().trim();
           }
       });



        genderTextView = findViewById(R.id.gender_pat);

        genderItems = new ArrayAdapter<String>(this, R.layout.list_item, gender_array);
        genderTextView.setAdapter(genderItems);

        genderTextView.setAdapter(genderItems);

        genderTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                gender_d = adapterView.getItemAtPosition(i).toString().trim();
            }
        });
        finishButton = findViewById(R.id.finishBtn);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                String name=sname.getText().toString();
                String pass=password.getText().toString();
                String addNotes=additionalNotes.getText().toString();
                String add=address.getText().toString();
                String Phone=phone.getText().toString();
                String emName=emergencyName.getText().toString();
                String emRela=emergencyRelation.getText().toString();
                String med=medHistory.getText().toString();
                String all=allergies.getText().toString();
                String Dob=dob.getText().toString();
                HelperClass helperClass = new HelperClass(mail,pass,name,addNotes,add,Phone,emName,emRela,med,all,Dob,gender_d,blood_d);
                Intent intent = new Intent(MainActivity.this,home.class);

                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
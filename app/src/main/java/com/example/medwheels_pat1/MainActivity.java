package com.example.medwheels_pat1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    String BloodGroup,Gender,blood_d,gender_d;
    String [] blood_group_array = {"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    String[] gender_array ={"Male","Female","Non-binary","Others"};
    EditText sname, email, password, additionalNotes,phone, emergencyRelation,emergencyName,medHistory,allergies,dob,address;
    Button finishButton, browseButton;
    ImageView uplodedImage;

    Uri uri;
    String permission[]={"android.permission.WRITE_EXTERNAL_STORAG"};
    String per[]={"android.permission.READ_MEDIA_IMAGES"};
    private static final int CAMERA_PERMISSION_CODE = 200;
    private static final String PERMISSION_USE_CAMERA = Manifest.permission.CAMERA;
    private static final int PERMISSION_REQUIRED_CODE = 100;
    public static final String SHARED_PREFS="sharedPrefs_pat";


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
        browseButton=findViewById(R.id.browse_btn);
        uplodedImage = findViewById(R.id.uploaded_img);

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select Image Source")
                        .setItems(new CharSequence[]{"Gallery", "Camera"}, (dialog, which) -> {
                            switch (which) {
                                case 0:
                                    Intent photoPicker = new Intent(Intent.ACTION_PICK);
                                    photoPicker.setType("image/*");
                                    activityResultLauncher.launch(photoPicker);
                                    break;
                                case 1:
                                    if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUIRED_CODE);
                                    } else {
                                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        activityResultLauncher.launch(cameraIntent);
                                    }
                                    break;
                            }
                        });
                builder.create().show();


            }
        });


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

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("mail", mail);
                editor.putString("name", name);
                editor.putString("pass", pass);
                editor.putString("addNotes", addNotes);
                editor.putString("add", add);
                editor.putString("Phone", Phone);
                editor.putString("emName", emName);
                editor.putString("emRela", emRela);
                editor.putString("med", med);
                editor.putString("all", all);
                editor.putString("Dob", Dob);
                editor.putString("gender_d", gender_d);
                editor.putString("blood_d", blood_d);
                editor.apply();

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

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            uri = data.getData();
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(uri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                Bitmap resizedBitmap = getResizedBitmap(bitmap, 300, 300);
                                uplodedImage.setImageBitmap(resizedBitmap);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Bundle extras = data.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            uplodedImage.setImageBitmap(imageBitmap);
                            uri = getImageUri(imageBitmap);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                    }

                }
            }
    );

    public Bitmap getResizedBitmap(Bitmap image, int maxWidth, int maxHeight) {
        int width = image.getWidth();
        int height = image.getHeight();

        float ratioBitmap = (float) width / (float) height;
        float ratioMax = (float) maxWidth / (float) maxHeight;

        int finalWidth = maxWidth;
        int finalHeight = maxHeight;
        if (ratioMax > ratioBitmap) {
            finalWidth = (int) ((float)maxHeight * ratioBitmap);
        } else {
            finalHeight = (int) ((float)maxWidth / ratioBitmap);
        }

        return Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
    }

    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

}
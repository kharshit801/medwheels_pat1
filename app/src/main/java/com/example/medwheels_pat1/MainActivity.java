package com.example.medwheels_pat1;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
final static int REQUEST_CODE = 1232;
    String BloodGroup,Gender,blood_d,gender_d;
    String [] blood_group_array = {"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    String[] gender_array ={"Male","Female","Non-binary","Others"};
    EditText sname, email, password, additionalNotes,phone, emergencyRelation,emergencyName,medHistory,allergies,dob,address;
//    double longitude,latitude;
    Button finishButton, browseButton;
    Button btnCreatePdf;
    ImageView uplodedImage;
    String mail,pass,name,addNotes,add,Phone,emName,emRela,med,all,Dob;
    String imageURL;
    String sos = "0";
    double latitude = 25.431474; // IIIT ALLAHABAD
    double longitude = 81.770500;

    Uri uri;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Circle locationCircle;
    String permission[]={"android.permission.WRITE_EXTERNAL_STORAG"};
    String per[]={"android.permission.READ_MEDIA_IMAGES"};
    private static final int CAMERA_PERMISSION_CODE = 200;
    private static final String PERMISSION_USE_CAMERA = Manifest.permission.CAMERA;
    private static final int PERMISSION_REQUIRED_CODE = 100;
    public static final String SHARED_PREFS="sharedPrefs_pat";
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 101;
    private boolean shouldShowRequestPermissionRationale = false;


AutoCompleteTextView bloodgroupTextView,genderTextView;
ArrayAdapter<String> bloodgroupItems,genderItems;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }



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

//       btnCreatePdf = findViewById(R.id.finishBtn);
//
//        btnCreatePdf.setOnClickListener(v -> {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_GRANTED) {
//                createPDF();
//            } else {
//                requestWriteExternalStoragePermission();
//            }
//        });
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
                mail=email.getText().toString();
                name=sname.getText().toString();
                pass=password.getText().toString();
                addNotes=additionalNotes.getText().toString();
                add=address.getText().toString();
                Phone=phone.getText().toString();
                emName=emergencyName.getText().toString();
                emRela=emergencyRelation.getText().toString();
                med=medHistory.getText().toString();
                all=allergies.getText().toString();
                Dob=dob.getText().toString();




                uplodeToDatabase();

                Intent intent = new Intent(MainActivity.this,home.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
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

    public void uplodeToDatabase(){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Patient Images")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!((Task<?>) uriTask).isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();

                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://medwheels-4b07d-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference reference = database.getReference("patient");

                HelperClass helperClass = new HelperClass(mail,pass,name,addNotes,add,Phone,emName,emRela,med,all,Dob,gender_d,blood_d,imageURL,sos,longitude,latitude);
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

                Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();

            }
        });



    }
    private void requestWriteExternalStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission is required to create PDF", Toast.LENGTH_LONG).show();
            shouldShowRequestPermissionRationale = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                createPDF();
//            } else {
//                if (shouldShowRequestPermissionRationale) {
//                    Toast.makeText(this, "Write External Storage permission is required to create PDF", Toast.LENGTH_LONG).show();
//                } else {
//                    openAppSettings();
//                }
//            }
//        }
//    }
    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
    private void createPDF() {
        PdfDocument myPDFDocument = new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(250, 400, 1).create();

        PdfDocument.Page myPage1 = myPDFDocument.startPage(myPageInfo1);
        Canvas canvas = myPage1.getCanvas();

        canvas.drawText("Welcome", 40, 50, myPaint);
        myPDFDocument.finishPage(myPage1);

        File file = new File(Environment.getExternalStorageDirectory(), "/FirstPDF.pdf");

        try {
            myPDFDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        myPDFDocument.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        enableMyLocation();
        getDeviceLocation();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            getDeviceLocation();
        }
    }
    private void getDeviceLocation() {
        // Set the desired latitude and longitude

        LatLng desiredLocation = new LatLng(25.431474, 81.770500);
        googleMap.addMarker(new MarkerOptions().position(desiredLocation).title("Desired Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(desiredLocation, 15f));

        // Draw a circle around the desired location
        if (desiredLocation != null) {
            if (locationCircle != null) {
                locationCircle.remove();
            }
            CircleOptions circleOptions = new CircleOptions()
                    .center(desiredLocation)
                    .radius(500)
                    .fillColor(0xE2856E)
                    .strokeColor(0x1e1e1e)
                    .strokeWidth(5);
            locationCircle = googleMap.addCircle(circleOptions);

            // Calculate the distance between your current location and the desired location
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
//                            calculateDistance(currentLocation, desiredLocation);
                        }
                    });
        }
    }



}
package com.example.medwheels_pat1;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ButtonNotifications extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_button_notifications);
        button = findViewById(R.id.btnNotifications);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(ButtonNotifications.this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(ButtonNotifications.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
makenotifications();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
        public void makenotifications(){
        String chanelID = "CHANEL_ID_NOTIFICATION";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),chanelID);
builder.setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Notification Title")
        .setContentText("Something")
        .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            Intent intent = new Intent(getApplicationContext(),NotificationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("data","Some value to be passed here");

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,
                    PendingIntent.FLAG_MUTABLE);
            builder.setContentIntent(pendingIntent);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
                NotificationChannel notificationChannel =
                        null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel = notificationManager.getNotificationChannel(chanelID);
                }
                if(notificationChannel == null){
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notificationChannel = new NotificationChannel(chanelID,"Some description",importance);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notificationChannel.setLightColor(Color.GREEN);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notificationChannel.enableVibration(true);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notificationManager.createNotificationChannel(notificationChannel);
                    }
                }
            }
           notificationManager.notify(0,builder.build());

    }
}
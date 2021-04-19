package com.example.aaapone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private Button mBtn_startLoc,mBtn_stopLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        mBtn_startLoc=findViewById(R.id.btn_star_locating);
        mBtn_stopLoc=findViewById(R.id.btn_stop_locating);
        mBtn_startLoc.setOnClickListener(this);
        mBtn_stopLoc.setOnClickListener(this);


        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity4.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_LOCATION_PERMISSION);
        }

        Button btn_showMap = findViewById(R.id.show_map);
        btn_showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity4.this,ShowMapActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==mBtn_startLoc){
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity4.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_LOCATION_PERMISSION);
            }else{
                startLocationService();
            }
        }
        if(v==mBtn_stopLoc){
            stopLocationService();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startLocationService();
            }else{
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isLocationServiceRunning(){
        ActivityManager activityManager=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager!=null){
            List<ActivityManager.RunningServiceInfo> servicesList = activityManager.getRunningServices(Integer.MAX_VALUE);
            for(ActivityManager.RunningServiceInfo serviceInfo : servicesList){
                if(LocationService.class.getName().equals(serviceInfo.service.getClassName())){
                    if(serviceInfo.foreground){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void startLocationService(){
        if(!isLocationServiceRunning()){
            Intent intent=new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(CustomConstants.START_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Loc service started", Toast.LENGTH_SHORT).show();
        }
    }

    public void stopLocationService(){
        if(isLocationServiceRunning()){
            Intent intent=new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(CustomConstants.STOP_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Loc service stop", Toast.LENGTH_SHORT).show();
        }
    }

}
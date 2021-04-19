package com.example.aaapone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity5 extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // Normal,Hybrid,Satellite,Terrain,None
        mMap.setTrafficEnabled(true);
        LatLng customLatLng = new LatLng(22.464908, 88.303245);
        mMap.addMarker(new MarkerOptions()
                .position(customLatLng)
                .title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(customLatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
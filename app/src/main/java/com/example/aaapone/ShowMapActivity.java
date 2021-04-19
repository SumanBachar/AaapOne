package com.example.aaapone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ShowMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mapCustomer;
    private double mDoub_latCustomer,mDoub_longCustomer;

    private class SetGetLatLong{
        String latLoc,longLoc;

        public String getLatLoc() {
            return latLoc;
        }

        public void setLatLoc(String latLoc) {
            this.latLoc = latLoc;
        }

        public String getLongLoc() {
            return longLoc;
        }

        public void setLongLoc(String longLoc) {
            this.longLoc = longLoc;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("LocService").child("ID1");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SetGetLatLong setGetLatLong=new SetGetLatLong();
                setGetLatLong.setLatLoc((String) snapshot.child("latLoc").getValue());
                setGetLatLong.setLongLoc((String) snapshot.child("longLoc").getValue());
                mDoub_latCustomer=Double.parseDouble((String) snapshot.child("latLoc").getValue());
                mDoub_longCustomer=Double.parseDouble((String) snapshot.child("longLoc").getValue());
                showMap();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //showMap();
    }

    private void showMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.show_map_frag);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapCustomer = googleMap;
        mapCustomer.clear();
        LatLng marker = new LatLng(mDoub_latCustomer, mDoub_longCustomer);
        mapCustomer.addMarker(new MarkerOptions()
                .position(marker)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_flag))
                .title("Marker"));
        mapCustomer.moveCamera(CameraUpdateFactory.newLatLng(marker));
        mapCustomer.animateCamera(CameraUpdateFactory.zoomTo(18));
        mapCustomer.setTrafficEnabled(true);

/*        mapCustomer = googleMap;

        LatLng mark = new LatLng(mDoub_latCustomer, mDoub_longCustomer);
        mapCustomer.addMarker(new MarkerOptions()
                .position(mark)
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.ic_flag))
                .title("Marker"));
        mapCustomer.moveCamera(CameraUpdateFactory.newLatLng(mark));
        mapCustomer.animateCamera(CameraUpdateFactory.zoomTo(18));*/

    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResID) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResID);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
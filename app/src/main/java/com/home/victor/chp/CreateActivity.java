package com.home.victor.chp;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateActivity extends Activity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    public Double latintude = 0.0;
    public Double longitude = 0.0;
    public GoogleMap mCreateMap;
    public LatLng loc;
    public String name;
    public static LocationSource.OnLocationChangedListener mListener2;
    public GoogleMap.OnMyLocationChangeListener myLocationChangeListener2;
    private CreateActivity mLocationSource2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_layout);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.create_map);
        mapFragment.getMapAsync(this);

        latintude = getIntent().getDoubleExtra("coordLat", latintude);
        longitude = getIntent().getDoubleExtra("coordLon", longitude);
        loc = new LatLng(latintude, longitude);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mCreateMap = googleMap;
        mCreateMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
        mCreateMap.addMarker(new MarkerOptions().position(loc));
        mCreateMap.setOnMyLocationChangeListener(myLocationChangeListener2);
        mCreateMap.setOnMapLongClickListener(mLocationSource2);
    }

    public void onMapLongClick(LatLng point) {
        Location location = new Location("LongPressLocationProvider");
        location.setLatitude(point.latitude);
        location.setLongitude(point.longitude);
        location.setAccuracy(100);
        mCreateMap.addMarker(new MarkerOptions()
                .position(point)
                .title(name)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        Log.d("Click","Tack");
    }
};


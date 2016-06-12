package com.home.victor.chp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements LocationSource, OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    public static   GoogleMap mMap;
    public static LocationSource.OnLocationChangedListener mListener;
    public OnMyLocationChangeListener myLocationChangeListener;
    private LatLng loc = null;
    private boolean mPaused;
    private MapsActivity mLocationSource;
    private boolean checkNormalPosMap = false;
    public MapsActivity() {
    }


    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocationSource = new MapsActivity();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(mLocationSource);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        myLocationChangeListener = new OnMyLocationChangeListener() {


            public void onMyLocationChange(Location location) {
                loc = new LatLng(location.getLatitude(), location.getLongitude());

                if(mMap != null && checkNormalPosMap == false){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                    checkNormalPosMap = true;
                }

            }

        };

        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);
    }

//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        //        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));


         public void onMapLongClick(LatLng point) {
            Location location = new Location("LongPressLocationProvider");
            location.setLatitude(point.latitude);
            location.setLongitude(point.longitude);
            location.setAccuracy(100);
           //  myLocationChangeListener.onMyLocationChange(location);
            mMap.addMarker(new MarkerOptions().position(point));

            Log.d("Clic","Tik");
    }



         protected void onPause() {
        super.onPause();
//        mLocationSource.onPause();
         }

        public void deactivate() {
            mListener = null;
        }

        protected void onResume() {
        super.onResume();
//        mLocationSource.onResume();
        }
}

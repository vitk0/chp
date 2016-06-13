package com.home.victor.chp;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.client.HttpClient;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;*/
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

public class MapsActivity extends Activity implements  LocationSource, OnMapReadyCallback, GoogleMap.OnMapLongClickListener ,  GoogleMap.OnInfoWindowClickListener {


    public static   GoogleMap mMap;
    public static LocationSource.OnLocationChangedListener mListener;
    public OnMyLocationChangeListener myLocationChangeListener;
    private LatLng loc = null;
    private boolean mPaused;
    private MapsActivity mLocationSource;
    private boolean checkNormalPosMap = false;
    public static DBHelper dbHelper;
    public static Intent intent2;

    public int id;
    public String name = "";
    public String coment = "";
    public int privacy;
    public String type = "";

    public int year = 0;
    public int month = 0 ;
    public int day = 0;
    public int hour  = 0;
    public int minute = 0;

    public int year2 = 0;
    public int month2 = 0 ;
    public int day2 = 0;
    public int hour2 = 0;
    public int minute2 = 0;
    double coordLat= 0.0;
    double coordLon= 0.0;
    public Double latitude2 = 0.0;
    public Double longitude2 = 0.0;
    public LatLng point2= new LatLng(latitude2,longitude2);
    public Marker[] arrayMarkers;
    public Cursor cursor;
    private Boolean checkCreateEdit = false;
    private Boolean nonLogin;

    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocationSource = this;
        nonLogin = getIntent().getBooleanExtra("nonLogin",false);
        checkCreateEdit = getIntent().getBooleanExtra("checkCreateEdit",checkCreateEdit);
        id= getIntent().getIntExtra("id",0);
        name = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
        privacy =getIntent().getIntExtra("privacy",0);
        coment = getIntent().getStringExtra("coment");
        year = getIntent().getIntExtra("year", year);
        month = getIntent().getIntExtra("month",  month);
        day = getIntent().getIntExtra("day", day);
        hour = getIntent().getIntExtra("hour", hour);
        minute = getIntent().getIntExtra("minute", minute);

        year2 = getIntent().getIntExtra("year2", year2);
        month2 = getIntent().getIntExtra("month2",  month2);
        day2 = getIntent().getIntExtra("day2", day2);
        hour2 = getIntent().getIntExtra("hour2", hour2);
        minute2 = getIntent().getIntExtra("minute2", minute2);

        coordLat = getIntent().getDoubleExtra("coord1Lat",  coordLat);
        coordLon  = getIntent().getDoubleExtra("coord1Lon",  coordLon);
        latitude2 = getIntent().getDoubleExtra("coord2Lat", point2.latitude);
        longitude2 = getIntent().getDoubleExtra("coord2Lon", point2.longitude);


        dbHelper = new DBHelper(this);
        if(name != null) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            if(!checkCreateEdit) {
                db.execSQL("insert into events " +
                        "(name ," +
                        "type ," +
                        "year ," +
                        "month ," +
                        "day ," +
                        "hour ," +
                        "minute ," +
                        "year2 ," +
                        "month2 ," +
                        "day2 ," +
                        "hour2 ," +
                        "minute2 ," +
                        "coordLat ," +
                        "coordLon ," +
                        "comment ," +
                        "privacy," +
                        "coordLat2 ," +
                        "coordLon2 " + ") " +
                        "values " +
                        "( '" + name + "',"
                        + "'" + type + "',"
                        + year + ","
                        + month + ","
                        + day + ","
                        + hour + ","
                        + minute + ","
                        + year2 + ","
                        + month2 + ","
                        + day2 + ","
                        + hour2 + ","
                        + minute2 + ","
                        + coordLat + ","
                        + coordLon + ","
                        + "'" + coment + "',"
                        + privacy + ","
                        + latitude2 + ","
                        + longitude2 + ");");
            }
            else
            {
                db.execSQL("update events set " +
                        "name = " + "'" + name + "'," +
                        "type = " + "'" + type + "'," +
                        "year = " + year + "," +
                        "month = " +  month + "," +
                        "day = " + day + ","+
                        "hour = " + hour + ","+
                        "minute = " + minute + ","+
                        "year2 = " + year2 + ","+
                        "month2 = " + month2 + ","+
                        "day2 = " + day2 + ","+
                        "hour2 = " + hour2 + ","+
                        "minute2 = " + minute2 + ","+
                        "coordLat = " + coordLat + ","+
                        "coordLon = " + coordLon + ","+
                        "comment = " + "'" + coment + "',"+
                        "privacy = " + privacy + ","+
                        "coordLat2 = " + latitude2 + ","+
                        "coordLon2 = " + longitude2 +
                        " where id = " + id + ";" );
            }
            dbHelper.close();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initListeners(mMap);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        myLocationChangeListener = new OnMyLocationChangeListener() {
            public void onMyLocationChange(Location location) {
                loc = new LatLng(location.getLatitude(), location.getLongitude());
                if(mMap != null && !checkNormalPosMap){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                    checkNormalPosMap = true;
                }
            }
        };

        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);



        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query("events",new String[] {"*"},null,null,null,null,null);
        int i = 0;

        arrayMarkers = new Marker[cursor.getCount()];
        if(nonLogin) {
            while (cursor.moveToNext()) {

                LatLng point = new LatLng(cursor.getDouble(13), cursor.getDouble(14));
                if (cursor.getInt(16) == 0)
                    arrayMarkers[i] = mMap.addMarker(new MarkerOptions().position(point).title(cursor.getString(1)));

                i += 1;

            }
        }
        else
        {
            while (cursor.moveToNext()) {

                LatLng point = new LatLng(cursor.getDouble(13), cursor.getDouble(14));
                    arrayMarkers[i] = mMap.addMarker(new MarkerOptions().position(point).title(cursor.getString(1)));
                i += 1;
                }
        }
    }

         public void onMapLongClick(LatLng point) {
             if(!nonLogin) {
                 Location location = new Location("LongPressLocationProvider");
                 location.setLatitude(point.latitude);
                 location.setLongitude(point.longitude);
                 location.setAccuracy(100);

                 mMap.addMarker(new MarkerOptions().position(point));

                 Intent intent2 = new Intent(MapsActivity.this, CreateActivity.class);
                 intent2.putExtra("checkCreateEdit",false);
                 intent2.putExtra("coordLat", point.latitude);
                 intent2.putExtra("coordLon", point.longitude);
                 startActivity(intent2);
                 }
             }




         protected void onPause() {
        super.onPause();
         }

        public void deactivate() {
            mListener = null;
        }

        protected void onResume() {
        super.onResume();
        }


    private void initListeners( GoogleMap map ) {
      //  map.setOnMarkerClickListener(this);
        map.setOnMapLongClickListener(this);
        map.setOnInfoWindowClickListener( this );
      //  map.setOnMapClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        int i= 0;
        for(i = 0 ;i < arrayMarkers.length;i++){
            if (arrayMarkers[i].equals(marker))
            break;
        }
        cursor.moveToPosition(i);
        Intent intent = new Intent(MapsActivity.this, ReadActivity.class);
        intent.putExtra("nonLoginE",nonLogin);
        intent.putExtra("idE",id = cursor.getInt(0));
        intent.putExtra("nameE", name = cursor.getString(1));
        intent.putExtra("typeE", type = cursor.getString(2));
        intent.putExtra("privacyE", privacy = cursor.getInt(16));
        intent.putExtra("comentE", coment = cursor.getString(15));
        intent.putExtra("yearE", year = cursor.getInt(3));
        intent.putExtra("monthE", month = cursor.getInt(4));
        intent.putExtra("dayE", day = cursor.getInt(5));
        intent.putExtra("hourE", hour = cursor.getInt(6));
        intent.putExtra("minuteE", minute = cursor.getInt(7) );

        intent.putExtra("year2E", year2 = cursor.getInt(8));
        intent.putExtra("month2E", month2 = cursor.getInt(9));
        intent.putExtra("day2E", day2 = cursor.getInt(10));
        intent.putExtra("hour2E", hour2 = cursor.getInt(11));
        intent.putExtra("minute2E", minute2 = cursor.getInt(12));

        intent.putExtra("coord1LatE", coordLat = cursor.getDouble(13));
        intent.putExtra("coord1LonE", coordLon = cursor.getDouble(14));

        intent.putExtra("coord2LatE", latitude2 = cursor.getDouble(17));
        intent.putExtra("coord2LonE", longitude2 = cursor.getDouble(18));
        startActivity(intent);


    }
}

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {

        super(context, "DB4", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("DB", "--- onCreate database ---");

        db.execSQL("create table if not EXISTS events ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "type text,"
                + "year integer ,"
                + "month integer ,"
                + "day integer,"
                + "hour integer ,"
                + "minute integer ,"
                + "year2 integer ,"
                + "month2 integer ,"
                + "day2 integer ,"
                + "hour2 integer ,"
                + "minute2 integer ,"
                + "coordLat real,"
                + "coordLon real,"
                + "comment text,"
                + "privacy integer,"
                + "coordLat2 real,"
                + "coordLon2 real"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
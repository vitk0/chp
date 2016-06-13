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

public class MapsActivity extends Activity implements  LocationSource, OnMapReadyCallback, GoogleMap.OnMapLongClickListener , GoogleMap.OnMarkerClickListener {

//    String str="new";
//    static ResultSet rs;
//    static PreparedStatement st;
//    static Connection con;

    public static   GoogleMap mMap;
    public static LocationSource.OnLocationChangedListener mListener;
    public OnMyLocationChangeListener myLocationChangeListener;
    private LatLng loc = null;
    private boolean mPaused;
    private MapsActivity mLocationSource;
    private boolean checkNormalPosMap = false;
    public static DBHelper dbHelper;
    public static Intent intent2;

    public String name = "";
    public String coment = "";
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
//    public static JSONObject  getJSONfromURL(String url) {
//        InputStream is = null;
//        String result = "";
//        JSONObject jArray = null;
//
//        try {
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpPost httppost = new HttpPost(url);
//            HttpResponse response = httpclient.execute(httppost);
//            HttpEntity entity = response.getEntity();
//            is = entity.getContent();
//
//        } catch (Exception e) {
//            Log.e("log_tag", "Error in http connection " + e.toString());
//        }
//
//        // Convert response to string
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    is, "UTF-8"), 8);
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//            is.close();
//            result = sb.toString();
//        } catch (Exception e) {
//            Log.e("log_tag", "Error converting result " + e.toString());
//        }
//
//        try {
//            jArray = new JSONObject(result);
//        } catch (JSONException e) {
//            Log.e("log_tag", "Error parsing data " + e.toString());
//        }
//
//        return jArray;
//    }


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
        mLocationSource = this;//new MapsActivity();

        name = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
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
                    "comment) " +
                    "values " +
                    "( '" + name +"',"
                    + "'" + type +"',"
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
                    + coordLat  + ","
                    + coordLon + ","
                    + "'" + coment + "');");

            dbHelper.close();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(mLocationSource);
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
        Cursor cursor = db.query("events",new String[] {"*"},null,null,null,null,null);
        while(cursor.moveToNext()){

            LatLng point = new LatLng(cursor.getDouble(13),cursor.getDouble(14));
            mMap.addMarker(new MarkerOptions().position(point).title(cursor.getString(1)));

        }
    }

//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        //        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));


         public void onMapLongClick(LatLng point) {
            Location location = new Location("LongPressLocationProvider");
            location.setLatitude(point.latitude);
            location.setLongitude(point.longitude);
            location.setAccuracy(100);
            mMap.addMarker(new MarkerOptions().position(point));

            Log.d("Clic","Tik");

             Intent intent2 = new Intent(MapsActivity.this,CreateActivity.class);

             intent2.putExtra("coordLat", point.latitude);
             intent2.putExtra("coordLon", point.longitude);
             startActivity(intent2);
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

    @Override
    public boolean onMarkerClick(Marker marker) {

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16.0f));

        Intent intent = new Intent(MapsActivity.this, ReadActivity.class);
        intent.putExtra("nameE", name);
        intent.putExtra("typeE", type);
        intent.putExtra("comentE", coment);
        intent.putExtra("yearE", year);
        intent.putExtra("monthE", month);
        intent.putExtra("dayE", day);
        intent.putExtra("hourE", hour);
        intent.putExtra("minuteE", minute);

        intent.putExtra("year2E", year2);
        intent.putExtra("month2E", month2);
        intent.putExtra("day2E", day2);
        intent.putExtra("hour2E", hour2);
        intent.putExtra("minute2E", minute2);

        intent.putExtra("coord1LatE", coordLat);
        intent.putExtra("coord1LonE", coordLon);
        intent.putExtra("coord2LatE", point2.latitude);
        intent.putExtra("coord2LonE", point2.longitude);
        return true;
    }
}

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {

        super(context, "DB1", null, 1);
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
                + "comment text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
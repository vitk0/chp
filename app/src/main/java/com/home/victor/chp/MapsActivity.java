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

public class MapsActivity extends Activity implements  LocationSource, OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

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
        dbHelper = new DBHelper(this);

//        try
//        {
//            Class.forName("com.mysql.jdbc.Driver");
//            con=DriverManager.getConnection("jdbc:192.168.0.38","root","toor");
//            st=con.prepareStatement("select * from event");
//            rs=st.executeQuery();
//            while(rs.next())
//            {
//                str=rs.getString(2);
//                Log.d("MySql",str);
//            }
//
//        }
//        catch(Exception e)
//        {
//            Log.d("MySql","Fail");
//        }
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
              startActivity(intent2);

             ContentValues cv = new ContentValues();

             // получаем данные из полей ввода
            // String name = etName.getText().toString();
             //String email = etEmail.getText().toString();

             // подключаемся к БД
             SQLiteDatabase db = dbHelper.getWritableDatabase();

                     cv.put("name", "1");
                     cv.put("type", "2");

                     // вставляем запись и получаем ее ID
//                     long rowID = db.insert("mytable", null, cv);
//                     Log.d(LOG_TAG, "row inserted, ID = " + rowID);
//                     break;
//                 case R.id.btnRead:
//                     Log.d(LOG_TAG, "--- Rows in mytable: ---");
//                     // делаем запрос всех данных из таблицы mytable, получаем Cursor
                     Cursor c = db.query("events", null, null, null, null, null, null);

                     // ставим позицию курсора на первую строку выборки
                     // если в выборке нет строк, вернется false
                     if (c.moveToFirst()) {

                         // определяем номера столбцов по имени в выборке
                         int idColIndex = c.getColumnIndex("id");
                         int nameColIndex = c.getColumnIndex("name");
                         int emailColIndex = c.getColumnIndex("email");

                         do {
                             // получаем значения по номерам столбцов и пишем все в лог
                             Log.d("DB",
                                     "ID = " + c.getInt(idColIndex) +
                                             ", name = " + c.getString(nameColIndex) +
                                             ", email = " + c.getString(emailColIndex));
                             // переход на следующую строку
                             // а если следующей нет (текущая - последняя), то false - выходим из цикла
                         } while (c.moveToNext());
                     } else
                         Log.d("DB", "0 rows");
                     c.close();
//                     break;
//                 case R.id.btnClear:
//                     Log.d(LOG_TAG, "--- Clear mytable: ---");
//                     // удаляем все записи
//                     int clearCount = db.delete("mytable", null, null);
//                     Log.d(LOG_TAG, "deleted rows count = " + clearCount);
//                     break;
             // закрываем подключение к БД
             dbHelper.close();
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

}

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB", "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table events ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "type text,"
                + "timeB data,"
                + "timeE data,"
                + "coordLat double,"
                + "coordLon double,"
                + "comment text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
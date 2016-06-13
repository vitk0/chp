package com.home.victor.chp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

public class ReadActivity extends Activity implements  OnMapReadyCallback, View.OnClickListener {
    public TextView textViewName;
    public TextView textViewType;
    public TextView textViewData1;
    public TextView textViewTime1;
    public TextView textViewData2;
    public TextView textViewTime2;
    public TextView textViewPrivate;
    public TextView textViewComent;
    public Button buttonEdit;

    public int id;
    public String name;
    public String type;
    public String coment;
    public int privacy;

    public int year;
    public int month ;
    public int day;
    public int hour ;
    public int minute;

    public int year2;
    public int month2 ;
    public int day2;
    public int hour2 ;
    public int minute2;

    double coordLat= 0.0;
    double coordLon= 0.0;
    public Double latitude2 = 0.0;
    public Double longitude2 = 0.0;
    public LatLng point;
    public LatLng point2;
    private Boolean nonLogin = false;
    private Switch switchSubscribe;

    public GoogleMap mReadMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.fragmentMarRead);
        mapFragment.getMapAsync(this);



        nonLogin = getIntent().getBooleanExtra("nonLoginE",false);
        id = getIntent().getIntExtra("idE",0);
        name = getIntent().getStringExtra("nameE");
        type = getIntent().getStringExtra("typeE");
        coment = getIntent().getStringExtra("comentE");
        privacy =getIntent().getIntExtra("privacyE",0);
        year = getIntent().getIntExtra("yearE", year);
        month = getIntent().getIntExtra("monthE",  month);
        day = getIntent().getIntExtra("dayE", day);
        hour = getIntent().getIntExtra("hourE", hour);
        minute = getIntent().getIntExtra("minuteE", minute);

        year2 = getIntent().getIntExtra("year2E", year2);
        month2 = getIntent().getIntExtra("month2E",  month2);
        day2 = getIntent().getIntExtra("day2E", day2);
        hour2 = getIntent().getIntExtra("hour2E", hour2);
        minute2 = getIntent().getIntExtra("minute2E", minute2);

        coordLat = getIntent().getDoubleExtra("coord1LatE",  coordLat);
        coordLon  = getIntent().getDoubleExtra("coord1LonE",  coordLon);
        point= new LatLng(coordLat,coordLon);

        latitude2 = getIntent().getDoubleExtra("coord2LatE", 0.0);
        longitude2 = getIntent().getDoubleExtra("coord2LonE", 0.0);
        point2= new LatLng(latitude2,longitude2);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewName.setText(name);
        textViewType = (TextView) findViewById(R.id.textViewType);
        textViewType.setText(type);
        textViewData1 = (TextView) findViewById(R.id.textViewData1);
        textViewData1.setText("Begin date " + new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        textViewTime1 = (TextView) findViewById(R.id.textViewTime1);
        textViewTime1.setText("Begin time " + new StringBuilder().append(hour).append(":")
                .append(minute));
        textViewData2 = (TextView) findViewById(R.id.textViewData2);
        textViewData2.setText("End date " + new StringBuilder().append(day2).append("/")
                .append(month2).append("/").append(year2));
        textViewTime2 = (TextView) findViewById(R.id.textViewTime2);
        textViewTime2.setText("End time " + new StringBuilder().append(hour2).append(":")
                .append(minute2));
        textViewPrivate = (TextView) findViewById(R.id.textViewPrivacy);
        if(privacy==1)
            textViewPrivate.setText("Privacy");
        else
            textViewPrivate.setText("No privacy");
        textViewComent = (TextView) findViewById(R.id.textViewComent);
        textViewComent.setText(coment);

        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(this);

        if(nonLogin)
            buttonEdit.setVisibility(View.INVISIBLE);


        switchSubscribe = (Switch) findViewById(R.id.switchSubscribe);
        if(nonLogin)
            switchSubscribe.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,CreateActivity.class);

        intent.putExtra("checkCreateEditE2",true);
        intent.putExtra("idE2",id);
        intent.putExtra("nameE2", name);
        intent.putExtra("typeE2", type);
        intent.putExtra("privacyE2", privacy);
        intent.putExtra("comentE2", coment);
        intent.putExtra("yearE2", year);
        intent.putExtra("monthE2", month);
        intent.putExtra("dayE2", day);
        intent.putExtra("hourE2", hour);
        intent.putExtra("minuteE2", minute);

        intent.putExtra("year2E2", year2);
        intent.putExtra("month2E2", month2);
        intent.putExtra("day2E2", day2);
        intent.putExtra("hour2E2", hour2);
        intent.putExtra("minute2E2", minute2);

        intent.putExtra("coord1LatE2", point.latitude);
        intent.putExtra("coord1LonE2", point.longitude);
        intent.putExtra("coord2LatE2", point2.latitude);
        intent.putExtra("coord2LonE2", point2.longitude);
        startActivity(intent);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mReadMap = googleMap;
        mReadMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 16.0f));
        mReadMap.addMarker(new MarkerOptions().position(point).title("Start"));
        mReadMap.addMarker(new MarkerOptions().position(point2).title("Finish")
                                .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        UiSettings uiSettings = mReadMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);
    }
}

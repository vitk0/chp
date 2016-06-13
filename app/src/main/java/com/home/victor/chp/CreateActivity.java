package com.home.victor.chp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateActivity extends Activity implements LocationSource, OnMapReadyCallback,View.OnClickListener, GoogleMap.OnMapLongClickListener {

    public GoogleMap mCreateMap;
    public LatLng loc;

    public int id;
    public String name;
    public int privacy =0;
    public String type;
    public String coment;

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

    public static LocationSource.OnLocationChangedListener mListener2;
    public GoogleMap.OnMyLocationChangeListener myLocationChangeListener2;
    public CreateActivity mLocationSource2;
    private Button btnCreate;
    private Button btnEditDate1;
    private Button btnEditTime1;
    private Button btnEditDate2;
    private Button btnEditTime2;
    private EditText editName;
    public static Spinner spinner;
    public String[] data = {"Sport", "Party", "Concert", "Beach", "Walk"};
    private EditText editComent;
    public Double latint = 0.0;
    public Double longit = 0.0;
    public Double latitude2 = 0.0;
    public Double longitude2 = 0.0;
    public LatLng point= new LatLng(latitude2,longitude2);
    private Boolean checkCreateEdit = false;
    public Switch switchPrivacy;

    public void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_layout);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.create_map);
        mapFragment.getMapAsync(this);

        checkCreateEdit = getIntent().getBooleanExtra("checkCreateEditE2",checkCreateEdit);

        id= getIntent().getIntExtra("idE2",0);
        name = getIntent().getStringExtra("nameE2");
        type = getIntent().getStringExtra("typeE2");
        coment = getIntent().getStringExtra("comentE2");
        privacy =getIntent().getIntExtra("privacyE2",0);
        year = getIntent().getIntExtra("yearE2", year);
        month = getIntent().getIntExtra("monthE2",  month);
        day = getIntent().getIntExtra("dayE2", day);
        hour = getIntent().getIntExtra("hourE2", hour);
        minute = getIntent().getIntExtra("minuteE2", minute);

        year2 = getIntent().getIntExtra("year2E2", year2);
        month2 = getIntent().getIntExtra("month2E2",  month2);
        day2 = getIntent().getIntExtra("day2E2", day2);
        hour2 = getIntent().getIntExtra("hour2E2", hour2);
        minute2 = getIntent().getIntExtra("minute2E2", minute2);

        latint = getIntent().getDoubleExtra("coord1LatE2",  latint);
        longit  = getIntent().getDoubleExtra("coord1LonE2",  longit);

        latitude2 = getIntent().getDoubleExtra("coord2LatE2", latitude2);
        longitude2 = getIntent().getDoubleExtra("coord2LonE2", longitude2);
        point= new LatLng(latitude2,longitude2);

        latint = getIntent().getDoubleExtra("coordLat", latint);
        longit = getIntent().getDoubleExtra("coordLon", longit);
        loc = new LatLng(latint, longit);

        btnCreate = (Button) findViewById(R.id.buttonCreate);
        btnCreate.setOnClickListener(this);

        btnEditDate1 = (Button) findViewById(R.id.btnEditDate1);
        btnEditDate1.setOnClickListener(this);


        btnEditTime1 = (Button) findViewById(R.id. btnEditTime1);
        btnEditTime1.setOnClickListener(this);

        btnEditDate2 = (Button) findViewById(R.id. btnEditDate2);
        btnEditDate2.setOnClickListener(this);

        btnEditTime2 = (Button) findViewById(R.id. btnEditTime2);
        btnEditTime2.setOnClickListener(this);

        editName = (EditText) findViewById(R.id.editName);

        editComent = (EditText) findViewById(R.id.editComent);
        switchPrivacy = (Switch) findViewById(R.id.switchPrivacy);

        switchPrivacy.setChecked(privacy ==1);
        if (checkCreateEdit) {
            btnCreate.setText("Save");

            btnEditDate1.setText("Begin date " + new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
            btnEditTime1.setText("Begin time " + new StringBuilder().append(hour).append(":")
                    .append(minute));
            btnEditDate2.setText("Begin date " + new StringBuilder().append(day2).append("/")
                    .append(month2).append("/").append(year2));
            btnEditTime2.setText("Begin time " + new StringBuilder().append(hour2).append(":")
                    .append(minute2));

        }
        switchPrivacy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    privacy = 1;
                } else {
                    privacy = 0;
                }
            }
        }
        );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Event type");
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                type = spinner.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mCreateMap = googleMap;
        mCreateMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
        mCreateMap.addMarker(new MarkerOptions().position(loc));
        if(checkCreateEdit)
            mCreateMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title(name)
                    .icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mCreateMap.setOnMyLocationChangeListener(myLocationChangeListener2);
        mCreateMap.setOnMapLongClickListener(this);
        UiSettings uiSettings = mCreateMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);
    }

    public void onMapLongClick(LatLng Point) {
        Location location = new Location("LongPressLocationProvider");
        location.setLatitude(Point.latitude);
        location.setLongitude(Point.longitude);
        point=Point;
        location.setAccuracy(100);
        mCreateMap.addMarker(new MarkerOptions()
                .position(Point)
                .title(name)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener2 = listener;
    }

    @Override
    public void deactivate() {
        mListener2 = null;
    }



    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCreate:
                name = editName.getText().toString();
                coment =  editComent.getText().toString();

                Intent intent = new Intent(CreateActivity.this, MapsActivity.class);

                intent.putExtra("checkCreateEdit",checkCreateEdit);

                intent.putExtra("id",id);
                intent.putExtra("name", name);
                intent.putExtra("type", type);
                intent.putExtra("privacy", privacy);
                intent.putExtra("coment", coment);
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("day", day);
                intent.putExtra("hour", hour);
                intent.putExtra("minute", minute);

                intent.putExtra("year2", year2);
                intent.putExtra("month2", month2);
                intent.putExtra("day2", day2);
                intent.putExtra("hour2", hour2);
                intent.putExtra("minute2", minute2);

                intent.putExtra("coord1Lat", latint);
                intent.putExtra("coord1Lon", longit);
                intent.putExtra("coord2Lat", point.latitude);
                intent.putExtra("coord2Lon", point.longitude);
                startActivity(intent);
                break;

            case R.id.btnEditDate1:
                setDate1(view);
                break;

            case R.id.btnEditTime1:
                setTime1(view);
                break;

            case R.id.btnEditDate2:
                setDate2(view);
                break;

            case R.id.btnEditTime2:
                setTime2(view);
                break;
        }
    }


    public void setDate1(View view) {
        showDialog(1);
    }

    public void setTime1(View view) {
        showDialog(2);
    }

    public void setDate2(View view) {
        showDialog(3);
    }

    public void setTime2(View view) {
        showDialog(4);
    }

    @Override
    protected Dialog onCreateDialog(int id) {

                if(id == 1)
                return new DatePickerDialog(this, myDateListener, year, month, day);

                if(id == 2)
                return new TimePickerDialog(this,myTimeListener,hour,minute,true);

                if(id == 3)
                return new DatePickerDialog(this, myDateListener2, year, month, day);

                if(id == 4)
                return new TimePickerDialog(this,myTimeListener2,hour,minute,true);

        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1, arg2+1, arg3);
        }
    };

    private TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            showTime(hourOfDay,minute);
        }
    };

    private DatePickerDialog.OnDateSetListener myDateListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate2(arg1, arg2+1, arg3);
        }
    };

    private TimePickerDialog.OnTimeSetListener myTimeListener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            showTime2(hourOfDay,minute);
        }
    };

    private void showDate(int year0, int month0, int day0) {
        btnEditDate1.setText("Begin date " + new StringBuilder().append(day0).append("/")
                .append(month).append("/").append(year0));
        year = year0;
        month = month0;
        day = day0;
    }

    private void showTime(int hour0, int minute0) {
        btnEditTime1.setText("Begin time " + new StringBuilder().append(hour0).append(":")
                .append(minute0));

        hour = hour0;
        minute= minute0;
    }

    private void showDate2(int year0, int month0, int day0) {
        btnEditDate2.setText("End date " + new StringBuilder().append(day0).append("/")
                .append(month0).append("/").append(year0));
        year2 = year0;
        month2 = month0;
        day2 = day0;
    }

    private void showTime2(int hour0, int minute0) {
        btnEditTime2.setText("End time " + new StringBuilder().append(hour0).append(":")
                .append(minute0));
        hour2 = hour0;
        minute2= minute0;
    }

};



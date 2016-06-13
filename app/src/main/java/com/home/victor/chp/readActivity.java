package com.home.victor.chp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class ReadActivity extends AppCompatActivity implements View.OnClickListener {
    public TextView textViewName;
    public TextView textViewType;
    public TextView textViewData1;
    public TextView textViewTime1;
    public TextView textViewData2;
    public TextView textViewTime2;
    public TextView textViewPrivate;
    public TextView textViewComent;
    public Button buttonEdit;

    public String name;
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

    double coordLat= 0.0;
    double coordLon= 0.0;
    public Double latitude2 = 0.0;
    public Double longitude2 = 0.0;
    public LatLng point2= new LatLng(latitude2,longitude2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        name = getIntent().getStringExtra("nameE");
        type = getIntent().getStringExtra("typeE");
        coment = getIntent().getStringExtra("comentE");
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
        latitude2 = getIntent().getDoubleExtra("coord2LatE", point2.latitude);
        longitude2 = getIntent().getDoubleExtra("coord2LonE", point2.longitude);

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
        textViewPrivate = (TextView) findViewById(R.id.textViewPrivate);
       // textViewName.setText(name);
        textViewComent = (TextView) findViewById(R.id.textViewComent);
        textViewComent.setText(coment);

        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,CreateActivity.class);
        //startActivity(intent);

    }
}

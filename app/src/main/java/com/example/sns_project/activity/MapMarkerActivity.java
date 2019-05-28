package com.example.sns_project.activity;


import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sns_project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapMarkerActivity extends FragmentActivity implements OnMapReadyCallback {

    //GoogleMap 객체
    GoogleMap googleMap;
    Button zIn, zOut;

    //위도 경도
    String contitle;
    String addr;
    Double mapx = null, mapy = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapmarker);

        Intent intent = getIntent();
        contitle = intent.getExtras().getString("conTitle");
        addr = intent.getExtras().getString("addr");
        mapx = intent.getExtras().getDouble("mapx");
        mapy = intent.getExtras().getDouble("mapy");

        TextView maptitle = findViewById(R.id.contitle);
        Button zIn = findViewById(R.id.zoomin);
        Button zOut = findViewById(R.id.zoomout);

        zIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        zOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

        maptitle.setText(contitle);

        //맵생성
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //구글맵 생성 콜백
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        //지도타입 - 일반
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //기본위치
        LatLng position = new LatLng(mapy , mapx);

        //화면중앙의 위치와 카메라 줌비율
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

        onAddMarker();
    }

    //마커추가
    public void onAddMarker(){
        LatLng position = new LatLng(mapy , mapx);

        //나의위치 마커
        MarkerOptions mymarker = new MarkerOptions()
                .position(position);   //마커위치

        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.marker);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 200, 200, false);
        mymarker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

        mymarker.title(contitle);
        mymarker.snippet(addr);

        //마커추가
        this.googleMap.addMarker(mymarker);
    }
}
package com.example.sns_project.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sns_project.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GpsFestTourlistActivity extends AppCompatActivity {

    Button update1, more1, update2, more2;
    ImageView gpsimg1, gpsimg2, gpsimg3, gpsimg4, festimg1, festimg2, festimg3, festimg4;
    TextView gpsttl1, gpsttl2, gpsttl3, gpsttl4, festttl1, festttl2, festttl3, festttl4;

    String key = "gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
    int totalCount=20;
    String searchSort1 = "gps", searchSort2 = "fest";

    //gps검색 파싱 변수
    ArrayList<String> tourListTitle = new ArrayList<>();
    ArrayList<String> imgURL = new ArrayList<>();
    ArrayList<String> tourListContent = new ArrayList<>();
    ArrayList<String> contId = new ArrayList<>();
    ArrayList<String> contenttypeid = new ArrayList<>();
    ArrayList<String> addr = new ArrayList<>();
    ArrayList<Double> mapx = new ArrayList<>();
    ArrayList<Double> mapy = new ArrayList<>();

    //행사, 공연, 축제 검색 파싱 변수
    ArrayList<String> tourListTitle2 = new ArrayList<>();
    ArrayList<String> imgURL2 = new ArrayList<>();
    ArrayList<String> tourListContent2 = new ArrayList<>();
    ArrayList<String> contId2 = new ArrayList<>();
    ArrayList<String> contenttypeid2 = new ArrayList<>();
    ArrayList<String> addr2 = new ArrayList<>();
    ArrayList<Double> mapx2 = new ArrayList<>();
    ArrayList<Double> mapy2 = new ArrayList<>();

    Double mylocateX = null, mylocateY = null;  //내 위치정보
    String dateToday;

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_fest);

        //gps 뷰 객체
        update1 = findViewById(R.id.update1);
        more1 = findViewById(R.id.more1);
        gpsimg1 = findViewById(R.id.gpsimg1);
        gpsimg2 = findViewById(R.id.gpsimg2);
        gpsimg3 = findViewById(R.id.gpsimg3);
        gpsimg4 = findViewById(R.id.gpsimg4);
        gpsttl1 = findViewById(R.id.gpsttl1);
        gpsttl2 = findViewById(R.id.gpsttl2);
        gpsttl3 = findViewById(R.id.gpsttl3);
        gpsttl4 = findViewById(R.id.gpsttl4);

        //행사공연축제 뷰 객체
        update2 = findViewById(R.id.update2);
        more2 = findViewById(R.id.more2);
        festimg1 = findViewById(R.id.festimg1);
        festimg2 = findViewById(R.id.festimg2);
        festimg3 = findViewById(R.id.festimg3);
        festimg4 = findViewById(R.id.festimg4);
        festttl1 = findViewById(R.id.festttl1);
        festttl2 = findViewById(R.id.festttl2);
        festttl3 = findViewById(R.id.festttl3);
        festttl4 = findViewById(R.id.festttl4);

        //로딩중 메세지
        gpsimg1.setImageDrawable(null);
        gpsimg2.setImageDrawable(null);
        gpsimg3.setImageDrawable(null);
        gpsimg4.setImageDrawable(null);
        gpsttl1.setText("LOADING");
        gpsttl2.setText("LOADING");
        gpsttl3.setText("LOADING");
        gpsttl4.setText("LOADING");

        festimg1.setImageDrawable(null);
        festimg2.setImageDrawable(null);
        festimg3.setImageDrawable(null);
        festimg4.setImageDrawable(null);
        festttl1.setText("LOADING");
        festttl2.setText("LOADING");
        festttl3.setText("LOADING");
        festttl4.setText("LOADING");

        final Intent i = new Intent(getApplicationContext(), TourListActivity.class);

        /* 앱의 사용권한 묻기 */
        //해당 권한이 사용자에 이해 승인되어있지 않음
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //이전 앱 실행시 권한 거부했을 경우 다시 권한 설정을 물음
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
                localBuilder.setTitle("권한 설정")
                        .setMessage("권한 거절로 인해 일부기능이 제한됩니다.")
                        .setPositiveButton("권한 설정하러 가기", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                                try {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                            .setData(Uri.parse("package:" + getPackageName()));
                                    startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    e.printStackTrace();
                                    Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("취소하기", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                            }
                        })
                        .create()
                        .show();

            //앱을 최초로 실행했을 때
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }

        }

        //위치 관리자 객체 참조
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        final ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);



        /* @@@@@@@@@@@@ 앱 실행시 받아오는 gps추천정보 @@@@@@@@@@*/
        //가장 최근 위치정보 & api받아오기
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GpsFestTourlistActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        } else {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mylocateX = location.getLongitude();    //위도
            mylocateY = location.getLatitude();      //경도

            //위치정보 갱신
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
        }

        //내 주변 광광지 api받아오기
        new Thread(new Runnable() {
            @Override
            public void run() {

                getGpsTourListData();

                final Drawable[] tourListImage = new Drawable[imgURL.size()];

                Log.d("imgURL", String.valueOf(imgURL.size()));
                Log.d("tourListTitle", String.valueOf(tourListTitle.size()));
                Log.d("tourListContent", String.valueOf(tourListContent.size()));
                Log.d("contId", String.valueOf(contId.size()));
                Log.d("imgURL", String.valueOf(imgURL.get(0)));

                //url에서 이미지 가져오기

                for(int i = 0; i<imgURL.size(); i++) {
                    if (imgURL.get(i) != "noimg") {
                        tourListImage[i] = getImageFromURL(imgURL.get(i));
                    }
                    if (imgURL.get(i).equals("noimg")) {
                        tourListImage[i] = ContextCompat.getDrawable(getApplicationContext(), R.drawable.noimage);
                    }
                }
                Log.d("imgURL", String.valueOf(tourListImage[0]));

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //메인 화면에 광광정보 출력
                        gpsimg1.setImageDrawable(tourListImage[0]);
                        gpsimg2.setImageDrawable(tourListImage[1]);
                        gpsimg3.setImageDrawable(tourListImage[2]);
                        gpsimg4.setImageDrawable(tourListImage[3]);

                        gpsttl1.setText(tourListTitle.get(0));
                        gpsttl2.setText(tourListTitle.get(1));
                        gpsttl3.setText(tourListTitle.get(2));
                        gpsttl4.setText(tourListTitle.get(3));

                    }
                });

            }
        }).start();

        /* @@@@@@@@@@@@ 앱 실행시 받아오는 행사공연축제 추천정보 @@@@@@@@@@*/
        //오늘 날짜 가져오기
        Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        dateToday = transFormat.format(from);
        Log.d("date", dateToday);


        new Thread(new Runnable() {
            @Override
            public void run() {

                getFestTourListData();

                final Drawable[] tourListImage2 = new Drawable[imgURL2.size()];

                Log.d("imgURL", String.valueOf(imgURL2.size()));
                Log.d("tourListTitle", String.valueOf(tourListTitle2.size()));
                Log.d("tourListContent", String.valueOf(tourListContent2.size()));
                Log.d("contId", String.valueOf(contId2.size()));
                Log.d("imgURL", String.valueOf(imgURL2.get(0)));

                //url에서 이미지 가져오기
                for(int i = 0; i<imgURL2.size(); i++) {
                    if (imgURL2.get(i) != "noimg") {
                        tourListImage2[i] = getImageFromURL(imgURL2.get(i));
                    }
                    if (imgURL2.get(i).equals("noimg")) {
                        tourListImage2[i] = ContextCompat.getDrawable(getApplicationContext(), R.drawable.noimage);
                    }
                }
                Log.d("imgURL", String.valueOf(tourListImage2[0]));

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //메인 화면에 광광정보 출력
                        festimg1.setImageDrawable(tourListImage2[0]);
                        festimg2.setImageDrawable(tourListImage2[1]);
                        festimg3.setImageDrawable(tourListImage2[2]);
                        festimg4.setImageDrawable(tourListImage2[3]);

                        festttl1.setText(tourListTitle2.get(0));
                        festttl2.setText(tourListTitle2.get(1));
                        festttl3.setText(tourListTitle2.get(2));
                        festttl4.setText(tourListTitle2.get(3));

                    }
                });

            }
        }).start();

        /* @@@@@@@@@@@@ 행사공연축제 업데이트 버튼 누를 시 받아오는 추천정보 @@@@@@@@@@*/
        update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        tourListTitle2.clear();
                        imgURL2.clear();
                        tourListContent2.clear();
                        contId2.clear();
                        contenttypeid2.clear();
                        addr2.clear();
                        mapx2.clear();
                        mapy2.clear();

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                //메인 화면에 광광정보 출력
                                festimg1.setImageDrawable(null);
                                festimg2.setImageDrawable(null);
                                festimg3.setImageDrawable(null);
                                festimg4.setImageDrawable(null);

                                festttl1.setText("LOADING");
                                festttl2.setText("LOADING");
                                festttl3.setText("LOADING");
                                festttl4.setText("LOADING");

                            }
                        });

                    }
                }).start();

                //행사공연축제 api받아오기
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        getFestTourListData();

                        final Drawable[] tourListImage2 = new Drawable[imgURL2.size()];

                        Log.d("imgURL", String.valueOf(imgURL2.size()));
                        Log.d("tourListTitle", String.valueOf(tourListTitle2.size()));
                        Log.d("tourListContent", String.valueOf(tourListContent2.size()));
                        Log.d("contId", String.valueOf(contId2.size()));
                        Log.d("imgURL", String.valueOf(imgURL2.get(0)));

                        //url에서 이미지 가져오기

                        for(int i = 0; i<imgURL2.size(); i++) {
                            if (imgURL2.get(i) != "noimg") {
                                tourListImage2[i] = getImageFromURL(imgURL2.get(i));
                            }
                            if (imgURL2.get(i).equals("noimg")) {
                                tourListImage2[i] = ContextCompat.getDrawable(getApplicationContext(), R.drawable.noimage);
                            }
                        }
                        Log.d("imgURL", String.valueOf(tourListImage2[0]));

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                //메인 화면에 광광정보 출력

                                festimg1.setImageDrawable(tourListImage2[0]);
                                festimg2.setImageDrawable(tourListImage2[1]);
                                festimg3.setImageDrawable(tourListImage2[2]);
                                festimg4.setImageDrawable(tourListImage2[3]);

                                festttl1.setText(tourListTitle2.get(0));
                                festttl2.setText(tourListTitle2.get(1));
                                festttl3.setText(tourListTitle2.get(2));
                                festttl4.setText(tourListTitle2.get(3));

                            }
                        });

                    }
                }).start();
            }
        });

        /* @@@@@@@@@@@@ 행사공연축제 더 보기 버튼 누를 시 동작 @@@@@@@@@@*/
        more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        i.putExtra("tourListTitle", tourListTitle2);
                        i.putExtra("tourListImage", imgURL2);
                        i.putExtra("tourListContent", tourListContent2);
                        i.putExtra("contId", contId2);
                        i.putExtra("mapx", mapx2);
                        i.putExtra("mapy", mapy2);
                        i.putExtra("addr", addr2);
                        i.putExtra("totalCount", totalCount);
                        i.putExtra("contenttypeid", contenttypeid2);
                        i.putExtra("searchSort", searchSort2);
                        startActivity(i);
                    }
                }).start();

            }
        });

        /* @@@@@@@@@@@@ gbs 업데이트 버튼 누를 시 받아오는 정보 @@@@@@@@@@*/
        //내 주변 광광지 업데이트 버튼
        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        tourListTitle.clear();
                        imgURL.clear();
                        tourListContent.clear();
                        contId.clear();
                        contenttypeid.clear();
                        addr.clear();
                        mapx.clear();
                        mapy.clear();

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                //메인 화면에 광광정보 출력
                                gpsimg1.setImageDrawable(null);
                                gpsimg2.setImageDrawable(null);
                                gpsimg3.setImageDrawable(null);
                                gpsimg4.setImageDrawable(null);

                                gpsttl1.setText("LOADING");
                                gpsttl2.setText("LOADING");
                                gpsttl3.setText("LOADING");
                                gpsttl4.setText("LOADING");

                            }
                        });

                    }
                }).start();

                //위치 관리자 객체 참조
                final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                final ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

                //가장 최근 위치정보 & api받아오기
                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(GpsFestTourlistActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            0);
                } else {
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    mylocateX = location.getLongitude();    //위도
                    mylocateY = location.getLatitude();      //경도

                    //위치정보 갱신
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                }

                //내 주변 광광지 api받아오기
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        getGpsTourListData();

                        final Drawable[] tourListImage = new Drawable[imgURL.size()];

                        Log.d("imgURL", String.valueOf(imgURL.size()));
                        Log.d("tourListTitle", String.valueOf(tourListTitle.size()));
                        Log.d("tourListContent", String.valueOf(tourListContent.size()));
                        Log.d("contId", String.valueOf(contId.size()));
                        Log.d("imgURL", String.valueOf(imgURL.get(0)));

                        //url에서 이미지 가져오기

                        for(int i = 0; i<imgURL.size(); i++) {
                            if (imgURL.get(i) != "noimg") {
                                tourListImage[i] = getImageFromURL(imgURL.get(i));
                            }
                            if (imgURL.get(i).equals("noimg")) {
                                tourListImage[i] = ContextCompat.getDrawable(getApplicationContext(), R.drawable.noimage);
                            }
                        }
                        Log.d("imgURL", String.valueOf(tourListImage[0]));

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                //메인 화면에 광광정보 출력

                                gpsimg1.setImageDrawable(tourListImage[0]);
                                gpsimg2.setImageDrawable(tourListImage[1]);
                                gpsimg3.setImageDrawable(tourListImage[2]);
                                gpsimg4.setImageDrawable(tourListImage[3]);

                                gpsttl1.setText(tourListTitle.get(0));
                                gpsttl2.setText(tourListTitle.get(1));
                                gpsttl3.setText(tourListTitle.get(2));
                                gpsttl4.setText(tourListTitle.get(3));

                            }
                        });

                    }
                }).start();
            }
        });

        /* @@@@@@@@@@@@ gps 더 보기 버튼 누를 시 동작 @@@@@@@@@@*/
        more1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        i.putExtra("tourListTitle", tourListTitle);
                        i.putExtra("tourListImage", imgURL);
                        i.putExtra("tourListContent", tourListContent);
                        i.putExtra("contId", contId);
                        i.putExtra("mapx", mapx);
                        i.putExtra("mapy", mapy);
                        i.putExtra("addr", addr);
                        i.putExtra("totalCount", totalCount);
                        i.putExtra("contenttypeid", contenttypeid);
                        i.putExtra("searchSort", searchSort1);
                        startActivity(i);
                    }
                }).start();

            }
        });




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                return;
            }
        }
    }

    //위치 리스너
    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            mylocateX = location.getLongitude();    //위도
            mylocateY = location.getLatitude();      //경도

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    //데아터 파싱
    void getGpsTourListData() {
        StringBuffer buffer = new StringBuffer();
        buffer.setLength(0);

        try {

            String requestUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?&mapX=" + mylocateX + "&mapY="
                    + mylocateY + "&radius=300&arrange=B&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=Dahang&ServiceKey=" + key;
            URL url= new URL(requestUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            Boolean isImgTag = false;
            String tag; //태그이름저장

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else if (tag.equals("addr1")) {
                            xpp.next();
                            addr.add(xpp.getText());
                            buffer.append("* 주소: " + xpp.getText() + "\n");
                        } else if (tag.equals("contentid")) {
                            xpp.next();
                            contId.add(xpp.getText());
                        } else if (tag.equals("contenttypeid")) {
                            xpp.next();
                            contenttypeid.add(xpp.getText());
                        } else if (tag.equals("createdtime")) {
                            xpp.next();
                            SimpleDateFormat before = new SimpleDateFormat("yyyyMMddHHmmss");
                            SimpleDateFormat after = new SimpleDateFormat("yyyy년 MM월 dd일  HH:mm:ss");
                            Date tempDate;
                            tempDate = before.parse(xpp.getText());
                            String transDate = after.format(tempDate);
                            buffer.append("* 등록날짜: " + transDate + "\n");
                        } else if (tag.equals("firstimage")) {
                            xpp.next();
                            isImgTag = true;
                            imgURL.add(xpp.getText());
                        } else if (tag.equals("mapx")) {
                            xpp.next();
                            mapx.add(Double.valueOf(xpp.getText()));
                        } else if (tag.equals("mapy")) {
                            xpp.next();
                            mapy.add(Double.valueOf(xpp.getText()));
                        } else if (tag.equals("modifiedtime")) {
                            xpp.next();
                            SimpleDateFormat before = new SimpleDateFormat("yyyyMMddHHmmss");
                            SimpleDateFormat after = new SimpleDateFormat("yyyy년 MM월 dd일  HH:mm:ss");
                            Date tempDate;
                            tempDate = before.parse(xpp.getText());
                            String transDate = after.format(tempDate);
                            buffer.append("* 수정날짜: " + transDate + "\n");
                        } else if (tag.equals("readcount")) {
                            xpp.next();
                            buffer.append("* 조회수: " + xpp.getText() + "\n");
                        } else if (tag.equals("tel")) {
                            xpp.next();
                            buffer.append("* 전화번호: " + xpp.getText() + "\n");
                        } else if (tag.equals("title")) {
                            xpp.next();
                            tourListTitle.add(xpp.getText());
                        } else if (tag.equals("totalCount")) {
                            xpp.next();
                            //totalCount = parseInt(xpp.getText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기
                        if (tag.equals("item")) {
                            if (isImgTag == false) {
                                imgURL.add("noimg");
                            } else if (isImgTag == true)
                                isImgTag = false;
                            tourListContent.add(buffer.toString());
                            buffer.setLength(0);
                        }
                        break;

                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
        }
    }

    void getFestTourListData() {
        StringBuffer buffer = new StringBuffer();
        buffer.setLength(0);

        try {

            String requestUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?arrange=B&eventStartDate=" + dateToday
                    + "&numOfRows=20&MobileOS=ETC&MobileApp=Dahang&serviceKey=gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
            URL url= new URL(requestUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            Boolean isImgTag = false;
            String tag; //태그이름저장

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기

                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else if (tag.equals("addr1")) {
                            xpp.next();
                            addr2.add(xpp.getText());
                            buffer.append("* 주소: " + xpp.getText() + "\n");
                        } else if (tag.equals("contentid")) {
                            xpp.next();
                            contId2.add(xpp.getText());
                        } else if (tag.equals("contenttypeid")) {
                            xpp.next();
                            contenttypeid2.add(xpp.getText());
                        } else if (tag.equals("createdtime")) {
                            xpp.next();
                            SimpleDateFormat before = new SimpleDateFormat("yyyyMMddHHmmss");
                            SimpleDateFormat after = new SimpleDateFormat("yyyy년 MM월 dd일  HH:mm:ss");
                            Date tempDate;
                            tempDate = before.parse(xpp.getText());
                            String transDate = after.format(tempDate);
                            buffer.append("* 등록날짜: " + transDate + "\n");
                        } else if (tag.equals("firstimage")) {
                            xpp.next();
                            isImgTag = true;
                            imgURL2.add(xpp.getText());
                        } else if (tag.equals("mapx")) {
                            xpp.next();
                            mapx2.add(Double.valueOf(xpp.getText()));
                        } else if (tag.equals("mapy")) {
                            xpp.next();
                            mapy2.add(Double.valueOf(xpp.getText()));
                        } else if (tag.equals("modifiedtime")) {
                            xpp.next();
                            SimpleDateFormat before = new SimpleDateFormat("yyyyMMddHHmmss");
                            SimpleDateFormat after = new SimpleDateFormat("yyyy년 MM월 dd일  HH:mm:ss");
                            Date tempDate;
                            tempDate = before.parse(xpp.getText());
                            String transDate = after.format(tempDate);
                            buffer.append("* 수정날짜: " + transDate + "\n");
                        } else if (tag.equals("readcount")) {
                            xpp.next();
                            buffer.append("* 조회수: " + xpp.getText() + "\n");
                        } else if (tag.equals("tel")) {
                            xpp.next();
                            buffer.append("* 전화번호: " + xpp.getText() + "\n");
                        } else if (tag.equals("title")) {
                            xpp.next();
                            tourListTitle2.add(xpp.getText());
                        } else if (tag.equals("totalCount")) {
                            xpp.next();
                            //totalCount = parseInt(xpp.getText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기
                        if (tag.equals("item")) {
                            if (isImgTag == false) {
                                imgURL2.add("noimg");
                            } else if (isImgTag == true)
                                isImgTag = false;
                            tourListContent2.add(buffer.toString());
                            buffer.setLength(0);
                        }
                        break;

                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
        }
    }

    public Drawable getImageFromURL(String strImageURL){
        Bitmap imgBitmap = null;

        try{
            URL url = new URL(strImageURL);
            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream imgIs = conn.getInputStream();
            imgBitmap = BitmapFactory.decodeStream(imgIs);
            imgIs.close();
        }catch (Exception e) {
        }
        return new BitmapDrawable(imgBitmap);
    }

}

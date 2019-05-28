package com.example.sns_project.activity;

import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sns_project.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Integer.compare;
import static java.lang.Integer.parseInt;

public class SearchActivity extends AppCompatActivity {

    Button dosi, sigungu, content, search, keywordSearch;
    TextView dosiSlct, sigunguSlct, contentSlct;
    String key = "gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
    ArrayList<String> dosiSel = new ArrayList<>(); //특별광역시, 도 정보
    ArrayList<String> dosiSelCode = new ArrayList<>(); //특별광역시, 도 코드
    ArrayList<String> sigunguSel = new ArrayList<>();
    ArrayList<String> sigunguSelCode = new ArrayList<>();
    String dosiname, sigunguname, contentname;
    int totalCount;
    String searchSort = "area";

    ArrayList<String> tourListTitle = new ArrayList<>();
    ArrayList<String> imgURL = new ArrayList<>();
    ArrayList<String> tourListContent = new ArrayList<>();
    ArrayList<String> contId = new ArrayList<>();
    ArrayList<String> contenttypeid = new ArrayList<>();
    ArrayList<String> addr = new ArrayList<>();
    ArrayList<Double> mapx = new ArrayList<>();
    ArrayList<Double> mapy = new ArrayList<>();

    String[] contentTypeId = {"관광지", "문화시설", "축제/공연/행사", "레포츠", "숙박", "쇼핑", "음식"};
    String[] contentTypeIdSelCode = {"12", "14", "15", "28", "32", "38", "39"};
    String dosiCode = null, sigunguCode = null, contentTypeIdCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmain);

        dosi = (Button) findViewById(R.id.dosi);
        sigungu = (Button) findViewById(R.id.sigungu);
        content = (Button) findViewById(R.id.content);
        search = (Button) findViewById(R.id.search);
        keywordSearch = (Button) findViewById(R.id.keywordSearch);
        dosiSlct = (TextView) findViewById(R.id.dosiSlct);
        sigunguSlct = (TextView) findViewById(R.id.sigunguSlct);
        contentSlct = (TextView) findViewById(R.id.contentSlct);

        //특별,광역시, 도 정보 가져오기
        Thread mThread = new Thread() {


            @Override
            public void run() {
                getDoSiData();

                /*runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }
                });*/

            }
        };
        mThread.start();

        try {
            mThread.join();

            //특별,광역시, 도 선택 버튼
            final ColorStateList oldColors =  dosiSlct.getTextColors();
            dosi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String[] dosisel = dosiSel.toArray(new String[dosiSel.size()]);

                    AlertDialog.Builder dosiDig = new AlertDialog.Builder(SearchActivity.this);
                    dosiDig.setTitle("대분류 선택");
                    dosiDig.setSingleChoiceItems(dosisel, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {

                            dosiSlct.setText("특별/광역시, 도: " + dosisel[i]);
                            dosiSlct.setTextColor(Color.BLACK);
                            dosiCode = dosiSelCode.get(i);
                            dosiname = dosisel[i];

                            //중분류 초기화
                            sigunguSlct.setText("특별/광역시, 도: ");
                            sigunguSlct.setTextColor(oldColors);
                            sigunguCode = null;
                            sigunguSel.clear();
                            sigunguSelCode.clear();

                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    getSigunguData();

                                }
                            }).start();

                        }
                    }).setPositiveButton("확인", null).show();
                }
            });
        } catch (InterruptedException e) {
        }

        //시군구 선택 버튼
        sigungu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dosiCode == null) {
                    Toast.makeText(getApplicationContext(), "특별/광역시, 도'를 먼저 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    final String[] sigungu = sigunguSel.toArray(new String[sigunguSel.size()]);

                    AlertDialog.Builder dosiDig = new AlertDialog.Builder(SearchActivity.this);
                    dosiDig.setTitle("중분류 선택");
                    dosiDig.setSingleChoiceItems(sigungu, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            sigunguSlct.setText("특별/광역시, 도: " + sigungu[i]);
                            sigunguSlct.setTextColor(Color.BLACK);
                            sigunguCode = sigunguSelCode.get(i);
                            sigunguname = sigungu[i];

                        }
                    }).setPositiveButton("확인", null).show();
                }
            }
        });

        //컨텐츠 선택버튼
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder contecDig = new AlertDialog.Builder(SearchActivity.this);
                contecDig.setTitle("소분류 선택");
                contecDig.setSingleChoiceItems(contentTypeId, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        contentSlct.setText("여행 콘텐츠: "+contentTypeId[i]);
                        contentSlct.setTextColor(Color.BLACK);
                        contentTypeIdCode = contentTypeIdSelCode[i];
                        contentname = contentTypeId[i];

                    }
                }).setPositiveButton("확인", null).show();

            }
        });

        final Intent i = new Intent(getApplicationContext(), TourListActivity.class);

        //검색버튼
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((dosiname==null) || (contentname==null)) {
                    Toast.makeText(getApplicationContext(), "지역과 컨텐츠를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {

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
                            getTourListData();

                            i.putExtra("tourListTitle", tourListTitle);
                            i.putExtra("tourListImage", imgURL);
                            i.putExtra("tourListContent", tourListContent);
                            i.putExtra("contId", contId);
                            i.putExtra("mapx", mapx);
                            i.putExtra("mapy", mapy);
                            i.putExtra("addr", addr);
                            i.putExtra("dosi", dosiname);
                            i.putExtra("totalCount", totalCount);
                            i.putExtra("contType", contentTypeIdCode);
                            i.putExtra("sigungu", sigunguname);
                            i.putExtra("content", contentname);
                            i.putExtra("contenttypeid", contenttypeid);
                            i.putExtra("searchSort", searchSort);
                            startActivity(i);
                        }
                    }).start();
                }
            }
        });

        keywordSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.sns_project.activity.KeywordActivity.class);
                startActivity(intent);
            }
        });

    }


    //특별, 광역시, 도 선택
    void getDoSiData() {
        try {

            String requestUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode?&numOfRows=10000&pageNo=1&MobileOS=ETC&MobileApp=Dahang&ServiceKey="+key;
            URL url= new URL(requestUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag; //태그이름저장

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("code")){
                            xpp.next();
                            dosiSelCode.add(xpp.getText());//addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
                        }
                        else if(tag.equals("name")){
                            xpp.next();
                            dosiSel.add(xpp.getText());
                        }
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
        }
    }

    //시군구 선택
    void getSigunguData() {
        try {

            String requestUrl = "https://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode?&numOfRows=10000&pageNo=1&MobileOS=ETC&MobileApp=Dahang&ServiceKey="+key+"&areaCode="+dosiCode;
            URL url= new URL(requestUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag; //태그이름저장

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("code")){
                            xpp.next();
                            sigunguSelCode.add(xpp.getText());//addr 요소의 TEXT 읽어와서 문자열버퍼에 추가
                        }
                        else if(tag.equals("name")){
                            xpp.next();
                            sigunguSel.add(xpp.getText());
                        }
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
        }
    }

    //관광지 정보 파싱
    void getTourListData() {
        StringBuffer buffer = new StringBuffer();
        buffer.setLength(0);

        try {

            String requestUrl = "https://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?&numOfRows=10000&pageNo=1&MobileOS=ETC&MobileApp=Dahang" +
                    "&ServiceKey="+key+"&areaCode="+dosiCode+"&sigunguCode="+sigunguCode+"&contentTypeId="+contentTypeIdCode;
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
                            totalCount = parseInt(xpp.getText());
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

}


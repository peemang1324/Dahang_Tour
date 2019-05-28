package com.example.sns_project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sns_project.R;
import com.example.sns_project.adapter.ListAdapter;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class TourListActivity extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourlist);

        result = (TextView) findViewById(R.id.result);

        final ListView tourlist;
        final ListAdapter adapter;

        adapter = new ListAdapter();

        tourlist = (ListView) findViewById(R.id.tourlist);

        result.setText("LOADING");

        new Thread(new Runnable() {
            @Override
            public void run() {

                final Intent intent = getIntent();

                final String dosiname = intent.getExtras().getString("dosi");
                final String sigunguname = intent.getExtras().getString("sigungu");
                final String contentname = intent.getExtras().getString("content");
                final String searchSort = intent.getExtras().getString("searchSort");
                final String keyword = intent.getExtras().getString("keyword");
                final String arrangename = intent.getExtras().getString("arrangename");
                int totalCount = intent.getExtras().getInt("totalCount");

                final ArrayList<String> tourListTitle = (ArrayList<String>)intent.getSerializableExtra("tourListTitle");
                final ArrayList<String> ImageUrl = (ArrayList<String>)intent.getSerializableExtra("tourListImage");
                final ArrayList<String> tourListContent = (ArrayList<String>)intent.getSerializableExtra("tourListContent");
                final ArrayList<String> contId = (ArrayList<String>)intent.getSerializableExtra("contId");
                final ArrayList<String> contenttypeid = (ArrayList<String>)intent.getSerializableExtra("contenttypeid");
                final ArrayList<String> addr = (ArrayList<String>)intent.getSerializableExtra("addr");
                final ArrayList<Double> mapx = (ArrayList<Double>)intent.getSerializableExtra("mapx");
                final ArrayList<Double> mapy = (ArrayList<Double>)intent.getSerializableExtra("mapy");
                final Drawable[] tourListImage1 = new Drawable[totalCount];

                for(int i = 0; i<ImageUrl.size(); i++) {
                    if (ImageUrl.get(i) != "noimg") {
                        tourListImage1[i] = getImageFromURL(ImageUrl.get(i));
                    }
                    if (ImageUrl.get(i).equals("noimg")) {
                        tourListImage1[i] = ContextCompat.getDrawable(getApplicationContext(), R.drawable.noimage);
                    }
                    addr.add(i, addr.get(i).substring(addr.get(i).indexOf(":")+1));
                    String last = tourListContent.get(i);
                    last = last.replace("~", "/").replace("<br>", " / ");
                    adapter.addItem(tourListImage1[i], tourListTitle.get(i), last, contId.get(i), mapx.get(i), mapy.get(i), contenttypeid.get(i), addr.get(i));
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        if (searchSort.equals("area"))
                            result.setText(dosiname + ", " + sigunguname + "의 " + contentname + "컨텐츠로 검색");
                        if (searchSort.equals("keyword")) {
                            result.setText("'" + keyword + "'키워드로 검색\n");
                            if ( (arrangename.equals("")) == false )
                                result.append("["+arrangename+"]");
                            if ( (dosiname.equals("")) == false )
                                result.append("["+dosiname+"]");
                            if ( (sigunguname.equals("")) == false )
                                result.append("["+sigunguname+"]");
                            if ( (contentname.equals("")) == false )
                                result.append("["+contentname+"]");
                        }
                        if (searchSort.equals("gps")) {
                            result.setText("내 주변 광광지 추천(조회순)");
                        }
                        if (searchSort.equals("fest")) {
                            result.setText("진행중인 행사/공연/축제 추천(조회순)");
                        }
                        tourlist.setAdapter(adapter);

                    }
                });

            }
        }).start();

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

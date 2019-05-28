package com.example.sns_project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import java.util.ArrayList;

public class GetOtherimgActivity extends AppCompatActivity {
    TextView title;
    ImageView otherimg;
    Button prvimg, nxtimg;

    ArrayList<String> moreImgUrl = new ArrayList<>();
    ArrayList<Drawable> moreImg = new ArrayList<>();

    int thisimg=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreimg);

        title = findViewById(R.id.title);
        otherimg = findViewById(R.id.otherimg);
        prvimg = findViewById(R.id.prvimg);
        nxtimg = findViewById(R.id.nxtimg);

        Intent intent = getIntent();
        final String contId = intent.getExtras().getString("contId");
        final String conTitle = intent.getExtras().getString("conTitle");

        new Thread(new Runnable() {
            @Override
            public void run() {

                getMoreImage(contId);

                if (moreImgUrl.size() == 0) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "There is no image", Toast.LENGTH_SHORT).show();
                            otherimg.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.noimage));
                        }
                    });

                } else {

                    for (int i = 0; i < moreImgUrl.size(); i++) {
                        moreImg.add(getImageFromURL(moreImgUrl.get(i)));
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            title.setText(conTitle);
                            otherimg.setImageDrawable(moreImg.get(0));


                            prvimg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (thisimg > 0) {
                                        otherimg.setImageDrawable(moreImg.get(thisimg - 1));
                                        thisimg = thisimg - 1;
                                    } else {
                                        otherimg.setImageDrawable(moreImg.get(moreImgUrl.size() - 1));
                                        thisimg = moreImgUrl.size() - 1;
                                    }

                                }
                            });

                            nxtimg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (thisimg + 1 < moreImgUrl.size()) {
                                        otherimg.setImageDrawable(moreImg.get(thisimg + 1));
                                        thisimg = thisimg + 1;
                                    } else{
                                        otherimg.setImageDrawable(moreImg.get(0));
                                        thisimg = 0;
                                    }

                                }
                            });

                        }
                    });
                }
            }
        }).start();


    }

    void getMoreImage(String contId) {

        try {

            String requestUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage?contentId="+ contId
                    +"&imageYN=Y&MobileOS=ETC&MobileApp=Dahang&ServiceKey=gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
            URL url= new URL(requestUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

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
                        else if (tag.equals("originimgurl")) {
                            xpp.next();
                            moreImgUrl.add(xpp.getText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
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

package com.example.sns_project;

import android.support.v7.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDetailMethod extends AppCompatActivity {

    //홈페이지, 개요 파싱
    public String getDetailCommon(String contId) {
        StringBuffer buffer = new StringBuffer();
        buffer.setLength(0);
        String last = null;

        try {
            String requestUrl = "https://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?&contentId=" +
                    contId + "&defaultYN=Y&overviewYN=Y&MobileOS=ETC&MobileApp=Dahang&ServiceKey=gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";

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
                        else if (tag.equals("homepage")) {
                            xpp.next();
                            buffer.append("* 홈페이지: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("overview")) {
                            xpp.next();
                            buffer.append("* 개요: " + xpp.getText() + "\n\n");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType= xpp.next();
            }
            last = buffer.toString();
            last = last.replace("&nbsp;", " ").replace("&lt;", "")
                    .replace("&gt;", "").replace("<br />", "\n");
            last = last.replaceAll("<[^>]*>", "");

        } catch (Exception e) {
        }
        return last;
    }

    //관광지 세부정보
    public String getDetailIntro12(String contId) {
        StringBuffer buffer = new StringBuffer();
        buffer.setLength(0);
        String last = null;

        try {

            String requestUrl = "https://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?&contentId=" +
                    contId + "&contentTypeId=12&MobileOS=ETC&MobileApp=Dahang&ServiceKey=gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
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
                        if (tag.equals("item"));// 첫번째 검색결과

                            //contTypeId = 12 관광지일 때 파싱
                        else if (tag.equals("accomcount")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 수용인원: " + "no info" + "\n\n");
                            else
                                buffer.append("* 수용인원: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("chkbabycarriage")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 유모차 대여 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 유모차 대여 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("chkcreditcard")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 신용카드가능 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 신용카드가능 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("chkpet")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 애완동물동반가능 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 애완동물동반가능 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("expagerange")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 체험가능 연령: " + "no info" + "\n\n");
                            else
                                buffer.append("* 체험가능 연령: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("expguide")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 체험안내: " + "no info" + "\n\n");
                            else
                                buffer.append("* 체험안내: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("heritage1")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 세계 문화유산 유무: " + "no info" + "\n\n");
                            else
                                buffer.append("* 세계 문화유산 유무: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("heritage2")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 세계 자연유산 유무: " + "no info" + "\n\n");
                            else
                                buffer.append("* 세계 자연유산 유무: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("heritage3")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 세계 기록유산 유무: " + "no info" + "\n\n");
                            else
                                buffer.append("* 세계 기록유산 유무: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("infocenter")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 문의 및 안내: " + "no info" + "\n\n");
                            else
                                buffer.append("* 문의 및 안내: " + xpp.getText() + "\n\n");

                        } else if (tag.equals("opendate")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 개장일: " + "no info" + "\n\n");
                            else {
                                buffer.append("* 개장일: " + xpp.getText() + "\n\n");
                            }
                        } else if (tag.equals("parking")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 주차시설: " + "no info" + "\n\n");
                            else
                                buffer.append("* 주차시설: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("restdate")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 쉬는날: " + "no info" + "\n\n");
                            else
                                buffer.append("* 쉬는날: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("useseason")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 이용시기: " + "no info" + "\n\n");
                            else
                                buffer.append("* 이용시기: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("usetime")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 이용시간: " + "no info" + "\n\n");
                            else
                                buffer.append("* 이용시간: " + xpp.getText() + "\n\n");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType= xpp.next();
            }
            last = buffer.toString();
            last = last.replace("&nbsp;", " ").replace("&lt;", "")
                    .replace("&gt;", "").replace("<br />", "\n");
            last = last.replaceAll("<[^>]*>", "");
        } catch (Exception e) {
        }
        return last;
    }

    //문화시설 세부정보
    public String getDetailIntro14(String contId) {
        StringBuffer buffer = new StringBuffer();
        buffer.setLength(0);
        String last = null;

        try {

            String requestUrl = "https://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?&contentId=" +
                    contId + "&contentTypeId=14&MobileOS=ETC&MobileApp=Dahang&ServiceKey=gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
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
                        if (tag.equals("item"));// 첫번째 검색결과
                        else if (tag.equals("accomcountculture")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 수용인원: " + "no info" + "\n\n");
                            else
                                buffer.append("* 수용인원: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("chkbabycarriageculture")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 유모차대여 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 유모차대여 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("chkcreditcardculture")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 신용카드가능 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 신용카드가능 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("chkpetculture")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 애완동물동반가능 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 애완동물동반가능 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("discountinfo")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 할인정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 할인정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("infocenterculture")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 문의 및 안내: " + "no info" + "\n\n");
                            else
                                buffer.append("* 문의 및 안내: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("parkingculture")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 주차시설: " + "no info" + "\n\n");
                            else
                                buffer.append("* 주차시설: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("parkingfee")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 주차요금: " + "no info" + "\n\n");
                            else
                                buffer.append("* 주차요금: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("restdateculture")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 쉬는날: " + "no info" + "\n\n");
                            else
                                buffer.append("* 쉬는날: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("usefee")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 이용요금: " + "no info" + "\n\n");
                            else
                                buffer.append("* 이용요금: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("usetimeculture")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 이용시간: " + "no info" + "\n\n");
                            else
                                buffer.append("* 이용시간: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("scale")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 규모: " + "no info" + "\n\n");
                            else
                                buffer.append("* 규모: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("spendtime")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 관람 소요시간: " + "no info" + "\n\n");
                            else
                                buffer.append("* 관람 소요시간: " + xpp.getText() + "\n\n");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType= xpp.next();
            }
            last = buffer.toString();
            last = last.replace("&nbsp;", " ").replace("&lt;", "")
                    .replace("&gt;", "").replace("<br />", "\n");
            last = last.replaceAll("<[^>]*>", "");
        } catch (Exception e) {
        }
        return last;
    }

    //행사/공연/축제 세부정보
    public String getDetailIntro15(String contId) {
        StringBuffer buffer = new StringBuffer();
        buffer.setLength(0);
        String last = null;

        try {

            String requestUrl = "https://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?&contentId=" +
                    contId + "&contentTypeId=15&MobileOS=ETC&MobileApp=Dahang&ServiceKey=gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
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
                        if (tag.equals("item"));// 첫번째 검색결과
                        else if (tag.equals("agelimit")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 관람 가능연령: " + "no info" + "\n\n");
                            else
                                buffer.append("* 관람 가능연령: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("bookingplace")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 예매처: " + "no info" + "\n\n");
                            else
                                buffer.append("* 예매처: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("discountinfofestival")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 할인정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 할인정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("eventenddate")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 행사 종료일: " + "no info" + "\n\n");
                            else {
                                buffer.append("* 행사 종료일: " + xpp.getText() + "\n\n");
                            }
                        } else if (tag.equals("eventhomepage")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 행사 홈페이지: " + "no info" + "\n\n");
                            else
                                buffer.append("* 행사 홈페이지: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("eventplace")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 행사 장소: " + "no info" + "\n\n");
                            else
                                buffer.append("* 행사 장소: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("eventstartdate")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 행사 시작일: " + "no info" + "\n\n");
                            else {
                                buffer.append("* 행사 시작일: " + xpp.getText() + "\n\n");
                            }
                        } else if (tag.equals("festivalgrade")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 축제등급: " + "no info" + "\n\n");
                            else
                                buffer.append("* 축제등급: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("placeinfo")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 행사장 위치안내: " + "no info" + "\n\n");
                            else
                                buffer.append("* 행사장 위치안내: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("playtime")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 공연시간: " + "no info" + "\n\n");
                            else
                                buffer.append("* 공연시간: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("program")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 행사 프로그램: " + "no info" + "\n\n");
                            else
                                buffer.append("* 행사 프로그램: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("spendtimefestival")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 관람 소요시간: " + "no info" + "\n\n");
                            else
                                buffer.append("* 관람 소요시간: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("sponsor1")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 주최자 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 주최자 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("sponsor1tel")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 주최자 연락처: " + "no info" + "\n\n");
                            else
                                buffer.append("* 주최자 연락처: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("sponsor2")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 주관사 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 주관사 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("sponsor2tel")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 주관사 연락처: " + "no info" + "\n\n");
                            else
                                buffer.append("* 주관사 연락처: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("subevent")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 부대행사: " + "no info" + "\n\n");
                            else
                                buffer.append("* 부대행사: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("usetimefestival")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 이용요금: " + "no info" + "\n\n");
                            else
                                buffer.append("* 이용요금: " + xpp.getText() + "\n\n");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType= xpp.next();
            }
            last = buffer.toString();
            last = last.replace("&nbsp;", " ").replace("&lt;", "")
                    .replace("&gt;", "").replace("<br />", "\n");
            last = last.replaceAll("<[^>]*>", "");
        } catch (Exception e) {
        }
        return last;
    }

    //레포츠 세부정보
    public String getDetailIntro28(String contId) {
        StringBuffer buffer = new StringBuffer();
        buffer.setLength(0);
        String last = null;

        try {

            String requestUrl = "https://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?&contentId=" +
                    contId + "&contentTypeId=28&MobileOS=ETC&MobileApp=Dahang&ServiceKey=gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
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
                        if (tag.equals("item"));// 첫번째 검색결과
                        else if (tag.equals("accomcountleports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 수용인원: " + "no info" + "\n\n");
                            else
                                buffer.append("* 수용인원: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("chkbabycarriageleports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 유모차대여 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 유모차대여 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("chkcreditcardleports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 신용카드가능 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 신용카드가능 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("chkpetleports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 애완동물동반가능 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 애완동물동반가능 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("expagerangeleports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 체험 가능연령: " + "no info" + "\n\n");
                            else
                                buffer.append("* 체험 가능연령: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("infocenterleports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 문의 및 안내: " + "no info" + "\n\n");
                            else
                                buffer.append("* 문의 및 안내: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("openperiod")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 개장기간: " + "no info" + "\n\n");
                            else
                                buffer.append("* 개장기간: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("parkingfeeleports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 주차요금: " + "no info" + "\n\n");
                            else
                                buffer.append("* 주차요금: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("parkingleports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 주차시설: " + "no info" + "\n\n");
                            else
                                buffer.append("* 주차시설: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("reservation")) {
                            xpp.next();
                            String hPage = null;
                            if(xpp.getText() == null)
                                buffer.append("* 예약안내: " + "no info" + "\n\n");
                            else
                                buffer.append("* 예약안내: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("restdateleports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 쉬는날: " + "no info" + "\n\n");
                            else
                                buffer.append("* 쉬는날: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("scaleleports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 규모: " + "no info" + "\n\n");
                            else
                                buffer.append("* 규모: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("usefeeleports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 입장료: " + "no info" + "\n\n");
                            else
                                buffer.append("* 입장료: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("usetimeleports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 이용시간: " + "no info" + "\n\n");
                            else
                                buffer.append("* 이용시간: " + xpp.getText() + "\n\n");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType= xpp.next();
            }
            last = buffer.toString();
            last = last.replace("&nbsp;", " ").replace("&lt;", "")
                    .replace("&gt;", "").replace("<br />", "\n");
            last = last.replaceAll("<[^>]*>", "");
        } catch (Exception e) {
        }
        return last;
    }

    //숙박 세부정보
    public String getDetailIntro32(String contId) {
        StringBuffer buffer = new StringBuffer();
        buffer.setLength(0);
        String last = null;

        try {

            String requestUrl = "https://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?&contentId=" +
                    contId + "&contentTypeId=32&MobileOS=ETC&MobileApp=Dahang&ServiceKey=gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
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
                        if (tag.equals("item"));// 첫번째 검색결과
                        else if (tag.equals("accomcountlodging")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 수용 가능인원: " + "no info" + "\n\n");
                            else
                                buffer.append("* 수용 가능인원: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("benikia")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 베니키아 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 베니키아 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("checkintime")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 입실 시간: " + "no info" + "\n\n");
                            else
                                buffer.append("* 입실 시간: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("checkouttime")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 퇴실 시간: " + "no info" + "\n\n");
                            else
                                buffer.append("* 퇴실 시간: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("chkcooking")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 객실내 취사 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 객실내 취사 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("foodplace")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 식음료장: " + "no info" + "\n\n");
                            else
                                buffer.append("* 식음료장: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("goodstay")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 굿스테이 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 굿스테이 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("hanok")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 한옥 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 한옥 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("infocenterlodging")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 문의 및 안내: " + "no info" + "\n\n");
                            else
                                buffer.append("* 문의 및 안내: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("parkinglodging")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 주차시설: " + "no info" + "\n\n");
                            else
                                buffer.append("* 주차시설: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("pickup")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 픽업 서비스: " + "no info" + "\n\n");
                            else
                                buffer.append("* 픽업 서비스: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("roomcount")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 객실수: " + "no info" + "\n\n");
                            else
                                buffer.append("* 객실수: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("reservationlodging")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 예약안내: " + "no info" + "\n\n");
                            else
                                buffer.append("* 예약안내: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("reservationurl")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 예약안내 홈페이지: " + "no info" + "\n\n");
                            else
                                buffer.append("* 예약안내 홈페이지: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("roomtype")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 객실유형: " + "no info" + "\n\n");
                            else
                                buffer.append("* 객실유형: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("scalelodging")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 규모: " + "no info" + "\n\n");
                            else
                                buffer.append("* 규모: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("subfacility")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 부대시설: " + "no info" + "\n\n");
                            else
                                buffer.append("* 부대시설: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("barbecue")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 바비큐장 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 바비큐장 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("beauty")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 뷰티시설 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 뷰티시설 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("beverage")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 식음료장 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 식음료장 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("bicycle")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 자전거 대여 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 자전거 대여 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("campfire")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 캠프파이어 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 캠프파이어 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("fitness")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 휘트니스 센터 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 휘트니스 센터 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("karaoke")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 노래방 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 노래방 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("publicbath")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 공용 샤워실 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 공용 샤워실 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("publicpc")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 공용 PC실 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 공용 PC실 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("sauna")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 사우나실 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 사우나실 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("seminar")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 세미나실 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 세미나실 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("sports")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 스포츠 시설 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 스포츠 시설 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("refundregulation")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 환불규정: " + "no info" + "\n\n");
                            else
                                buffer.append("* 환불규정: " + xpp.getText() + "\n\n");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType= xpp.next();
            }
            last = buffer.toString();
            last = last.replace("&nbsp;", " ").replace("&lt;", "")
                    .replace("&gt;", "").replace("<br />", "\n");
            last = last.replaceAll("<[^>]*>", "");
        } catch (Exception e) {
        }
        return last;
    }

    //쇼핑 세부정보
    public String getDetailIntro38(String contId) {
        StringBuffer buffer = new StringBuffer();
        buffer.setLength(0);
        String last = null;

        try {

            String requestUrl = "https://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?&contentId=" +
                    contId + "&contentTypeId=38&MobileOS=ETC&MobileApp=Dahang&ServiceKey=gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
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
                        if (tag.equals("item"));// 첫번째 검색결과
                        else if (tag.equals("chkbabycarriageshopping")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 유모차대여 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 유모차대여 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("chkcreditcardshopping")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 신용카드가능 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 신용카드가능 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("chkpetshopping")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 애완동물동반가능 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 애완동물동반가능 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("culturecenter")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 문화센터 바로가기: " + "no info" + "\n\n");
                            else
                                buffer.append("* 문화센터 바로가기: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("fairday")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 장서는 날: " + "no info" + "\n\n");
                            else
                                buffer.append("* 장서는 날: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("infocentershopping")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 문의: " + "no info" + "\n\n");
                            else
                                buffer.append("* 문의: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("opendateshopping")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 개장일: " + "no info" + "\n\n");
                            else
                                buffer.append("* 개장일: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("opentime")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 영업시간: " + "no info" + "\n\n");
                            else
                                buffer.append("* 영업시간: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("parkingshopping")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 주차시설: " + "no info" + "\n\n");
                            else
                                buffer.append("* 주차시설: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("restdateshopping")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 쉬는날: " + "no info" + "\n\n");
                            else
                                buffer.append("* 쉬는날: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("restroom")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 화장실 설명: " + "no info" + "\n\n");
                            else
                                buffer.append("* 화장실 설명: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("saleitem")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 판매 품목: " + "no info" + "\n\n");
                            else
                                buffer.append("* 판매 품목: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("saleitemcost")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 판매 품목별 가격: " + "no info" + "\n\n");
                            else
                                buffer.append("* 판매 품목별 가격: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("scaleshopping")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 규모: " + "no info" + "\n\n");
                            else
                                buffer.append("* 규모: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("shopguide")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 매장안내: " + "no info" + "\n\n");
                            else
                                buffer.append("* 매장안내: " + xpp.getText() + "\n\n");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType= xpp.next();
            }
            last = buffer.toString();
            last = last.replace("&nbsp;", " ").replace("&lt;", "")
                    .replace("&gt;", "").replace("<br />", "\n");
            last = last.replaceAll("<[^>]*>", "");
        } catch (Exception e) {
        }
        return last;
    }

    //음식점 세부정보
    public String getDetailIntro39(String contId) {
        StringBuffer buffer = new StringBuffer();
        buffer.setLength(0);
        String last = null;

        try {

            String requestUrl = "https://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?&contentId=" +
                    contId + "&contentTypeId=39&MobileOS=ETC&MobileApp=Dahang&ServiceKey=gO2Eoip9hdgf3exFNc8KHuUOmZSRUctee1jwg4WNu5pvDzifwOM4BWJbdZIzn6A8w3uPYJIX0sAGnqeyw6PSeA%3D%3D";
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
                        if (tag.equals("item"));// 첫번째 검색결과
                        else if (tag.equals("chkcreditcardfood")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 신용카드가능 정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 신용카드가능 정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("discountinfofood")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 할인정보: " + "no info" + "\n\n");
                            else
                                buffer.append("* 할인정보: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("firstmenu")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 대표 메뉴: " + "no info" + "\n\n");
                            else
                                buffer.append("* 대표 메뉴: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("infocenterfood")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 문의 및 안내: " + "no info" + "\n\n");
                            else
                                buffer.append("* 문의 및 안내: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("kidsfacility")) {
                            xpp.next();if(xpp.getText() == null)
                                buffer.append("* 어린이 놀이방 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 어린이 놀이방 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("opendatefood")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 개업일: " + "no info" + "\n\n");
                            else {
                                xpp.next();
                                SimpleDateFormat before = new SimpleDateFormat("yyyyMMddHHmmss");
                                SimpleDateFormat after = new SimpleDateFormat("yyyy년 MM월 dd일  HH:mm:ss");
                                Date tempDate;
                                tempDate = before.parse(xpp.getText());
                                String transDate = after.format(tempDate);
                                buffer.append("* 개업일: " + transDate + "\n\n");
                            }
                        } else if (tag.equals("opentimefood")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 영업시간: " + "no info" + "\n\n");
                            else
                                buffer.append("* 영업시간: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("packing")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 포장 가능: " + "no info" + "\n\n");
                            else
                                buffer.append("* 포장 가능: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("parkingfood")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 주차시설: " + "no info" + "\n\n");
                            else
                                buffer.append("* 주차시설: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("reservationfood")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 예약안내: " + "no info" + "\n\n");
                            else
                                buffer.append("* 예약안내: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("restdatefood")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 쉬는날: " + "no info" + "\n\n");
                            else
                                buffer.append("* 쉬는날: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("scalefood")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 규모: " + "no info" + "\n\n");
                            else
                                buffer.append("* 규모: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("seat")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 좌석수: " + "no info" + "\n\n");
                            else
                                buffer.append("* 좌석수: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("smoking")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 금연/흡연 여부: " + "no info" + "\n\n");
                            else
                                buffer.append("* 금연/흡연 여부: " + xpp.getText() + "\n\n");
                        } else if (tag.equals("treatmenu")) {
                            xpp.next();
                            if(xpp.getText() == null)
                                buffer.append("* 취급 메뉴: " + "no info" + "\n\n");
                            else
                                buffer.append("* 취급 메뉴: " + xpp.getText() + "\n\n");
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType= xpp.next();
            }
            last = buffer.toString();
            last = last.replace("&nbsp;", " ").replace("&lt;", "")
                    .replace("&gt;", "").replace("<br />", "\n");
            last = last.replaceAll("<[^>]*>", "");
        } catch (Exception e) {
        }
        return last;
    }

}

package com.example.sns_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.example.sns_project.GetDetailMethod;
import com.example.sns_project.R;


public class DetailActivity extends AppCompatActivity {
    TextView title, detail;

    com.example.sns_project.GetDetailMethod getmtd = new GetDetailMethod();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);//

        title = (TextView) findViewById(R.id.title);
        detail = (TextView) findViewById(R.id.detail);
        detail.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        final String contId = intent.getExtras().getString("contId");
        final String conTitle = intent.getExtras().getString("conTitle");
        final String contypeid = intent.getExtras().getString("contypeid");
        Log.d("contypeid", contypeid);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuffer buffer = new StringBuffer();
                switch (contypeid) {
                    case "12":
                        buffer.append(getmtd.getDetailCommon(contId));
                        buffer.append(getmtd.getDetailIntro12(contId));
                        break;
                    case "14":
                        buffer.append(getmtd.getDetailCommon(contId));
                        buffer.append(getmtd.getDetailIntro14(contId));
                        break;
                    case "15":
                        buffer.append(getmtd.getDetailCommon(contId));
                        buffer.append(getmtd.getDetailIntro15(contId));
                        break;
                    case "28":
                        buffer.append(getmtd.getDetailCommon(contId));
                        buffer.append(getmtd.getDetailIntro28(contId));
                        break;
                    case "32":
                        buffer.append(getmtd.getDetailCommon(contId));
                        buffer.append(getmtd.getDetailIntro32(contId));
                        break;
                    case "38":
                        buffer.append(getmtd.getDetailCommon(contId));
                        buffer.append(getmtd.getDetailIntro38(contId));
                        break;
                    case "39":
                        buffer.append(getmtd.getDetailCommon(contId));
                        buffer.append(getmtd.getDetailIntro39(contId));
                        break;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        title.setText(conTitle);
                        detail.setText(buffer.toString());
                    }
                });
            }
        }).start();



    }
}

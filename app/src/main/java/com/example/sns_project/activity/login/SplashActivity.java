package com.example.sns_project.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.example.sns_project.R;
import com.example.sns_project.activity.BasicActivity;
import com.facebook.login.Login;

public class SplashActivity extends BasicActivity {

    Handler handler = new Handler();
    Runnable runnable = () -> {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 상태바 삭제
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(runnable, 2000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);

    }
}

package com.example.sns_project.activity.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sns_project.R;
import com.example.sns_project.activity.BasicActivity;

public class BoardSelectActivity extends BasicActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_select);


        findViewById(R.id.ll_guide).setOnClickListener(onClickListener); //로그인 버튼이 클릭된 경우
        findViewById(R.id.ll_tourlist).setOnClickListener(onClickListener); //비밀번호 초기화 버튼

    }

    //버튼별 클릭 메소드
    View.OnClickListener onClickListener = v -> {
        switch (v.getId()){
            case R.id.ll_guide:
                myStartActivity(GuideBoardActivity.class);
                break;
            case R.id.ll_tourlist:
                myStartActivity(TourlistBoardActivity.class);
                break;
        }
    };

    private void myStartActivity(Class c){ //원하는 Activity로 이동시켜주는 메소드
        Intent intent = new Intent(this, c);
        intent.putExtra("contentId", "default");
        intent.putExtra("contentTitle", "전체 게시판");
        startActivity(intent);
    }
}

package com.example.sns_project.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sns_project.R;
import com.example.sns_project.activity.BasicActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import static com.example.sns_project.Util.SYSTMEM_LOG;

public class NameActivity extends BasicActivity {
    private FirebaseUser user; //Firebase 사용자 정보를 담을 변수
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        user = FirebaseAuth.getInstance().getCurrentUser(); //사용자가 로그인 되어있는지 확인
        TextView tv_name_result = findViewById(R.id.tv_name_result);
        firebaseFirestore = FirebaseFirestore.getInstance(); //firestore 초기화(DataBase)
        DocumentReference docRef = firebaseFirestore.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(task -> {//
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    tv_name_result.setText(document.getData().get("name").toString());

                } else {
                    Log.d(SYSTMEM_LOG, "No such document");
                }
            } else {
                Log.d(SYSTMEM_LOG, "get failed with ", task.getException());
            }
        });
        findViewById(R.id.bt_name_submit).setOnClickListener(onClickListener); //로그인 버튼이 클릭된 경우
    }

    View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.bt_name_submit:
                myStartActivity(MainActivity.class);
                break;
        }
    };

    private void myStartActivity(Class c) { //원하는 Activity로 이동시켜주는 메소드
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}

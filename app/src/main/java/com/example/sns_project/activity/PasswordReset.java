package com.example.sns_project.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sns_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.sns_project.Util.showToast;

public class PasswordReset extends BasicActivity {
    private FirebaseAuth mAuth; //FirebaseAuth 인스턴스 선언

    EditText send_email;
    Button btn_reset_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        mAuth = FirebaseAuth.getInstance(); //FirebaseAuth 인스턴스 초기화

        send_email = findViewById(R.id.et_address); // 초기화 비밀번호를 입력받을 email 텍스트
        btn_reset_password = findViewById(R.id.btn_reset_password); // 비밀번호 초기화 버튼

        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = send_email.getText().toString(); // 입력받은 이메일에 대해서 문자열로 변환

                if (email.equals("")){ // 이메일 입력란에 아무것도 입력하지 않았을 시
                    Toast.makeText(PasswordReset.this,"이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PasswordReset.this, "입력하신 이메일을 확인해주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PasswordReset.this, "이메일의 형식이 잘못되었으니 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
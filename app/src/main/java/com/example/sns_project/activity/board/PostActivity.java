package com.example.sns_project.activity.board;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sns_project.Notifications.Client;
import com.example.sns_project.Notifications.Data;
import com.example.sns_project.Notifications.MyResponse;
import com.example.sns_project.Notifications.Sender;
import com.example.sns_project.Notifications.Token;
import com.example.sns_project.activity.BasicActivity;
import com.example.sns_project.fragment.APIService;
import com.example.sns_project.info.PostInfo;
import com.example.sns_project.R;
import com.example.sns_project.info.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sns_project.Util.SYSTMEM_LOG;
import static com.example.sns_project.Util.isStorageUrl;

public class PostActivity extends BasicActivity {
    private FirebaseUser fuser;
    private FirebaseFirestore firebaseFirestore;

    /****************/
    private ImageButton btn_chat;
    String userid;
    Intent intent;
    boolean notify = false;
    APIService apiService;
    TextView message_timestamp;
    private String name;
    /****************/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//aa
        setContentView(R.layout.activity_post);

        PostInfo postInfo = (PostInfo) getIntent().getSerializableExtra("postInfo");
        TextView titleTextView = findViewById(R.id.tv_title);
        TextView guidePay = findViewById(R.id.tv_guide_pay);
        TextView guideHour = findViewById(R.id.tv_guide_hour);
        TextView publisher = findViewById(R.id.tv_post_publisher);

        /****************/
        btn_chat = findViewById(R.id.btn_board_chat); // chat 으로 연결 버튼
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);//msg 보내기
        message_timestamp = findViewById(R.id.message_timestamp);
        /****************/

        titleTextView.setText(postInfo.getTitle());
        guidePay.setText("시간당 " + postInfo.getGuidePay() + "(원)");
        guideHour.setText("가능한 시간: " + postInfo.getGuideHour());

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance(); //firestore 초기화(DataBase)
        DocumentReference docRef = firebaseFirestore.collection("users").document(postInfo.getPublisher());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    publisher.setText(document.getData().get("name").toString());
                    name = document.getData().get("name").toString();
                } else {
                    Log.d(SYSTMEM_LOG, "No such document");
                }
            } else {
                Log.d(SYSTMEM_LOG, "get failed with ", task.getException());
            }
        });

        TextView createdAtTextView = findViewById(R.id.tv_createdAt);
        createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(postInfo.getCreatedAt()));

        LinearLayout contentsLayout = findViewById(R.id.ll_contents);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ArrayList<String> contentsList = postInfo.getContents();

        if (contentsLayout.getTag() == null || !contentsLayout.getTag().equals(contentsList)) {
            contentsLayout.setTag(contentsList);
            contentsLayout.removeAllViews();
            for (int i = 0; i < contentsList.size(); i++) {
                String contents = contentsList.get(i);
                if (isStorageUrl(contents)) {
                    ImageView imageView = new ImageView(this);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    contentsLayout.addView(imageView);
                    Glide.with(this).load(contents).override(1000).thumbnail(0.1f).into(imageView);
                } else {
                    TextView textView = new TextView(this);
                    textView.setLayoutParams(layoutParams);
                    textView.setText(contents);
                    textView.setTextColor(Color.rgb(0, 0, 0));
                    contentsLayout.addView(textView);
                }
            }
        }

        /****************/
        intent = getIntent();
        userid = postInfo.getPublisher();
        publisher.setText(name);
        final String msg = "글 제목: "+postInfo.getTitle()+", 글 작성자: "+name+", 글 작성시간: "+postInfo.getGuideHour()+", 글 금액: "+postInfo.getGuidePay();

        btn_chat.setOnClickListener(v -> { // 채팅버튼을 눌러서 채팅으로 바로 이동하기
            Toast.makeText(this, "메세지를 보냈습니다.", Toast.LENGTH_SHORT).show();
            sendMessage(fuser.getUid(), userid, msg, message_timestamp);
        });
        /****************/
    }

    /****************/
    private void sendMessage(String sender, String receiver, String message, Object timestamp) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        timestamp = ServerValue.TIMESTAMP;

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);
        hashMap.put("timestamp", timestamp);

        reference.child("Chats").push().setValue(hashMap);

        // add user to chat fragment
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(fuser.getUid()) // 발신자
                .child(userid); // 수신자

        final DatabaseReference chatRef2 = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userid) // 수신자
                .child(fuser.getUid()); // 발신자

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    chatRef.child("id").setValue(userid);
                    chatRef2.child("id").setValue(fuser.getUid());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final String msg = message;

        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (notify) {
                    sendNotification(receiver, user.getUsername(), msg);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendNotification(String receiver, final String username, final String message){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser.getUid(), R.mipmap.ic_launcher, username+": "+message,
                            "다행(Message)", userid);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200){
                                        if (response.body().success != 1){
                                            //Toast.makeText(MessageActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    /****************/
}
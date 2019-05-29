package com.example.sns_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.sns_project.R;
import com.example.sns_project.activity.tour_api.DetailActivity;
import com.example.sns_project.activity.tour_api.GetOtherimgActivity;
import com.example.sns_project.activity.tour_api.MapMarkerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.example.sns_project.Util.BOARD_GUIDE;
import static com.example.sns_project.Util.BOARD_TOURLIST;
import static com.example.sns_project.Util.SYSTMEM_LOG;

public class ListAdapter extends BaseAdapter {
    private FirebaseUser user;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private String userType;
    private ArrayList<TourlistItem> TourlistItemList = new ArrayList<TourlistItem>();

    public ListAdapter() {

    }

    @Override
    public int getCount() {
        return TourlistItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tourlist_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView tourimage = (ImageView) convertView.findViewById(R.id.tourimage);
        TextView tourcontent = (TextView) convertView.findViewById(R.id.tourcontent);
        TextView contitle = (TextView) convertView.findViewById(R.id.contitle);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final TourlistItem listViewItem = TourlistItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tourimage.setImageDrawable(listViewItem.getimage());
        contitle.setText(listViewItem.getContitle());
        tourcontent.setText(listViewItem.getTourcontent());

        //상세정보 버튼
        Button detail = (Button) convertView.findViewById(R.id.detail);
        detail.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("contId", listViewItem.getContId());
            i.putExtra("conTitle", listViewItem.getContitle());
            i.putExtra("contypeid", listViewItem.getContypeid());

            context.startActivity(i);
        });

        //다른 사진 버튼
        Button moreimg = (Button) convertView.findViewById(R.id.moreimg);
        moreimg.setOnClickListener(v -> {
            Intent i = new Intent(context, GetOtherimgActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("contId", listViewItem.getContId());
            i.putExtra("conTitle", listViewItem.getContitle());

            context.startActivity(i);
        });

        Button onMap = (Button) convertView.findViewById(R.id.onMap);
        onMap.setOnClickListener(v -> {
            Intent i = new Intent(context, MapMarkerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("conTitle", listViewItem.getContitle());
            i.putExtra("addr", listViewItem.getAddr());
            i.putExtra("mapx", listViewItem.getV());
            i.putExtra("mapy", listViewItem.getV1());

            context.startActivity(i);
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance(); //firestore 초기화(DataBase)
        DocumentReference docRef = firebaseFirestore.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                  userType = document.getData().get("tour").toString();
                } else {
                    Log.d(SYSTMEM_LOG, "No such document");
                }
            } else {
                Log.d(SYSTMEM_LOG, "get failed with ", task.getException());
            }
        });

        Button goBoard = (Button) convertView.findViewById(R.id.select);
        goBoard.setOnClickListener(v -> {
            Intent intent;
          if(userType.equals("tourlist")){
              intent = new Intent(context, com.example.sns_project.activity.board.TourlistBoardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              intent.putExtra("contentId", listViewItem.getContId());
              intent.putExtra("contentTitle", listViewItem.getContitle());
              context.startActivity(intent);
          }else{
              intent = new Intent(context, com.example.sns_project.activity.board.GuideBoardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              intent.putExtra("contentId", listViewItem.getContId());
              intent.putExtra("contentTitle", listViewItem.getContitle());
              Log.e("abc", listViewItem.getContitle());
              context.startActivity(intent);
          }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return TourlistItemList.get(position) ;
    }

    public void addItem(Drawable img, String title, String con, String id, Double mapx, Double mapy, String contyp, String ad) {
        TourlistItem item = new TourlistItem();

        item.setimage(img);
        item.setTitle(title);
        item.setTourcontent(con);
        item.setContId(id);
        item.setV(mapx);
        item.setV1(mapy);
        item.setContypeid(contyp);
        item.setAddr(ad);

        TourlistItemList.add(item);
    }
}

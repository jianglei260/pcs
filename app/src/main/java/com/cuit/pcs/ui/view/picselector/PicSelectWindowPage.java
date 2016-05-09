package com.cuit.pcs.ui.view.picselector;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cuit.pcs.R;
import com.cuit.pcs.application.Config;
import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-1 on 2015/8/9.
 */
public class PicSelectWindowPage extends WindowPage {
    private Context context;
    private LinearLayout rootView;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<String> piclist;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Config.PICTURE_SCAN_FINISH) {
                updateView((List<String>) msg.obj);
            }
        }
    };

    public PicSelectWindowPage(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    private void initUI() {
        rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.pic_select_view, null);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.id_pic_recyclerview);

        gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);


        addBodyView(rootView);
        piclist = new ArrayList<>();
        titleBar.setTitleText("本机图片");
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putBackObject(piclist);
                FastWindow.getInstance().goBack();
            }
        });


        findPicture();


    }

    public void updateView(final List<String> list) {
        PicSelecctViewAdapter adapter = new PicSelecctViewAdapter(list);
        adapter.setOnItemClickListener(new PicSelecctViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (piclist.contains(list.get(position)))
                    piclist.remove(list.get(position));
                else
                    piclist.add(list.get(position));

            }
        });
        recyclerView.setAdapter(adapter);
    }


    private void findPicture() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<>();
                Uri picUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver contentResolver = MyApplication.getInstance().getMainActivity().getContentResolver();
                Cursor cursor = contentResolver.query(picUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    list.add(path);
                }
                cursor.close();
                handler.sendMessage(handler.obtainMessage(Config.PICTURE_SCAN_FINISH, list));
            }
        });
        thread.start();
    }

    @Override
    public void onBack() {
        super.onBack();
        putBackObject(piclist);
    }
}

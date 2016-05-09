package com.cuit.remind;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuit.pcs.application.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ASUS-1 on 2015/9/13.
 */
public class RemindAdapter extends RecyclerView.Adapter {

    private List<RemindInfo> list;
    private LayoutInflater inflater;

    public RemindAdapter(List<RemindInfo> list) {
        this.list = list;
        inflater = LayoutInflater.from(MyApplication.getInstance());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.remind_item_view, null);
        RemindViewHolder remindViewHolder = new RemindViewHolder(view);
        return remindViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RemindViewHolder remindViewHolder = (RemindViewHolder) holder;
        remindViewHolder.nameText.setText(list.get(position).getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(list.get(position).getTime());
        remindViewHolder.timeText.setText(simpleDateFormat.format(date));
        remindViewHolder.stateText.setText(list.get(position).getState());
        remindViewHolder.imageView.setImageResource(R.mipmap.ic_remind_blue);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

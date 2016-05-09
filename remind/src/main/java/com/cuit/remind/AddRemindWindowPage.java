package com.cuit.remind;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ASUS-1 on 2015/9/13.
 */
public class AddRemindWindowPage extends WindowPage implements View.OnClickListener {

    private AppContext context;
    private LinearLayout rootView;
    private EditText titleEditText;
    private TextView timeEditText;
    private Button setButton;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int year, month, day, hour, min;
    private RemindUtils remindUtils;
    private Calendar timeCalendar = Calendar.getInstance();
    private App app;

    public AddRemindWindowPage(App app, AppContext context) {
        super(app.getMainActivity());
        this.context = context;
        this.app = app;
        initUI();
    }

    private void initUI() {
        rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.remind_add_view, null);
        titleEditText = (EditText) rootView.findViewById(R.id.id_addremind_title);
        timeEditText = (TextView) rootView.findViewById(R.id.id_addremind_time);
        setButton = (Button) rootView.findViewById(R.id.id_addremind_add);

        titleBar.setTitleText("添加提醒");
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.goBack();
            }
        });
        addBodyView(rootView);
        remindUtils = new RemindUtils(context);
        timeEditText.setOnClickListener(this);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemindInfo remindInfo = new RemindInfo();
                if (titleEditText.getText().toString().length() <= 0) {
                    Toast.makeText(context, "标题为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (timeCalendar.getTimeInMillis() <= System.currentTimeMillis()) {
                    Toast.makeText(context, "未设置时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                remindInfo.setName(titleEditText.getText().toString());
                remindInfo.setTime(timeCalendar.getTimeInMillis());
                remindInfo.setState("已设置");
                remindUtils.setRemind(remindInfo);
                putBackObject(remindInfo);
                Toast.makeText(context, "提醒设置成功", Toast.LENGTH_SHORT).show();
                FastWindow.getInstance().goBack();
            }
        });
    }

    @Override
    public void onClick(View v) {
        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(app.getMainActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AddRemindWindowPage.this.year = year;
                AddRemindWindowPage.this.month = monthOfYear;
                AddRemindWindowPage.this.day = dayOfMonth;
                datePickerDialog.dismiss();
                timePickerDialog.show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Calendar.DAY_OF_MONTH);

        timePickerDialog = new TimePickerDialog(app.getMainActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                AddRemindWindowPage.this.hour = hourOfDay;
                AddRemindWindowPage.this.min = minute;
                timePickerDialog.dismiss();
                timeCalendar.set(year, month, day, hour, min);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(timeCalendar.getTimeInMillis());
                timeEditText.setText(simpleDateFormat.format(date));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

        datePickerDialog.show();
    }
}
package com.cuit.clean;

import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuit.clean.widget.RoundProgressBar;
import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.sys.window.WindowPage;


/**
 * Created by ASUS-1 on 2015/9/11.
 */
public class CleanWindowPage extends WindowPage {
    private AppContext context;
    private LinearLayout rootView;
    private RoundProgressBar roundProgressBar;
    private Button cleanButton;
    private MemoryUtils memoryUtils;
    private App app;

    public CleanWindowPage(App app, AppContext context) {
        super(app.getMainActivity());
        this.context = context;
        this.app = app;
        memoryUtils = new MemoryUtils(context);
    }

    public void initUI() {
        LinearLayout rootView = new LinearLayout(context);
        rootView.setGravity(Gravity.CENTER);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setBackgroundColor(Color.parseColor("#FF18B4ED"));
        roundProgressBar = new RoundProgressBar(context);
        roundProgressBar.setCricleColor(Color.parseColor("#FF046090"));
        roundProgressBar.setCricleProgressColor(Color.WHITE);
        roundProgressBar.setMax((int) memoryUtils.getTotalMemory());
        roundProgressBar.setProgress((int) memoryUtils.getAvilableMemory());
        roundProgressBar.setRoundWidth(40);
        roundProgressBar.setTextColor(Color.WHITE);

        LayoutParams roundBarLayoutParams = new LayoutParams(480, 480);
        rootView.addView(roundProgressBar, roundBarLayoutParams);

        cleanButton = new Button(context);
        cleanButton.setText("一键清理");
        cleanButton.setTextColor(Color.WHITE);
        cleanButton.setBackgroundColor(Color.parseColor("#FF37BC84"));
        LayoutParams cleanButtonLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cleanButtonLayoutParams.rightMargin = cleanButtonLayoutParams.leftMargin = 240;
        cleanButtonLayoutParams.topMargin = 240;
        rootView.addView(cleanButton, cleanButtonLayoutParams);
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.goBack();
            }
        });
        titleBar.setTitleText("内存清理");
        TextView textView = new TextView(context);
        textView.setText(context.getText(R.string.app_name));
        addBodyView(rootView);

        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoryUtils.cleanMemory();
            }
        });
        memoryUtils.setProgressCallBack(new MemoryUtils.ProgressCallBack() {
            @Override
            public void onProgress(long avilableMemory) {
                roundProgressBar.setProgress((int) avilableMemory);
            }
        });
    }
}

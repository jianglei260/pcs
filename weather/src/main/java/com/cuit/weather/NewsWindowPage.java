package com.cuit.weather;

import android.content.Context;
import android.view.View;

import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.sys.user.User;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.ui.view.login.LoginWindowPage;
import com.cuit.pcs.webloader.WebLoader;

/**
 * Created by ASUS-1 on 2015/9/12.
 */
public class NewsWindowPage extends WindowPage {
    private AppContext context;
    private WebLoader webLoader;
    private App app;

    public NewsWindowPage(App app, AppContext context) {
        super(app.getMainActivity());
        this.context = context;
        this.app = app;
    }

    public NewsWindowPage initUI() {
        webLoader = new WebLoader(context);
        titleBar.setTitleText("新闻头条");
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.goBack();
            }
        });
        addBodyView(webLoader.load("http://pcs.avosapps.com/news/"));
        return this;
    }
}

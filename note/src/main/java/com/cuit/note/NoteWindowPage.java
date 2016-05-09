package com.cuit.note;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.sys.user.User;
import com.cuit.pcs.sys.user.UserInfo;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.ui.view.login.LoginWindowPage;
import com.cuit.pcs.webloader.WebLoader;

/**
 * Created by ASUS-1 on 2015/9/12.
 */
public class NoteWindowPage extends WindowPage {
    private AppContext context;
    private WebLoader webLoader;
    private UserInfo userInfo;
    private App app;

    public NoteWindowPage(App app, AppContext context) {
        super(app.getMainActivity());
        this.context = context;
        this.app = app;
    }

    public NoteWindowPage initUI() {
        webLoader = new WebLoader(context);
        userInfo = User.getInstance().getUserInfo();
        titleBar.setTitleText("私密笔记");
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.goBack();
            }
        });
        addBodyView(webLoader.load("http://pcs.avosapps.com/note/" + userInfo.getUserName()));
        return this;
    }
}

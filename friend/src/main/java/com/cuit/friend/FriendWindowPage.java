package com.cuit.friend;

import android.content.Context;
import android.view.View;
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
 * Created by ASUS-1 on 2015/9/15.
 */
public class FriendWindowPage extends WindowPage {

    private AppContext context;
    private WebLoader webLoader;
    private UserInfo userInfo;
    private App app;

    public FriendWindowPage(App app, AppContext context) {
        super(app.getMainActivity());
        this.context = context;
        this.app = app;
    }

    public void initUI() {
        webLoader = new WebLoader(context);
        userInfo = User.getInstance().getUserInfo();
        setNoTitleBar();
        addBodyView(webLoader.load("http://pcs.avosapps.com/friends/" + userInfo.getUserName()));
    }

}

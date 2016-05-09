package com.cuit.friend;

import android.content.Context;
import android.view.View;

import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.apkloader.AppWrapper;
import com.cuit.pcs.apkloader.IApp;
import com.cuit.pcs.sys.user.User;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.ui.view.login.LoginWindowPage;

/**
 * Created by ASUS-1 on 2015/9/15.
 */
public class FriendApp extends AppWrapper {

    public FriendApp(App app) {
        super(app);
    }

    @Override
    public void onStart(AppContext context) {
        super.onStart(context);
        if (User.getInstance().getUserInfo() != null) {
            FriendWindowPage friendWindowPage = new FriendWindowPage(this, context);
            friendWindowPage.initUI();
            startWindowPage(friendWindowPage);
        } else {
            startWindowPage(new LoginWindowPage(getMainActivity()));
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
}

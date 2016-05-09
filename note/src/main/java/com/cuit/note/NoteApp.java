package com.cuit.note;


import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.apkloader.AppWrapper;
import com.cuit.pcs.sys.user.User;
import com.cuit.pcs.ui.view.login.LoginWindowPage;

/**
 * Created by ASUS-1 on 2015/9/12.
 */
public class NoteApp extends AppWrapper {

    public NoteApp(App app) {
        super(app);
    }

    @Override
    public void onStart(AppContext context) {
        super.onStart(context);
        if (User.getInstance().getUserInfo() == null) {
            startWindowPage(new LoginWindowPage(getMainActivity()));
        } else {
            startWindowPage(new NoteWindowPage(this, context).initUI());
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
}

package com.cuit.pcs.sys.user;

import android.content.Context;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.cuit.pcs.apkloader.EventBus;
import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.sys.utils.AVCloudUtil;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.ui.view.login.LoginWindowPage;
import com.cuit.pcs.ui.widget.MaterialDialog;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

/**
 * Created by ASUS-1 on 2015/7/25.
 */
public class User {

    private static User user;

    private UserInfo userInfo;
    private DbUtils dbUtils;
    private Context context;

    private User() {
        context = MyApplication.getInstance().getApplicationContext();
        dbUtils = DbUtils.create(context);
    }

    public static User getInstance() {
        if (user == null)
            user = new User();
        return user;
    }

    public boolean isLogin() {
        return AVUser.getCurrentUser() != null ? true : false;
    }

    public void logout() {
        try {
            dbUtils.deleteAll(UserInfo.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        AVUser.logOut();
        userInfo = null;
        EventBus.getInstance().publishEvent(userInfo, "onUserLogout");
    }

    public void singUp(final String userEmail, String passWord, final UserSignUpCallback signUpCallback) {
        AVUser avUser = new AVUser();
        avUser.setEmail(userEmail);
        avUser.setPassword(passWord);
        avUser.setUsername(userEmail);

        avUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    userInfo = new UserInfo();
                    userInfo.setUserName(userEmail);
                    userInfo.setEmail(userEmail);
                    userInfo.setUserHead("http://ac-gwkliimu.clouddn.com/25a99d1ec140b4b9.jpg");
                    userInfo.setDescribe("这个人很懒，什么也没有留下...");
                    userInfo.setLevel("0");
                    try {
                        dbUtils.replace(userInfo);
                    } catch (DbException e1) {
                        e1.printStackTrace();
                    }
                    AVCloudUtil.saveObject(userInfo, null);
                }
                signUpCallback.done(e);
            }
        });
    }

    public void checkNeedLogin() {
        final MaterialDialog dialog = new MaterialDialog(context, "未登录", "你还未登录，登录了才能查看自己上传的资源和上传资源哦...");
        dialog.addCancelButton("不了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastWindow.getInstance().goBack();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getButtonAccept().setText("去登录");
        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastWindow.getInstance().startWindow(new LoginWindowPage(context));
                dialog.dismiss();
            }
        });

        return;

    }

    public void changePassWord(String old, String newPassword, UpdatePasswordCallback updatePasswordCallback) {
        if (isLogin()) {
            AVUser.getCurrentUser().updatePasswordInBackground(old, newPassword, updatePasswordCallback);
        }
    }

    public void logIn(String userNmae, String passWord, final UserLoginCalllback userLoginCalllback) {
        AVUser.logInInBackground(userNmae, passWord, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (avUser != null) {
                    findUserInfo(new NormalCallBack() {
                        @Override
                        public <T> void done(T t) {

                        }
                    });
                    userLoginCalllback.successed();
                } else {
                    userLoginCalllback.failed(e);
                }
            }
        });
    }

    public interface UserSignUpCallback {
        public void done(Exception e);
    }

    public interface UserLoginCalllback {
        public void successed();

        public void failed(Exception e);
    }

    public interface NormalCallBack {
        public <T> void done(T t);
    }

    public static String getUserName() {
        return AVUser.getCurrentUser().getUsername();
    }

    public UserInfo getUserInfo() {
        if (userInfo == null)
            try {
                userInfo = dbUtils.findFirst(Selector.from(UserInfo.class));
            } catch (DbException e) {
                e.printStackTrace();
            }
        return userInfo;
    }

    public void findUserInfo(final NormalCallBack callBack) {
        String email = AVUser.getCurrentUser().getEmail();
        AVQuery<AVObject> query = new AVQuery<>("UserInfo");
        query.whereEqualTo("email", email);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    userInfo = AVCloudUtil.generateBean(UserInfo.class, avObject);
                    userInfo.setEmail(AVUser.getCurrentUser().getEmail());
                    try {
                        dbUtils.replace(userInfo);
                        EventBus.getInstance().publishEvent(userInfo, "onUserLogin");
                        callBack.done(userInfo);
                    } catch (DbException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;

    }

    public void updateUserInfo() {
        try {
            AVCloudUtil.saveObject(userInfo, null);
            dbUtils.replace(userInfo);
            EventBus.getInstance().publishEvent(userInfo, "onUserLogin");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}

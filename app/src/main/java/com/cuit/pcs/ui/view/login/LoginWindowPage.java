package com.cuit.pcs.ui.view.login;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.cuit.pcs.R;
import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.sys.user.User;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.ui.view.main.MainWindowPage;
import com.cuit.pcs.ui.widget.MaterialProgressDialog;

/**
 * Created by ASUS-1 on 2015/8/10.
 */
public class LoginWindowPage extends WindowPage implements View.OnClickListener {
    private LinearLayout rootView;
    private Context context;
    private EditText userNameEditText, passwordEditText;
    private Button loginButton;

    public LoginWindowPage(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    private void initUI() {
        rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.login_view, null);
        userNameEditText = (EditText) rootView.findViewById(R.id.id_username_editText);
        passwordEditText = (EditText) rootView.findViewById(R.id.id_password_editText);
        loginButton = (Button) rootView.findViewById(R.id.login_button);

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String userName = userNameEditText.getText().toString();
                    if (userName.length() < 0) {
                        Snackbar.make(rootView, "用户名不能为空", Snackbar.LENGTH_SHORT).show();
                        return;
                    } else {
                        checkUser(userName);
                    }
                }
            }
        });
        addBodyView(rootView);
        titleBar.setTitleText("用户登录");
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastWindow.getInstance().goBack();
            }
        });
        loginButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (((Button) v).getText().equals("登录"))
            doLogin();
        else if (((Button) v).getText().equals("注册"))
            doRegiste();
        else if (((Button) v).getText().equals("我已经验证成功，开始登录"))
            doLogin();
    }

    private void checkUser(String userEmail) {
        AVQuery<AVObject> query = new AVQuery<AVObject>("_User");
        query.whereEqualTo("email", userEmail);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (avObject != null) {
                    loginButton.setText("登录");
                } else {
                    loginButton.setText("注册");
                }
            }
        });
    }

    private void doRegiste() {
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (userName.length() == 0) {
            Snackbar.make(rootView, "用户名不能为空", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (password.length() == 0) {
            Snackbar.make(rootView, "密码不能为空", Snackbar.LENGTH_SHORT).show();
            return;
        }
        User.getInstance().singUp(userName, password, new User.UserSignUpCallback() {
            @Override
            public void done(Exception e) {
                if (e == null) {
                    loginButton.setText("我已经验证成功，开始登录");
                }
            }
        });
    }

    private void doLogin() {
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (userName.length() == 0) {
            Snackbar.make(rootView, "用户名不能为空", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (password.length() == 0) {
            Snackbar.make(rootView, "密码不能为空", Snackbar.LENGTH_SHORT).show();
            return;
        }
        final MaterialProgressDialog materialProgressDialog = new MaterialProgressDialog(MyApplication.getInstance().getMainActivity(), "正在登录");
        materialProgressDialog.show();
        User.getInstance().logIn(userName, password, new User.UserLoginCalllback() {
            @Override
            public void successed() {
                Snackbar.make(rootView, "登录成功", Snackbar.LENGTH_SHORT).show();
                materialProgressDialog.dismiss();
                FastWindow.getInstance().goBack();
            }

            @Override
            public void failed(Exception e) {
                Snackbar.make(rootView, "登录失败", Snackbar.LENGTH_SHORT).show();
                materialProgressDialog.dismiss();
            }
        });


    }
}

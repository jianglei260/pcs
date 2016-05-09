package com.cuit.pcs.ui.view.user;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.cuit.pcs.R;
import com.cuit.pcs.sys.user.User;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.ui.view.login.LoginWindowPage;
import com.cuit.pcs.ui.widget.MaterialProgressDialog;

/**
 * Created by ASUS-1 on 2015/8/18.
 */
public class ChangePasswordWindowPage extends WindowPage {

    private Context context;
    private LinearLayout rootView;
    private EditText oldpasswordEditText, newpasswordEditText, confirmEditText;
    private Button changeButton;
    private MaterialProgressDialog dialog;

    public ChangePasswordWindowPage(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    private void initUI() {
        rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.change_password_view, null);
        oldpasswordEditText = (EditText) rootView.findViewById(R.id.id_password_old);
        newpasswordEditText = (EditText) rootView.findViewById(R.id.id_password_new);
        confirmEditText = (EditText) rootView.findViewById(R.id.id_password_confirm);
        changeButton = (Button) rootView.findViewById(R.id.id_password_changebutton);
        dialog = new MaterialProgressDialog(context, "请稍等...");

        addBodyView(rootView);
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastWindow.getInstance().goBack();
            }
        });
        titleBar.setTitleText("修改密码");
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });
    }

    private void updatePassword() {
        String oldPassword = oldpasswordEditText.getText().toString();
        String newPassword = newpasswordEditText.getText().toString();
        String confirm = confirmEditText.getText().toString();
        if (oldPassword.length() <= 0) {
            Snackbar.make(rootView, "旧密码不能为空", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (newPassword.length() <= 0) {
            Snackbar.make(rootView, "新密码不能为空", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (confirm.length() <= 0) {
            Snackbar.make(rootView, "请确认新密码", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (!confirm.equals(newPassword)) {
            Snackbar.make(rootView, "两次新密码不一样", Snackbar.LENGTH_SHORT).show();
            return;
        }
        dialog.show();
        User.getInstance().changePassWord(oldPassword, newPassword, new UpdatePasswordCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    dialog.dismiss();
                    Snackbar.make(rootView, "修改密码成功", Snackbar.LENGTH_SHORT).show();
                    User.getInstance().logout();
                    LoginWindowPage loginWindowPage = new LoginWindowPage(context);
                    FastWindow.getInstance().startWindow(loginWindowPage);
                } else {
                    dialog.dismiss();
                    Snackbar.make(rootView, "修改密码失败", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}

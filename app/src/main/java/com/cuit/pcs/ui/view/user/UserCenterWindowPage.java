package com.cuit.pcs.ui.view.user;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.cuit.pcs.R;
import com.cuit.pcs.sys.user.User;
import com.cuit.pcs.sys.utils.AVCloudUtil;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.ui.view.login.LoginWindowPage;
import com.cuit.pcs.ui.view.picselector.PicSelectWindowPage;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by ASUS-1 on 2015/8/6.
 */
public class UserCenterWindowPage extends WindowPage {
    private Context context;
    private CoordinatorLayout rootView;
    private TextView userAccount;
    private ImageView toolbarImage;
    private BitmapUtils bitmapUtils;
    private RelativeLayout changeHeadLayout, changePasswordLayout;
    private EditText describeText;
    private String headPath;
    private Button logoutButton;
    private String describe;
    private boolean changed = false;

    public UserCenterWindowPage(Context context) {
        super(context);
        this.context = context;
        initUI();
    }


    private void initUI() {
        rootView = (CoordinatorLayout) LayoutInflater.from(context).inflate(R.layout.user_center_view, null);
        userAccount = (TextView) rootView.findViewById(R.id.id_user_center_account);
        describeText = (EditText) rootView.findViewById(R.id.id_user_center_describe);
        toolbarImage = (ImageView) rootView.findViewById(R.id.id_toolbar_bg);
        changeHeadLayout = (RelativeLayout) rootView.findViewById(R.id.id_user_center_change_head);
        changePasswordLayout = (RelativeLayout) rootView.findViewById(R.id.id_user_center_change_password);
        logoutButton = (Button) rootView.findViewById(R.id.id_user_center_logout);
        bitmapUtils = new BitmapUtils(context);
        addBodyView(rootView);

        titleBar.setTitleText("个人中心");
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastWindow.getInstance().goBack();
            }
        });
        bitmapUtils.display(toolbarImage, User.getInstance().getUserInfo().getUserHead());
        userAccount.setText(User.getInstance().getUserInfo().getEmail());
        describeText.setText(User.getInstance().getUserInfo().getDescribe());
        changeHeadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicSelectWindowPage picSelectWindowPage = new PicSelectWindowPage(context);
                FastWindow.getInstance().startWindowForResult(picSelectWindowPage, 0);
            }
        });
        changePasswordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordWindowPage changePasswordWindowPage = new ChangePasswordWindowPage(context);
                FastWindow.getInstance().startWindow(changePasswordWindowPage);
            }
        });
        describeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                describe = s.toString();
                changed = true;
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.getInstance().logout();
                FastWindow.getInstance().startWindow(new LoginWindowPage(context));
            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
        if (changed) {
            User.getInstance().getUserInfo().setDescribe(describe);
            User.getInstance().updateUserInfo();
        }
    }

    @Override
    public void onResult(Object object, int requestCode) {
        super.onResult(object, requestCode);
        if (requestCode == 0) {
            if (((List<String>) object).size() > 0) {
                headPath = ((List<String>) object).get(0);
                bitmapUtils.display(toolbarImage, headPath);
                AVCloudUtil.uploadHeadImage(User.getInstance().getUserInfo().getUserHead(), headPath, new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        Snackbar.make(rootView, "更换头像成功", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }
}

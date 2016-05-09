package com.cuit.pcs.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cuit.pcs.R;
import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppManageService;
import com.cuit.pcs.application.Config;
import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.ui.view.add.AddWindowPage;
import com.cuit.pcs.ui.view.main.AddActionProvider;
import com.cuit.pcs.ui.view.main.MainWindowPage;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends ActionBarActivity {

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        MainWindowPage mainWindowPage = new MainWindowPage(context);
        FastWindow.getInstance().startWindow(mainWindowPage);
        setContentView(FastWindow.getInstance().getContainerView());
    }

    private void init() {
        context = this;
        MyApplication.getInstance().setMainActivity(this);
        FastWindow.getInstance().init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem addItem = menu.findItem(R.id.action_add);
        AddActionProvider addActionProvider = (AddActionProvider) MenuItemCompat.getActionProvider(addItem);
        addActionProvider.setOnAddClickListener(new AddActionProvider.OnAddClickListener() {
            @Override
            public void onAddClick(View v) {
                FastWindow.getInstance().startWindowForResult(new AddWindowPage(context), 0);
            }
        });
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public void onBackPressed() {
        if (AppManageService.getInstance().onBackPressed())
            return;
        if (!FastWindow.getInstance().goBack()) {
            super.onBackPressed();
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MaiActvy", "onDestory");
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return super.getAssets();
    }
}

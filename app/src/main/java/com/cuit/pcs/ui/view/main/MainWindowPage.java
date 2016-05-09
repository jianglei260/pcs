package com.cuit.pcs.ui.view.main;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cuit.pcs.R;
import com.cuit.pcs.apkloader.AppInfo;
import com.cuit.pcs.apkloader.AppManageService;
import com.cuit.pcs.apkloader.AppManager;
import com.cuit.pcs.apkloader.EventBus;
import com.cuit.pcs.application.Config;
import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.sys.user.User;
import com.cuit.pcs.sys.user.UserInfo;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.ui.view.add.AddWindowPage;
import com.cuit.pcs.ui.view.login.LoginWindowPage;
import com.cuit.pcs.ui.view.settings.SettingsWindowPage;
import com.cuit.pcs.ui.view.user.UserCenterWindowPage;
import com.cuit.pcs.ui.view.web.WebWindowPage;
import com.cuit.pcs.ui.widget.AppDetialDialog;
import com.cuit.pcs.ui.widget.MaterialDialog;
import com.cuit.pcs.ui.widget.RoundedImageView;
import com.cuit.pcs.ui.widget.UnTouchableLinearLayout;
import com.cuit.pcs.ui.widget.sitemenu.SatelliteMenu;
import com.cuit.pcs.ui.widget.sitemenu.SatelliteMenuItem;
import com.kimo.lib.alexei.Alexei;
import com.kimo.lib.alexei.Answer;
import com.kimo.lib.alexei.calculus.DominantColorCalculus;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import library.widget.AsymmetricGridView;
import library.widget.AsymmetricGridViewAdapter;

/**
 * Created by ASUS-1 on 2015/9/9.
 */
public class MainWindowPage extends WindowPage implements SatelliteMenu.SateliteClickedListener {
    private Context context;
    private AsymmetricGridView asymmetricGridView;
    //    private SatelliteMenu menuButton;
    private List<AppInfo> list;
    private List<SatelliteMenuItem> satelliteMenuItemList;
    private AsymmetricGridViewAdapter asymmetricGridViewAdapter;
    private DrawerLayout rootView;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RoundedImageView headImageView;
    private TextView userAccount;
    private UserInfo userInfo;
    private BitmapUtils bitmapUtils;
    private CoordinatorLayout coordinatorLayout;
    private View navHeader;

    public MainWindowPage(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    private void initUI() {
        bitmapUtils = new BitmapUtils(context);
        rootView = (DrawerLayout) LayoutInflater.from(context).inflate(R.layout.main_view, null);
        navigationView = (NavigationView) rootView.findViewById(R.id.navigation);
        navHeader = navigationView.inflateHeaderView(R.layout.nav_header);
        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.rootLayout);
        asymmetricGridView = (AsymmetricGridView) rootView.findViewById(R.id.metro_list);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        headImageView = (RoundedImageView) rootView.findViewById(R.id.id_header_face);
        userAccount = (TextView) rootView.findViewById(R.id.id_header_account);
//        menuButton = (SatelliteMenu) rootView.findViewById(R.id.menu_button);
        asymmetricGridView.setRequestedColumnCount(3);
        asymmetricGridView.forceLayout();
        setNoTitleBar();
        list = AppManager.getInstance().getInstalledApps();
        asymmetricGridViewAdapter = new AsymmetricGridViewAdapter(context, asymmetricGridView, new AppListAdapter(list));
        asymmetricGridView.setAdapter(asymmetricGridViewAdapter);
        addBodyView(rootView);


        //初始化toolbar
        MyApplication.getInstance().getMainActivity().setSupportActionBar(toolbar);
        MyApplication.getInstance().getMainActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MyApplication.getInstance().getMainActivity(), rootView, toolbar, R.string.string_actionbar_drawertoggle_open, R.string.string_actionbar_drawertoggle_close);
        actionBarDrawerToggle.syncState();

        rootView.setDrawerListener(actionBarDrawerToggle);

        toolbar.setTitle("我的应用");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_menu_add:
                        //TODO
                        FastWindow.getInstance().startWindowForResult(new AddWindowPage(context), 1);
                        break;
                    case R.id.nav_menu_setting:
                        FastWindow.getInstance().startWindowForResult(new SettingsWindowPage(context), 2);
                        break;
                    case R.id.nav_menu_about:
                        //TODO
                        WebWindowPage webWindowPage = new WebWindowPage(context, "关于", "file:///android_asset/about/js/about.html", false);
                        webWindowPage.initUI();
                        FastWindow.getInstance().startWindow(webWindowPage);
                        break;
                }
                return false;
            }
        });


//        satelliteMenuItemList = new ArrayList<>();
//        SatelliteMenuItem userItem = new SatelliteMenuItem(1, R.mipmap.ic_menu_user);
//        SatelliteMenuItem setItem = new SatelliteMenuItem(2, R.mipmap.ic_menu_setting);
//        SatelliteMenuItem addItem = new SatelliteMenuItem(3, R.mipmap.ic_menu_add);
//        SatelliteMenuItem aboutItem = new SatelliteMenuItem(4, R.mipmap.ic_menu_about);
//        satelliteMenuItemList.add(userItem);
//        satelliteMenuItemList.add(setItem);
//        satelliteMenuItemList.add(aboutItem);
//        satelliteMenuItemList.add(addItem);
//        menuButton.addItems(satelliteMenuItemList);
//        menuButton.setOnItemClickedListener(this);

        asymmetricGridView.setDividerHeight(0);
        asymmetricGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo appInfo = list.get(position);
                if (appInfo.getName().equals("添加")) {
                    FastWindow.getInstance().startWindowForResult(new AddWindowPage(MyApplication.getInstance().getMainActivity()), 1);
                } else {
                    openApp(appInfo);
                }
            }
        });
        asymmetricGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int postion, long id) {
                if (list.get(postion).getName().equals("添加")) {
                    Snackbar.make(rootView, "不能卸载该插件", Snackbar.LENGTH_SHORT).show();
                    return true;
                }
                onAppDetial(list.get(postion));
                return true;
            }
        });
        coordinatorLayout.setBackgroundResource(Config.BG_IMG);
        navHeader.setBackgroundResource(R.color.colorPrimary);
        configUserHead();
        EventBus.getInstance().registe(this, "onEvent");
        EventBus.getInstance().registe(this, "onThemeChanged");
        EventBus.getInstance().registe(this, "onWallpaperChanged");
        EventBus.getInstance().registe(this, "onUserLogout");
        EventBus.getInstance().registe(this, "onUserLogin");
    }

    public void onUserLogout(Object object) {
        configUserHead();
    }

    public void onUserLogin(Object object) {
        configUserHead();
    }

    private void configUserHead() {
        userInfo = User.getInstance().getUserInfo();
        if (userInfo != null) {
            bitmapUtils.display(headImageView, userInfo.getUserHead());
            userAccount.setText(userInfo.getUserName());
        } else {
            headImageView.setImageResource(R.mipmap.ic_account_blue_192dp);
            userAccount.setText("请登录");
        }
        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!User.getInstance().isLogin()) {
                    LoginWindowPage loginWindowPage = new LoginWindowPage(context);
                    FastWindow.getInstance().startWindow(loginWindowPage);
                } else {
                    UserCenterWindowPage userCenterWindowPage = new UserCenterWindowPage(context);
                    FastWindow.getInstance().startWindow(userCenterWindowPage);
                }
            }
        });
    }

    private void onAppDetial(final AppInfo appInfo) {
        final AppDetialDialog appDetialDialog = new AppDetialDialog(context, appInfo.getName());
        appDetialDialog.show();
        if (appInfo.getColumnSpan() > 1)
            appDetialDialog.setChoosedItem(R.id.bigIcon);
        else
            appDetialDialog.setChoosedItem(R.id.smallIcon);
        appDetialDialog.setOnItemChoosed(new AppDetialDialog.OnItemChoosed() {
            @Override
            public void onItemChoosed(int id) {
                if (id == R.id.smallIcon) {
                    appInfo.setColumnSpan(1);
                } else {
                    appInfo.setColumnSpan(2);
                }
                appDetialDialog.dismiss();
                asymmetricGridViewAdapter.recalculateItemsPerRow();
                asymmetricGridViewAdapter.notifyDataSetChanged();
                AppManager.getInstance().saveOrUpdateData();
            }
        });
        appDetialDialog.setOnRemoveListener(new AppDetialDialog.OnRemoveListener() {
            @Override
            public void onRemove(View v) {
                appDetialDialog.dismiss();
                removeApp(appInfo);
            }
        });
    }

    private void recalculate() {
        Collections.sort(list, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo lhs, AppInfo rhs) {
                if (lhs.getColumnSpan() + rhs.getColumnSpan() >= 4)
                    return 0;
                return -1;
            }
        });
    }

    private void removeApp(final AppInfo appInfo) {
        final MaterialDialog materialDialog = new MaterialDialog(context, "卸载插件", "你确定要卸载这款插件吗？");
        materialDialog.addCancelButton("取消");
        materialDialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().removeApp(appInfo);
                asymmetricGridViewAdapter.recalculateItemsPerRow();
                asymmetricGridViewAdapter.notifyDataSetChanged();
                materialDialog.dismiss();
            }
        });
        materialDialog.setOnCancelButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }

    private void openApp(AppInfo appInfo) {
        if (appInfo.getPath().endsWith(".apk")) {
            AppManageService.getInstance().start(appInfo);
        } else {
            WebWindowPage webWindowPage = new WebWindowPage(context, appInfo.getName(), appInfo.getPath());
            webWindowPage.initUI();
            FastWindow.getInstance().startWindow(webWindowPage);
        }
    }

    public void onEvent(Object object) {
        asymmetricGridViewAdapter.recalculateItemsPerRow();
        asymmetricGridViewAdapter.notifyDataSetChanged();
    }

    public void onThemeChanged(Object o) {
        asymmetricGridViewAdapter.notifyDataSetChanged();
        asymmetricGridView.invalidate();
    }

    public void onWallpaperChanged(Object o) {
        coordinatorLayout.setBackgroundResource((Integer) o);
    }

    @Override
    public void onResult(Object object, int requestCode) {
        super.onResult(object, requestCode);
        asymmetricGridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void eventOccured(int id) {
        switch (id) {
            case 1:
                if (User.getInstance().getUserInfo() != null)
                    FastWindow.getInstance().startWindow(new UserCenterWindowPage(context));
                else
                    FastWindow.getInstance().startWindow(new LoginWindowPage(context));
                break;
            case 2:
                //TODO
                FastWindow.getInstance().startWindowForResult(new SettingsWindowPage(context), 1);
                break;
            case 3:
                FastWindow.getInstance().startWindowForResult(new AddWindowPage(context), 1);
                break;
            case 4:
                //TODO
                WebWindowPage webWindowPage = new WebWindowPage(context, "关于", "file:///android_asset/about/about.html", false);
                webWindowPage.initUI();
                FastWindow.getInstance().startWindow(webWindowPage);
                break;

        }
    }


    public class AppListAdapter extends BaseAdapter {

        private List<AppInfo> list;
        private LayoutInflater inflater;

        public AppListAdapter(List<AppInfo> list) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.mian_item_view, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.id_main_item_image);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.id_main_item_name);
                viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            MyApplication.getInstance().getBitmapUtils().display(viewHolder.imageView, list.get(position).getLogo(), new BitmapLoadCallBack<ImageView>() {
                @Override
                public void onLoadCompleted(final ImageView container, String uri, final Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
                    if (Config.THEME == Config.THEME_WIN) {
                        viewHolder.textView.setTextColor(Color.WHITE);
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                viewHolder.relativeLayout.setBackgroundColor(palette.getVibrantSwatch().getRgb());
                            }
                        });
                    }
                    container.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadFailed(ImageView container, String uri, Drawable drawable) {

                }
            });
            viewHolder.textView.setText(list.get(position).getName());
            return convertView;
        }
    }

    public class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
    }
}

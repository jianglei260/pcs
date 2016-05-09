package com.cuit.pcs.sys.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by ASUS-1 on 2015/9/13.
 */
public class ProxyService extends Service {

    public ProxyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

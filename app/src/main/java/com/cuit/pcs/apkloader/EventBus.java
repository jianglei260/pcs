package com.cuit.pcs.apkloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import android.os.Handler;

/**
 * Created by ASUS-1 on 2015/9/26.
 */
public class EventBus {
    private static EventBus eventBus;
    private WeakHashMap<Object, List<String>> map = new WeakHashMap<>();
    private Handler handler;

    public static EventBus getInstance() {
        if (eventBus == null)
            eventBus = new EventBus();
        return eventBus;
    }

    EventBus() {
        handler = new Handler();
    }

    public void registe(Object object, String method) {
        if (map.containsKey(object)) {
            map.get(object).add(method);
        } else {
            List<String> list = new ArrayList<>();
            list.add(method);
            map.put(object, list);
        }
    }

    public void publishEvent(final Object object, String methodName) {
        Set<Map.Entry<Object, List<String>>> entrys = map.entrySet();
        for (final Map.Entry<Object, List<String>> entry : entrys) {
            if (entry.getValue().contains(methodName)) {
                Class tmpClass = entry.getKey().getClass();
                try {
                    final Method method = tmpClass.getDeclaredMethod(methodName, Object.class);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                method.invoke(entry.getKey(), object);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void publishEvent(final Object object, Class clazz, String methodName) {
        Set<Map.Entry<Object, List<String>>> entrys = map.entrySet();
        for (final Map.Entry<Object, List<String>> entry : entrys) {
            Class tmpClass = entry.getKey().getClass();
            if (tmpClass.getName().equals(clazz.getName())) {
                try {
                    final Method method = clazz.getDeclaredMethod(methodName, Object.class);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                method.invoke(entry.getKey(), object);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}

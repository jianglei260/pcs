package com.cuit.pcs.sys.utils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.cuit.pcs.sys.user.User;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ASUS-1 on 2015/7/27.
 */
public class AVCloudUtil {

    public static synchronized void putValue(String tableName, String key, String value, String callBack) {
        AVObject avObject = new AVObject(tableName);
        avObject.put(key, value);
        avObject.saveInBackground();
    }

    public static synchronized void saveObject(Object object, SaveCallback callback) {
        String tableName = object.getClass().getAnnotation(DatabaseTable.class).value();
        AVObject avObject = new AVObject(tableName);
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getAnnotation(NumberField.class) != null) {
                    avObject.put(field.getName(), (int) field.get(object));
                } else if (field.getAnnotation(DateField.class) != null) {
                    continue;
                } else if (field.getName().equals("objectId")) {
                    if (field.get(object) != null)
                        avObject.setObjectId((String) field.get(object));
                } else if (field.getAnnotation(IgnoreField.class) == null) {
                    String value = (String) field.get(object);
                    avObject.put(field.getName(), value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        avObject.saveInBackground(callback);
    }

    public static synchronized <T> void findObject(final Class<T> bean, final List<T> beanList, final CloudFindCallBack findCallback) {
        try {
            String tableName = bean.newInstance().getClass().getAnnotation(DatabaseTable.class).value();
            AVQuery<AVObject> avQuery = new AVQuery(tableName);
            avQuery.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        for (AVObject object : list) {
                            beanList.add(generateBean(bean, object));
                        }
                        findCallback.done(beanList);
                    } else findCallback.failed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized <T> void findObject(final Class<T> bean, String key, Object value, int limit, final CloudFindCallBack findCallback) {
        try {
            String tableName = bean.newInstance().getClass().getAnnotation(DatabaseTable.class).value();
            AVQuery<AVObject> avQuery = new AVQuery(tableName);
            avQuery.whereEqualTo(key, value);
            avQuery.setLimit(limit);
            avQuery.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    List<T> beanList = new ArrayList<T>();
                    if (e == null) {
                        for (AVObject object : list) {
                            beanList.add(generateBean(bean, object));
                        }
                        findCallback.done(beanList);
                    } else findCallback.failed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CloudFindCallBack {
        public <T> void done(List<T> list);

        public void failed();
    }

    public static synchronized <T> T generateBean(Class<T> bean, AVObject object) {
        try {
            T t = (T) bean.newInstance();
            Field[] fields = bean.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getAnnotation(NumberField.class) != null) {
                    fields[i].setAccessible(true);
                    fields[i].set(t, object.getInt(fields[i].getName()));
                } else if (fields[i].getAnnotation(DateField.class) != null) {
                    fields[i].setAccessible(true);
                    fields[i].set(t, object.getCreatedAt().getTime());
                } else if (fields[i].getName().equals(bean.getDeclaredField("objectId").getName())) {
                    fields[i].setAccessible(true);
                    fields[i].set(t, object.getObjectId());
                } else if (fields[i].getAnnotation(IgnoreField.class) == null) {
                    fields[i].setAccessible(true);
                    fields[i].set(t, object.getString(fields[i].getName()));
                }

            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void uploadHeadImage(final String oldPath, String path, final SaveCallback callback) {
        File file = new File(path);
        final AVFile avFile = new AVFile(file.getName(), Utils.getScaledBitmapBytes(path, 512, 512));
        avFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    User.getInstance().getUserInfo().setUserHead(avFile.getUrl());
                    User.getInstance().updateUserInfo();
                    AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
                    query.whereEqualTo("url", oldPath);
                    callback.done(e);
                    query.getFirstInBackground(new GetCallback<AVObject>() {
                        @Override
                        public void done(AVObject avObject, AVException e) {
                            if (avObject != null) {
                                avObject.deleteInBackground();
                            }
                        }
                    });
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer integer) {

            }
        });

    }
}

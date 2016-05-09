package link.sharein.access;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;
import java.util.concurrent.Callable;

public class AccessService extends AccessibilityService {
    private static final String TAG = "AccessService";

    public AccessService() {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        Log.d(TAG, "onAccessibilityEvent: "+event.toString());
//        int count = rootNode.getChildCount();
//        for (int i = 0; i < count; i++) {
//            AccessibilityNodeInfo childNode = rootNode.getChild(i);
//
//        }
//            Log.d(TAG, "onAccessibilityEvent: " +childNode.getClassName());
        findTextAndClick("我的", rootNode);

    }
    private void findTextAndClick(String text,AccessibilityNodeInfo rootNode){
        List<AccessibilityNodeInfo> nodes=rootNode.findAccessibilityNodeInfosByText(text);
        for (AccessibilityNodeInfo node:nodes) {
            Log.d(TAG, "onAccessibilityEvent: " +node.getText());
            Log.d(TAG, "onAccessibilityEvent: " +node.getContentDescription());
            node.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);

            break;
        }
    }
    @Override
    public void onInterrupt() {

    }

}

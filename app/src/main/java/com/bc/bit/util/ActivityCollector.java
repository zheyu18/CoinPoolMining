package com.bc.bit.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjiyu on 2019/1/2.
 * Describe:
 * Email:3301360040@qq.com
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivty(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivty(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activty : activities) {
            if (!activty.isFinishing()) {
                activty.finish();
            }
        }
        activities.clear();
    }

}

package com.moo.test.test_android.utils;

import java.util.Stack;

import android.app.Activity;

/**
 * activity 栈理
 *
 * @author moo
 */
public class ActivityTaskManager {
    private static Stack<Activity> activityStack;
    private static ActivityTaskManager instance;

    private ActivityTaskManager() {

    }

    public static int getSize() {
        return activityStack.size();
    }

    public static ActivityTaskManager getActivityTaskManager() {
        if (instance == null) {
            instance = new ActivityTaskManager();
        }
        return instance;
    }

    public void popActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }

    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public void popAllActivityExceptOne(Class<?> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }
}
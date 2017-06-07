package cn.zplayer.mc.framework.manager;

import android.app.Activity;

import java.util.Stack;

import cn.zplayer.mc.activity.MainActivity;

/**
 * 自定义 stack 管理activity
 *
 * @author lkk
 */
public class ActivityStack {

    private volatile Stack<Activity> stack;
    private volatile static ActivityStack instance;

    /**
     * 单例模式（未加同步锁）  获取实例对象
     *
     * @return
     */
    public static ActivityStack getInstance() {
        if (instance == null) {
            instance = new ActivityStack();
        }
        return instance;
    }

    public int getActivitySize() {
        return stack.size();
    }

    /**
     * 删除指定activity
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            stack.remove(activity);
        }
    }

    /**
     * 删除顶层activity对象
     */
    public void popActivity() {
        Activity activity = stack.lastElement();
        if (activity != null) {
            activity.finish();
            stack.remove(activity);
        }
    }

    /**
     * 压栈
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (stack == null) {
            stack = new Stack<Activity>();
        }
        if (!stack.contains(activity)) {
            stack.add(activity);
        }
    }

    /**
     * 清除栈内所有activity对象（main界面除外）
     */
    public void popAllActivity() {
        if (stack == null) {
            return;
        }
        while (stack.iterator().hasNext()) {
            Activity activity = stack.lastElement();
            if (activity == null) {
                break;
            }
            if (activity instanceof MainActivity) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 清除栈内所有activity对象（main和当前界面除外）
     */
    public void popAllActivityCurrent() {
        if (stack == null) {
            return;
        }
        while (stack.iterator().hasNext()) {
            Activity activity = stack.lastElement();
            if (activity == null) {
                break;
            }
            if (activity instanceof MainActivity) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 取出当前activity
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (stack == null) {
            return null;
        } else {
            if (!stack.empty())
                activity = stack.lastElement();
            return activity;
        }
    }


    /**
     * main 界面是否存在
     *
     * @return
     */
    public boolean isStackMain() {
        for (Activity activity : stack) {
            if (activity instanceof MainActivity) {
                return true;
            }
        }
        return false;
    }
}

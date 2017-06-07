package cn.zplayer.mc.framework.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.List;

import cn.zplayer.mc.framework.utils.LogUtils.LogInfo;


public class SystemInfo {

    private final static String TAG = "SystemInfo";

    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    @SuppressLint("InlinedApi")
    public static void debugDensity(Context context) {
        final DisplayMetrics dm = getDeviceDisplayMetrics(context);
        LogInfo.d(TAG, "dm.density: " + dm.density);
        LogInfo.d(TAG, "dm.densityDpi: " + dm.densityDpi);
        LogInfo.d(TAG, "dm.heightPixels: " + dm.heightPixels);
        LogInfo.d(TAG, "dm.scaledDensity: " + dm.scaledDensity);
        LogInfo.d(TAG, "dm.widthPixels: " + dm.widthPixels);
        LogInfo.d(TAG, "dm.xdpi: " + dm.xdpi);
        LogInfo.d(TAG, "dm.ydpi: " + dm.ydpi);
        LogInfo.d(TAG, "dm.DENSITY_DEFAULT: " + DisplayMetrics.DENSITY_DEFAULT);
        LogInfo.d(TAG, "dm.DENSITY_HIGH: " + DisplayMetrics.DENSITY_HIGH);
        LogInfo.d(TAG, "dm.DENSITY_LOW: " + DisplayMetrics.DENSITY_LOW);
        LogInfo.d(TAG, "dm.DENSITY_MEDIUM: " + DisplayMetrics.DENSITY_MEDIUM);
        LogInfo.d(TAG, "dm.DENSITY_TV: " + DisplayMetrics.DENSITY_TV);
        LogInfo.d(TAG, "dm.DENSITY_XHIGH: " + DisplayMetrics.DENSITY_XHIGH);
    }

    public static DisplayMetrics getDeviceDisplayMetrics(Context context) {
        android.view.WindowManager windowsManager = (android.view.WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        android.view.Display display = windowsManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * 检查是否与网络可用
     *
     * @return
     */

    public boolean isNetworkAvailable(Context activity) {
        Context context = activity.getApplicationContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 判断应用程序是否后台运行
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static void callPhone(Context cxt, String tel) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
        if (ActivityCompat.checkSelfPermission(cxt, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        cxt.startActivity(intent);
    }

    /**
     * 跳转到发短信界面
     *
     * @param cxt
     * @param phone
     */
    public static void sendSMS(Context cxt, String phone) {
        Uri smsToUri = Uri.parse("smsto://" + phone);
        Intent mIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        cxt.startActivity(mIntent);
    }

}

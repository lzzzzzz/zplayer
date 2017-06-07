package cn.zplayer.mc.Utils;

import android.text.TextUtils;

/**
 * Created by LZ on 2016/5/9.
 */
public class FileUtils {
    /**判断文件大小*/
    public static String getFileSize(double size){
        String datasize = "";
        if (size > 1024 * 1024 * 1024) {
            datasize = String.format("%.2f", size / 1024 / 1024 / 1024) + "G";
        } else if (size > 1024 * 1024) {
            datasize = String.format("%.2f", size / 1024 / 1024) + "M";
        } else if (size > 1024) {
            datasize = String.format("%.2f", size / 1024) + "K";
        } else {
            datasize = size + "B";
        }
        return datasize;
    }
    /**判断文件时长*/
    public static String getMediaTime(long time){
        if(time<0){
            return "未知大小";
        }
        String timel="";
        long hour = time / 3600;
        long minu = (time - hour * 3600) / 60;
        long sec = time - hour * 3600 - minu * 60;
        timel=hour + ":" + minu + ":" + sec;
        return timel;
    }
}

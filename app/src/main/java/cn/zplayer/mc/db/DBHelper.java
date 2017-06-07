package cn.zplayer.mc.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.zplayer.mc.db.bean.Video;

/**
 * Created by Administrator on 2016/4/26 0026.
 */
public class DBHelper {
    private static DBHelper instance;

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }



}

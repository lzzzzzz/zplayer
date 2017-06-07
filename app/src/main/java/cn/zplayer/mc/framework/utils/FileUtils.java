package cn.zplayer.mc.framework.utils;

import java.io.File;

/**
 * Created by Administrator on 2016/4/29 0029.
 */
public class FileUtils {

    /**
     * 查询文件是否存在
     */
    public static boolean ifFileExits(String path) {
        File file = new File(path);
        return file.exists();
    }
}

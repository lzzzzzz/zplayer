package cn.zplayer.mc.Utils.MediaUtils;

import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.zplayer.mc.db.bean.Video;
import cn.zplayer.mc.framework.utils.FileUtils;

/**
 * Created by Administrator on 2016/4/29 0029.
 */
public class VideoFactory {
    private static VideoFactory instance;

    public static VideoFactory getInstance() {
        if (null == instance) {
            instance = new VideoFactory();
        }
        return instance;
    }

    /**
     * 从数据库查询视频文件
     */
    public List<Video> getVideos(Activity act) {
        String[] thumbColumns = new String[]{
                MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID
        };

        String[] mediaColumns = new String[]{
                MediaStore.Video.Media.DATA,//绝对路径
                MediaStore.Video.Media._ID,//id
                MediaStore.Video.Media.TITLE,//video标题
                MediaStore.Video.Media.MIME_TYPE,//文件类型
                MediaStore.Video.Media.DURATION,//时长
                MediaStore.Video.Media.DISPLAY_NAME,//文件名
                MediaStore.Video.Media.SIZE//大小
        };

        //首先检索SDcard上所有的video
        Cursor cursor = act.managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);

        ArrayList<Video> videoList = new ArrayList<Video>();

        if (cursor.moveToFirst()) {
            do {
                Video video = new Video();

                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                String display_name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                Long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

                if (!TextUtils.isEmpty(filePath)) {
                    video.setFilePath(filePath);
                }
                if (!TextUtils.isEmpty(mimeType)) {
                    video.setMimeType(mimeType);
                }
                if (!TextUtils.isEmpty(title)) {
                    video.setTitle(title);
                }
                if (!TextUtils.isEmpty(display_name)) {
                    video.setDiaplay_name(display_name);
                }
                if (null != duration) {
                    video.setDuration(duration);
                }
                if (null != size) {
                    video.setSize(size);
                }

                //获取当前Video对应的Id，然后根据该ID获取其Thumb
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                String selection = MediaStore.Video.Thumbnails.VIDEO_ID + "=?";
                String[] selectionArgs = new String[]{
                        id + ""
                };
                Cursor thumbCursor = act.managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, selection, selectionArgs, null);

                if (thumbCursor.moveToFirst()) {
                    String thumbPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
                    if (!TextUtils.isEmpty(thumbPath)) {
                        video.setThumbPath(thumbPath);
                    }
                }
                cursorClose(thumbCursor);
                //文件存在则保存然后将其加入到videoList
                if (FileUtils.ifFileExits(video.getFilePath())) {
                    videoList.add(video);
                }
            } while (cursor.moveToNext());
        }
        cursorClose(cursor);
        return videoList;
    }

    /**
     * SDK版本号小于14手动关闭游标
     */
    private void cursorClose(Cursor cursor) {
        if (Build.VERSION.SDK_INT < 14) {//SDK版本号小于14手动关闭游标
            cursor.close();
        }
    }
}

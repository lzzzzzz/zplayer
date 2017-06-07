package cn.zplayer.mc.Utils.MediaUtils;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.zplayer.mc.Utils.FileUtils;
import cn.zplayer.mc.db.bean.Music;

/**
 * Created by Administrator on 2016/4/29 0029.
 */
public class MusicFactory {

    private static MusicFactory instance;

    public static MusicFactory getInstance() {
        if (null == instance) {
            instance = new MusicFactory();
        }
        return instance;
    }

    /**
     * 从数据库查询视频文件
     */
    public List<Music> getMusics(Activity act) {
        String[] thumbColumns = new String[]{
                MediaStore.Audio.Albums.ALBUM_ID,
                MediaStore.Audio.Albums.ALBUM_ART
        };
        //首先检索SDcard上所有的音乐
        Cursor cursor = act.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        ArrayList<Music> musicList = new ArrayList<Music>();

        if (cursor.moveToFirst()) {
            do {
                Music music = new Music();

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));//查询id
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));//查询歌曲名
                String album  = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));//查询专辑名
                String artist  = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));//查询歌手名
                String url  = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));//查询歌曲文件全路径
                String display_name  = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));//查询歌曲文件的名称
                String year=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR));//查询歌曲发行日期
                int duration =cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));//查询歌曲播放时长
                int size =cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));//查询歌曲文件大小

//                int album_id  = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));//查询专辑id
//                String albums_path=getAlbumsPath(act,thumbColumns,album_id);//查询封面路径
                //判断音乐文件是否无效
                if(!TextUtils.isEmpty(url)){
                    File file=new File(url);
                    if(file.exists()){
                        music.setUrl(url);
                    }else{
                        continue;//如果文件不存在则歌曲无效，不添加到列表
                    }
                }else{
                    continue;//如果文件不存在则歌曲无效，不添加到列表
                }

            if(id>0){//获取id
                music.setId(String.valueOf(id));
            }
            if(duration>0){//获取时长
                music.setDuration(FileUtils.getMediaTime(duration));
            }
                if(size>0){//获取文件大小
                    music.setSize(FileUtils.getFileSize(size));
                }
                if(!TextUtils.isEmpty(title)){//获取标题
                    music.setName(title);
                }
                if(!TextUtils.isEmpty(album)){//获取专辑
                    music.setAlbum(album);
                }
                if(!TextUtils.isEmpty(artist)){//获取歌手名
                    music.setArtist(artist);
                }
                if(!TextUtils.isEmpty(display_name)){//获取文件名称
                    music.setDisplay_name(display_name);
                }
                if(!TextUtils.isEmpty(year)){//获取发行日期
                    music.setYear(year);
                }
               /* if(!TextUtils.isEmpty(albums_path)){//获取发行日期
                    music.setAlbums_path(albums_path);
                }*/
                musicList.add(music);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return musicList;
    }
public String getAlbumsPath(Activity act,String[] thumbColumns,int albums_id){
    String selection = MediaStore.Audio.Albums.ALBUM_ID + "=?";
    String[] selectionArgs = new String[]{
            albums_id + ""
    };
    Cursor albumsCursor = act.managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, thumbColumns, selection, selectionArgs, null);
    String albumsPath="";
    if (albumsCursor.moveToFirst()) {
        String thumbPath = albumsCursor.getString(albumsCursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART));
        if (!TextUtils.isEmpty(thumbPath)) {
            albumsPath=thumbPath;
        }
    }
    albumsCursor.close();
    return albumsPath;
}

    /**
     * @Description 获取专辑封面
     * @param filePath 文件路径，like XXX/XXX/XX.mp3
     * @return 专辑封面bitmap
     */
    public static Bitmap createAlbumArt(final String filePath) {
        Bitmap bitmap = null;
        //能够获取多媒体文件元数据的类
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath); //设置数据源
            byte[] embedPic = retriever.getEmbeddedPicture(); //得到字节型数据
            bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length); //转换为图片
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return bitmap;
    }
}

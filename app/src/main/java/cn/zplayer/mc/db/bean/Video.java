package cn.zplayer.mc.db.bean;

/**
 * Created by Administrator on 2016/4/26 0026.
 */
public class Video {
    String filePath;//路径
    String mimeType;//
    String thumbPath;//缩略图
    String diaplay_name;//文件名
    String title;//vedio标题
    Long duration;//时长
    Long size;//大小单位byte

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiaplay_name() {
        return diaplay_name;
    }

    public void setDiaplay_name(String diaplay_name) {
        this.diaplay_name = diaplay_name;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Video{" +
                "filePath='" + filePath + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", thumbPath='" + thumbPath + '\'' +
                ", diaplay_name='" + diaplay_name + '\'' +
                ", title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                ", size=" + size +
                '}';
    }
}

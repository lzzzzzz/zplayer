package cn.zplayer.mc.db.bean;

/**
 * Created by lz on 2016/4/29 0029.
 */
public class Music {
    private String id;//歌曲id
    private String name;//名称
    private String album;//专辑名
    private String artist;//歌手名
    private String url;//歌曲全路径
    private String display_name;//歌曲文件全名称
    private String year;//发行日期
    private String duration;//总播放时长
    private String size;//歌曲文件大小
    private String albums_path;//专辑封面

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAlbums_path() {
        return albums_path;
    }

    public void setAlbums_path(String albums_path) {
        this.albums_path = albums_path;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", url='" + url + '\'' +
                ", display_name='" + display_name + '\'' +
                ", year='" + year + '\'' +
                ", duration='" + duration + '\'' +
                ", size='" + size + '\'' +
                ", albums_path='" + albums_path + '\'' +
                '}';
    }
}

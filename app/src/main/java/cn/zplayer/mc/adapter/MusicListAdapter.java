package cn.zplayer.mc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import cn.zplayer.mc.R;
import cn.zplayer.mc.Utils.MediaUtils.MusicFactory;
import cn.zplayer.mc.db.bean.Music;

/**
 * Created by LZ on 2016/5/9.
 */
public class MusicListAdapter extends RecyclerView.Adapter{
    private List<Music> musics;
    private LayoutInflater inflater;
    private Context context;
    private OnClickPlayMusicListener onClickPlayListener;

    public MusicListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public MusicListAdapter(Context context, List<Music> musics) {
        this.context = context;
        this.musics = musics;
        inflater = LayoutInflater.from(context);
    }
    public void setOnClickPlayListener(OnClickPlayMusicListener onClickPlayListener) {
        this.onClickPlayListener = onClickPlayListener;
    }
    public void setMusicDatas(List<Music> musics) {
        this.musics = musics;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View svn = inflater.inflate(R.layout.music_list_item_view, parent, false);
        ViewHolder holder = new ViewHolder(svn);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindVideo(position,musics.get(position));
    }

    @Override
    public int getItemCount() {
        return musics==null?0:musics.size();
    }
    private class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView albums_img;
        private ImageView albums_img_play;
        private TextView music_title;//名称
        private TextView music_name;//文件名


        public ViewHolder(View itemView) {
            super(itemView);
            albums_img = (ImageView) itemView.findViewById(R.id.albums_img);
            albums_img_play=(ImageView) itemView.findViewById(R.id.albums_img_play);
            music_title = (TextView) itemView.findViewById(R.id.music_title);
            music_name = (TextView) itemView.findViewById(R.id.music_name);
        }

        public void bindVideo(final int position,final Music music) {
            albums_img.setImageBitmap(MusicFactory.createAlbumArt(music.getUrl()));
            music_title.setText("歌曲名:"+music.getName());
            music_name.setText("文件名:"+music.getDisplay_name());
            albums_img_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickPlayListener != null) {
                        onClickPlayListener.onClickPlay(position,music);
                    }
                }
            });
        }
    }
    public interface OnClickPlayMusicListener {
        void onClickPlay(int position, Music music);
    }

}
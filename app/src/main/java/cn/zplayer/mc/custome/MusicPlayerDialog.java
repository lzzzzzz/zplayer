package cn.zplayer.mc.custome;

import android.app.Dialog;
import android.content.Context;

import cn.zplayer.mc.R;

/**
 * 音乐播放dialog
 */
public class MusicPlayerDialog extends Dialog{
private Context context;
    public MusicPlayerDialog(Context context) {
        super(context);
        this.context=context;
        this.setContentView(R.layout.music_player_dilog_view);
    }

    public MusicPlayerDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
        this.setContentView(R.layout.music_player_dilog_view);
    }
}

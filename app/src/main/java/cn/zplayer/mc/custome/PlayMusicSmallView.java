package cn.zplayer.mc.custome;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.zplayer.mc.R;
import cn.zplayer.mc.Utils.MediaUtils.MusicFactory;
import cn.zplayer.mc.db.bean.Music;

/**
 * 音乐列表底部播放栏自定义控件
 * Created by LZ on 2016/5/9.
 */
public class PlayMusicSmallView extends LinearLayout implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {
    private Context context;
    private TextView music_title;
    private ImageView albums_img;
    private ImageButton play_button;
    private ImageButton play_last_button;
    private ImageButton play_next_button;
    private Music music;
    private MediaPlayer mPlayer;
    private SeekBar seekbar;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private int currentTime;
    private boolean isChanging=false;//互斥变量，防止定时器与SeekBar拖动时进度冲突
    private OnChangeMusicListener onChangeMusicListener;

  /*  private final Runnable mTicker = new Runnable() {
        public void run() {
            onTimeChanged();

            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);

            getHandler().postAtTime(mTicker, next);
        }
    }*/;

    public PlayMusicSmallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View view= inflater.inflate(R.layout.music_small_play_view,this);
        music_title= (TextView) view.findViewById(R.id.music_title);
        albums_img= (ImageView) view.findViewById(R.id.albums_img);
        play_button= (ImageButton) view.findViewById(R.id.play_button);
        play_last_button= (ImageButton) view.findViewById(R.id.play_last_button);
        play_next_button= (ImageButton) view.findViewById(R.id.play_next_button);
        seekbar= (SeekBar) view.findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(this);
        play_button.setOnClickListener(this);
        play_last_button.setOnClickListener(this);
        play_next_button.setOnClickListener(this);
    }
/**设置全局监听器*/
    public void setOnChangeMusicListener(OnChangeMusicListener onChangeMusicListener){
        this.onChangeMusicListener=onChangeMusicListener;
    }
    public void setViewDatas(Music music){
        music_title.setText(music.getName());
        albums_img.setImageBitmap(MusicFactory.createAlbumArt(music.getUrl()));
    }
    public void initMusic(Music music){
    this.music=music;
        setViewDatas(music);
        try {
        if(mPlayer!=null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer=null;
        }
            mPlayer=new MediaPlayer();
            mPlayer.setOnCompletionListener(this);
            mPlayer.setDataSource(music.getUrl());// 设置流媒体的数据源
            mPlayer.prepare();
            seekbar.setMax((int) mPlayer.getDuration());
            currentTime=0;
            seekbar.setProgress(currentTime);
            setTimerSeekBar();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playMusic(Music music){
        setViewDatas(music);
        try {
            if(mPlayer!=null){
                    mPlayer.stop();
                mPlayer.release();
                mPlayer=null;
            }
            mPlayer=new MediaPlayer();
            mPlayer.setDataSource(music.getUrl());// 设置流媒体的数据源
            mPlayer.prepare();
            seekbar.setMax((int) mPlayer.getDuration());
            currentTime=0;
            seekbar.setProgress(currentTime);

            mPlayer.start();
            play_button.setBackgroundResource(R.drawable.icon_music_pause);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public void setTimerSeekBar(){
    mTimer = new Timer();
    mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if(isChanging==true) {
                return;
            }
            if(mPlayer!=null){
                currentTime=(int)mPlayer.getCurrentPosition();
                seekbar.setProgress(currentTime);
            }
        }
    };
    mTimer.schedule(mTimerTask, 0, 500);
}
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_button:
                if(mPlayer.isPlaying()){
                    mPlayer.pause();
                    play_button.setBackgroundResource(R.drawable.icon_music_play);
                }else{
                   /* if(currentTime==mPlayer.getDuration()){
                    mPlayer.seekTo(0);
                    }*/
                    mPlayer.start();
                    play_button.setBackgroundResource(R.drawable.icon_music_pause);
                }
                break;
            case R.id.play_last_button:
                if(onChangeMusicListener!=null){
                    onChangeMusicListener.playLast();
                }
                break;
            case R.id.play_next_button:
                if(onChangeMusicListener!=null){
                    onChangeMusicListener.playNext();
                }
                break;
            default:
            break;
        }
    }
/**资源回收*/
    public void release(){
        /**释放播放器*/
        if(mPlayer!=null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer=null;
        }
        /**释放定时器*/
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }
        /**释放定时器*/
        if(mTimerTask!=null){
            mTimerTask.cancel();
            mTimerTask=null;
        }
    }
/**页面停止，播放暂停*/
    public void onPause(){
        cancleTimer();
    }
    /**释放定时器*/
    public void cancleTimer(){
        /**释放定时器*/
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }
        /**释放定时器*/
        if(mTimerTask!=null){
            mTimerTask.cancel();
            mTimerTask=null;
        }
    }
/**页面返回后继续播放*/
    public void onResume(){
        setTimerSeekBar();
    }
/**进度改变事件*/
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        /**如果进度改变由用户改变则调整播放进度*/
        if(progress!=currentTime){
            if(mPlayer != null){
                mPlayer.seekTo(progress);
            }
}
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isChanging=true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isChanging=true;
    }
/**音乐播放完毕监听*/
    @Override
    public void onCompletion(MediaPlayer mp) {
        mPlayer.seekTo(0);
        mPlayer.pause();
        play_button.setBackgroundResource(R.drawable.icon_music_play);
    }

    public interface OnChangeMusicListener{
        void playLast();
        void playNext();
    }
}

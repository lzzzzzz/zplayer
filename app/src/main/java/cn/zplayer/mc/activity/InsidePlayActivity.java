package cn.zplayer.mc.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;

import cn.zplayer.mc.R;
import cn.zplayer.mc.framework.utils.LogUtils.LogInfo;
import cn.zplayer.mc.view.MyMediaController;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;
/**播放视频activity用于应用内和Url跳转播放*/
public class InsidePlayActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private MyMediaController myMediaController;

    private int mLayout = VideoView.VIDEO_LAYOUT_ZOOM;
    private GestureDetector mGestureDetector;
    public String path;
    private long currentTime = 0;//当前播放时间进度

    public boolean uriFromFlag = true;//true：播放地址来自内部 false：播放地址来自外部

    public static Intent getIntent(Activity act, String path) {
        Intent intent = new Intent(act, InsidePlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(this);
        toggleHideyBar();
        setContentView(R.layout.activity_inside_play_video);
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        resiveUri(getIntent().getData());
        eventBundle(getIntent().getExtras());
        if(null!=savedInstanceState){//获取中途播放进度
            currentTime=savedInstanceState.getInt("currentTime",0);
        }
        if (null != savedInstanceState && savedInstanceState.containsKey("currentTime")) {
            currentTime = savedInstanceState.getLong("currentTime", 0);
        }
        mVideoView = (VideoView) this.findViewById(R.id.video_player);
        myMediaController = new MyMediaController(this,mVideoView,this);
        playVideo(path);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tag", "----------------onResume()");
        mVideoView.seekTo(currentTime);
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag","----------------onPause()");
        currentTime = mVideoView.getCurrentPosition();
        mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tag", "----------------onDestroy()");
        currentTime = 0;
    }

    //保存状态覆盖此方法
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存数据
        outState.putLong("currentTime", currentTime);
    }

    /**
     * 播放视频
     */
    public void playVideo(String path) {
        if (TextUtils.isEmpty(path)) {
            showUriErrorDialog();
            return;
        }
        mVideoView.setVideoPath(path);
//        mVideoView.setMediaController(controller);
        mVideoView.setMediaController(myMediaController);
        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogInfo.d("------------------播放完毕");
                onBackPressed();
            }
        });

        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        mVideoView.start();
    }

    /**
     * 外部跳转应用接收传值
     * 接收scheme格式
     * zpshare://zplayer:1024/mediaplayer?video=path
     */
    protected void resiveUri(Uri uri) {
        if (null == uri) {
            return;
        }
        Log.d("---play", "-----------------------uri:" + uri);
        this.path = uri.getQueryParameter("video");
        uriFromFlag = false;
    }

    /**
     * 接收上个界面传递过来的信息
     *
     * @param bundle
     */
    protected void eventBundle(Bundle bundle) {
        if (uriFromFlag) {
            this.path = bundle.getString("path");
        }
    }

    /**
     * 播放地址出错显示警告dialog
     */
    public void showUriErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("播放地址出错!");
        builder.setTitle("warnning");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                InsidePlayActivity.this.onBackPressed();
            }
        });
        builder.create().show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (mVideoView != null)
            mVideoView.setVideoLayout(mLayout, 0);
        super.onConfigurationChanged(newConfig);
    }


    public void toggleHideyBar() {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (get_current_ui_flags)
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("z-player", "Turning immersive mode mode off. ");
        } else {
            Log.i("z-player", "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }
}

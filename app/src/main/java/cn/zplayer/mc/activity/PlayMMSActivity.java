package cn.zplayer.mc.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;

import cn.zplayer.mc.BaseActivity;
import cn.zplayer.mc.R;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;

/**播放网络音乐界面*/
public class PlayMMSActivity extends BaseActivity {
    MediaPlayer mPlayer;
private String path;
    public boolean uriFromFlag = true;//true：播放地址来自内部 false：播放地址来自外部
    @Override
    protected int getLayoutId() {
        return R.layout.activity_play_mms;
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
     * 外部跳转应用接收传值
     * 接收scheme格式
     * zpshare://zplayer:1024/mediaplayer?mms=path
     */
    protected void resiveUri(Uri uri) {
        if (null == uri) {
            return;
        }
        Log.d("---play", "-----------------------uri:" + uri);
        this.path = uri.getQueryParameter("mms");
        uriFromFlag = false;
    }
    @Override
    protected void initView() {
        MediaController controller = new MediaController(this);//创建控制对象
        this.addContentView(controller, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void initData() {
        String path = "mms://ting.mop.com/mopradio";//猫扑电台地址,这里可以添加自己的喜欢的电台地址,mms协议的
        if(TextUtils.isEmpty(path)){
            showNoMMSDialog();
            return;
        }
        try {
            mPlayer = new MediaPlayer(this);//播放流媒体的对象
            mPlayer.setDataSource(path);//设置流媒体的数据源
            mPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doStart(View view){
        mPlayer.start();//开始播放  
    }  
    public void doStop(View view){  
        mPlayer.stop();//停止播放  
    }  
/**无法播放提示*/
    public void showNoMMSDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
builder.setTitle("提示");
        builder.setMessage("无效播放");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                PlayMMSActivity.this.onBackPressed();
            }
        });
        builder.show();
    }
}  
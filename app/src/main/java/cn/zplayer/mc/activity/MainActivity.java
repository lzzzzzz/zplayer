package cn.zplayer.mc.activity;

import android.view.View;

import cn.zplayer.mc.BaseActivity;
import cn.zplayer.mc.R;
import cn.zplayer.mc.activity.mimeresiveplay.WebSocketTestActivity;
import cn.zplayer.mc.test.TestMainActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.app_name);
        setToolbarNavigationIcon(R.mipmap.ic_launcher);
    }

    public void toScanner(View view) {
        startActivity(ScanMediaActivity.getIntent(this));
    }

    public void toScanMusic(View view) {
        startActivity(ScanMusicActivity.getIntent(this));
    }

    public void toTestTalk(View view) {
        startActivity(WebSocketTestActivity.getIntent(this));
    }

    public void toSeeVideoList(View view) {
        //startActivity(VideoListActivity.getIntent(this));
        startActivity(TestMainActivity.getIntent(this));
    }
}

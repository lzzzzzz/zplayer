package cn.zplayer.mc.activity.mimeresiveplay;

import android.net.Uri;

import cn.zplayer.mc.activity.InsidePlayActivity;

public class AudioPlayActivity extends InsidePlayActivity {

    @Override
    protected void resiveUri(Uri uri) {
        if (null == uri) {
            return;
        }
        super.path = uri.getPath();
        super.uriFromFlag = false;
    }
}

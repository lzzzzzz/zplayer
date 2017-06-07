package cn.zplayer.mc.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.List;

import cn.zplayer.mc.BaseActivity;
import cn.zplayer.mc.R;
import cn.zplayer.mc.Utils.MediaUtils.VideoFactory;
import cn.zplayer.mc.adapter.VideoRecyclerAdapter;
import cn.zplayer.mc.animator.SpaceItemDecoration;
import cn.zplayer.mc.db.bean.Video;
/**视频列表界面*/
public class ScanMediaActivity extends BaseActivity implements VideoRecyclerAdapter.OnClickPlayListener {

    private VideoRecyclerAdapter videoRecyclerAdapter;
    private RecyclerView rl_medias_list;

    public static Intent getIntent(Activity act) {
        Intent intent = new Intent(act, ScanMediaActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_media;
    }

    @Override
    protected void initView() {
        setToolbarTitle(R.string.search_video);
        setToolbarNavigationIcon(R.drawable.ic_left_return);
        rl_medias_list = (RecyclerView) this.findViewById(R.id.rl_medias_list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rl_medias_list.setLayoutManager(mLayoutManager);
        rl_medias_list.addItemDecoration(new SpaceItemDecoration(10));
        videoRecyclerAdapter = new VideoRecyclerAdapter(this);
        videoRecyclerAdapter.setScreenWidth(getScreenWidth());
        videoRecyclerAdapter.setOnClickPlayListener(this);
        rl_medias_list.setAdapter(videoRecyclerAdapter);
        rl_medias_list.setItemAnimator(new DefaultItemAnimator());
        ScanMedia();
    }

    @Override
    public void onClickLeftMenu() {
        onBackPressed();
    }

    public void ScanMedia() {
        List<Video> videoList = VideoFactory.getInstance().getVideos(this);
        for (Video video : videoList) {
            Log.d("scanMedia", "--------------------video" + video.toString());
        }
        videoRecyclerAdapter.setVideoDatas(videoList);
        videoRecyclerAdapter.notifyDataSetChanged();
    }

    public int getScreenWidth() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        return width;
    }

    @Override
    public void onClickPlay(Video video) {
        startActivity(InsidePlay2Activity.getIntent(this, video.getFilePath()));
    }
}

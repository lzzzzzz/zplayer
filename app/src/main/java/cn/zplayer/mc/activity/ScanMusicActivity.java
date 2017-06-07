package cn.zplayer.mc.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import cn.zplayer.mc.BaseActivity;
import cn.zplayer.mc.R;
import cn.zplayer.mc.Utils.ListUtils;
import cn.zplayer.mc.Utils.MediaUtils.MusicFactory;
import cn.zplayer.mc.adapter.MusicListAdapter;
import cn.zplayer.mc.animator.SpaceItemDecoration;
import cn.zplayer.mc.custome.PlayMusicSmallView;
import cn.zplayer.mc.db.bean.Music;
import cn.zplayer.mc.view.RecycleViewDivider;
import io.vov.vitamio.LibsChecker;
/**音乐列表界面*/
public class ScanMusicActivity extends BaseActivity implements MusicListAdapter.OnClickPlayMusicListener, PlayMusicSmallView.OnChangeMusicListener {

    private RecyclerView music_list;
    private MusicListAdapter adapter;
    private MediaPlayer mPlayer;
    private PlayMusicSmallView playView;

    private   List<Music> musics;

    private int currentMusicPosition;//当前播放音乐序列值

    public static Intent getIntent(Activity act){
        Intent intent=new Intent(act,ScanMusicActivity.class);
        return intent;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_music;
    }

    @Override
    protected void initView() {
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
       setTitle("本地音乐");
        setToolbarNavigationIcon(R.drawable.ic_left_return);
        music_list=getView(R.id.music_list);
        playView= getView(R.id.play_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        music_list.setLayoutManager(mLayoutManager);
//        RecycleViewDivider divider= new RecycleViewDivider(
//                this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.color_bg_dark_blue));
//        music_list.addItemDecoration(new SpaceItemDecoration(10));
//        music_list.addItemDecoration(divider);
        adapter = new MusicListAdapter(this);
        adapter.setOnClickPlayListener(this);
        music_list.setAdapter(adapter);
        music_list.setItemAnimator(new DefaultItemAnimator());
        playView.setOnChangeMusicListener(this);
    }
    @Override
    protected void initData() {
        scanMusic();
    }
    /**扫描视频文件并加载列表*/
    public void  scanMusic(){
        musics = MusicFactory.getInstance().getMusics(this);
        if(!ListUtils.isEmpty(musics)){
            currentMusicPosition=0;
            playView.initMusic(musics.get(0));
            playView.setVisibility(View.VISIBLE);
        }else{
            playView.setVisibility(View.GONE);
        }
        adapter.setMusicDatas(musics);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onClickLeftMenu() {
        onBackPressed();
    }
    /**上一曲*/
    @Override
    public void playLast() {
        if(ListUtils.isEmpty(musics)){
            return;
        }
        if(currentMusicPosition>0&&currentMusicPosition<musics.size()){
            io.vov.vitamio.utils.Log.d("music", "-------------------------last currentMusicPosition:" + currentMusicPosition);
            playView.playMusic(musics.get(--currentMusicPosition));
        }
    }
    /**下一曲*/
    @Override
    public void playNext() {
        if(ListUtils.isEmpty(musics)){
            return;
        }
        if(currentMusicPosition>=0&&currentMusicPosition<musics.size()-1){
            io.vov.vitamio.utils.Log.d("music", "------------------------- next currentMusicPosition:" + currentMusicPosition);
            playView.playMusic(musics.get(++currentMusicPosition));
        }
    }
    /**item点击事件*/
    @Override
    public void onClickPlay(int position, Music music) {
        currentMusicPosition=position;
        playView.playMusic(music);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playView.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playView.onPause();
    }
}

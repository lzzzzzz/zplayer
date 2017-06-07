package cn.zplayer.mc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.zplayer.mc.R;
import cn.zplayer.mc.Utils.MediaUtils.VideoFactory;
import cn.zplayer.mc.adapter.VideoAdapter;
import cn.zplayer.mc.db.bean.Video;

public class VideoListActivity extends Activity {

	RecyclerView rlVideoList;
	List<String> videos=new ArrayList<String>();

    public static Intent getIntent(Activity act) {
        Intent intent = new Intent(act, VideoListActivity.class);
        return intent;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		rlVideoList=(RecyclerView) findViewById(R.id.rv_vieo_list);
		LinearLayoutManager layoutManager=new LinearLayoutManager(VideoListActivity.this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		rlVideoList.setLayoutManager(layoutManager);
		videos=getVideos();
		VideoAdapter adapter=new VideoAdapter(VideoListActivity.this, videos);
		rlVideoList.setAdapter(adapter);
	}

	public List<String> getVideos(){
		List<Video> vs=VideoFactory.getInstance().getVideos(this);
		List<String> vsStr=new ArrayList<String>();
		for(Video video:vs){
			vsStr.add(video.getFilePath());
		}
		return vsStr;
	}
}

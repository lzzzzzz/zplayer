package cn.zplayer.mc.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.zplayer.mc.R;
import cn.zplayer.mc.Utils.FileUtils;
import cn.zplayer.mc.animator.FlipInXAnimator;
import cn.zplayer.mc.db.bean.Video;

/**
 * Created by Administrator on 2016/4/26 0026.
 */
public class VideoRecyclerAdapter extends RecyclerView.Adapter {

    private List<Video> videos;
    private LayoutInflater inflater;
    private Context context;
    private int screenWidth;
    private OnClickPlayListener onClickPlayListener;
    private Handler mHandler;

    public VideoRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setOnClickPlayListener(OnClickPlayListener onClickPlayListener) {
        this.onClickPlayListener = onClickPlayListener;
    }

    public VideoRecyclerAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
        inflater = LayoutInflater.from(context);
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setVideoDatas(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View svn = inflater.inflate(R.layout.video_item_view, parent, false);
        ViewHolder holder = new ViewHolder(svn);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindVideo(videos.get(position));
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_thundata_img1;
        private TextView tv_video_name;//名称
        private TextView tv_video_time;//时长和大小


        public ViewHolder(View itemView) {
            super(itemView);
            iv_thundata_img1 = (ImageView) itemView.findViewById(R.id.iv_thundata_img1);
            tv_video_name = (TextView) itemView.findViewById(R.id.tv_video_name);
            tv_video_time = (TextView) itemView.findViewById(R.id.tv_video_time);
        }

        public void bindVideo(final Video video) {
            String title = video.getTitle();
            String videoPath = video.getFilePath();
            Log.d("tag","------------------path:"+videoPath);
            long time = video.getDuration() / 1000;
            long size = video.getSize();
            if (!TextUtils.isEmpty(videoPath)) {
//                Bitmap bmp1 = getVideoThumbnail(videoPath);
//                Drawable bd=new BitmapDrawable(bmp1);
//                iv_thundata_img1.setBackground(bd);
                showImageByThread(iv_thundata_img1,videoPath);
            }

            if (!TextUtils.isEmpty(title)) {
                tv_video_name.setText(title);
            }
            /*long hour = time / 3600;
            long minu = (time - hour * 3600) / 60;
            long sec = time - hour * 3600 - minu * 60;

            String datasize = "";
            double sizecache = size;
            if (size > 1024 * 1024 * 1024) {
                datasize = String.format("%.2f", sizecache / 1024 / 1024 / 1024) + "G";
            } else if (size > 1024 * 1024) {
                datasize = String.format("%.2f", sizecache / 1024 / 1024) + "M";
            } else if (size > 1024) {
                datasize = String.format("%.2f", sizecache / 1024) + "K";
            } else {
                datasize = size + "B";
            }*/
            tv_video_time.setText("时长：" +FileUtils.getMediaTime(time) + "/大小:" + FileUtils.getFileSize(size));
            iv_thundata_img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickPlayListener != null) {
                        onClickPlayListener.onClickPlay(video);
                    }
                }
            });
        }
    }

    public void flipView(final View oldview, final View newView) {
        FlipInXAnimator flip = new FlipInXAnimator();
        AnimatorSet mAnimatorSet = flip.transitionAnimator(oldview);
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                oldview.setVisibility(View.GONE);
                newView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    /**获取视频缩略图
     * @param  filePath 视频路径
     * */
    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        }
        catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                retriever.release();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * Using Thread 异步加载图片
     * @param imageView
     * @param filePath
     */
    public void showImageByThread(final ImageView imageView, final String filePath) {

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bitmap bitmap = (Bitmap) msg.obj;
                if (null!=bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                Log.d("tag","------------------------start new thread");
                Message message = Message.obtain();
                message.obj=getVideoThumbnail(filePath);
                mHandler.sendMessage(message);
            }
        }.start();
    }
    public interface OnClickPlayListener {
        void onClickPlay(Video video);
    }
}

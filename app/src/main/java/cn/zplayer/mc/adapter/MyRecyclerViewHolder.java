package cn.zplayer.mc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**recyclerview item管理器*/
 public class MyRecyclerViewHolder extends RecyclerView.ViewHolder{
	/**item根部局*/
	private View rootView;

	public MyRecyclerViewHolder(View itemView) {
		super(itemView);
		this.rootView=itemView;
		bindView(itemView);
	}
	/**视图管理器*/
	public void bindView(View itemView){}
	/**数据管理*/
	public void bindData(int position){}
}
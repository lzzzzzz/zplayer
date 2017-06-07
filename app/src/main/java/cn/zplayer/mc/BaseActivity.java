package cn.zplayer.mc;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import cn.zplayer.mc.framework.controller.NoDoubleClickListener;
import cn.zplayer.mc.framework.manager.ActivityStack;
import cn.zplayer.mc.framework.utils.LogUtils.LogInfo;


/**
 * Created by Administrator on 2016/1/7 0007.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Toolbar toolbar;
    private TextView tvTitle;

    private long lastClickTime = 0;
    private long currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ActivityStack.getInstance().pushActivity(this);
        eventBundle(getIntent().getExtras());
        initView();
        initData();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    private Toolbar getToolbar() {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.id_toolbar);
//            toolbar.setTitleTextColor(getResources().getColor(R.color.color_gray_text_3));
            setSupportActionBar(toolbar);
        }
        return toolbar;
    }

    private TextView getToolbarTextView() {
        if (tvTitle == null) {
            tvTitle = getView(R.id.tv_title_theme_show);
        }
        return tvTitle;
    }


    /**
     * 添加toolbar标题
     */
    public void setToolbarTitle(int resText) {
        setToolbarTitle(getString(resText));
    }


    /**
     * 添加toolbar返回按键
     */
    public void setToolbarNavigationIcon(int resId) {
        setSupportActionBar(getToolbar());
        getToolbar().setNavigationIcon(resId);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLeftMenu();
            }
        });
    }

    /**
     * 去除 ToolbarNavigationIcon
     */
    public void setToolbarNavigationIcon() {
        if (getToolbar() != null) {
            setSupportActionBar(toolbar);
            getToolbar().setNavigationIcon(null);
        }
    }


    /**
     * toolbar左边按钮点击事件
     */
    public void onClickLeftMenu() {
    }


    /**
     * 添加toolbar标题
     */
    public void setToolbarTitle(String textTitle) {
        getToolbar().setTitle("");
        getToolbarTextView().setText(textTitle);
    }


    /**
     * 隐藏toolBar
     */
    protected void hideTitle() {
        if (getToolbar() == null) {
            return;
        }
        getToolbar().setVisibility(View.GONE);
    }

    @SuppressWarnings("unchecked")
    public final <E extends View> E getView(int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            LogInfo.d("Could not cast View to concrete class." + ex);
            throw ex;
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(View view, int id) {
        return (T) view.findViewById(id);
    }


    /**
     * layout xml文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 接收上个界面传递过来的信息
     *
     * @param bundle
     */
    protected void eventBundle(Bundle bundle) {
    }

    /**
     * 控件初始化
     */
    protected void initView() {
    }


    /**
     * 初始化数据
     */
    protected void initData() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().popActivity(this);
    }

    @Override
    public void onClick(View v) {
        currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > NoDoubleClickListener.MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onClickView(v.getId());
        }
    }

    /**
     * 点击事件转换
     *
     * @param key
     */
    protected void onClickView(int key) {

    }

    /**
     * 隐藏输入键盘
     *
     * @param
     */
    public static void hideSortInput(View v) {
        InputMethodManager inputmanger = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}

package cn.zplayer.mc.framework.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 改变字体样式
 */
public class FontTextView extends TextView {
    private Context mContext;
    private String TypefaceName = "";

    public String getTypefaceName() {
        return TypefaceName;
    }

    public void setTypefaceName(String typefaceName) {
        TypefaceName = typefaceName;
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "font/" + TypefaceName + ".ttf");
        this.setTypeface(typeface);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        int resouceId = attrs.getAttributeResourceValue(null, "typefaceName", 0);
        if (resouceId != 0) {
            TypefaceName = context.getResources().getString(resouceId);
        } else {
            TypefaceName = attrs.getAttributeValue(null, "typefaceName");
        }
        if (TypefaceName != null && !"".equals(TypefaceName)) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + TypefaceName + ".ttf");
            this.setTypeface(typeface);
        }
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        // 先判断是否配置的资源文件
        int resouceId = attrs.getAttributeResourceValue(null, "typefaceName", 0);
        if (resouceId != 0) {
            TypefaceName = context.getResources().getString(resouceId);
        } else {
            TypefaceName = attrs.getAttributeValue(null, "typefaceName");
        }
        if (!TextUtils.isEmpty(TypefaceName)) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + TypefaceName + ".ttf");
            this.setTypeface(typeface);
        }
    }

    public FontTextView(Context context) {
        super(context);
        this.mContext = context;
        // TypefaceName = attrs.getAttributeValue(null, "TypefaceName");
        if (TypefaceName != null && !"".equals(TypefaceName)) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + TypefaceName + ".ttf");
            this.setTypeface(typeface);
        }
    }
}
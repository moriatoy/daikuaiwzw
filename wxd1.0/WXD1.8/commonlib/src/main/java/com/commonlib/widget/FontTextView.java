package com.commonlib.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dmx on 16/10/24.
 */
public class FontTextView extends TextView {
    public FontTextView(Context context) {
        super(context);
        init(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //设置字体图标
        Typeface font = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
        this.setTypeface(font);
    }

}

package com.fuzhuangfaqianla.xinhuahua.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.fuzhuangfaqianla.xinhuahua.R;

/**
 * Created by xiongchang on 2017/9/29.
 */

public class DashLineView extends View {

    public Context ctx;

    public DashLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx=context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setStrokeWidth(dip2px(ctx,2));
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, dip2px(getContext(),120));
        // PathEffect是用来控制绘制轮廓(线条)的方式
        // 代码中的float数组,必须是偶数长度,且>=2,指定了多少长度的实线之后再画多少长度的空白.
        // 如本代码中,绘制长度4的实线,再绘制长度4的空白,再绘制长度4的实线,再绘制长度4的空白,依次重复.0是偏移量,可以不用理会.
        PathEffect effects = new DashPathEffect(new float[]{dip2px(getContext(),4), dip2px(getContext(),8), dip2px(getContext(),4), dip2px(getContext(),8)}, 0);
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

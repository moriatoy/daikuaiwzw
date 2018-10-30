package com.commonlib.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * ListView的基础适配器，继承于BaseAdapter
 *
 * @author Lanyan
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected ArrayList<T> mList;// 列表List
    protected LayoutInflater mInflater;// 布局管理
    protected OnCustomListener listener;// 某个控件的点击事件
    protected DisplayImageOptions options;
    protected ImageLoader imageLoader;
    protected Context mContext;

    protected static final int NO_DEFAULT = -1;// 有图片但是没有默认图
    protected static final int NO_IMAGE = 0;// 没有图片

    public BaseListAdapter(Context context, ArrayList<T> list) {
        this(context, list, 0, 0);
    }


    /**
     * 构造器
     *
     * @param context
     * @param list      起始数据
     * @param defaultId NO_IMAGE为没有图片要显示，NO_DEFAULT为需要显示但没有默认图片，R.drawable.XXX为默认图id
     */
    public BaseListAdapter(Context context, ArrayList<T> list, int defaultId) {
        this(context, list, defaultId, 0);
    }

    /**
     * 构造器
     *
     * @param context
     * @param list      起始数据
     * @param defaultId NO_IMAGE为没有图片要显示，NO_DEFAULT为需要显示但没有默认图片，R.drawable.XXX为默认图id
     * @param radius    图片圆角半径值
     */
    public BaseListAdapter(Context context, ArrayList<T> list,
                           int defaultId, int radius) {
        mContext = context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mList = list;

        if (defaultId == NO_IMAGE) {// 没有图片则无需创建options对象
            return;
        }

        DisplayImageOptions.Builder build = new DisplayImageOptions.Builder();
        build.resetViewBeforeLoading(true).cacheOnDisk(true)
                .cacheInMemory(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565);
        if (defaultId != NO_IMAGE && defaultId != NO_DEFAULT) {// 有图片有默认图
            build.showImageForEmptyUri(defaultId).showImageOnFail(defaultId)
                    .showImageOnLoading(defaultId);
        }
        if (radius > 0) {
            build.displayer(new RoundedBitmapDisplayer(radius));
        }
        options = build.build();
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return (mList == null) ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return (mList == null) ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取该适配器的列表数据
     *
     * @return
     */
    public ArrayList<T> getData() {
        return mList;
    }

    /**
     * 设置适配器上，某个控件的监听事件
     *
     * @param listener
     */
    public void setOnCustomListener(OnCustomListener listener) {
        this.listener = listener;
    }

    public void loadWebImage(ImageView imageview, String url) {
        imageLoader.displayImage(url, imageview, options);
    }

    public void loadWebImage(ImageView imageview, String url, DisplayImageOptions options) {
        imageLoader.displayImage(url, imageview, options);
    }

    public void loadWebImage(ImageView imageview, String url, ImageLoadingListener mListener) {
        imageLoader.displayImage(url, imageview, options, mListener);
    }

    public void loadLocImage(ImageView imageview, String filepath) {
        imageLoader.displayImage("file://" + filepath, imageview, options);
    }


}

package com.commonlib.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.commonlib.R;
import com.commonlib.config.ProjectConfig;


/**
 * Created by xiongchang on 17/7/15.
 */

public class ImageLoaderManager implements IImageLoaderstrategy {
    private static final ImageLoaderManager instance = new ImageLoaderManager();
    private IImageLoaderstrategy loaderstrategy;

    public ImageLoaderManager(){}

    public static ImageLoaderManager getInstance(){
        return instance;
    }

    @Override
    public void showImage(@NonNull ImageLoaderOptions options) {
        if(loaderstrategy!=null){
            loaderstrategy.showImage(options);
        }
    }

    @Override
    public void cleanMemory(Context context) {
        if(loaderstrategy!=null){
            loaderstrategy.cleanMemory(context);
        }
    }

    //在application中初始化
    @Override
    public void init(Context context) {
        //loaderstrategy=new FrescoImageLoader();
        loaderstrategy=new GlideImageLoader();
        loaderstrategy.init(context);
    }

    /**
     * 创建默认的Options，假如不需要使用ImageView ，
     *    请自行new一个Imageview传入即可
     */

    public static ImageLoaderOptions getDefaultOptions(@NonNull View container, @NonNull String url){
        //TODO  过滤非相对地址的url
//        String baseUrl = ProjectConfig.getBaseUrl().substring(0,ProjectConfig.getBaseUrl().length()-1);
//        String sub_str = url.substring(baseUrl.length(),url.length());
//        if(sub_str.startsWith("http")){
//            url = sub_str;
//        }

        ImageLoaderOptions.Builder builder = new ImageLoaderOptions.Builder(container,url);
        builder.placeholder(R.drawable.ic_loading).error(R.drawable.ic_loading).diskCacheStrategy(ImageLoaderOptions.DiskCacheStrategy.DEFAULT);
        if(url.endsWith(".gif")){
            return builder.asGif(true).build();
        }else {
            return builder.isCrossFade(true).build();
            //return builder.isCrossFade(true).blurImage(true).build();
        }
    }

    public static ImageLoaderOptions getResourceOption(@NonNull View container, @NonNull int res, boolean isGif){
        ImageLoaderOptions.Builder builder = new ImageLoaderOptions.Builder(container,res);
        builder.placeholder(R.drawable.ic_loading).error(R.drawable.ic_loading).diskCacheStrategy(ImageLoaderOptions.DiskCacheStrategy.DEFAULT);
        if(isGif){
            return builder.asGif(true).build();
        }else {
            return builder.isCrossFade(true).build();
            //return builder.isCrossFade(true).blurImage(true).build();
        }
    }

    public static ImageLoaderOptions getCircleOptions(@NonNull View container, @NonNull String url){
        if(TextUtils.isEmpty(url)){
            ImageLoaderOptions.Builder builder = new ImageLoaderOptions.Builder(container,R.drawable.ic_avatar_blue);
            builder.isCircleTransform(true).placeholder(R.drawable.ic_avatar_blue).error(R.drawable.ic_avatar_blue).diskCacheStrategy(ImageLoaderOptions.DiskCacheStrategy.DEFAULT);
            return builder.build();
        }

        //TODO  过滤非相对地址的url
        String baseUrl = ProjectConfig.getBaseUrl().substring(0,ProjectConfig.getBaseUrl().length()-1);
        String sub_str = url.substring(baseUrl.length(),url.length());
        if(sub_str.startsWith("http")){
            url = sub_str;
        }

        ImageLoaderOptions.Builder builder = new ImageLoaderOptions.Builder(container,url);
        builder.isCircleTransform(true).placeholder(R.drawable.ic_avatar_blue).error(R.drawable.ic_avatar_blue).diskCacheStrategy(ImageLoaderOptions.DiskCacheStrategy.DEFAULT);
        return builder.build();
    }
}

package com.fuzhuangfaqianla.xinhuahua.ui.me.model;

import android.content.Context;

import com.commonlib.http.BaseModel;
import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.commonlib.http.SchedulerTransformer;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.me.bean.UploadImage;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.MyInfoContract;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Created by xiongchang on 2017/9/27.
 */

public class MyInfoModelImpl implements MyInfoContract.Model {
    private Context context;
    private UploadImage uploadImage;

    public MyInfoModelImpl(Context context){
        this.context = context;
    }

    @Override
    public Observable<UploadImage> uploadImage(String path) {
        List<String> strings = new ArrayList<>();
        strings.add(path);

        return Observable.just(strings).map(new Func1<List<String>, List<File>>() {
            @Override
            public List<File> call(List<String> strings) {
                try {
                    return Luban.with(context).load(strings).get();
                } catch (IOException e) {
                    e.printStackTrace();
                    Observable.error(e);
                }
                return null;
            }
        }).flatMap(new Func1<List<File>, Observable<UploadImage>>() {
            @Override
            public Observable<UploadImage> call(List<File> files) {
                File file = files.get(0);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
                return HttpManager.api.uploadpic(body);
            }
        }).compose(new SchedulerTransformer<UploadImage>()).asObservable();
    }

    @Override
    public Subscription modifyAvatar(String path, DefaultSubscriber<String> subscriber) {
        return uploadImage(path)
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<UploadImage, Observable<BaseModel<String>>>() {
            @Override
            public Observable<BaseModel<String>> call(UploadImage uploadImage) {
                MyInfoModelImpl.this.uploadImage = uploadImage;

                Map<String,String> map = new HashMap<String, String>();
                map.put("url",uploadImage.getUrl());

                return HttpManager.api.modifyAvatar(map);
            }
        }).compose(RxHelper.<String>handleResult()).subscribe(subscriber);
    }

    @Override
    public String getUploadUrl() {
        return uploadImage.getUrl();
    }
}

package com.fuzhuangfaqianla.xinhuahua.ui.me.model;

import android.content.Context;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.SchedulerTransformer;
import com.google.gson.Gson;
import com.fuzhuangfaqianla.xinhuahua.ui.me.bean.AreaBean;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.BasicInfoContract;
import com.fuzhuangfaqianla.xinhuahua.util.GetJsonDataUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by xiongchang on 2017/9/29.
 */

public class BasicInfoModelImpl implements BasicInfoContract.Model {
    private Context context;

    public BasicInfoModelImpl(Context context){
        this.context = context;
    }

    @Override
    public void requestArea(DefaultSubscriber<List<Object>> subscriber) {
        Observable.create(new Observable.OnSubscribe<List<Object>>() {
            @Override
            public void call(Subscriber<? super List<Object>> subscriber) {
                subscriber.onNext(getJsonData());
                subscriber.onCompleted();
            }
        }).compose(new SchedulerTransformer<List<Object>>()).subscribe(subscriber);
    }


    public List<Object> getJsonData(){
        List<Object> list = new ArrayList<>();

        ArrayList<String> options1Items = new ArrayList<>();
        ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(context,"province.json");//获取assets目录下的json文件数据

        ArrayList<AreaBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        for(AreaBean areaBean : jsonBean){
            options1Items.add(areaBean.getName());
        }

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        list.add(options1Items);
        list.add(options2Items);
        list.add(options3Items);

        return list;
    }

    public ArrayList<AreaBean> parseData(String result) {//Gson 解析
        ArrayList<AreaBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                AreaBean entity = gson.fromJson(data.optJSONObject(i).toString(), AreaBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


}

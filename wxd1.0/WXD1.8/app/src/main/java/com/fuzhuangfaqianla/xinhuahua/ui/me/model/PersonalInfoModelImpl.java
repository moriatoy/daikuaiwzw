package com.fuzhuangfaqianla.xinhuahua.ui.me.model;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.PersonalInfoContract;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by xiongchang on 2017/9/29.
 */

public class PersonalInfoModelImpl implements PersonalInfoContract.Model {
    @Override
    public Subscription setUserInfo(User user, DefaultSubscriber<String> subsciber) {
        Map<String,Object> map = new HashMap<>();
        if(user.getRealName()!=null){
            map.put("realName",user.getRealName());
        }
        if(user.getIdNum()!=null){
            map.put("idNum",user.getIdNum());
        }
        if(user.getUserInfo().getGender()!=null){
            map.put("gender",user.getUserInfo().getGender());
        }
        if(user.getAddress()!=null){
            map.put("address",user.getAddress());
        }
        if(user.getUserInfo().getMaritalStatus()!=null){
            map.put("maritalStatus",user.getUserInfo().getMaritalStatus());
        }
        if(user.getUserInfo().getCulturalLevel()!=null){
            map.put("culturalLevel",user.getUserInfo().getCulturalLevel());
        }
        if(user.getUserInfo().getTimeOfBirth()!=null){
            map.put("timeOfBirth",user.getUserInfo().getTimeOfBirth());
        }
        if(user.getUserInfo().getOcp()!=null){
            map.put("ocp",user.getUserInfo().getOcp());
        }
        if(user.getUserInfo().getMonthlyIncome()!=null){
            map.put("monthlyIncome",user.getUserInfo().getMonthlyIncome());
        }
        if(user.getUserInfo().getIncomeForm()!=null){
            map.put("incomeForm",user.getUserInfo().getIncomeForm());
        }
        if(user.getUserInfo().getLocalSocSec()!=null){
            map.put("localSocSec",user.getUserInfo().getLocalSocSec());
        }
        if(user.getUserInfo().getLocalProvidentFund()!=null){
            map.put("localProvidentFund",user.getUserInfo().getLocalProvidentFund());
        }
        if(user.getUserInfo().getCreditCardLimit()!=null){
            map.put("creditCardLimit",user.getUserInfo().getCreditCardLimit());
        }
        if(user.getUserInfo().getProperty()!=null){
            map.put("property",user.getUserInfo().getProperty());
        }
        if(user.getUserInfo().getCar()!=null){
            map.put("car",user.getUserInfo().getCar());
        }
        if(user.getUserInfo().getLoanSuccess()!=null){
            map.put("isLoanSuccess",user.getUserInfo().getLoanSuccess());
        }
        if(user.getUserInfo().getPsnlIns()!=null){
            map.put("psnlIns",user.getUserInfo().getPsnlIns());
        }
        if(user.getUserInfo().getCreditRecord()!=null){
            map.put("creditRecord",user.getUserInfo().getCreditRecord());
        }

        return HttpManager.api.setUserInfo(map).compose(RxHelper.<String>handleResult()).subscribe(subsciber);
    }
}

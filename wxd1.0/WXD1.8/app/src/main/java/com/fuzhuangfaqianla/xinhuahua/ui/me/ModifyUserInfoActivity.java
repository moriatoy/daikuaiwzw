package com.fuzhuangfaqianla.xinhuahua.ui.me;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.commonlib.http.DefaultSubscriber;
import com.commonlib.http.RxHelper;
import com.commonlib.http.SchedulerTransformer;
import com.commonlib.util.DeviceUtil;
import com.commonlib.util.SPUtil;
import com.commonlib.util.ToastUtil;
import com.commonlib.util.VerifyUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class ModifyUserInfoActivity extends AppBaseActivity {
    @BindView(R.id.modify_user_info_activity_et_name)
    EditText etName;
    @BindView(R.id.modify_user_info_activity_et_idnumber)
    EditText etIdNum;
    @BindView(R.id.modify_user_info_activity_btn)
    FancyButton fancyButton;
    Subscription keyboardSubscription;
    String name;
    String idNum;

    public ModifyUserInfoActivity() {
        super(R.layout.activity_modify_user_info);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        name = getIntent().getStringExtra("name");
        idNum = getIntent().getStringExtra("idnumber");
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        initTitlebar();
        initInfo();
        judgeInput();
    }

    private void initInfo() {
        etName.setText(name);
        etIdNum.setText(idNum);
    }

    private void judgeInput() {
        if(TextUtils.isEmpty(name) && TextUtils.isEmpty(idNum)){
            fancyButton.setEnabled(false);
        }else {
            showKeyboard();
        }
    }

    private void showKeyboard() {
        keyboardSubscription = Observable.timer(250, TimeUnit.MILLISECONDS)
                .compose(new SchedulerTransformer<Long>())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        etName.requestFocus();
                        etName.setFocusable(true);
                        DeviceUtil.showInputMethodView(ModifyUserInfoActivity.this,etName);
                    }
                });
    }

    private void initTitlebar() {
        initTitle("个人信息",getResources().getColor(R.color.white),getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.modify_user_info_activity_btn})
    public void onClick(View view){
        if(TextUtils.isEmpty(etName.getText().toString())){
            ToastUtil.toastShortShow("请输入姓名!");
            return;
        }
        if(TextUtils.isEmpty(etIdNum.getText().toString())){

        }else {
            if(!VerifyUtil.IDCardValidate(etIdNum.getText().toString())){
                ToastUtil.toastShortShow("请输入正确的身份证号!");
                return;
            }
        }

//        Map<String,String> map = new HashMap<>();
//        map.put("realName",etName.getText().toString());
//        if(!TextUtils.isEmpty(etIdNum.getText().toString())){
//            map.put("idNum",etIdNum.getText().toString());
//        }
//        HttpManager.api.verfRealName(map)
//                .compose(RxHelper.<String>handleResult())
//                .subscribe(new DefaultSubscriber<String>(_getContext(),true) {
//                    @Override
//                    protected void _onNext(String s) {
//                        ToastUtil.toastShortShow("填写成功");
//                        User user = LoginHelper.getUserInfo();
//                        user.setRealName(etName.getText().toString());
//                        user.setIdNum(etIdNum.getText().toString());
//                        SPUtil.saveObjectToShare("user",user);
//                        setResult(RESULT_OK);
//                        finish();
//                    }
//
//                    @Override
//                    protected void _onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    protected void _onCompleted() {
//
//                    }
//                });

        Map<String,Object> map = new HashMap<>();
        map.put("realName",etName.getText().toString());
        if(!TextUtils.isEmpty(etIdNum.getText().toString())){
            map.put("idNum",etIdNum.getText().toString());
        }
        HttpManager.api.setUserInfo(map).compose(RxHelper.<String>handleResult())
                .subscribe(new DefaultSubscriber<String>(_getContext(),true) {
            @Override
            protected void _onNext(String s) {
                ToastUtil.toastShortShow("填写成功");

                PersonalInfoActivity.user.setRealName(etName.getText().toString());
                PersonalInfoActivity.user.setIdNum(etIdNum.getText().toString());

                User user = LoginHelper.getUserInfo();
                user.setRealName(etName.getText().toString());
                user.setIdNum(etIdNum.getText().toString());
                SPUtil.saveObjectToShare(LoginHelper.USER,user);
                setResult(RESULT_OK);
                finish();
            }

            @Override
            protected void _onError(Throwable e) {

            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    @OnTextChanged({R.id.modify_user_info_activity_et_name,R.id.modify_user_info_activity_et_idnumber})
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!TextUtils.isEmpty(etName.getText().toString()) || !TextUtils.isEmpty(etIdNum.getText().toString())){
            fancyButton.setEnabled(true);
            fancyButton.setClickable(true);
        }else {
            fancyButton.setEnabled(false);
            fancyButton.setClickable(false);
        }
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","个人信息");
        ZhugeHelper.browseOther(this,map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(keyboardSubscription!=null){
            keyboardSubscription.unsubscribe();
        }
    }
}

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
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.http.HttpManager;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class EditNameActivity extends AppBaseActivity {
    @BindView(R.id.edit_name_activity_et)
    EditText editText;
    String name;
    Subscription keyboardSubscription;

    public EditNameActivity() {
        super(R.layout.activity_edit_name);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        name = getIntent().getStringExtra("name");
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        initTitlebar();
        initEditText();
    }

    private void initEditText() {
        editText.setText(name);
    }

    private void initTitlebar() {
        initTitle("昵称", getResources().getColor(R.color.white), getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTitle.setTv_Right("保存", getResources().getColor(R.color.color_33), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText().toString())){
                    ToastUtil.toastShortShow("请输入昵称");
                    return;
                }

                Map<String,String> map=new HashMap<String, String>();
                map.put("userName",editText.getText().toString());
                HttpManager.api.modifyNickname(map)
                        .compose(RxHelper.<String>handleResult())
                        .subscribe(new DefaultSubscriber<String>(EditNameActivity.this,true) {
                    @Override
                    protected void _onNext(String s) {
                        //修改本地数据
                        User user = LoginHelper.getUserInfo();
                        user.setUserName(editText.getText().toString());
                        SPUtil.saveObjectToShare(LoginHelper.USER,user);
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
        });
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","修改昵称");
        ZhugeHelper.browseOther(this,map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showKeyboard();
    }

    private void showKeyboard() {
        keyboardSubscription = Observable.timer(250, TimeUnit.MILLISECONDS)
                .compose(new SchedulerTransformer<Long>())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        editText.requestFocus();
                        editText.setFocusable(true);
                        DeviceUtil.showInputMethodView(EditNameActivity.this,editText);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(keyboardSubscription!=null){
            keyboardSubscription.unsubscribe();
        }
    }
}

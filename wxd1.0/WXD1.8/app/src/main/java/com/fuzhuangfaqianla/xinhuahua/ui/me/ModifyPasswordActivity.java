package com.fuzhuangfaqianla.xinhuahua.ui.me;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonlib.http.SchedulerTransformer;
import com.commonlib.util.DeviceUtil;
import com.commonlib.util.DialogUtil;
import com.commonlib.util.ToastUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.ModifyPasswordContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.presenter.ModifyPasswordPresenterImpl;
import com.fuzhuangfaqianla.xinhuahua.util.ShowPasswordUtil;

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

public class ModifyPasswordActivity extends AppBaseActivity<ModifyPasswordContract.Presenter> implements ModifyPasswordContract.View {
    @BindView(R.id.modify_password_activity_et_phone)
    EditText etPhone;
    @BindView(R.id.modify_password_activity_et_checkcode)
    EditText etCheckCode;
    @BindView(R.id.modify_password_activity_et_password)
    EditText etPassword;
    @BindView(R.id.modify_password_activity_tv_get_checkcode)
    TextView tvGetCheckCode;
    @BindView(R.id.modify_password_activity_btn)
    FancyButton fancyButton;
    @BindView(R.id.modify_password_activity_iv_show_password)
    ImageView ivShowPassword;
    CountDownTimer countDownTimer;
    String from;
    Subscription keyboardSubscription;
    String pageName1 = "找回密码页面";
    String pageName2 = "修改密码";

    public ModifyPasswordActivity() {
        super(R.layout.activity_modify_password);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        from = getIntent().getStringExtra("from");
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        fancyButton.setEnabled(false);
        fancyButton.setClickable(false);
        mPresenter = new ModifyPasswordPresenterImpl(this);
    }

    @Override
    public void showKeyBoard() {
        keyboardSubscription = Observable.timer(250, TimeUnit.MILLISECONDS)
                .compose(new SchedulerTransformer<Long>())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        etPhone.requestFocus();
                        etPhone.setFocusable(true);
                        DeviceUtil.showInputMethodView(ModifyPasswordActivity.this,etPhone);
                    }
                });
    }

    @Override
    public void initTitlebar() {
        if(from.equals("modify")){
            initTitle("修改密码", getResources().getColor(R.color.white), getResources().getColor(R.color.color_33));
        }else if(from.equals("forget")){
            initTitle("忘记密码", getResources().getColor(R.color.white), getResources().getColor(R.color.color_33));
        }

        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initShowPassword() {
        new ShowPasswordUtil(etPassword,ivShowPassword);
    }

    @Override
    public void getCheckCodeSuccessful() {
        ToastUtil.toastShortShow("短信发送成功");
        tvGetCheckCode.setTextColor(getResources().getColor(R.color.color_999999));
        countDownTimer = new CountDownTimer(60000,1000){

            @Override
            public void onTick(long l) {
                tvGetCheckCode.setText(l/1000+"s");
            }

            @Override
            public void onFinish() {
                tvGetCheckCode.setClickable(true);
                tvGetCheckCode.setText("获取验证码");
                tvGetCheckCode.setTextColor(getResources().getColor(R.color.colorMain));
            }
        }.start();
    }

    @Override
    public void getCheckCodeFailed() {
        ToastUtil.toastShortShow("短信发送失败");
        tvGetCheckCode.setClickable(true);
    }

    @Override
    public void modifyPasswordSuccessful() {
        DialogUtil.getAlertDialog(ModifyPasswordActivity.this, "重置密码成功", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }

    @Override
    public void modifyPasswordFailed() {
        ToastUtil.toastShortShow("重置密码失败");
    }

    @OnClick({R.id.modify_password_activity_tv_get_checkcode,R.id.modify_password_activity_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.modify_password_activity_tv_get_checkcode:
                if (TextUtils.isEmpty(etPhone.getText().toString()) || etPhone.getText().toString().length() != 11) {
                    ToastUtil.toastShortShow("请输入正确的手机号!");
                }else {
                    tvGetCheckCode.setClickable(false);

                    //ZhugeIO
                    clickGetCheckCode();

                    Map<String,String> map = new HashMap<>();
                    map.put("phone",etPhone.getText().toString());
                    mPresenter.getCheckCode(map);
                }
                break;

            case R.id.modify_password_activity_btn:
                if (TextUtils.isEmpty(etPhone.getText().toString()) || etPhone.getText().toString().length() != 11) {
                    ToastUtil.toastShortShow("请输入正确的手机号!");
                } else if (TextUtils.isEmpty(etCheckCode.getText().toString())) {
                    ToastUtil.toastShortShow("请输入验证码!");
                } else if(TextUtils.isEmpty(etPassword.getText().toString())){
                    ToastUtil.toastShortShow("请输入密码!");
                }  else {
                    //ZhugeIO
                    clickSubmit();

                    Map<String,String> map = new HashMap<>();
                    map.put("phoneNum",etPhone.getText().toString());
                    map.put("password",etPassword.getText().toString());
                    map.put("checkCode",etCheckCode.getText().toString());
                    mPresenter.modifyPassword(map);
                }
                break;
        }
    }

    private void clickSubmit() {
        if(from.equals("forget")){
            Map<String,String> zhuge_map = new HashMap<>();
            zhuge_map.put("pagename",pageName1);
            zhuge_map.put("name", ZhugeHelper.password_retrieval_submit);
            ZhugeHelper.clickButton(this,zhuge_map);
        }else if(from.equals("modify")){
            Map<String,String> zhuge_map = new HashMap<>();
            zhuge_map.put("pagename",pageName2);
            zhuge_map.put("name", ZhugeHelper.alter_password_submit);
            ZhugeHelper.clickButton(this,zhuge_map);
        }
    }

    private void clickGetCheckCode() {
        if(from.equals("forget")){
            Map<String,String> zhuge_map = new HashMap<>();
            zhuge_map.put("pagename",pageName1);
            zhuge_map.put("name", ZhugeHelper.password_retrieval_get_identifying_code);
            ZhugeHelper.clickButton(this,zhuge_map);
        }else if(from.equals("modify")){
            Map<String,String> zhuge_map = new HashMap<>();
            zhuge_map.put("pagename",pageName2);
            zhuge_map.put("name", ZhugeHelper.alter_password_get_identifying_code);
            ZhugeHelper.clickButton(this,zhuge_map);
        }
    }

    @OnTextChanged({R.id.modify_password_activity_et_phone,R.id.modify_password_activity_et_checkcode,R.id.modify_password_activity_et_password})
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!TextUtils.isEmpty(etPhone.getText().toString()) || !TextUtils.isEmpty(etPassword.getText().toString()) || !TextUtils.isEmpty(etCheckCode.getText().toString())){
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
        if(from.equals("modify")){
            map.put("name","修改密码");
        }else if(from.equals("forget")){
            map.put("name","忘记密码");
        }
        ZhugeHelper.browseOther(this,map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer=null;
        }
        if(keyboardSubscription!=null){
            keyboardSubscription.unsubscribe();
            keyboardSubscription=null;
        }
    }
}

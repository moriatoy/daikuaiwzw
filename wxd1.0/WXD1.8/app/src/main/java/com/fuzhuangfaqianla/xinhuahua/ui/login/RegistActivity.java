package com.fuzhuangfaqianla.xinhuahua.ui.login;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonlib.http.SchedulerTransformer;
import com.commonlib.util.DeviceUtil;
import com.commonlib.util.DialogUtil;
import com.commonlib.util.ToastUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.login.contract.RegistContract;
import com.fuzhuangfaqianla.xinhuahua.ui.login.presenter.RegistPresenterImpl;
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

public class RegistActivity extends AppBaseActivity<RegistContract.Presenter> implements RegistContract.View {
    @BindView(R.id.regist_activity_et_phone)
    EditText etPhone;
    @BindView(R.id.regist_activity_et_checkcode)
    EditText etCheckCode;
    @BindView(R.id.regist_activity_et_password)
    EditText etPassword;
    @BindView(R.id.regist_activity_tv_get_checkcode)
    TextView tvGetCheckCode;
    @BindView(R.id.regist_activity_checkbox)
    CheckBox checkBox;
    @BindView(R.id.regist_activity_btn)
    FancyButton fancyButton;
    @BindView(R.id.regist_activity_iv_show_password)
    ImageView ivShowPassword;
    CountDownTimer countDownTimer;
    Subscription keyboardSubscription;
    String pageName = "注册页面";

    public RegistActivity() {
        super(R.layout.activity_regist);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        fancyButton.setEnabled(false);
        fancyButton.setClickable(false);
        mPresenter = new RegistPresenterImpl(this);
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
                        DeviceUtil.showInputMethodView(RegistActivity.this,etPhone);
                    }
                });
    }

    @Override
    public void initTitlebar() {
        initTitle("注册", getResources().getColor(R.color.white), getResources().getColor(R.color.color_33));
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
    public void registSuccessful() {
        ToastUtil.toastShortShow("注册成功");
        DialogUtil.getAlertDialog(this, "注册成功", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }

    @Override
    public void registFailed() {
        ToastUtil.toastShortShow("注册失败");
    }

    @OnClick({R.id.regist_activity_tv_get_checkcode,R.id.regist_activity_btn,R.id.regist_activity_tv_agreement})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.regist_activity_tv_get_checkcode:
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

            case R.id.regist_activity_btn:
                if (TextUtils.isEmpty(etPhone.getText().toString()) || etPhone.getText().toString().length() != 11) {
                    ToastUtil.toastShortShow("请输入正确的手机号!");
                } else if (TextUtils.isEmpty(etCheckCode.getText().toString())) {
                    ToastUtil.toastShortShow("请输入验证码!");
                } else if(TextUtils.isEmpty(etPassword.getText().toString())){
                    ToastUtil.toastShortShow("请输入密码!");
                } else if(etPassword.getText().toString().length()<8){
                    ToastUtil.toastShortShow("请输入8位以上的密码");
                } else if(!checkBox.isChecked()){
                    ToastUtil.toastShortShow("请先同意花钱啦注册协议!");
                } else {
                    //ZhugeIO
                    clickRegist();

                    Map<String,String> map = new HashMap<>();
                    map.put("phoneNum",etPhone.getText().toString());
                    map.put("password",etPassword.getText().toString());
                    map.put("checkCode",etCheckCode.getText().toString());
                    map.put("source","android");
                    mPresenter.regist(map);
                }
                break;

            case R.id.regist_activity_tv_agreement:
                OpenHelper.startActivity(RegistActivity.this,AgreementActivity.class);
                break;
        }
    }

    private void clickRegist() {
        Map<String,String> zhuge_map3 = new HashMap<>();
        zhuge_map3.put("pageName",pageName);
        zhuge_map3.put("name", ZhugeHelper.register_page_register);
        ZhugeHelper.clickButton(this,zhuge_map3);
    }

    private void clickGetCheckCode() {
        Map<String,String> zhuge_map3 = new HashMap<>();
        zhuge_map3.put("pageName",pageName);
        zhuge_map3.put("name", ZhugeHelper.register_page_get_identifying_code);
        ZhugeHelper.clickButton(this,zhuge_map3);
    }

    @OnTextChanged({R.id.regist_activity_et_phone,R.id.regist_activity_et_checkcode,R.id.regist_activity_et_password})
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
        map.put("name","注册");
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

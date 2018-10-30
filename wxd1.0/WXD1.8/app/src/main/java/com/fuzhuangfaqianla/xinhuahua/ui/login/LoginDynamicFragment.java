package com.fuzhuangfaqianla.xinhuahua.ui.login;


import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.commonlib.http.SchedulerTransformer;
import com.commonlib.rxbus.RxBus;
import com.commonlib.rxbus.RxBusEvent;
import com.commonlib.util.DeviceUtil;
import com.commonlib.util.SPUtil;
import com.commonlib.util.ToastUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseFragment;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.login.contract.LoginDynamicContract;
import com.fuzhuangfaqianla.xinhuahua.ui.login.presenter.LoginDynamicPresenterImpl;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;

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


public class LoginDynamicFragment extends AppBaseFragment<LoginDynamicContract.Presenter> implements LoginDynamicContract.View {
    @BindView(R.id.login_dynamic_fragment_et_phone)
    EditText etPhone;
    @BindView(R.id.login_dynamic_fragment_et_checkcode)
    EditText etCheckCode;
    @BindView(R.id.login_dynamic_fragment_tv_get_checkcode)
    TextView tvGetCheckCode;
    @BindView(R.id.login_dynamic_fragment_checkbox)
    CheckBox checkBox;
    @BindView(R.id.login_dynamic_fragment_btn)
    FancyButton fancyButton;
    CountDownTimer countDownTimer;
    Subscription keyboardSubscription;
    String pageName = "登录—验证码登录";

    public LoginDynamicFragment() {
        super(R.layout.fragment_login_dynamic);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        ButterKnife.bind(this,view);
        fancyButton.setEnabled(false);
        fancyButton.setClickable(false);
        mPresenter = new LoginDynamicPresenterImpl(this);
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
                        DeviceUtil.showInputMethodView(getActivity(),etPhone);
                    }
                });
    }

    @Override
    public void loginSuccessful(User user) {
        //发送广播通知开屏广告已登录
        RxBus.getDefault().post(new RxBusEvent(101,"start_banner_login_successful"));

        //存储值
        if(user.getUserInfo()==null){
            user.setUserInfo(new User.UserInfo());
        }
        SPUtil.saveObjectToShare(LoginHelper.USER, user);
        SPUtil.saveString(LoginHelper.ISLOGIN, "1");
        //设置deviceToken
        RxBus.getDefault().post(new RxBusEvent(0,"set_device_token"));
        getActivity().finish();
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

    @OnClick({R.id.login_dynamic_fragment_tv_get_checkcode,R.id.login_dynamic_fragment_btn,R.id.login_dynamic_fragment_tv_agreement})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_dynamic_fragment_tv_get_checkcode:
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

            case R.id.login_dynamic_fragment_btn:
                if (TextUtils.isEmpty(etPhone.getText().toString()) || etPhone.getText().toString().length() != 11) {
                    ToastUtil.toastShortShow("请输入正确的手机号!");
                }else if (TextUtils.isEmpty(etCheckCode.getText().toString())) {
                    ToastUtil.toastShortShow("请输入验证码!");
                }else if(!checkBox.isChecked()){
                    ToastUtil.toastShortShow("请先同意花钱啦注册协议!");
                }else {
                    //ZhugeIO
                    clickLogin();

                    Map<String,String> map = new HashMap<>();
                    map.put("phoneNum",etPhone.getText().toString());
                    map.put("checkCode",etCheckCode.getText().toString());
                    map.put("source","android");
                    mPresenter.loginByDynamic(map);
                }
                break;

            case R.id.login_dynamic_fragment_tv_agreement:
                OpenHelper.startActivity(getContext(),AgreementActivity.class);
                break;
        }
    }

    private void clickLogin() {
        Map<String,String> zhuge_map3 = new HashMap<>();
        zhuge_map3.put("pageName",pageName);
        zhuge_map3.put("name", ZhugeHelper.login_with_identifying_code);
        ZhugeHelper.clickButton(getContext(),zhuge_map3);
    }

    private void clickGetCheckCode() {
        Map<String,String> zhuge_map3 = new HashMap<>();
        zhuge_map3.put("pageName",pageName);
        zhuge_map3.put("name", ZhugeHelper.login_page_get_identifying_code);
        ZhugeHelper.clickButton(getContext(),zhuge_map3);
    }

    @OnTextChanged({R.id.login_dynamic_fragment_et_phone,R.id.login_dynamic_fragment_et_checkcode})
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!TextUtils.isEmpty(etPhone.getText().toString()) || !TextUtils.isEmpty(etCheckCode.getText().toString())){
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
        map.put("name","短信登录");
        ZhugeHelper.browseOther(getContext(),map);
    }

    @Override
    public void onDestroy() {
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            showKeyBoard();
        }
    }
}

package com.fuzhuangfaqianla.xinhuahua.ui.login;


import android.content.Intent;
import android.graphics.Paint;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonlib.http.SchedulerTransformer;
import com.commonlib.rxbus.RxBus;
import com.commonlib.rxbus.RxBusEvent;
import com.commonlib.util.DeviceUtil;
import com.commonlib.util.SPUtil;
import com.commonlib.util.ToastUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseFragment;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.login.contract.LoginPasswordContract;
import com.fuzhuangfaqianla.xinhuahua.ui.login.presenter.LoginPasswordPresenterImpl;
import com.fuzhuangfaqianla.xinhuahua.ui.me.ModifyPasswordActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;
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

public class LoginPasswordFragment extends AppBaseFragment<LoginPasswordContract.Presenter> implements LoginPasswordContract.View {
    @BindView(R.id.login_password_fragment_tv_regist)
    TextView tvRegist;
    @BindView(R.id.login_password_fragment_tv_find_pw)
    TextView tvFindPw;
    @BindView(R.id.login_password_fragment_et_phone)
    EditText etPhone;
    @BindView(R.id.login_password_fragment_et_password)
    EditText etPassword;
    @BindView(R.id.login_password_fragment_btn)
    FancyButton fancyButton;
    @BindView(R.id.login_password_fragment_iv_show_password)
    ImageView ivShowPassword;
    Subscription timeSubscription;
    String pageName = "登录—密码登录页面";

    public LoginPasswordFragment() {
        super(R.layout.fragment_login_password);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        ButterKnife.bind(this,view);
        fancyButton.setEnabled(false);
        fancyButton.setClickable(false);
        mPresenter = new LoginPasswordPresenterImpl(this);
    }

    @Override
    public void showKeyBoard() {
        timeSubscription = Observable.timer(250, TimeUnit.MILLISECONDS)
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
    public void initTextView() {
        String str="新用户<font color='#00AEFF'>注册</font>";
        tvRegist.setText(Html.fromHtml(str));
        Paint regist_paint = tvRegist.getPaint();
        regist_paint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
        regist_paint.setAntiAlias(true);
        Paint find_paint = tvFindPw.getPaint();
        find_paint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
        find_paint.setAntiAlias(true);
    }

    @Override
    public void initShowPassword() {
        new ShowPasswordUtil(etPassword,ivShowPassword);
    }

    @Override
    public void loginSuccessful(User user) {
        //发送广播通知开屏广告已登录
        RxBus.getDefault().post(new RxBusEvent(101,"start_banner_login_successful"));

        //存储相关值
        if(user.getUserInfo()==null){
            user.setUserInfo(new User.UserInfo());
        }
        SPUtil.saveObjectToShare(LoginHelper.USER, user);
        SPUtil.saveString(LoginHelper.ISLOGIN, "1");
        //设置deviceToken
        RxBus.getDefault().post(new RxBusEvent(0,"set_device_token"));
        getActivity().finish();
    }

    @OnClick({R.id.login_password_fragment_btn,R.id.login_password_fragment_tv_regist,R.id.login_password_fragment_tv_find_pw})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_password_fragment_tv_regist:
                //ZhugeIO
                clickRegist();

                OpenHelper.startActivity(getActivity(),RegistActivity.class);
                break;

            case R.id.login_password_fragment_btn:
                if (TextUtils.isEmpty(etPhone.getText().toString()) || etPhone.getText().toString().length() != 11) {
                    ToastUtil.toastShortShow("请输入正确的手机号!");
                } else if(TextUtils.isEmpty(etPassword.getText().toString())){
                    ToastUtil.toastShortShow("请输入密码!");
                }else {
                    //ZhugeIO
                    clickLogin(pageName, ZhugeHelper.login_with_password);

                    Map<String,String> map = new HashMap<>();
                    map.put("phoneNum",etPhone.getText().toString());
                    map.put("password",etPassword.getText().toString());

                    mPresenter.loginByPassword(map);
                }
                break;

            case R.id.login_password_fragment_tv_find_pw:
                //ZhugeIO
                clickForget();

                Intent intent = new Intent(getActivity(), ModifyPasswordActivity.class);
                intent.putExtra("from","forget");
                OpenHelper.startActivity(getActivity(),intent);
                break;
        }
    }

    private void clickRegist() {
        Map<String,String> zhuge_map1 = new HashMap<>();
        zhuge_map1.put("pageName", pageName);
        zhuge_map1.put("name", ZhugeHelper.login_page_register);
        ZhugeHelper.clickButton(getContext(),zhuge_map1);
    }

    private void clickForget() {
        Map<String,String> zhuge_map3 = new HashMap<>();
        zhuge_map3.put("pageName", pageName);
        zhuge_map3.put("name", ZhugeHelper.password_retrieval);
        ZhugeHelper.clickButton(getContext(),zhuge_map3);
    }

    private void clickLogin(String pagename2, String login_with_password) {
        Map<String, String> zhuge_map2 = new HashMap<>();
        zhuge_map2.put("pageName", pagename2);
        zhuge_map2.put("name", login_with_password);
        ZhugeHelper.clickButton(getContext(), zhuge_map2);
    }

    @OnTextChanged({R.id.login_password_fragment_et_phone,R.id.login_password_fragment_et_password})
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!TextUtils.isEmpty(etPhone.getText().toString()) || !TextUtils.isEmpty(etPassword.getText().toString())){
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
        map.put("name","密码登录");
        ZhugeHelper.browseOther(getContext(),map);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timeSubscription!=null){
            timeSubscription.unsubscribe();
            timeSubscription=null;
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

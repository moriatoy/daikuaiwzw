package com.fuzhuangfaqianla.xinhuahua.ui.me;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.commonlib.http.SchedulerTransformer;
import com.commonlib.util.DeviceUtil;
import com.commonlib.util.ToastUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.FeedbackContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.presenter.FeedbackPresenterImpl;

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

public class FeedbackActivity extends AppBaseActivity<FeedbackContract.Presenter> implements FeedbackContract.View {
    @BindView(R.id.activity_feedback_edittext)
    EditText etContent;
    @BindView(R.id.activity_feedback_submit)
    FancyButton btnSubmit;
    Subscription keyboardSubscription;

    public FeedbackActivity() {
        super(R.layout.activity_feedback);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        btnSubmit.setEnabled(false);
        btnSubmit.setClickable(false);
        mPresenter = new FeedbackPresenterImpl(this);
    }

    @Override
    public void initTitlebar() {
        initTitle("意见反馈", getResources().getColor(R.color.white), getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void showKeyboard() {
        keyboardSubscription = Observable.timer(250, TimeUnit.MILLISECONDS)
                .compose(new SchedulerTransformer<Long>())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        etContent.requestFocus();
                        etContent.setFocusable(true);
                        DeviceUtil.showInputMethodView(FeedbackActivity.this,etContent);
                    }
                });
    }

    @Override
    public void feedbackSuccessful() {
        ToastUtil.toastShortShow("提交成功");
        finish();
    }

    @OnClick({R.id.activity_feedback_submit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_feedback_submit:
                if(TextUtils.isEmpty(etContent.getText().toString())){
                    ToastUtil.toastShortShow("输入内容不能为空");
                    return;
                }
                Map<String,String> map = new HashMap<>();
                map.put("content",etContent.getText().toString());
                mPresenter.feedback(map);
                break;
        }
    }

    @OnTextChanged({R.id.activity_feedback_edittext})
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!TextUtils.isEmpty(etContent.getText().toString())){
            btnSubmit.setEnabled(true);
            btnSubmit.setClickable(true);
        }else {
            btnSubmit.setEnabled(false);
            btnSubmit.setClickable(false);
        }
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","意见反馈");
        ZhugeHelper.browseOther(this,map);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(keyboardSubscription!=null){
            keyboardSubscription.unsubscribe();
            keyboardSubscription=null;
        }
    }
}

package com.fuzhuangfaqianla.xinhuahua.ui.me;


import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseFragment;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdentityInfoFragment extends AppBaseFragment {
    @BindView(R.id.identity_info_fragment_tv_ocp)
    TextView tvOcp;
    @BindView(R.id.identity_info_fragment_tv_monthlyIncome)
    TextView tvMonthlyIncome;
    @BindView(R.id.identity_info_fragment_tv_incomeForm)
    TextView tvIncomeForm;
    @BindView(R.id.identity_info_fragment_tv_localSocSec)
    TextView tvLocalSocSec;
    @BindView(R.id.identity_info_fragment_tv_localProvidentFund)
    TextView tvLocalProvidentFund;
    String[] ocp = new String[]{"上班族", "自由职业", "个体户"};
    String[] monthlyIncome = new String[]{"两千元以下", "两千到四千元", "四千到六千元", "六千到八千元", "八千元以上"};
    String[] incomeForm = new String[]{"银行代发", "转账工资", "现金发放"};
    String[] localSocSec = new String[]{"无", "三个月以下", "连续三个月", "连续六个月"};
    String[] localProvidentFund = new String[]{"无", "三个月以下", "连续三个月", "连续六个月"};

    public IdentityInfoFragment() {
        super(R.layout.fragment_identity_info);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        ButterKnife.bind(this,view);
        initInfo();
    }

    private void initInfo() {
        judgeEmpty(tvOcp,PersonalInfoActivity.user.getUserInfo().getOcp());
        judgeEmpty(tvMonthlyIncome,PersonalInfoActivity.user.getUserInfo().getMonthlyIncome());
        judgeEmpty(tvIncomeForm,PersonalInfoActivity.user.getUserInfo().getIncomeForm());
        judgeEmpty(tvLocalSocSec,PersonalInfoActivity.user.getUserInfo().getLocalSocSec());
        judgeEmpty(tvLocalProvidentFund,PersonalInfoActivity.user.getUserInfo().getLocalProvidentFund());
    }

    @OnClick({R.id.identity_info_fragment_ll_ocp,R.id.identity_info_fragment_ll_monthlyIncome,R.id.identity_info_fragment_ll_incomeForm,R.id.identity_info_fragment_ll_localSocSec,R.id.identity_info_fragment_ll_localProvidentFund})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.identity_info_fragment_ll_ocp:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvOcp.setText(ocp[index]);
                        //
                        PersonalInfoActivity.user.getUserInfo().setOcp(ocp[index]);
                    }
                },ocp);
                break;

            case R.id.identity_info_fragment_ll_monthlyIncome:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvMonthlyIncome.setText(monthlyIncome[index]);
                        //
                        PersonalInfoActivity.user.getUserInfo().setMonthlyIncome(monthlyIncome[index]);
                    }
                },monthlyIncome);
                break;

            case R.id.identity_info_fragment_ll_incomeForm:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvIncomeForm.setText(incomeForm[index]);
                        //
                        PersonalInfoActivity.user.getUserInfo().setIncomeForm(incomeForm[index]);
                    }
                },incomeForm);
                break;

            case R.id.identity_info_fragment_ll_localSocSec:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvLocalSocSec.setText(localSocSec[index]);
                        //
                        PersonalInfoActivity.user.getUserInfo().setLocalSocSec(localSocSec[index]);
                    }
                },localSocSec);
                break;

            case R.id.identity_info_fragment_ll_localProvidentFund:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvLocalProvidentFund.setText(localProvidentFund[index]);
                        //
                        PersonalInfoActivity.user.getUserInfo().setLocalProvidentFund(localProvidentFund[index]);
                    }
                },localProvidentFund);
                break;
        }
    }

    private void judgeEmpty(TextView tv,String str){
        if(TextUtils.isEmpty(str)){
            tv.setText("请选择");
        }else {
            tv.setText(str);
        }
    }

    private void showSheet(ActionSheet.ActionSheetListener actionSheetListener, String... titles){
        ActionSheet.createBuilder(getActivity(), getActivity().getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(titles)
                .setCancelableOnTouchOutside(true)
                .setListener(actionSheetListener).show();
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","身份信息");
        ZhugeHelper.browseOther(getContext(),map);
    }
}

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

public class AssetInfoFragment extends AppBaseFragment {
    @BindView(R.id.asset_info_fragment_tv_creditCardLimit)
    TextView tvCreditCardLimit;
    @BindView(R.id.asset_info_fragment_tv_property)
    TextView tvProperty;
    @BindView(R.id.asset_info_fragment_tv_car)
    TextView tvCar;
    @BindView(R.id.asset_info_fragment_tv_isLoanSuccess)
    TextView tvIsLoanSuccess;
    @BindView(R.id.asset_info_fragment_tv_psnlIns)
    TextView tvPsnlIns;
    @BindView(R.id.asset_info_fragment_tv_creditRecord)
    TextView tvCreditRecord;
    String[] creditCardLimit = new String[]{"无", "三千元以下", "三千到一万元", "一万到三万元", "三万元以上"};
    String[] property = new String[]{"无房产", "有房产可接受抵押", "有房产不接受抵押"};
    String[] car = new String[]{"无车产", "无车准备购买", "有车可接受抵押", "有车不接受抵押"};
    String[] isLoanSuccess = new String[]{"无", "有"};
    String[] psnlIns = new String[]{"无", "投保人寿险且投保两年以下", "投保人寿险且投保两年以上"};
    String[] creditRecord = new String[]{"无信用记录", "信用记录良好", "少量逾期"};

    public AssetInfoFragment() {
        super(R.layout.fragment_asset_info);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        ButterKnife.bind(this,view);
        initInfo();
    }

    private void initInfo() {
        judgeEmpty(tvCreditCardLimit,PersonalInfoActivity.user.getUserInfo().getCreditCardLimit());
        judgeEmpty(tvProperty,PersonalInfoActivity.user.getUserInfo().getProperty());
        judgeEmpty(tvCar,PersonalInfoActivity.user.getUserInfo().getCar());
        if(PersonalInfoActivity.user.getUserInfo().getLoanSuccess()==null){
            tvIsLoanSuccess.setText("请选择");
        }else if(PersonalInfoActivity.user.getUserInfo().getLoanSuccess()) {
            tvIsLoanSuccess.setText("有");
        }else {
            tvIsLoanSuccess.setText("无");
        }
        judgeEmpty(tvPsnlIns,PersonalInfoActivity.user.getUserInfo().getPsnlIns());
        judgeEmpty(tvCreditRecord,PersonalInfoActivity.user.getUserInfo().getCreditRecord());
    }

    @OnClick({R.id.asset_info_fragment_ll_creditCardLimit,R.id.asset_info_fragment_ll_property,R.id.asset_info_fragment_ll_car,R.id.asset_info_fragment_ll_isLoanSuccess,R.id.asset_info_fragment_ll_psnlIns,R.id.asset_info_fragment_ll_creditRecord})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.asset_info_fragment_ll_creditCardLimit:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvCreditCardLimit.setText(creditCardLimit[index]);
                        //
                        PersonalInfoActivity.user.getUserInfo().setCreditCardLimit(creditCardLimit[index]);
                    }
                },creditCardLimit);
                break;

            case R.id.asset_info_fragment_ll_property:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvProperty.setText(property[index]);
                        //
                        PersonalInfoActivity.user.getUserInfo().setProperty(property[index]);
                    }
                },property);
                break;

            case R.id.asset_info_fragment_ll_car:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {


                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvCar.setText(car[index]);
                        //
                        PersonalInfoActivity.user.getUserInfo().setCar(car[index]);
                    }
                },car);
                break;

            case R.id.asset_info_fragment_ll_isLoanSuccess:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvIsLoanSuccess.setText(isLoanSuccess[index]);
                        //
                        if(index==0){
                            PersonalInfoActivity.user.getUserInfo().setLoanSuccess(false);
                        }else if(index==1){
                            PersonalInfoActivity.user.getUserInfo().setLoanSuccess(true);
                        }

                    }
                },isLoanSuccess);
                break;

            case R.id.asset_info_fragment_ll_psnlIns:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvPsnlIns.setText(psnlIns[index]);
                        //
                        PersonalInfoActivity.user.getUserInfo().setPsnlIns(psnlIns[index]);
                    }
                },psnlIns);
                break;

            case R.id.asset_info_fragment_ll_creditRecord:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvCreditRecord.setText(creditRecord[index]);
                        //
                        PersonalInfoActivity.user.getUserInfo().setCreditRecord(creditRecord[index]);
                    }
                },creditRecord);
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
        map.put("name","资产信息");
        ZhugeHelper.browseOther(getContext(),map);
    }
}

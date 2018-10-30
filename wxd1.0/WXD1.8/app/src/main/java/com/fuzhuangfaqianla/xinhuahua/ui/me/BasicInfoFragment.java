package com.fuzhuangfaqianla.xinhuahua.ui.me;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.baoyz.actionsheet.ActionSheet;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.commonlib.util.DateUtil;
import com.commonlib.util.ToastUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseFragment;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.BasicInfoContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.presenter.BasicInfoPresenterImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BasicInfoFragment extends AppBaseFragment<BasicInfoContract.Presenter> implements BasicInfoContract.View {
    @BindView(R.id.basic_info_fragment_tv_name)
    TextView tvName;
    @BindView(R.id.basic_info_fragment_tv_idnum)
    TextView tvIdNumber;
    @BindView(R.id.basic_info_fragment_tv_sex)
    TextView tvSex;
    @BindView(R.id.basic_info_fragment_tv_city)
    TextView tvCity;
    @BindView(R.id.basic_info_fragment_tv_maritalStatus)
    TextView tvMaritalStatus;
    @BindView(R.id.basic_info_fragment_tv_culturalLevel)
    TextView tvCulturalLevel;
    @BindView(R.id.basic_info_fragment_tv_age)
    TextView tvAge;
    String[] sex = new String[]{"男","女"};
    List<Object> area;
    String[] maritalStatus = new String[]{"未婚", "已婚"};
    String[] culturalLevel = new String[]{"本科以上", "大专", "中专", "高中", "初中"};
    final int REQUEST_MODIFY_USER_INFO = 10001;

    public BasicInfoFragment() {
        super(R.layout.fragment_basic_info);
    }

    @Override
    public Context _getContext() {
        return getContext();
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        ButterKnife.bind(this,view);
        mPresenter = new BasicInfoPresenterImpl(this);
    }

    @Override
    public void initInfo() {
        judgeEmpty(tvName,PersonalInfoActivity.user.getRealName());
        judgeEmpty(tvIdNumber,PersonalInfoActivity.user.getIdNum());
        judgeEmpty(tvSex,PersonalInfoActivity.user.getUserInfo().getGender());
        judgeEmpty(tvCity,PersonalInfoActivity.user.getAddress());
        judgeEmpty(tvMaritalStatus,PersonalInfoActivity.user.getUserInfo().getMaritalStatus());
        judgeEmpty(tvCulturalLevel,PersonalInfoActivity.user.getUserInfo().getCulturalLevel());
        //TODO  年龄
        Long timeOfBirth = PersonalInfoActivity.user.getUserInfo().getTimeOfBirth();
        if(timeOfBirth==null){
            tvAge.setText("请选择");
        }else {
            String birth = DateUtil.format(timeOfBirth,"yyyy-MM-dd");
            judgeEmpty(tvAge,birth);
        }
    }

    @OnClick({R.id.basic_info_fragment_ll_name,R.id.basic_info_fragment_ll_idnumber,R.id.basic_info_fragment_ll_sex,R.id.basic_info_fragment_ll_city,R.id.basic_info_fragment_ll_maritalStatus,R.id.basic_info_fragment_ll_culturalLevel,R.id.basic_info_fragment_ll_age})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.basic_info_fragment_ll_name:
            case R.id.basic_info_fragment_ll_idnumber:
                Intent intent = new Intent(getActivity(),ModifyUserInfoActivity.class);
                String name = tvName.getText().toString();
                if(name.equals("请选择")){
                    name = null;
                }
                String idNumber = tvIdNumber.getText().toString();
                if(idNumber.equals("请选择")){
                    idNumber = null;
                }
                intent.putExtra("name",name);
                intent.putExtra("idnumber",idNumber);
                OpenHelper.startActivity(getActivity(),intent,REQUEST_MODIFY_USER_INFO);
                break;

            case R.id.basic_info_fragment_ll_sex:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvSex.setText(sex[index]);
                        //修改全局变量
                        PersonalInfoActivity.user.getUserInfo().setGender(sex[index]);
                    }
                }, sex);
                break;

            case R.id.basic_info_fragment_ll_city:
                if(area==null){
                    mPresenter.requestArea();
                }else {
                    requestAreaSuccessful(area);
                }
                break;

            case R.id.basic_info_fragment_ll_maritalStatus:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvMaritalStatus.setText(maritalStatus[index]);
                        //修改全局变量
                        PersonalInfoActivity.user.getUserInfo().setMaritalStatus(maritalStatus[index]);
                    }
                }, maritalStatus);
                break;

            case R.id.basic_info_fragment_ll_culturalLevel:
                showSheet(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        tvCulturalLevel.setText(culturalLevel[index]);
                        //修改全局变量
                        PersonalInfoActivity.user.getUserInfo().setCulturalLevel(culturalLevel[index]);
                    }
                }, culturalLevel);
                break;

            case R.id.basic_info_fragment_ll_age:
                Calendar currentCalendar = Calendar.getInstance();
                int current_year = currentCalendar.get(Calendar.YEAR);

                Calendar selectedDate = Calendar.getInstance();
                Calendar startDate = Calendar.getInstance();
                startDate.set(current_year-100, 0, 1);
                Calendar endDate = Calendar.getInstance();
                endDate.set(current_year, 11, 1);
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        tvAge.setText(DateUtil.dateToStr(date,"yyyy-MM-dd"));

                        //转成时间戳
                        long time = DateUtil.stringToLong(tvAge.getText().toString(),"yyyy-MM-dd");
                        //
                        PersonalInfoActivity.user.getUserInfo().setTimeOfBirth(time);
                    }
                })
                        //年月日时分秒 的显示与否，不设置则默认全部显示
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setLabel("", "", "", "", "", "")
                        .isCenterLabel(false)
                        .setDate(selectedDate)
                        .setRangDate(startDate, endDate)
                        .setTitleText("出生年月")
                        .build()
                        ;
                pvTime.show();
                break;
        }
    }

    @Override
    public void requestAreaSuccessful(List<Object> datas) {
        area = datas;

        final ArrayList<String> options1Items = (ArrayList<String>) datas.get(0);
        final ArrayList<ArrayList<String>> options2Items = (ArrayList<ArrayList<String>>) datas.get(1);
//        final ArrayList<ArrayList<ArrayList<String>>> options3Items = (ArrayList<ArrayList<ArrayList<String>>>) datas.get(2);

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String address = options1Items.get(options1)+
                        options2Items.get(options1).get(options2);

                tvCity.setText(address);
                //修改全局变量
                PersonalInfoActivity.user.setAddress(address);

            }
        }).setTitleText("城市选择").build();

        pvOptions.setPicker(options1Items, options2Items);//三级选择器
        pvOptions.show();
    }

    @Override
    public void requestAreaFailed() {
        ToastUtil.toastShortShow("解析失败");
    }

    private void showSheet(ActionSheet.ActionSheetListener actionSheetListener, String... titles){
        ActionSheet.createBuilder(getActivity(), getActivity().getSupportFragmentManager())
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(titles)
                .setCancelableOnTouchOutside(true)
                .setListener(actionSheetListener).show();
    }

    private void judgeEmpty(TextView tv,String str){
        if(TextUtils.isEmpty(str)){
            tv.setText("请选择");
        }else {
            tv.setText(str);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_MODIFY_USER_INFO && resultCode==getActivity().RESULT_OK){
            User user = LoginHelper.getUserInfo();
            if(TextUtils.isEmpty(user.getRealName())){
                tvName.setText("请选择");
            }else {
                tvName.setText(user.getRealName());
            }
            if(TextUtils.isEmpty(user.getIdNum())){
                tvIdNumber.setText("请选择");
            }else {
                tvIdNumber.setText(user.getIdNum());
            }
        }
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","基本信息");
        ZhugeHelper.browseOther(getContext(),map);
    }
}

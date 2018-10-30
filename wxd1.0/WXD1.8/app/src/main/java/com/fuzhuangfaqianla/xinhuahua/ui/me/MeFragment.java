package com.fuzhuangfaqianla.xinhuahua.ui.me;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonlib.imageloader.ImageLoaderManager;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppLazyBaseFragment;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.MeContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.presenter.MePresenterImpl;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeFragment extends AppLazyBaseFragment<MeContract.Presenter> implements MeContract.View {
    @BindView(R.id.me_fragment_iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.me_fragment_tv_info)
    TextView tvInfo;

    public MeFragment() {
        super(R.layout.fragment_me);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        ButterKnife.bind(this,view);
        mPresenter = new MePresenterImpl(this);
    }

    @Override
    public void initTitlebar() {
    }

    @Override
    public void initUserInfo() {
        if(LoginHelper.isLogin()){
            User user = LoginHelper.getUserInfo();
            if(user!=null){
                ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getCircleOptions(ivAvatar,user.getAvatar()));
                tvInfo.setText(user.getUserName());
            }
        }else {
            ivAvatar.setImageResource(R.drawable.ic_avatar_blue);
            tvInfo.setText("点击登录");
        }
    }

    @OnClick({R.id.me_fragment_iv_avatar,R.id.me_fragment_ll_user_info, R.id.me_fragment_ll_feedback,R.id.me_fragment_ll_browse,R.id.me_fragment_ll_setting})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.me_fragment_iv_avatar:
                if(LoginHelper.isLogin()){
                    OpenHelper.startActivity(getActivity(), MyInfoActivity.class);
                }else {
                    LoginHelper.unLoginGoToLoginActivity(getActivity());
                }
                break;

            case R.id.me_fragment_ll_user_info:
                if(LoginHelper.isLogin()){
                    OpenHelper.startActivity(getActivity(), PersonalInfoActivity.class);
                }else {
                    LoginHelper.unLoginGoToLoginActivity(getActivity());
                }

                break;

            case R.id.me_fragment_ll_browse:
                if(LoginHelper.isLogin()){
                    OpenHelper.startActivity(getActivity(),BrowseActivity.class);
                }else {
                    LoginHelper.unLoginGoToLoginActivity(getActivity());
                }
                break;

            case R.id.me_fragment_ll_feedback:
                if(LoginHelper.isLogin()){
                    OpenHelper.startActivity(getActivity(),FeedbackActivity.class);
                }else {
                    LoginHelper.unLoginGoToLoginActivity(getActivity());
                }
                break;

            case R.id.me_fragment_ll_setting:
                OpenHelper.startActivity(getActivity(),SettingActivity.class);
                break;
        }
    }


    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","我的");
        ZhugeHelper.browseOther(getContext(),map);
    }
}

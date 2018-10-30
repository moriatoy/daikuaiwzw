package com.fuzhuangfaqianla.xinhuahua.ui.me;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.commonlib.config.ProjectConfig;
import com.commonlib.imageloader.ImageLoaderManager;
import com.commonlib.imagepicker.ImagePickerUtil;
import com.commonlib.util.MyLogUtil;
import com.commonlib.util.SPUtil;
import com.commonlib.util.ToastUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.login.bean.User;
import com.fuzhuangfaqianla.xinhuahua.ui.me.contract.MyInfoContract;
import com.fuzhuangfaqianla.xinhuahua.ui.me.presenter.MyInfoPresenterImpl;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MyInfoActivity extends AppBaseActivity<MyInfoContract.Presenter> implements MyInfoContract.View {
    @BindView(R.id.my_info_activity_iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.my_info_activity_tv_nickname)
    TextView tvNickname;
    @BindView(R.id.my_info_activity_tv_phone)
    TextView tvPhone;

    public MyInfoActivity() {
        super(R.layout.activity_my_info);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        mPresenter = new MyInfoPresenterImpl(this);
    }

    @Override
    public void initTitlebar() {
        initTitle("我的信息", getResources().getColor(R.color.white), getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initUserInfo() {
        User user = LoginHelper.getUserInfo();
        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getCircleOptions(ivAvatar,user.getAvatar()));
        tvNickname.setText(user.getUserName());
        tvPhone.setText(user.getPhoneNum());
    }

    @Override
    public void modifyAvatarSuccessful() {
        ToastUtil.toastShortShow("修改成功");
        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getCircleOptions(ivAvatar,mPresenter.getUploadUrl()));
        //修改本地存储
        User user = LoginHelper.getUserInfo();
        user.setAvatar(mPresenter.getUploadUrl());
        SPUtil.saveObjectToShare(LoginHelper.USER,user);
    }

    @OnClick({R.id.activity_myinfo_rl_headview,R.id.activity_myinfo_rl_nickview})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_myinfo_rl_headview:
                MyInfoActivityPermissionsDispatcher.modifyAvatarWithPermissionCheck(this);
                break;

            case R.id.activity_myinfo_rl_nickview:
                Intent intent = new Intent(MyInfoActivity.this,EditNameActivity.class);
                intent.putExtra("name",tvNickname.getText().toString());
                OpenHelper.startActivity(MyInfoActivity.this,intent);
                break;
        }
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","我的信息");
        ZhugeHelper.browseOther(this,map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && (requestCode == ProjectConfig.REQUES_OPEN_GALLERY || requestCode == ProjectConfig.REQUES_OPEN_CAMERA)) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                MyLogUtil.i("test",images.get(0).path);

                //开始上传
                mPresenter.modifyAvatar(images.get(0).path);
            }
        }
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void modifyAvatar(){
        ImagePickerUtil.chooseImage(this,getSupportFragmentManager());
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void showRationaleForPermission(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("需要获取一些权限")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void showDeniedForPermission() {
        ToastUtil.toastShortShow("获取权限失败");
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void showNeverAskForPermission() {
        ToastUtil.toastShortShow("获取权限失败");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyInfoActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }
}

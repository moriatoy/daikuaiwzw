package com.fuzhuangfaqianla.xinhuahua.ui.me;

import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonlib.config.ProjectConfig;
import com.commonlib.util.DialogUtil;
import com.commonlib.util.ToastUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class VipActivity extends AppBaseActivity {
    @BindView(R.id.communication_activity_iv_code)
    ImageView iv;
    @BindView(R.id.communication_activity_ll_code)
    LinearLayout llCode;
    @BindView(R.id.communication_activity_tv_code)
    TextView tvCode;
    ProgressDialog progressDialog;

    public VipActivity() {
        super(R.layout.activity_vip);
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","Vip交流群");
        ZhugeHelper.browseOther(this,map);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        initTitlebar();
    }

    private void initTitlebar() {
        initTitle("Vip交流群",getResources().getColor(R.color.white),getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.communication_activity_ll_code,R.id.communication_activity_iv_code})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.communication_activity_ll_code:
                ClipboardManager copyManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                copyManager.setText(tvCode.getText().toString());
                ToastUtil.toastShortShow("复制成功");
                break;
            case R.id.communication_activity_iv_code:
                ToastUtil.toastShortShow("长按可保存到本地");
                break;
        }
    }

    @OnLongClick({R.id.communication_activity_iv_code})
    public boolean onLongClick(View v) {
        DialogUtil.getAlertDialog(VipActivity.this, "", "是否保存到本地?", "确定", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = showProgressDialog("保存中...");

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.qrcode);
                saveToLocal(bitmap);
            }
        }).show();
        return true;
    }

    private void saveToLocal(Bitmap bitmap) {
        File bitmapDir = new File(ProjectConfig.DIR_IMG);
        if(!bitmapDir.exists()){
            bitmapDir.mkdirs();
        }

        File imageFile = new File(bitmapDir,System.currentTimeMillis()+".png");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressDialog(progressDialog);
            ToastUtil.toastShortShow("保存失败");
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    hideProgressDialog(progressDialog);
                    ToastUtil.toastShortShow("保存失败");
                }
            }
        }

        bitmap.recycle();
        //保存图片后发送广播通知更新数据库
        Uri uri = Uri.fromFile(imageFile);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

        ToastUtil.toastShortShow("保存成功");
        hideProgressDialog(progressDialog);
    }
}

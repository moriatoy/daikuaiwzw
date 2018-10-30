package com.fuzhuangfaqianla.xinhuahua.ui.me;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUsActivity extends AppBaseActivity {
    @BindView(R.id.about_us_activity_tv_version)
    TextView tvVersion;

    public AboutUsActivity() {
        super(R.layout.activity_about_us);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);

        initTitlebar();
        initVersion();
    }

    private void initVersion() {
        tvVersion.setText(getVersion());
    }

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void initTitlebar() {
        initTitle("关于我们", getResources().getColor(R.color.white),getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.about_us_activity_ll_pinfen,R.id.about_us_activity_ll_about})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.about_us_activity_ll_pinfen:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.about_us_activity_ll_about:
                OpenHelper.startActivity(AboutUsActivity.this, AboutCompanyActivity.class);
                break;
        }
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","关于我们");
        ZhugeHelper.browseOther(this,map);
    }
}

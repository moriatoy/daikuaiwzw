package com.fuzhuangfaqianla.xinhuahua.ui.main;

import android.view.View;
import android.widget.TextView;

import com.commonlib.util.DateUtil;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.main.bean.Message;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageDetailActivity extends AppBaseActivity {
    Message message;
    @BindView(R.id.activity_message_detail_title)
    TextView tvTitle;
    @BindView(R.id.activity_message_detail_time1)
    TextView tvTime1;
    @BindView(R.id.activity_message_detail_content)
    TextView tvContent;
    @BindView(R.id.activity_message_detail_time2)
    TextView tvTime2;
    @BindView(R.id.activity_message_detail_tv_created_by)
    TextView tvCreatedBy;

    public MessageDetailActivity() {
        super(R.layout.activity_message_detail);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        message = (Message) getIntent().getSerializableExtra("data");
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        initTitlebar();
        bindData();
    }

    private void bindData() {
        tvTitle.setText(message.getTitle());
        tvTime1.setText("接收时间"+ DateUtil.format(message.getSendTime(),"yyyy-MM-dd HH:mm:ss"));
        tvContent.setText(message.getContent());
        tvTime2.setText(DateUtil.format(message.getSendTime(),"yyyy年MM月dd日"));
        if(message.getCreateBy()==null){
            tvCreatedBy.setText("花钱啦团队");
        }else {
            tvCreatedBy.setText(message.getCreateBy());
        }
    }

    private void initTitlebar() {
        initTitle("消息详情", getResources().getColor(R.color.white), getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","消息详情");
        ZhugeHelper.browseOther(this,map);
    }
}

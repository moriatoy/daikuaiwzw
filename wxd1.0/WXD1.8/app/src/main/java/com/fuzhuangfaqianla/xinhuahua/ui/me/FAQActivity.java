package com.fuzhuangfaqianla.xinhuahua.ui.me;

import android.view.View;
import android.widget.ExpandableListView;

import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.me.holder.FAQAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FAQActivity extends AppBaseActivity {
    @BindView(R.id.faq_activity_listview)
    ExpandableListView listview;
    List<String> contentList;
    List<List<String>> childList;
    List<String> groupList;
    FAQAdapter adapter;

    public FAQActivity() {
        super(R.layout.activity_faq);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
        initTitlebar();
        initData();
        initListView();
    }

    private void initListView() {
        adapter = new FAQAdapter(this,childList,groupList);
        listview.setAdapter(adapter);
    }

    private void initTitlebar() {
        initTitle("常见问题",getResources().getColor(R.color.white),getResources().getColor(R.color.black));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        childList = new ArrayList<>();
        groupList = new ArrayList<>();

        groupList.add("如何申请贷款？");
        contentList = new ArrayList<>();
        contentList.add("您可以根据需求在“首页”板块选择贷款产品，我们建议您选择符合个人信息和需求的贷款产品，并根据此贷款产品的要求填写完资料并提交申请。");
        childList.add(contentList);

        groupList.add("申请贷款后多久能出审核结果？");
        contentList = new ArrayList<>();
        contentList.add("您完成申请后，申请会被立即递交给机构，各个产品的审核时间不同。请您耐心等待。若超出审核时间依然没出审核结果，您可申请其他产品缓解资金需求。");
        childList.add(contentList);

        groupList.add("如何提高贷款成功率？");
        contentList = new ArrayList<>();
        contentList.add("A、请你结合自己的资质正确地选择我们为您准备的贷款分类可大大提高成功率。\n" +
                "B、可在“我的”—“信用信息”和“个人认证”板块填写提交资料，有助于大幅提高成功率。\n" +
                "C、数据表明：申请多个产品，可大幅提高贷款成功率；您可以尝试同时申请几个贷款产品。\n");
        childList.add(contentList);

        groupList.add("申请贷款的利率和额度是多少？");
        contentList = new ArrayList<>();
        contentList.add("平台推荐的众多贷款产品，利率和额度各有不同：\n" +
                "①一般参考月利率范围在1%~1.5%，建议在申请时查看详细介绍;\n" +
                "②额度一般都在500元至50万元，您可以根据自身的资金需求自主申请;\n" +
                "特别提醒您，信贷机构会根据您的个人资质给出最终的贷款额度和利率，请以放款前的确认信息为准。\n");
        childList.add(contentList);

        groupList.add("贷款成功后，如何还款？");
        contentList = new ArrayList<>();
        contentList.add("根据贷款产品所属机构不同，还款方式也可能不同。有些机构会从您申请放款的银行卡中按期扣收，有些机构会要求您在该机构的注册账户中按期储值进行还款。机构放款前会与您确认，请关注确认信息；如果还有疑问，可以直接致电相应的贷款机构。");
        childList.add(contentList);

        groupList.add("平台是否有还款提醒？");
        contentList = new ArrayList<>();
        contentList.add("在“我的”—“我的申请”中可查看“已申请”和“已浏览”。");
        childList.add(contentList);

        groupList.add("为什么审批下来的金额和期限与申请信息不一致？");
        contentList = new ArrayList<>();
        contentList.add("信贷机构会根据您的个人资料、征信记录，并参考您的申请信息，进行综合评估得出审批结果，因此可能与您的申请信息产生出入；最终放款额将以信贷机构给出的确认批复为准，如果未能完全满足您的资金需求，建议多申请几笔其他产品作为备用金。");
        childList.add(contentList);

        groupList.add("我想要消除认证信息、个人资料怎么办？");
        contentList = new ArrayList<>();
        contentList.add("所有认证信息一个月以后会自动失效并消除。请您放心，您的信息只会用于贷款资格审核，我们会对您的个人信息严格保密。");
        childList.add(contentList);

        groupList.add("为什么我的申请没有通过审核？");
        contentList = new ArrayList<>();
        contentList.add("每家信贷机构的审批侧重点和风控政策都各不相同。所以申请某家机构的产品未通过也不要气馁，我们建议您多申请几家不同的机构，会提高审批通过率哦。");
        childList.add(contentList);

        groupList.add("为什么我已经还款了，状态还是显示“待还款”？");
        contentList = new ArrayList<>();
        contentList.add("还款成功到状态更新会有一定时差，一般还款成功24小时内会更新状态。只要您确认还款银行卡中扣除了应还金额，或者收到了还款成功的提醒短信即为还款成功。不会影响正常结清，请您稍安勿躁哦~");
        childList.add(contentList);
    }


    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name","常见问题");
        ZhugeHelper.browseOther(this,map);
    }
}

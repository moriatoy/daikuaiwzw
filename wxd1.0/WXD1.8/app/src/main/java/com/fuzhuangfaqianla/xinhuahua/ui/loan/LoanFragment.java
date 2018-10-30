package com.fuzhuangfaqianla.xinhuahua.ui.loan;


import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonlib.util.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppLazyBaseFragment;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.inter.CustomRefreshListener;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.bean.Sort;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.contract.LoanContract;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.holder.ChooseAdapter;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.holder.LoanViewBinder;
import com.fuzhuangfaqianla.xinhuahua.ui.loan.presenter.LoanPresenterImpl;
import com.fuzhuangfaqianla.xinhuahua.helper.RefreshHelper;
import com.fuzhuangfaqianla.xinhuahua.util.AnimUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.MultiTypeAdapter;

public class LoanFragment extends AppLazyBaseFragment<LoanContract.Presenter> implements LoanContract.View,ChooseAdapter.ChooseItemClickListener {
    @BindView(R.id.loan_fragment_activity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.loan_fragment_activity_appbarlayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.loan_fragment_refreshlayout)
    TwinklingRefreshLayout refreshLayout;
    @BindView(R.id.loan_fragment_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.loan_fragment_activity_iv_choose1)
    ImageView ivChoose1;
    @BindView(R.id.loan_fragment_activity_iv_choose2)
    ImageView ivChoose2;
    @BindView(R.id.loan_fragment_activity_iv_choose3)
    ImageView ivChoose3;
    @BindView(R.id.loan_fragment_recyclerview_choose)
    RecyclerView chooseRecyclerView;
    @BindView(R.id.loan_fragment_ll_show_choose)
    LinearLayout llShowChoose;
    @BindView(R.id.loan_fragment_ll_choose)
    LinearLayout llChoose;
    @BindView(R.id.loan_fragment_ll_show_choose_shadow)
    View shadowView;
    @BindView(R.id.loan_fragment_activity_tv_choose1)
    TextView tvChoose1;
    @BindView(R.id.loan_fragment_activity_tv_choose2)
    TextView tvChoose2;
    @BindView(R.id.loan_fragment_activity_tv_choose3)
    TextView tvChoose3;
    @BindView(R.id.loan_fragment_activity_fl_placeholder)
    FrameLayout flContent;
    View view;
    MultiTypeAdapter adapter;
    ChooseAdapter chooseAdapter;
    List<Sort> ocp_list;
    List<Sort> loanType_list;
    List<Sort> money_list;
    Map<String,String> select_map = new HashMap<>();
    int ocp_pos = 0;
    int loan_pos = 0;
    int money_pos = 0;
    String current_show = "";
    String first = "first";
    String second = "second";
    String third = "third";
    String pageName = "贷款大全";
    int page = 1;

    public LoanFragment() {
        super(R.layout.fragment_loan);
    }

    @Override
    public void loadData() {
        startToRefresh();

        //请求筛选条件
        Map<String,String> ocp_map = new HashMap<>();
        ocp_map.put("tagType", Uri.decode("身份"));
        mPresenter.getTagsByType("ocp",ocp_map);

        Map<String,String> loan_map = new HashMap<>();
        loan_map.put("tagType", Uri.decode("贷款类型"));
        mPresenter.getTagsByType("loantype",loan_map);

        Map<String,String> money_map = new HashMap<>();
        money_map.put("tagType", Uri.decode("金额"));
        mPresenter.getTagsByType("money",money_map);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        this.view = view;
        ButterKnife.bind(this,view);
        mPresenter = new LoanPresenterImpl(this);
    }

    @Override
    public void initLoadSir(View view) {
        super.initLoadSir(view);
        ButterKnife.bind(this,view);
        mLoadService = LoadSir.getDefault().register(flContent, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                startToRefresh();
            }
        });
    }

    @Override
    public void initTitlebar() {
        ImmersionBar.setTitleBar(getActivity(),toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        initTitle("贷款",view,getResources().getColor(R.color.white),getResources().getColor(R.color.color_33));
    }

    @Override
    public void initRefreshLayout() {
        RefreshHelper.initRefreshLayout(refreshLayout, true, new CustomRefreshListener() {
            @Override
            public void _onRefresh(TwinklingRefreshLayout refreshLayout) {
                startToRefresh();
            }

            @Override
            public void _onLoadMore(TwinklingRefreshLayout refreshLayout) {
                page++;
                if(page>mPresenter.getPageCount()){
                    ToastUtil.toastShortShow("已加载全部数据");
                    hideLoadMoreLayout();
                    return;
                }

                select_map.put("isPage","1");
                select_map.put("page",page +"");
                mPresenter.loadMore(select_map);
            }
        });
    }

    private void startToRefresh() {
        page = 1;

        select_map.put("isPage","1");
        select_map.put("page",page +"");
        mPresenter.refreshRecyclerView(select_map);
    }

    @Override
    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MultiTypeAdapter();
        adapter.register(OnlineLoan.class, new LoanViewBinder());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void refreshRecyclerView(List<Object> datas) {
        adapter.setItems(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideRefreshLayout() {
        RefreshHelper.hideRefreshLayout(refreshLayout);
    }

    @Override
    public void hideLoadMoreLayout() {
        RefreshHelper.hideLoadMorLayout(refreshLayout);
    }

    @Override
    public void initChooseRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chooseRecyclerView.setLayoutManager(linearLayoutManager);
        chooseRecyclerView.setItemAnimator(new DefaultItemAnimator());
        chooseAdapter = new ChooseAdapter();
        chooseRecyclerView.setAdapter(chooseAdapter);
        chooseAdapter.setChooseItemClickListener(this);
    }

    @Override
    public void requestOcpSuccessful(List<Sort> sorts) {
        ocp_list = sorts;
        chooseAdapter.addAll(ocp_list,true);
    }

    @Override
    public void requestLoanTypeSuccessful(List<Sort> sorts) {
        loanType_list = sorts;
        chooseAdapter.addAll(loanType_list,true);
    }

    @Override
    public void requestTimeSuccessful(List<Sort> sorts) {
        money_list = sorts;
        chooseAdapter.addAll(money_list,true);
    }

    @Override
    public void requestSortFailed() {
        ToastUtil.toastShortShow("获取筛选条件失败");
    }

    @OnClick({R.id.loan_fragment_activity_ll_choose1,R.id.loan_fragment_activity_ll_choose2,R.id.loan_fragment_activity_ll_choose3,R.id.loan_fragment_ll_show_choose})
    public void onClick(View view){
        if(!chooseRecyclerView.isShown()){
            showAnimation(chooseRecyclerView);
            chooseRecyclerView.setVisibility(View.VISIBLE);
        }
        llShowChoose.setVisibility(View.VISIBLE);
        ivChoose1.setImageResource(R.drawable.arrow_down_gray);
        ivChoose2.setImageResource(R.drawable.arrow_down_gray);
        ivChoose3.setImageResource(R.drawable.arrow_down_gray);

        switch (view.getId()){
            case R.id.loan_fragment_activity_ll_choose1:
                if(ocp_list==null){
                    requestSortFailed();
                    return;
                }

                if(current_show.equals(first)){
                    hideChooseView();
                }else {
                    //替换数据标记颜色
                    chooseAdapter.showSelectedColor(ocp_list,ocp_pos);
                    ivChoose1.setImageResource(R.drawable.arrow_up_gray);
                    current_show = first;
                }
                break;

            case R.id.loan_fragment_activity_ll_choose2:
                if(loanType_list==null){
                    requestSortFailed();
                    return;
                }

                if(current_show.equals(second)){
                    hideChooseView();
                }else {
                    //替换数据标记颜色
                    chooseAdapter.showSelectedColor(loanType_list,loan_pos);
                    ivChoose2.setImageResource(R.drawable.arrow_up_gray);
                    current_show = second;
                }
                break;

            case R.id.loan_fragment_activity_ll_choose3:
                if(money_list==null){
                    requestSortFailed();
                    return;
                }

                if(current_show.equals(third)){
                    hideChooseView();

                }else {
                    //替换数据标记颜色
                    chooseAdapter.showSelectedColor(money_list,money_pos);
                    ivChoose3.setImageResource(R.drawable.arrow_up_gray);
                    current_show = third;
                }
                break;

            case R.id.loan_fragment_ll_show_choose:
                //隐藏
                hideChooseView();
                break;
        }
    }

    private void hideChooseView() {
        ivChoose1.setImageResource(R.drawable.arrow_down_gray);
        ivChoose2.setImageResource(R.drawable.arrow_down_gray);
        ivChoose3.setImageResource(R.drawable.arrow_down_gray);
        current_show = "";
        hideAnimation(chooseRecyclerView);
        chooseRecyclerView.setVisibility(View.GONE);
    }

    private void showAnimation(View view) {
        AnimUtil.showWithTranslate(view, new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shadowView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void hideAnimation(View view){
        AnimUtil.hideWithTranslate(view, new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                shadowView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llShowChoose.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //选择列表的item点击事件
    @Override
    public void onItemClick(View view, Sort sort, int position) {
        //隐藏
        hideChooseView();
        switch (sort.getType()){
            case "ocp":
                //记录点击的位置
                ocp_pos = position;
                //加入查询条件map
                if(sort.getName().equals("不限")){
                    tvChoose1.setText("身份");
                    select_map.remove("tag1");
                }else {
                    tvChoose1.setText(sort.getName());
                    select_map.put("tag1",String.valueOf(sort.getId()));
                }
                break;

            case "loantype":
                //记录点击的位置
                loan_pos = position;
                //加入查询条件map
                if(sort.getName().equals("不限")){
                    tvChoose2.setText("贷款金额");
                    select_map.remove("tag2");
                }else {
                    tvChoose2.setText(sort.getName());
                    select_map.put("tag2",String.valueOf(sort.getId()));
                }
                break;

            case "money":
                //记录点击的位置
                money_pos = position;
                //加入查询条件map
                if(sort.getName().equals("不限")){
                    tvChoose3.setText("金额");
                    select_map.remove("tag3");
                }else {
                    tvChoose3.setText(sort.getName());
                    select_map.put("tag3",String.valueOf(sort.getId()));
                }
                break;
        }
        //开始刷新
        refreshLayout.startRefresh();
    }

    @Override
    protected void initZhugeEvent() {
        Map<String,String> map = new HashMap<>();
        map.put("name",pageName);
        ZhugeHelper.browseOther(getContext(),map);
    }
}

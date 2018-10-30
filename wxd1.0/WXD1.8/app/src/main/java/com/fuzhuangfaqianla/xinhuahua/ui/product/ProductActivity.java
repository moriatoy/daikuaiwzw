package com.fuzhuangfaqianla.xinhuahua.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonlib.util.MeasureUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.base.AppBaseActivity;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.OnlineLoan;
import com.fuzhuangfaqianla.xinhuahua.ui.product.bean.PinnedHeaderEntity;
import com.fuzhuangfaqianla.xinhuahua.ui.product.contract.ProductContract;
import com.fuzhuangfaqianla.xinhuahua.ui.product.holder.ApplyViewBinder;
import com.fuzhuangfaqianla.xinhuahua.ui.product.holder.IntroViewBinder;
import com.fuzhuangfaqianla.xinhuahua.ui.product.holder.LoanDetailViewBinder;
import com.fuzhuangfaqianla.xinhuahua.ui.product.holder.PinnedHeaderViewBinder;
import com.fuzhuangfaqianla.xinhuahua.ui.product.holder.ProductInfoViewBinder;
import com.fuzhuangfaqianla.xinhuahua.ui.product.presenter.ProductPresenterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.Linker;
import me.drakeet.multitype.MultiTypeAdapter;

public class ProductActivity extends AppBaseActivity<ProductContract.Presenter> implements ProductContract.View {
    @BindView(R.id.product_activity_coo)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.product_activity_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.product_activity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.product_activity_appbarlayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.product_activity_tv_getmoney)
    TextView tvGetMoney;
    @BindView(R.id.product_activity_fl_placeholder)
    FrameLayout flPlaceHolder;
    @BindView(R.id.product_activity_ll_collapsed_info)
    LinearLayout llCollapsed;
    @BindView(R.id.product_activity_ll_collapsed_info_tv_range)
    TextView tvCollapsedRange;
    @BindView(R.id.product_activity_ll_collapsed_info_tv_rate)
    TextView tvCollapsedRate;
    @BindView(R.id.product_activity_ll_collapsed_info_tv_ratetype)
    TextView tvRateType;
    MultiTypeAdapter adapter;
    BottomSheetBehavior mBottomSheetBehavior;
    OnlineLoan data;
    String from;
    String productId;
    String pageName = "产品详情页面";

    public ProductActivity() {
        super(R.layout.activity_product);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this).statusBarColor(R.color.white).statusBarDarkFont(true,0.2f).keyboardEnable(true);
        mImmersionBar.init();
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        data = (OnlineLoan) getIntent().getSerializableExtra("data");
        from = getIntent().getStringExtra("from");
        productId = getIntent().getStringExtra("productId");
    }

    @Override
    public OnlineLoan getOnlineLoan() {
        return data;
    }

    @Override
    public void setOnlineData(OnlineLoan onlineData) {
        data = onlineData;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public void initZhuge() {
        Map<String,String> map = new HashMap<>();
        map.put("name",data.getProductName());
        map.put("from",from);
        ZhugeHelper.browseProductDetail(this,map);
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initLoadService() {
        super.initLoadService();
        ButterKnife.bind(this);
        mPresenter = new ProductPresenterImpl(this);

        mLoadService = LoadSir.getDefault().register(flPlaceHolder, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.requestRecyclerView();
            }
        });
    }

    @Override
    public void initZhugeEvent() {
        //请求成功之后再注册
    }

    @Override
    public void initTitlebar(String title) {
        initTitle(title,getResources().getColor(R.color.white),getResources().getColor(R.color.color_33));
        mTitle.setIv_left(R.drawable.nav_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void initProductInfo() {
        tvCollapsedRange.setText(data.getLoanRange());
        tvRateType.setText(data.getRateType());
        tvCollapsedRate.setText(data.getRate());
    }

    @Override
    public void initRecyclerView() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

//        recyclerView.addItemDecoration(new PinnedHeaderItemDecoration.Builder(PinnedHeaderEntity.TYPE_PINNED_HEADER).setDividerId(R.drawable.divider).enableDivider(false)
//                .setHeaderClickListener(null).create());

        adapter = new MultiTypeAdapter();

        adapter.register(PinnedHeaderEntity.class).to(
                new ProductInfoViewBinder(),
                new PinnedHeaderViewBinder(),
                new IntroViewBinder(),
                new LoanDetailViewBinder(),
                new ApplyViewBinder()
        ).withLinker(new Linker<PinnedHeaderEntity>() {
            @Override
            public int index(@NonNull PinnedHeaderEntity pinnedHeaderEntity) {
                if(pinnedHeaderEntity.getItemType() == PinnedHeaderEntity.TYPE_PRODUCT_INFO){
                    return 0;
                } else if(pinnedHeaderEntity.getItemType() == PinnedHeaderEntity.TYPE_PINNED_HEADER){
                    return 1;
                } else if(pinnedHeaderEntity.getItemType() == PinnedHeaderEntity.TYPE_INTRO){
                    return 2;
                } else if(pinnedHeaderEntity.getItemType() == PinnedHeaderEntity.TYPE_LOAN_DETAIL){
                    return 3;
                } else if(pinnedHeaderEntity.getItemType() == PinnedHeaderEntity.TYPE_APPLY){
                    return 4;
                }
                return 1;
            }
        });

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void initBottomAnimation() {
//        mBottomSheetBehavior = BottomSheetBehavior.from(tvGetMoney);
//        recyclerView.addOnScrollListener(new RecyclerViewScrollDetector() {
//
//            @Override
//            public void onScrollUp(int scrollY) {
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            }
//
//            @Override
//            public void onScrollDown(int scrollY) {
//                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            }
//        });
//        //默认展开
//        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void initCollapsedAnimation() {
        llCollapsed.setAlpha(0);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if(newState==RecyclerView.SCROLL_STATE_SETTLING){
//                    //快速下拉
//                    flTitle.setBackgroundColor(getResources().getColor(R.color.white));
//                    tvTitle.setTextColor(getResources().getColor(R.color.color_33));
//                    line.setVisibility(View.VISIBLE);
//                    flTitle.setAlpha(1);
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int scrollY = recyclerView.computeVerticalScrollOffset();
//
//                if(scrollY>0 && scrollY<100 && dy>0){
//                    flTitle.setBackgroundColor(getResources().getColor(R.color.white));
//                    tvTitle.setTextColor(getResources().getColor(R.color.color_33));
//                    line.setVisibility(View.VISIBLE);
//                    flTitle.setAlpha(0);
//                }else if(scrollY>0 && scrollY<100 && dy<0){
//                    flTitle.setBackgroundColor(Color.parseColor("#66000000"));
//                    tvTitle.setTextColor(getResources().getColor(R.color.white));
//                    line.setVisibility(View.INVISIBLE);
//                    flTitle.setAlpha(1);
//                }else if(scrollY>=100) {
//                    //改变透明度
//                    changeAlpha(flTitle,216,scrollY);
//                }
//            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int scrollY = recyclerView.computeVerticalScrollOffset();
                //改变透明度
                changeAlpha(llCollapsed,194,scrollY);
            }
        });
    }

    private void changeAlpha(View view, int judgeHeight, int scrollY) {
        //快速下拉会引起scollY<0
        /*if(scrollY<0){
            view.setAlpha(0);
            return;
        }*/
        int bannerHeight = MeasureUtil.dip2px(this,judgeHeight);
        float radio = Math.min(1, scrollY/(bannerHeight - view.getHeight()*1f));
        view.setAlpha(radio);
    }

    @Override
    public void refreshRecyclerView(List<Object> items) {
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.product_activity_tv_getmoney})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.product_activity_tv_getmoney:
                //ZhugeIO
                clickGetMoney();

                Intent intent = new Intent(ProductActivity.this,ProductH5Activity.class);
                intent.putExtra("from",ZhugeHelper.product_detail_from);
                intent.putExtra("url",data.getProductUrl());
                intent.putExtra("title",data.getProductName());
                OpenHelper.startActivity(ProductActivity.this,intent);
                break;
        }
    }

    private void clickGetMoney() {
        Map<String,String> zhuge_map = new HashMap<>();
        zhuge_map.put("pagename",pageName);
        zhuge_map.put("name", ZhugeHelper.take_the_money_immediately);
        ZhugeHelper.clickButton(this,zhuge_map);
    }

}

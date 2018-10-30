package com.fuzhuangfaqianla.xinhuahua.ui.index.holder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonlib.imageloader.ImageLoaderManager;
import com.commonlib.widget.CircleImageView;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.helper.ZhugeHelper;
import com.fuzhuangfaqianla.xinhuahua.ui.index.RecommandActivity;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.RecommandTag;
import com.fuzhuangfaqianla.xinhuahua.ui.index.bean.RecommandTagList;
import com.fuzhuangfaqianla.xinhuahua.helper.LoginHelper;
import com.fuzhuangfaqianla.xinhuahua.helper.OpenHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by xiongchang on 2017/9/21.
 */
public class RecommandTagViewBinder extends ItemViewBinder<RecommandTagList, RecommandTagViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.index_fragment_item_recommandtag, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RecommandTagList recommandTagList) {
        int size = recommandTagList.getDatas().size();

        if(size>=1){
            holder.bindData(recommandTagList,holder.iv1,holder.tv1,0);
            holder.ll1.setVisibility(View.VISIBLE);
        }
        if(size>=2){
            holder.bindData(recommandTagList,holder.iv2,holder.tv2,1);
            holder.ll2.setVisibility(View.VISIBLE);
        }
        if(size>=3){
            holder.bindData(recommandTagList,holder.iv3,holder.tv3,2);
            holder.ll3.setVisibility(View.VISIBLE);
        }
        if(size>=4){
            holder.bindData(recommandTagList,holder.iv4,holder.tv4,3);
            holder.ll4.setVisibility(View.VISIBLE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.index_fragment_item_applet_iv1)
        CircleImageView iv1;
        @BindView(R.id.index_fragment_item_applet_iv2)
        CircleImageView iv2;
        @BindView(R.id.index_fragment_item_applet_iv3)
        CircleImageView iv3;
        @BindView(R.id.index_fragment_item_applet_iv4)
        CircleImageView iv4;
        @BindView(R.id.index_fragment_item_applet_tv1)
        TextView tv1;
        @BindView(R.id.index_fragment_item_applet_tv2)
        TextView tv2;
        @BindView(R.id.index_fragment_item_applet_tv3)
        TextView tv3;
        @BindView(R.id.index_fragment_item_applet_tv4)
        TextView tv4;
        @BindView(R.id.index_fragment_item_applet_ll1)
        LinearLayout ll1;
        @BindView(R.id.index_fragment_item_applet_ll2)
        LinearLayout ll2;
        @BindView(R.id.index_fragment_item_applet_ll3)
        LinearLayout ll3;
        @BindView(R.id.index_fragment_item_applet_ll4)
        LinearLayout ll4;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindData(RecommandTagList recommandTagList, CircleImageView iv, TextView tv, int i) {
            RecommandTag data = recommandTagList.getDatas().get(i);
            ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(iv,data.getPicUrl()));
            tv.setText(data.getTitle());
        }

        @OnClick({R.id.index_fragment_item_applet_ll1,R.id.index_fragment_item_applet_ll2,R.id.index_fragment_item_applet_ll3,R.id.index_fragment_item_applet_ll4})
        public void onClick(View v) {
            RecommandTagList tags = (RecommandTagList) getAdapter().getItems().get(getLayoutPosition());

            int position = 0;
            switch (v.getId()){
                case R.id.index_fragment_item_applet_ll1:
                    position = 0;
                    break;
                case R.id.index_fragment_item_applet_ll2:
                    position = 1;
                    break;
                case R.id.index_fragment_item_applet_ll3:
                    position = 2;
                    break;
                case R.id.index_fragment_item_applet_ll4:
                    position = 3;
                    break;
            }
            RecommandTag tag = tags.getDatas().get(position);

            if(LoginHelper.isLogin()){
                //zhuge click
                Map<String,String> click_map = new HashMap<>();
                click_map.put("name",tag.getTitle());
                click_map.put("from",ZhugeHelper.recomm_from);
                ZhugeHelper.clickRecommend(v.getContext(),click_map);

                Intent intent = new Intent(v.getContext(), RecommandActivity.class);
                intent.putExtra("title",tag.getTitle());
                intent.putExtra("tagId",tag.getRecommendTagId());
                OpenHelper.startActivity(v.getContext(),intent);
            }else {
                LoginHelper.unLoginGoToLoginActivity(v.getContext());
            }

        }
    }
}

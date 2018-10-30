package com.fuzhuangfaqianla.xinhuahua.ui.product.holder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonlib.imageloader.ImageLoaderManager;
import com.commonlib.util.MeasureUtil;
import com.commonlib.widget.CircleImageView;
import com.fuzhuangfaqianla.xinhuahua.R;
import com.fuzhuangfaqianla.xinhuahua.view.DashLineView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiongchang on 2017/9/28.
 */

public class ApplyAdapter extends RecyclerView.Adapter<ApplyAdapter.ViewHolder> {
    int[] imgs = new int[]{R.drawable.ic_apply_1,R.drawable.ic_apply_2,R.drawable.ic_apply_3,R.drawable.ic_apply_4,R.drawable.ic_apply_5,R.drawable.ic_apply_6};
    int[] colors = new int[]{R.color.color_f8c86c,R.color.color_75f0e5,R.color.color_fecfee,R.color.color_f7cb69,R.color.color_fecfee,R.color.color_75f0e5};

    List<Object[]> datas = new ArrayList<>();

    public void addAll(List<Object[]> datas, boolean isClear){
        if(isClear){
            this.datas.clear();
        }
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_apply_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position==0){
            holder.dashLineView1.setVisibility(View.INVISIBLE);
        }

        if(position==datas.size()-1){
            holder.dashLineView2.setVisibility(View.INVISIBLE);
        }

        int circle_width = 6 + position*2;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.circleImageView.getLayoutParams();
        params.width = MeasureUtil.dip2px(holder.circleImageView.getContext(),circle_width);
        params.height = MeasureUtil.dip2px(holder.circleImageView.getContext(),circle_width);
        holder.circleImageView.setLayoutParams(params);

        Object[] data = datas.get(position);
        Map<String,Object> process = (Map<String, Object>) data[0];
        int id = (int)(double)process.get("id");
        String columnName = (String) process.get("columnName");
        int rate = (int)(double)data[1];

        //排序集合
//        sortList();
        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getResourceOption(holder.imageView,imgs[id-1],false));
        holder.tvTitle.setText(columnName);
        holder.tvRate.setText(rate+"%");
        holder.tvRate.setTextColor(holder.tvRate.getContext().getResources().getColor(colors[id-1]));
    }

    private void sortList() {
//        Collections.sort(datas, new Comparator<OnlineLoan.Processes>() {
//            @Override
//            public int compare(OnlineLoan.Processes o1, OnlineLoan.Processes o2) {
//                //进行升序排列
//                if(o1.getApplyProcessId() > o2.getApplyProcessId()){
//                    return 1;
//                }
//                if(o1.getApplyProcessId() == o2.getApplyProcessId()){
//                    return 0;
//                }
//                return -1;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return datas==null ? 0: datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.inflate_apply_list_item_iv)
        ImageView imageView;
        @BindView(R.id.inflate_apply_list_item_dashview1)
        DashLineView dashLineView1;
        @BindView(R.id.inflate_apply_list_item_dashview2)
        DashLineView dashLineView2;
        @BindView(R.id.inflate_apply_list_item_circle)
        CircleImageView circleImageView;
        @BindView(R.id.inflate_apply_list_item_tv_title)
        TextView tvTitle;
        @BindView(R.id.inflate_apply_list_item_tv_rate)
        TextView tvRate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

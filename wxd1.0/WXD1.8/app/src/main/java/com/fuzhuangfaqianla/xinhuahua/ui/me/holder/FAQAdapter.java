package com.fuzhuangfaqianla.xinhuahua.ui.me.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.commonlib.adapter.BaseExpandListAdapter;
import com.fuzhuangfaqianla.xinhuahua.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiongchang on 17/7/15.
 */

public class FAQAdapter extends BaseExpandListAdapter<String,String> {

    public FAQAdapter(Context context, List<List<String>> childList, List<String> groupList) {
        super(context, childList, groupList);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.inflate_faq_group_item,null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        holder.tvQuestion.setText((groupPosition+1) + "." + groupList.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.inflate_faq_child_item,null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        holder.tvAnswer.setText(childList.get(groupPosition).get(childPosition));

        return convertView;
    }

    class GroupViewHolder{
        @BindView(R.id.inflate_faq_group_item_tv_question)
        TextView tvQuestion;

        public GroupViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    class ChildViewHolder{
        @BindView(R.id.inflate_faq_child_item_tv_answer)
        TextView tvAnswer;

        public ChildViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}

package com.commonlib.widget;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commonlib.R;


/**
 * Created by xiongchang on 17/7/7.
 */

public class MenuBar implements View.OnClickListener{
    private Activity activity;
    private OnMenuSelectedListener listener;

    public LinearLayout ll_menu1;
    ImageView iv_menu1;
    TextView tv_menu1;

    public LinearLayout ll_menu2;
    ImageView iv_menu2;
    TextView tv_menu2;

    public LinearLayout ll_menu3;
    ImageView iv_menu3;
    public TextView tv_menu3;

    public LinearLayout ll_menu4;
    ImageView iv_menu4;
    public TextView tv_menu4;

    public LinearLayout ll_menu5;
    ImageView iv_menu5;
    TextView tv_menu5;

    public static final int MENU_1 = 0;
    public static final int MENU_2 = 1;
    public static final int MENU_3 = 2;
    public static final int MENU_4 = 3;
    public static final int MENU_5 = 4;

    public MenuBar(Activity activity, OnMenuSelectedListener listener){
        this.activity = activity;
        this.listener = listener;
        initView();
        initEvent();
        //默认选择第一个
        ll_menu1.performClick();
    }

    private void initEvent() {
        ll_menu1.setOnClickListener(this);
        ll_menu2.setOnClickListener(this);
        ll_menu3.setOnClickListener(this);
        ll_menu4.setOnClickListener(this);
        ll_menu5.setOnClickListener(this);
    }

    private void initView() {
        ll_menu1 = (LinearLayout) activity.findViewById(R.id.comm_menu_first_ll);
        iv_menu1 = (ImageView) activity.findViewById(R.id.comm_menu_first_iv);
        tv_menu1 = (TextView) activity.findViewById(R.id.comm_menu_first_tv);

        ll_menu2 = (LinearLayout) activity.findViewById(R.id.comm_menu_second_ll);
        iv_menu2 = (ImageView) activity.findViewById(R.id.comm_menu_second_iv);
        tv_menu2 = (TextView) activity.findViewById(R.id.comm_menu_second_tv);

        ll_menu3 = (LinearLayout) activity.findViewById(R.id.comm_menu_third_ll);
        iv_menu3 = (ImageView) activity.findViewById(R.id.comm_menu_third_iv);
        tv_menu3 = (TextView) activity.findViewById(R.id.comm_menu_third_tv);

        ll_menu4 = (LinearLayout) activity.findViewById(R.id.comm_menu_forth_ll);
        iv_menu4 = (ImageView) activity.findViewById(R.id.comm_menu_forth_iv);
        tv_menu4 = (TextView) activity.findViewById(R.id.comm_menu_forth_tv);

        ll_menu5 = (LinearLayout) activity.findViewById(R.id.comm_menu_fifth_ll);
        iv_menu5 = (ImageView) activity.findViewById(R.id.comm_menu_fifth_iv);
        tv_menu5 = (TextView) activity.findViewById(R.id.comm_menu_fifth_tv);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.comm_menu_first_ll) {
            if (listener != null) {
                listener.onMenuSelected(MENU_1);
            }
            showMenu(MENU_1);
        } else if (v.getId() == R.id.comm_menu_second_ll) {
            if (listener != null) {
                listener.onMenuSelected(MENU_2);
            }
            showMenu(MENU_2);
        } else if (v.getId() == R.id.comm_menu_third_ll) {
            if (listener != null) {
                listener.onMenuSelected(MENU_3);
            }
            showMenu(MENU_3);
        } else if (v.getId() == R.id.comm_menu_forth_ll) {
            if (listener != null) {
                listener.onMenuSelected(MENU_4);
            }
            showMenu(MENU_4);
        } else if (v.getId() == R.id.comm_menu_fifth_ll) {
            if (listener != null) {
                listener.onMenuSelected(MENU_5);
            }
            showMenu(MENU_5);
        }
    }

    private void showMenu(int type) {
        iv_menu1.setSelected(false);
        tv_menu1.setSelected(false);
        iv_menu2.setSelected(false);
        tv_menu2.setSelected(false);
        iv_menu3.setSelected(false);
        tv_menu3.setSelected(false);
        iv_menu4.setSelected(false);
        tv_menu4.setSelected(false);
        iv_menu5.setSelected(false);
        tv_menu5.setSelected(false);

        switch (type) {
            case MENU_1:
                iv_menu1.setSelected(true);
                tv_menu1.setSelected(true);
                break;
            case MENU_2:
                iv_menu2.setSelected(true);
                tv_menu2.setSelected(true);
                break;
            case MENU_3:
                iv_menu3.setSelected(true);
                tv_menu3.setSelected(true);
                break;
            case MENU_4:
                iv_menu4.setSelected(true);
                tv_menu4.setSelected(true);
                break;
            case MENU_5:
                iv_menu5.setSelected(true);
                tv_menu5.setSelected(true);
                break;
            default:
                break;
        }
    }

    public interface OnMenuSelectedListener {
        void onMenuSelected(int position);
    }
}

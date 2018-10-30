package com.xiyoukeji.ldqb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.just.agentweb.AgentWeb;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private AgentWeb agentWebView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.main_view);
        agentWebView = AgentWeb.with(this)//传入Activity or Fragment
                .setAgentWebParent(relativeLayout, new RelativeLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .createAgentWeb()//
                .ready()
                .go("http://dkcs.chendind.com/");
//                .go("https://api1.wawazz.cn/doll/openScreen.html");
        ;
    }

}

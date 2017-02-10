package com.wangjie.rapidrouter.example.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wangjie.rapidrouter.core.RapidRouter;
import com.wangjie.rapidrouter.core.RouterStuff;
import com.wangjie.rapidrouter.core.listener.RouterGoAfterCallback;
import com.wangjie.rapidrouter.core.listener.RouterGoAroundCallback;
import com.wangjie.rapidrouter.core.listener.RouterGoBeforeCallback;
import com.wangjie.rapidrouter.core.listener.RouterTargetNotFoundCallback;
import com.wangjie.rapidrouter.core.strategy.RapidRouterStrategyRegular;
import com.wangjie.rapidrouter.core.strategy.RapidRouterStrategySimple;
import com.wangjie.rapidrouter.example.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.activity_main_to_a_btn).setOnClickListener(this);
        findViewById(R.id.activity_main_to_b_error_btn).setOnClickListener(this);
        findViewById(R.id.activity_main_to_c_intercept_btn).setOnClickListener(this);
        findViewById(R.id.activity_main_to_c_not_found_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_to_a_btn:
                RapidRouter.with(this)
                        .uri("rr://rapidrouter.a?p_name=wangjie&p_age=18")
                        .goBefore(new RouterGoBeforeCallback() {
                            @Override
                            public boolean onRouterGoBefore(RouterStuff routerStuff) {
                                Log.i(TAG, "onRouterGoBefore: " + routerStuff);
                                return true;
                            }
                        })
                        .goAfter(new RouterGoAfterCallback() {
                            @Override
                            public boolean onRouterGoAfter(RouterStuff routerStuff) {
                                Log.i(TAG, "onRouterGoAfter: " + routerStuff);
                                return false;
                            }
                        })
                        .go();
                break;
            case R.id.activity_main_to_b_error_btn:
                RapidRouter.with(this)
                        .uri("rr://rapidrouter.b?id=234a")
                        .go();
                break;
            case R.id.activity_main_to_c_intercept_btn:
                RapidRouter.with(this)
                        .uri("rr://rapidrouter.c?p_name=wangjie&p_age=18")
                        .goAround(new RouterGoAroundCallback() {
                            @Override
                            public void onRouterGoAround(RouterStuff routerStuff) {
                                // ignore
                                Log.i(TAG, "onRouterGoAround: " + routerStuff);
                            }
                        })
                        .go();
                break;
            case R.id.activity_main_to_c_not_found_btn:
                RapidRouter.with(this)
                        .uri("sc://wangL0vjie.c?paramOfCActivity=3.14")
                        .targetNotFound(new RouterTargetNotFoundCallback() {
                            @Override
                            public boolean onRouterTargetNotFound(RouterStuff routerStuff) {
                                Toast.makeText(MainActivity.this, "target not found", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        })
                        .strategies(RapidRouterStrategyRegular.class, RapidRouterStrategySimple.class)
                        .go();
                break;
            default:
                break;
        }
    }
}

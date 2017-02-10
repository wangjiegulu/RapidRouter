package com.wangjie.rapidrouter.example.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wangjie.rapidrouter.api.annotations.RRParam;
import com.wangjie.rapidrouter.api.annotations.RRUri;
import com.wangjie.rapidrouter.api.annotations.RRouter;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
@RRouter(
        {
                @RRUri(uri = "rr://rapidrouter.a",
                        params = {
                                @RRParam(name = "p_name"),
                                @RRParam(name = "p_age", type = int.class)
                        }
                ),
                @RRUri(uri = "rr://rapidrouter_extra.a",
                        params = {
                                @RRParam(name = "p_name"),
                                @RRParam(name = "p_age", type = int.class)
                        }
                )
        }
)
public class AActivity extends BaseActivity {

    private static final String TAG = AActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0xffaabbcc));

        Intent intent = getIntent();

        Log.i(TAG, "p_age: " + intent.getIntExtra("p_age", -1) + ", p_name: " + intent.getStringExtra("p_name"));

    }
}

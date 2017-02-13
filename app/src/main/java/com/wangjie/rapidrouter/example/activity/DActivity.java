package com.wangjie.rapidrouter.example.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wangjie.rapidrouter.api.annotations.RRParam;
import com.wangjie.rapidrouter.api.annotations.RRUri;

/**
 * rf://rapidrouter.d/v/:apiVer/id/:id rf://rapidrouter.d/v/v1/id/123456
 *
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
@RRUri(uri = "rf://rapidrouter.d", params = {
        @RRParam(name = "vUrl")
})
public class DActivity extends BaseActivity {
    private static final String TAG = BActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0xffaaccbb));

        Log.i(TAG, "paramOfCActivity: " + getIntent().getFloatExtra("paramOfCActivity", -1L));
    }
}

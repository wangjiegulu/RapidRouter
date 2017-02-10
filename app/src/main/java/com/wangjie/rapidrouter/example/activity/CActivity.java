package com.wangjie.rapidrouter.example.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wangjie.rapidrouter.api.annotations.RRParam;
import com.wangjie.rapidrouter.api.annotations.RRUri;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
@RRUri(uri = "~((rr)|(sc))://wang.*jie\\.[cx].*", params = {
        @RRParam(name = "paramOfCActivity", type = float.class)
})
public class CActivity extends BaseActivity {
    private static final String TAG = BActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0xffccaabb));
//        "adsb".matches("~((rr)|(sc))://wang.*jie\\.[cx].*");
        Log.i(TAG, "paramOfCActivity: " + getIntent().getFloatExtra("paramOfCActivity", -1L));
    }
}

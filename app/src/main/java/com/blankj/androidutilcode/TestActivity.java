package com.blankj.androidutilcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;

import static com.blankj.utilcode.util.StartActivityResultExtKt.startActivityForObject;


/**
 * Created by Cat-x on 2018/2/8.
 * For ToolSample
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivityForObject(this, new Intent(this, MainActivity.class), 1, new Function3<Integer, Integer, Object, Unit>() {
            @Override
            public Unit invoke(Integer integer, Integer integer2, Object data) {

                return null;
            }
        });
    }
}

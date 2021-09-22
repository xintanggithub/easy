package com.tson.easydemo.aop;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.easy.aop.annotation.Stub;
import com.tson.easydemo.R;

public class TestAopActivity extends AppCompatActivity {
    public static final String TAG = "TsonLog";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_o_p);
//        findViewById(R.id.aop1).setOnClickListener(v -> testMt());
    }

//    @Stub("哈哈")
//    public void testMt() {
//        Log.d(TAG, "222");
//    }

}

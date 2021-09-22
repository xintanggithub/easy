package com.tson.easydemo.aop

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.easy.aop.annotation.Stub
import com.tson.easydemo.R
import kotlinx.android.synthetic.main.activity_a_o_p.*

class AOPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_o_p)
        aop1.setOnClickListener {
            testMt("测试参数")
        }
    }

    //    插桩
    @Stub(tag = "插桩tag", content = "插桩日志内容")
    fun testMt(name: String) {
        Log.d("StubAspect", " = !23 $name")
    }



}

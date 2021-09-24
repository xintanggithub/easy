package com.tson.easydemo.aop

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.easy.aop.AopManager
import com.easy.aop.annotation.*
import com.easy.aop.auto.AutoAction
import com.easy.aop.callback.DoProceed
import com.easy.aop.enumerate.Statistics.MAIN
import com.easy.aop.utils.ktxRunOnUiDelay
import com.tson.easydemo.R
import kotlinx.android.synthetic.main.activity_a_o_p.*

class AOPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_o_p)
        aop1.setOnClickListener {
            testMt("测试参数")
        }
        run1.setOnClickListener {
            testIO()
        }
        lock1.setOnClickListener {
            ktxRunOnUiDelay(1000) {
                lockUtils("1")
            }
            ktxRunOnUiDelay(1000) {
                lockUtils("2")
            }
            ktxRunOnUiDelay(1000) {
                lockUtils("3")
            }
        }
        auto1.setOnClickListener {
            testAuto()
        }
        AopManager.instance.setAutoListener(object : AutoAction {
            override fun proceedBefore(action: String, map: MutableMap<String, String>, proceed: DoProceed) {
                Log.d("统一处理", "在方法执行前  action=$action")
                proceed.runMethod()
            }

            override fun proceedAfter(action: String, map: MutableMap<String, String>) {
                Log.d("统一处理", "在方法执行后  action=$action")
            }
        })
    }

    @Auto(action = "action1", parameter = [AutoParameter(key = "1", value = "2")])
    fun testAuto() {
        Log.d("auto", "auto doing")
    }

    @Lock
    fun lockUtils(value: String) {
        Thread.sleep(1000)//每个停一会儿
        Log.d("value = ", value)// 不会一次性的把 1 2 3全部输出，而是间隔1000ms左右逐个输出
    }

    @Run(type = MAIN)
    fun testIO() {
        try {
            run1.text = "123123"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //    插桩
    @Stub(tag = "插桩tag", content = "插桩日志内容")
    fun testMt(name: String) {
        Log.d("StubAspect", " = !23 $name")
    }


}

package com.tson.utils.time

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import java.util.*

/**
 *  Date 2021/3/17 7:35 PM
 *
 * @author Tson
 */
class EasyTimer {

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { EasyTimer() }
    }

    private var timer: Timer? = null
    private var task: MyTimerTask? = null
    private var time: Long = 1000
    private var start: Long = 1000
    lateinit var callBack: TimerCallBack

    /**
     * [delay] 延迟多久触发，默认值1000 (1秒)
     *
     * [period] 间隔多久执行，默认值1000 (1秒)
     */
    fun run(delay: Long, period: Long, c: TimerCallBack) {
        time = delay
        start = period
        callBack = c
    }

    fun start() {
        callBack.start()
        if (null == timer) {
            timer = Timer(true)
        }
        if (null == task) {
            task = MyTimerTask(myHandler)
        }
        timer?.schedule(task, start, time)
    }

    fun stop() {
        timer?.cancel()
        task?.cancel()
        timer = null
        task = null
        callBack.stop()
    }

    class MyTimerTask(var handler: Handler) : TimerTask() {
        override fun run() {
            val message = Message()
            message.what = 2
            handler.sendMessage(message)
        }
    }

    private var myHandler: Handler = @SuppressLint("HandlerLeak") object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                2 -> {
                    callBack.doNotify()
                }
            }
            super.handleMessage(msg)
        }
    }
}

interface TimerCallBack {

    fun doNotify()

    fun stop()

    fun start()

}
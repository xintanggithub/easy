package com.tson.utils.time

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 *  Date 2021/3/17 7:31 PM
 *
 * @author Tson
 */

@SuppressLint("CheckResult")
fun <T> T.wait(block: T.() -> Unit, interval: Long = 500L) {
    Observable.timer(interval, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe { block() }
}
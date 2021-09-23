package com.easy.aop.utils

import android.os.Handler
import android.os.Looper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock


private val handler = Handler(Looper.getMainLooper())
private val coreSize = Runtime.getRuntime().availableProcessors() + 1

private val fix: ExecutorService = Executors.newFixedThreadPool(coreSize)
private val cache: ExecutorService = Executors.newCachedThreadPool()
private val single: ExecutorService = Executors.newSingleThreadExecutor()

/**
 * 切换到主线程
 */
fun <T> T.ktxRunOnUi(block: T.() -> Unit) {
    handler.post {
        block()
    }
}

private val lock = ReentrantLock()
fun withLock(block: () -> Unit) {
    try {
        lock.lock()
        block()
    } finally {
        lock.unlock()
    }
}

fun <T> T.ktxRunOnIO(block: T.() -> Unit) {
    Observable.create<T> { block() }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe()
}

fun <T> T.ktxRunOnMain(block: T.() -> Unit) {
    Observable.create<T> { block() }.subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread()).subscribe()
}

/**
 * 延迟delayMillis后切换到主线程
 */
fun <T> T.ktxRunOnUiDelay(delayMillis: Long, block: T.() -> Unit) {
    handler.postDelayed({
        block()
    }, delayMillis)
}

/**
 * 子线程执行。SingleThreadPool
 */
fun <T> T.ktxRunOnBgSingle(block: T.() -> Unit) {
    single.execute {
        block()
    }
}

/**
 * 子线程执行。FixedThreadPool
 */
fun <T> T.ktxRunOnBgFix(block: T.() -> Unit) {
    fix.execute {
        block()
    }
}

/**
 * 子线程执行。CachedThreadPool
 */
fun <T> T.ktxRunOnBgCache(block: T.() -> Unit) {
    cache.execute {
        block()
    }
}
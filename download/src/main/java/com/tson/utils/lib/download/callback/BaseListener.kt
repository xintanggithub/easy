package com.tson.utils.lib.download.callback

import com.liulishuo.filedownloader.BaseDownloadTask

/**
 * Created tangxin
 * Time 2018/11/1 11:37 AM
 */
interface BaseListener {
    fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int)

    fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int)

    fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int)

    fun error(task: BaseDownloadTask, e: Throwable)

    fun completed(task: BaseDownloadTask)

    fun warn(task: BaseDownloadTask)

    fun retry(task: BaseDownloadTask, ex: Throwable, retryingTimes: Int, soFarBytes: Int)
}

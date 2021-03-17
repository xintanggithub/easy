package com.tson.utils.lib.download.callback

import com.liulishuo.filedownloader.BaseDownloadTask

/**
 * Created tangxin
 * Time 2018/10/31 2:42 PM
 */
abstract class DownloadListener : BaseListener {

    override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}

    override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}

    override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}

    override fun error(task: BaseDownloadTask, e: Throwable) {}

    override fun warn(task: BaseDownloadTask) {}

    override fun retry(task: BaseDownloadTask, ex: Throwable, retryingTimes: Int, soFarBytes: Int) {}
}

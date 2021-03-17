package com.tson.utils.lib.download

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.tson.utils.lib.download.callback.ConnectServiceCallback
import com.tson.utils.lib.download.callback.DownloadListener
import com.tson.utils.lib.download.utils.log.LogUtils
import com.tson.utils.lib.download.utils.sp.SpHelper

/**
 * Created tangxin
 * Time 2018/10/31 5:41 PM
 *
 * @author tangxin
 */
class DownloadViewModel : ViewModel() {

    private var application: Application? = null
    private var connectService: ConnectServiceCallback? = null

    private var mSp: SpHelper? = null
    internal fun setConnectService(connectService: ConnectServiceCallback) {
        this.connectService = connectService
    }

    fun getConnectService(): ConnectServiceCallback {
        if (null == connectService) {
            connectService = object : ConnectServiceCallback {
                override fun connect() {
                    LogUtils.d(TAG, "download service connect")
                }

                override fun disConnect() {
                    LogUtils.d(TAG, "download service disConnect")
                }

            }
        }
        return connectService!!
    }

    val sp: SpHelper
        get() {
            if (null == mSp) {
                mSp = SpHelper(application!!)
            }
            return mSp!!
        }

    fun instance(application: Application) {
        this.application = application
        mSp = SpHelper(application)
    }

    private fun getFileName(url: String): String {
        val arr = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return arr[arr.size - 1]
    }

    fun setPath(path: String) {
        sp.path = path
    }

    fun setRetryCount(count: Int) {
        sp.retryCount = count
    }

    fun setGlobalPost2UIInterval(size: Int) {
        if (size <= 0) {
            FileDownloader.setGlobalPost2UIInterval(size)
        } else {
            Throwable("setGlobalPost2UIInterval  ------  size <= 0")
        }
    }

    fun setMaxThreadCount(count: Int) {
        sp.maxThreadCount = count
        FileDownloader.getImpl()
            .bindService { FileDownloader.getImpl().setMaxNetworkThreadCount(count) }
    }

    fun setDebugLog(isOpenLog: Boolean) {
        sp.debugLog = isOpenLog
        LogUtils.openLog = isOpenLog
    }

    fun start(url: String, downloadListener: DownloadListener) {
        start(url, sp.path, downloadListener)
    }

    fun start(url: String, outPath: String, downloadListener: DownloadListener) {
        val task: BaseDownloadTask
        if (TextUtils.isEmpty(outPath)) {
            task = FileDownloader.getImpl().create(url)
        } else {
            val path = outPath + getFileName(url)
            LogUtils.d(TAG, "Download Task url:$url")
            LogUtils.d(TAG, "Download Task file:$path")
            task = FileDownloader.getImpl().create(url).setPath(path)
        }
        task.setAutoRetryTimes(sp.retryCount)
            .setListener(object : FileDownloadListener() {
                override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {
                    LogUtils.d(TAG, "pending 【 task id = " + task.id + "】")
                    downloadListener.pending(task, soFarBytes, totalBytes)
                }

                override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {
                    LogUtils.d(
                        TAG, "progress【 task id = " + task.id + "| soFarBytes = " +
                                soFarBytes + "|  totalBytes = " + totalBytes + " 】"
                    )
                    downloadListener.progress(task, soFarBytes, totalBytes)
                }

                override fun completed(task: BaseDownloadTask) {
                    LogUtils.d(TAG, "completed 【 task id = " + task.id + " 】")
                    downloadListener.completed(task)
                }

                override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {
                    LogUtils.d(
                        TAG, "paused 【 task id = " + task.id + "| soFarBytes = " +
                                soFarBytes + "|  totalBytes = " + totalBytes + " 】"
                    )
                    downloadListener.paused(task, soFarBytes, totalBytes)
                }

                override fun error(task: BaseDownloadTask, e: Throwable) {
                    LogUtils.e(
                        TAG, "error 【 task id = " + task.id + "| error = " +
                                e.message + " 】"
                    )
                    downloadListener.error(task, e)
                }

                override fun warn(task: BaseDownloadTask) {
                    LogUtils.d(TAG, "warn 【 task id = " + task.id + " 】")
                    downloadListener.warn(task)
                }

                override fun retry(
                    task: BaseDownloadTask?, ex: Throwable?, retryingTimes: Int,
                    soFarBytes: Int
                ) {
                    LogUtils.d(
                        TAG, "warn 【 task id = " + task!!.id + " | error = " +
                                ex!!.message + "  | retryingTimes = " + retryingTimes
                                + " |  soFarBytes = " + soFarBytes + "】"
                    )
                    super.retry(task, ex, retryingTimes, soFarBytes)
                    downloadListener.retry(task, ex, retryingTimes, soFarBytes)
                }
            }).start()
    }

    fun pause(id: Int) {
        LogUtils.d(TAG, "pause 【 task id = $id】")
        FileDownloader.getImpl().pause(id)
    }

    fun pauseAll() {
        LogUtils.d(TAG, "pauseAll")
        FileDownloader.getImpl().pauseAll()
    }

    fun clear(id: Int, path: String) {
        LogUtils.d(TAG, "clear 【 task id = $id】")
        FileDownloader.getImpl().clear(id, path)
    }

    fun clearAll() {
        LogUtils.d(TAG, "clearAll")
        FileDownloader.getImpl().clearAllTaskData()
    }

    fun getSoFar(id: Int): Long {
        return FileDownloader.getImpl().getSoFar(id)
    }

    fun getTotal(id: Int): Long {
        return FileDownloader.getImpl().getTotal(id)
    }

    companion object {

        private val TAG = "DownloadViewModel"
    }


}

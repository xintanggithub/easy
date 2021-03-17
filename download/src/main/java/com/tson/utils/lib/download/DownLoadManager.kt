package com.tson.utils.lib.download

import com.tson.utils.lib.download.callback.DownloadListener
import com.tson.utils.lib.download.utils.log.LogUtils

/**
 * Created tangxin
 * Time 2018/10/31 2:31 PM
 * @author tangxin
 */
class DownLoadManager {
    companion object {

        private const val TAG = "DownLoadManager"

        private var sDownloader: Downloader = Downloader()

        /**
         * 实例化
         *
         * @return Downloader对象 instance
         */
        val instance: Downloader
            get() {
                LogUtils.d(TAG, "instance success")
                return sDownloader
            }

        /**
         * 获取Downloader对象.
         *
         * @return Downloader对象  downloader
         */
        val downloader: Downloader?
            get() {
                return sDownloader
            }

        /**
         * 绑定监听
         *
         * @param listener 下载监听器
         */
        fun bindChangeListener(listener: DownloadListener) {
            sDownloader.addListener(listener)
        }

        /**
         * 解绑监听器.
         *
         * @param listener 下载监听器
         */
        fun unBindChangeListener(listener: DownloadListener) {
            sDownloader.removeListener(listener)
        }
    }
}

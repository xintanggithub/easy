package com.tson.utils.lib.download.utils

import com.liulishuo.filedownloader.connection.FileDownloadConnection
import com.liulishuo.filedownloader.util.FileDownloadHelper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.InputStream
import java.net.ProtocolException

/**
 * Created tangxin
 * Time 2018/12/26 11:52 AM
 */
class OkHttp3Connection internal constructor(private val mRequestBuilder: Request.Builder, private val mClient: OkHttpClient) : FileDownloadConnection {

    private var mRequest: Request? = null
    private var mResponse: Response? = null

    constructor(url: String, client: OkHttpClient) : this(Request.Builder().url(url), client) {}

    override fun addHeader(name: String, value: String) {
        mRequestBuilder.addHeader(name, value)
    }

    override fun dispatchAddResumeOffset(etag: String, offset: Long): Boolean {
        return false
    }

    @Throws(IOException::class)
    override fun getInputStream(): InputStream {
        if (mResponse == null) {
            throw IOException("Please invoke #execute first!")
        }
        val body = mResponse!!.body() ?: throw IOException("No body found on response!")

        return body.byteStream()
    }

    override fun getRequestHeaderFields(): Map<String, List<String>> {
        if (mRequest == null) {
            mRequest = mRequestBuilder.build()
        }

        return mRequest!!.headers().toMultimap()
    }

    override fun getResponseHeaderFields(): Map<String, List<String>>? {
        return if (mResponse == null) null else mResponse!!.headers().toMultimap()
    }

    override fun getResponseHeaderField(name: String): String? {
        return if (mResponse == null) null else mResponse!!.header(name)
    }

    @Throws(ProtocolException::class)
    override fun setRequestMethod(method: String): Boolean {
        mRequestBuilder.method(method, null)
        return true
    }

    @Throws(IOException::class)
    override fun execute() {
        if (mRequest == null) {
            mRequest = mRequestBuilder.build()
        }

        mResponse = mClient.newCall(mRequest!!).execute()
    }

    @Throws(IOException::class)
    override fun getResponseCode(): Int {
        if (mResponse == null) {
            throw IllegalStateException("Please invoke #execute first!")
        }

        return mResponse!!.code()
    }

    override fun ending() {
        mRequest = null
        mResponse = null
    }

    /**
     * The creator for the connection implemented with the okhttp3.
     */
    class Creator : FileDownloadHelper.ConnectionCreator {

        private var mClient: OkHttpClient? = null
        private var mBuilder: OkHttpClient.Builder? = null

        constructor() {}

        /**
         * Create the Creator with the customized `client`.
         *
         * @param builder the builder for customizing the okHttp client.
         */
        constructor(builder: OkHttpClient.Builder) {
            mBuilder = builder
        }

        /**
         * Get a non-null builder used for customizing the okHttpClient.
         *
         *
         * If you have already set a builder through the construct method, we will return it directly.
         *
         * @return the non-null builder.
         */
        fun customize(): OkHttpClient.Builder {
            if (mBuilder == null) {
                mBuilder = OkHttpClient.Builder()
            }

            return mBuilder!!
        }

        @Throws(IOException::class)
        override fun create(url: String): FileDownloadConnection {
            if (mClient == null) {
                synchronized(Creator::class.java) {
                    if (mClient == null) {
                        mClient = if (mBuilder != null) mBuilder!!.build() else OkHttpClient()
                        mBuilder = null
                    }
                }
            }

            return OkHttp3Connection(url, mClient!!)
        }
    }
}

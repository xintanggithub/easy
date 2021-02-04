package com.tson.http.config

/**
 *  Date 2020-08-04 14:36
 *
 * @author Tson
 */
class HttpConfig {
    companion object {

        private var headMap = mutableMapOf<String, Any>()
        private var baseUrl = ""
        private var timeOut = mutableListOf(60L, 60L, 60L)
        private var useLog = true

        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { HttpConfig() }
    }

    fun header(key: String, value: Any): HttpConfig {
        headMap[key] = value
        return instance
    }

    fun host(url: String): HttpConfig {
        baseUrl = url
        return instance
    }

    fun timeOutSecond(connectTimeout: Long, readTimeout: Long, writeTimeout: Long): HttpConfig {
        timeOut[0] = connectTimeout
        timeOut[1] = readTimeout
        timeOut[2] = writeTimeout
        return instance
    }

    fun headers() = headMap

    fun url() = baseUrl

    fun useLog() = useLog

    fun timeOut() = timeOut
}
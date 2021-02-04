package com.tson.http.factory

import android.util.Log
import com.google.gson.GsonBuilder
import com.tson.http.config.HttpConfig
import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  Date 2020-08-04 14:15
 *
 * @author Tson
 */
object RetrofitFactory {

    private const val TAG = "RetrofitFactory"

    private val mLoggingInterceptor = invoke { chain ->
        val original = chain.request()
        val builder = original.newBuilder()
            .header("X-AUTH-DEVICE-TYPE", "android")
        val headers = HttpConfig.instance.headers()
        for (header in headers) {
            builder.header(header.key, header.value.toString())
        }
        val request = builder
            .method(original.method, original.body)
            .build()
        val response = chain.proceed(request)
        Log.i(
            TAG, String.format(
                "Sending request: %s on %s%n%s",
                request.url,
                chain.connection(),
                request.headers
            )
        )
        response
    }

    fun buildOkHttpClient(): OkHttpClient {
        val timeOut = HttpConfig.instance.timeOut()
        val builder = OkHttpClient.Builder()
            .connectTimeout(timeOut[0], TimeUnit.SECONDS)//设置连接超时
            .readTimeout(timeOut[1], TimeUnit.SECONDS)//读取超时
            .writeTimeout(timeOut[2], TimeUnit.SECONDS)//写入超时
            .addInterceptor(mLoggingInterceptor)//添加日志拦截器：显示链接信息
        if (HttpConfig.instance.useLog()) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return builder.build()
    }

    fun <T> defaultCreateApi(service: Class<T>): T {
        return createApi(service, HttpConfig.instance.url())
    }

    fun <T> createApi(service: Class<T>, url: String): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(buildOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return retrofit.create(service)
    }

}
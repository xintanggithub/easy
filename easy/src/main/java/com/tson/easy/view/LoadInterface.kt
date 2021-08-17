package com.tson.easy.view

/**
 *  Date 2021/2/2 1:41 PM
 *
 * @author Tson
 */
interface LoadInterface {

    fun requestLoadingViewId(): Int

    fun showLoading()

    fun showLoading(message: String)

    fun hideLoading()

    fun error(error: Throwable)

    fun retry()

    fun anyHandle(status: Int, any: Any)

    fun success()

    fun success(any: Any)

}
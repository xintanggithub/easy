package com.tson.easydemo.api

import retrofit2.http.Body
import retrofit2.http.POST

/**
 *  Date 2021/2/4 1:26 PM
 *
 * @author Tson
 */
interface TestApi {

    @POST("channelorg/mobile")
    suspend fun login(@Body loginRequest: LoginRequest): Any

}


data class LoginRequest(var appId: String, var password: String, var userName: String) {
    constructor() : this("103", "123456", "17501648274")
}
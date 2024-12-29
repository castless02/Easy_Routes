package com.novaengine.easyroutes.serversupport

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface DBSupportServiceInterface {

    @POST("postSelect/")
    @FormUrlEncoded
    fun provideQuery(@Field("query") query: String): Call <JsonObject>

    @GET
    fun getAvatar(@Url url: String) : Call <ResponseBody>

    @POST("api/saveImage")
    fun saveImage(@Body requestBody: MultipartBody.Part): Call<ResponseBody>
}
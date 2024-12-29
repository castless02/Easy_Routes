package com.novaengine.easyroutes.serversupport.dataclasses

import com.google.gson.annotations.SerializedName

data class RegisterData(
    @SerializedName("name")
    val name: String?,

    @SerializedName("surname")
    val surname: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("password")
    val password: String?,

    /*@SerializedName("sex")
    val sex: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("location")
    val location: String?*/
)

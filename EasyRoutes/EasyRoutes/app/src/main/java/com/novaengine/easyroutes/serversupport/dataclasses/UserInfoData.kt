package com.novaengine.easyroutes.serversupport.dataclasses

import com.google.gson.annotations.SerializedName

data class UserInfoData(
    @SerializedName("Nome")
    val name: String?,

    @SerializedName("Cognome")
    val surname: String?,

    @SerializedName("Email")
    val email: String?,

    @SerializedName("Password")
    val password: String?,

    @SerializedName("sex")
    val sex: String?,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("location")
    val location: String?
) {

}

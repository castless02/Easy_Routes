package com.novaengine.easyroutes.serversupport.dataclasses

import com.google.gson.annotations.SerializedName

data class PrenotazioneData(

    @SerializedName("dataprenotazione")
    val dataprenotazione: String?,

    @SerializedName("eta")
    val eta: String?,

    @SerializedName("dataandata")
    val dataandata: String?,

    @SerializedName("dataritorno")
    val dataritorno: String?,

    @SerializedName("luogo")
    val luogo: String?,

    @SerializedName("nomestruttura")
    val nomestruttura: String?,

    @SerializedName("idstruttura")
    val idstruttura: String?,

    @SerializedName("idutente")
    val idutente: String?,

    @SerializedName("pagamento")
    val pagamento: Int?,

    @SerializedName("persone")
    val persone: String?,

    @SerializedName("carta")
    val carta: String?,

    @SerializedName("mezzo")
    val mezzo: String?,

    @SerializedName("idcamera")
    val idcamera: String?,

)

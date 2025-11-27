package com.example.practicsing.data.etri

import com.google.gson.annotations.SerializedName

data class EtriRequest(
    @SerializedName("access_key")
    val accessKey: String,

    @SerializedName("argument")
    val argument: EtriArgument
)

data class EtriArgument(
    @SerializedName("language_code")
    val languageCode: String,

    @SerializedName("script")
    val script: String? = null,

    @SerializedName("audio")
    val audio: String
)

data class EtriResponse(
    @SerializedName("result")
    val result: Int?,   // 0 = 성공

    @SerializedName("return_object")
    val returnObject: ReturnObject?
)

data class ReturnObject(
    @SerializedName("recognized")
    val recognized: String?,

    @SerializedName("score")
    val score: Float?
)

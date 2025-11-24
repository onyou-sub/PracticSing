package com.example.practicsing.data.etri

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface EtriApiService {

    @Headers("Content-Type: application/json")
    @POST("WiseASR/Recognition")
    suspend fun recognize(@Body request: EtriRequest): EtriResponse

    @Headers("Content-Type: application/json")
    @POST("WiseASR/Pronunciation")
    suspend fun pronunciation(@Body request: EtriRequest): EtriResponse

    companion object {
        fun create(): EtriApiService {
            return Retrofit.Builder()
                .baseUrl("http://aiopen.etri.re.kr:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EtriApiService::class.java)
        }
    }
}

package com.example.practicsing.data.etri

data class EtriRequest(
    val access_key: String,
    val argument: Map<String, Any>
)

data class EtriResponse(
    val result: String?
)

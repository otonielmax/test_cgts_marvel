package com.example.testcgts.network.response

data class ResponseCommon<T>(
    val code: Int? = null,
    val status: String? = null,
    val copyright: String? = null,
    val attributionText: String? = null,
    val attributionHTML: String? = null,
    val etag: String? = null,
    val data: ResponseSettings<T>? = null
)

data class ResponseSettings<U>(
    val offset: Int? = null,
    val limit: Int? = null,
    val total: Int? = null,
    val count: Int? = null,
    val results: MutableList<U> = arrayListOf()
)
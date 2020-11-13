package com.example.testcgts.network.interfaces

import com.example.testcgts.models.Character
import com.example.testcgts.network.response.ResponseCommon
import com.example.testcgts.network.request.RequestCommon
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelAPIInterface {

    @GET("characters")
    fun getCharacters(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: String
    ): Call<ResponseCommon<Character>>

    @GET("characters/{id}")
    fun getCharactersByID(
        @Path("id") id: Int,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: String
    ): Call<ResponseCommon<Character>>
}
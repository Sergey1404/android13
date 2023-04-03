package com.example.rickandmorty

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodesApi {
    companion object {
        fun createAPI(): EpisodesApi{
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofitBuilder.create(EpisodesApi::class.java)
        }
    }

    @GET("episode/{id}")
    suspend fun getEpisodesByCharacter(
        @Path("id")
        id: String
    ): Response<EpisodeResponseNW>
}
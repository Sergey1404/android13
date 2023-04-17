package com.example.rickandmorty

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    companion object{
        fun createAPI(): RickAndMortyApi {
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofitBuilder.create(RickAndMortyApi::class.java)
        }
    }

    @GET("character")
    fun getAllCharacters(
        @Query("page")
        page: String
    ) : Call<CharacterResponse>
}

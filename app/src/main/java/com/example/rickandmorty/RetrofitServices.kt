package com.example.rickandmorty

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {
    @GET("episode/{id}")
    fun getEpisodesByCharacter(
        @Path("id")
        id: String
    ): Call<EpisodeResponseNW>

    @GET("character")
    fun getAllCharacters(
        @Query("page")
        page: String
    ) : Call<CharacterResponse>
}

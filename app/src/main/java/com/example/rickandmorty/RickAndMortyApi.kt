package com.example.rickandmorty

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getAllCharacters(
        @Query("page")
        page: String
    ) : Response<CharacterResponse>

    @GET("episode/{id}")
    suspend fun getEpisodesByCharacter(
        @Path("id")
        id: String
    ): Response<EpisodeResponseNW>
}
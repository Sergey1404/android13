package com.example.rickandmorty

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var api: Retrofit? = null

    fun getClient(): Retrofit? {
        if (api == null){
            api = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return api
    }

}

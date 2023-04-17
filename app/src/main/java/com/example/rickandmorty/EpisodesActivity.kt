package com.example.rickandmorty

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.ActivityEpisodesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class EpisodesActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEpisodesBinding
    private var episodeList = emptyList<String>()
    private val episodesAdapter = EpisodesAdapter()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        //Получить список эпизодов и отправить запрос на эти эпизоды

        episodeList = intent.getStringArrayListExtra("episodeList")!!
        Log.d("aaaIntent","get:$episodeList")

        recyclerViewInit()

        loadEpisodes()
    }
//1
    private fun loadEpisodes()  {
        val url = mutableListOf<String>()
        for (it in episodeList){
            val value = it.substringAfterLast("/")
            url.add(value)
        }
        val result = url.joinToString(",")
        Log.d("aaa","$result")
            //

        MainActivity.api.getEpisodesByCharacter(result)
            .enqueue(object : Callback<EpisodeResponseNW> {
            override fun onResponse(call: Call<EpisodeResponseNW>, response: Response<EpisodeResponseNW>) {
                if (response.isSuccessful) {
                    episodesAdapter.submitList(response.body())
                } else {
                    Log.d("EpisodesActivity", "Response error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<EpisodeResponseNW>, t: Throwable) {
                Log.d("EpisodesActivity", "Error: ${t.message}")
            }
        })
    }

    private fun recyclerViewInit() {
        binding.rvEpisodes.adapter = episodesAdapter
        binding.rvEpisodes.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }
}

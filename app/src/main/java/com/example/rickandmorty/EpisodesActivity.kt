package com.example.rickandmorty

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.ActivityEpisodesBinding
import timber.log.Timber

class EpisodesActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEpisodesBinding
    private lateinit var viewModel: EpisodeViewModel

    private var episodeList = emptyList<String>()
    private val episodesAdapter = EpisodesAdapter()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        recyclerViewInit()

        episodeList = intent.getStringArrayListExtra("episodeList")!!

        viewModel = ViewModelProvider(this)[EpisodeViewModel::class.java]
        viewModel.setEpisode(getEpisodeId(episodeList))
        viewModel.loadEpisodes()

        viewModel.episodeList.observe(this){result->
            episodesAdapter.submitList(result)
        }
    }

    private fun getEpisodeId(episodeList: List<String>): String {
        val url = mutableListOf<String>()
        for (it in episodeList) {
            val value = it.substringAfterLast("/")
            url.add(value)
        }
        Log.d("aaa","${url.joinToString(",")}")
        return url.joinToString(",")
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
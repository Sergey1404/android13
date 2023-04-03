package com.example.rickandmorty

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private val rickAndMortyAdapter = RickAdapter(
        onButtonClick = {
            viewModel.loadNextPage()
        },
        onCharacterClick = {
            val intent = Intent(this, EpisodesActivity::class.java)
            intent.putStringArrayListExtra("episodeList", ArrayList(it))
            startActivity(intent)
        } )
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        recyclerViewInit()
        initRetrofit(this)

        viewModel = ViewModelProvider(this)[MainModelView::class.java]
        viewModel.rickAndMortyList.observe(this){ result ->
            rickAndMortyAdapter.submitList(result)
        }
    }

    private fun recyclerViewInit() {
        binding.rvRickAndMorty.adapter = rickAndMortyAdapter
        binding.rvRickAndMorty.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }
    companion object{
        lateinit var api: RickAndMortyApi
        fun initRetrofit(context: Context){
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            api = retrofitBuilder.create(RickAndMortyApi::class.java)
        }
    }
}
package com.example.rickandmorty

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.SimpleTimeZone

class EpisodeViewModel: ViewModel() {
    private val _episodeList = MutableLiveData<List<EpisodeResponseNW.EpisodeResponseNWItem>>()
    val episodeList get() = _episodeList
    private var episodeId: String = ""

    private var episodesAPI = EpisodesApi.createAPI()

    init {
    }
    fun setEpisode(item: String){
        episodeId = item
        Log.d("id","$episodeId")
    }

    fun loadEpisodes() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = MainActivity.api.getEpisodesByCharacter(episodeId)
            try {
                if (response.isSuccessful) {
                    _episodeList.postValue(response.body())
                } else {
                    Log.e("Error", "${response.code()}")
                }
            } catch (e: HttpException) {
                Log.e("aaa", "Exception ${e.message}")
            } catch (e: Throwable) {
                Log.d("ooops", "Something else went wrong: $e")
            }
        }
    }
}
package com.example.rickandmorty

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class MainModelView : ViewModel() {
    private val _rickAndMortyList = MutableLiveData<List<RickAndMortySealed>>()
    val rickAndMortyList get() = _rickAndMortyList
    var page: Int = 1

    init {
        loadAllCharacters(page.toString())
    }

    private fun loadAllCharacters(page: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = MainActivity.api.getAllCharacters(page)

            try {
                if (response.isSuccessful) {
                    val actualList = _rickAndMortyList.value.orEmpty().toMutableList()
                    actualList.removeAll { it == RickAndMortySealed.Button }

                    val newList = mutableListOf<RickAndMortySealed>()
                    val characters = response
                        .body()
                        ?.characters
                        ?.map { RickAndMortySealed.Character(it) }
                        ?: emptyList()

                    newList.addAll(characters)
                    newList.add(RickAndMortySealed.Button)

                    _rickAndMortyList.postValue(actualList + newList)
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

    fun loadNextPage() {
        ++page
        Log.d("page:", "$page")
        if (page == 42) {
            return
        }
        loadAllCharacters(page.toString())
    }
}

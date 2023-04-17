package com.example.rickandmorty

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModelView : ViewModel() {
    private val _rickAndMortyList = MutableLiveData<List<RickAndMortySealed>>()
    val rickAndMortyList get() = _rickAndMortyList
    var page: Int = 1

    init {
        loadAllCharacters(page.toString())
    }

    private fun loadAllCharacters(page: String) {
        MainActivity.api.getAllCharacters(page).enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
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

                    _rickAndMortyList.value = actualList + newList
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                Log.e("aaa", "$t")
            }
        })
    }

    fun loadNextPage() {
        ++page
        Log.d("page:","$page")
        if(page == 42){
            return
        }
        loadAllCharacters(page.toString())
    }
}

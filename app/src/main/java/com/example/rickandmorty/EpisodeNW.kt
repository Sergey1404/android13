package com.example.rickandmorty


import com.google.gson.annotations.SerializedName
data class EpisodesNW(
    @SerializedName("results")
    val episodes: List<EpisodeNW>
)
data class EpisodeNW(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("characters")
    val characters: List<String>,
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
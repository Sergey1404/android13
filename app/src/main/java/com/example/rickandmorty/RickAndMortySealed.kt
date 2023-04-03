package com.example.rickandmorty

sealed class RickAndMortySealed {
    data class Character(val character: CharacterResponse.RickAndMortyNW): RickAndMortySealed()
    object Button: RickAndMortySealed()
}
package com.example.starwarsinfo.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface CharactersApi {
    //Aquí van los endpoints

    @GET
    fun getCharacters(
        @Url url: String?
    ): Call<CharacterDetail>

    @GET
    fun getPlanetDetail(
        @Url url: String?
    ): Call<PlanetDetail>

    @GET
    fun getFilmDetail(
        @Url url: String?
    ): Call<FilmDetail>
}
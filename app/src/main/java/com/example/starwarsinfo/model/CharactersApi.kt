package com.example.starwarsinfo.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("films/{id}/")
    fun getFilmDetail(
        @Path("id") id: String?
    ): Call<MoviesDetail>
}
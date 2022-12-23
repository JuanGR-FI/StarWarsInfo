package com.example.starwarsinfo.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Constants {
    const val BASE_URL = "https://swapi.dev/api/"
    const val CHARACTERS_URL = "people/"
    const val FILMS_URL = "films/"
    const val LOGTAG = "LOGS"

    fun getRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
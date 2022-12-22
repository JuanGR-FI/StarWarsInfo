package com.example.starwarsinfo.model

data class FilmDetail(
    val results: ArrayList<Film> = arrayListOf()
)

data class Film(
    val title: String? = null,
    val director: String? = null,
    val producer: String? = null,
    val release_date: String? = null
)

/*


 */
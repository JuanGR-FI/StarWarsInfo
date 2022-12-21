package com.example.starwarsinfo.model

data class CharacterDetail(
    var results: ArrayList<Character> = arrayListOf()
)

data class Results(
    val name: String? = null,
    val height: String? = null,
    val birth_year: String? = null,
    val gender: String? = null,
    val homeworld: String? = null,
    val films: ArrayList<String>? = null
)
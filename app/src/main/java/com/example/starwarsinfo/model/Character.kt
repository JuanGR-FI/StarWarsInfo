package com.example.starwarsinfo.model

data class Character(
   // val id: Long? = null,
    val name: String? = null,
    val height: String? = null,
    val birth_year: String? = null,
    val gender: String? = null,
    val homeworld: String? = null,
    val films: ArrayList<String>? = null
)

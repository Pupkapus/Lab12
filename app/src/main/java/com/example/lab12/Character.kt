package com.example.lab12

data class Character(val id: Int,
                     val name: String,
                     val species: String,
                     val gender: String,
                     val status: String,
                     val image: String,
                     val origin: String,
                     var location: String)

var DataCharacters: List<com.example.lab12.Character>? = null

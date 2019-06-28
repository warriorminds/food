package com.warriorminds.lifesum.repositories

interface Locale {
    fun getCountry(): String

    fun getLanguage(): String
}
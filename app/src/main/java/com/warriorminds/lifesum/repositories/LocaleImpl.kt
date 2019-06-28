package com.warriorminds.lifesum.repositories

import javax.inject.Inject

class LocaleImpl @Inject constructor(): Locale {
    override fun getCountry() = java.util.Locale.getDefault().country

    override fun getLanguage() = java.util.Locale.getDefault().language

}
package com.warriorminds.lifesum

import java.io.File

fun getJson(jsonFile: String, classLoader: ClassLoader): String {
    val uri = classLoader.getResource(jsonFile)
    val file = File(uri.path)
    return String(file.readBytes())
}
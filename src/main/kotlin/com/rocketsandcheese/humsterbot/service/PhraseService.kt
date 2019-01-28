package com.rocketsandcheese.humsterbot.service

interface PhraseService {
    fun getPhrases(categoryId: Long): String
    fun addPhrase(categoryId: Long, phraseValue: String): String
    fun deletePhrase(phraseId: Long): String
    fun getRandomPhrase(): String
}

package com.rocketsandcheese.humsterbot.service

interface PhraseService {
    fun getPhrases() : String
    fun addPhrase(phraseValue: String): String
    fun deletePhrase(phraseId: Long): String
    fun getRandomPhrase(): String
}

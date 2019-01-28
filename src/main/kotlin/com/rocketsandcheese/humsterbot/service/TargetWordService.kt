package com.rocketsandcheese.humsterbot.service

interface TargetWordService {
    fun createWord(categoryId: Long, value: String): String
    fun getWords(categoryId: Long): String
}

package com.rocketsandcheese.humsterbot.service

interface CategoryService {
    fun getCategories(): String
    fun removeCategory(id: Long): String
    fun createCategory(value: String): String
}

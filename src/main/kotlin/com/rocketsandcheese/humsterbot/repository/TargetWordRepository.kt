package com.rocketsandcheese.humsterbot.repository

import com.rocketsandcheese.humsterbot.entity.TargetWord
import org.springframework.data.jpa.repository.JpaRepository

interface TargetWordRepository : JpaRepository<TargetWord, Long> {
    fun findByCategoryId(categoryId: Long): List<TargetWord>
}

package com.rocketsandcheese.humsterbot.repository

import com.rocketsandcheese.humsterbot.entity.Phrase
import org.springframework.data.jpa.repository.JpaRepository

interface PhraseRepository : JpaRepository<Phrase, Long> {
    fun findByCategoryId(categoryId: Long): List<Phrase>
}

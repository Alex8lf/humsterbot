package com.rocketsandcheese.humsterbot.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Phrase(
        @Id @GeneratedValue val id: Long,
        val value: String
//        val lastUsed: Instant
)

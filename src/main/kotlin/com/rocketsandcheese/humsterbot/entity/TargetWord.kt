package com.rocketsandcheese.humsterbot.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class TargetWord(
        @Id @GeneratedValue val id: Long,
        val value: String
)

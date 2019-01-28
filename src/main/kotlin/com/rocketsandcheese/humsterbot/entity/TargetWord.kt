package com.rocketsandcheese.humsterbot.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class TargetWord(
        @Id @GeneratedValue val id: Long,
        val value: String,
        @ManyToOne val category : Category
)

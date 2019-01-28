package com.rocketsandcheese.humsterbot.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Phrase(
    @Id @GeneratedValue val id: Long?,
    val value: String,
    @ManyToOne val category: Category
) {
    constructor(value: String, category: Category) : this(null, value, category)
}

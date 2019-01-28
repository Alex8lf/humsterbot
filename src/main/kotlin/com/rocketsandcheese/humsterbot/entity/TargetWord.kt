package com.rocketsandcheese.humsterbot.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class TargetWord(
    @Id @GeneratedValue val id: Long?,
    @Column(unique = true) val value: String,
    @ManyToOne val category: Category
) {
    constructor(value: String, category: Category) : this(null, value, category)
}

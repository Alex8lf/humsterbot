package com.rocketsandcheese.humsterbot.entity

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Category(
    @Id @GeneratedValue val id: Long?,
    val value: String,
    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL]) val phrases: List<Phrase>? = ArrayList(),
    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL]) val targetWords: List<TargetWord>? = ArrayList()
) {
    constructor(value: String) : this(null, value, null, null)
}

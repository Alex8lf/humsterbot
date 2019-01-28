package com.rocketsandcheese.humsterbot.repository

import com.rocketsandcheese.humsterbot.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long>

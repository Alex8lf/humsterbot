package com.rocketsandcheese.humsterbot.service.impl

import com.rocketsandcheese.humsterbot.entity.Category
import com.rocketsandcheese.humsterbot.repository.CategoryRepository
import com.rocketsandcheese.humsterbot.service.CategoryService
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private var categoryRepository: CategoryRepository) : CategoryService {
    override fun createCategory(value: String): String {
        val category = categoryRepository.save(Category(value))
        return "Category \"" + category.value + "\" with id: " + category.id + " successfully added"
    }

    override fun removeCategory(id : Long): String {
        categoryRepository.deleteById(id)
        return "Category successfully removed"
    }

    override fun getCategories(): String {
        val categories = categoryRepository.findAll()
        val formattedOutput = StringBuilder()
        formattedOutput.append("Categories")
        categories.forEach { formattedOutput.append("\n" + it.id + "\t" + it.value) }
        return formattedOutput.toString()
    }
}

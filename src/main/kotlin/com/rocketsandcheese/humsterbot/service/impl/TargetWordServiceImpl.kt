package com.rocketsandcheese.humsterbot.service.impl

import com.rocketsandcheese.humsterbot.entity.TargetWord
import com.rocketsandcheese.humsterbot.repository.CategoryRepository
import com.rocketsandcheese.humsterbot.repository.TargetWordRepository
import com.rocketsandcheese.humsterbot.service.TargetWordService
import org.springframework.stereotype.Service

@Service
class TargetWordServiceImpl(
    private val targetWordRepository: TargetWordRepository,
    private val categoryRepository: CategoryRepository
) : TargetWordService {
    override fun createWord(categoryId: Long, value: String): String {
        val category = categoryRepository.getOne(categoryId)
        val targetWord = targetWordRepository.save(TargetWord(value, category))
        return "Target word \"" + targetWord.value + "\" in category \"" + targetWord.category.value +
            "\" successfully " + "created"
    }

    override fun getWords(categoryId: Long): String {
        val category = categoryRepository.findById(categoryId).get()
        val targetWords = targetWordRepository.findByCategoryId(categoryId)
        val formattedOutput = StringBuilder()
        formattedOutput.append("Target words for category \"" + category.value + "\"")
        targetWords.forEach { formattedOutput.append("\n" + it.id + "\t" + it.value) }
        return formattedOutput.toString()
    }
}

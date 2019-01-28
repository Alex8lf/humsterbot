package com.rocketsandcheese.humsterbot.service.impl

import com.rocketsandcheese.humsterbot.entity.Phrase
import com.rocketsandcheese.humsterbot.repository.CategoryRepository
import com.rocketsandcheese.humsterbot.repository.PhraseRepository
import com.rocketsandcheese.humsterbot.service.PhraseService
import org.springframework.stereotype.Service
import java.util.Random
import javax.transaction.Transactional

@Service
class PhraseServiceImpl(
    private val phraseRepository: PhraseRepository,
    private val categoryRepository: CategoryRepository
) : PhraseService {

    override fun getPhrases(categoryId: Long): String {
        val category = categoryRepository.findById(categoryId).get()
        val phrases = phraseRepository.findByCategoryId(categoryId)
        val formattedOutput = StringBuilder()
        formattedOutput.append("Phrases for category \"" + category.value + "\"")
        phrases.forEach { formattedOutput.append("\n" + it.id + "\t" + it.value) }
        return formattedOutput.toString()
    }

    override fun addPhrase(categoryId: Long, phraseValue: String): String {
        val category = categoryRepository.getOne(categoryId)
        var phrase = Phrase(phraseValue, category)
        phrase = phraseRepository.save(phrase)
        return "Phrase \"" + phrase.value + "\" in category \"" + phrase.category.value + "\" successfully created"
    }

    @Transactional
    override fun deletePhrase(phraseId: Long): String {
        phraseRepository.deleteById(phraseId)
        return ("Phrase successfully removed")
    }

    override fun getRandomPhrase(): String {
        //TODO refactor to get random phrase directly from db
        val phrases = phraseRepository.findAll()
        val randomIndex = Random().nextInt(phrases.size)
        return phrases[randomIndex].value
    }
}

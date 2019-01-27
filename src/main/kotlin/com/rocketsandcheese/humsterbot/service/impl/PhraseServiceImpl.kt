package com.rocketsandcheese.humsterbot.service.impl

import com.rocketsandcheese.humsterbot.entity.Phrase
import com.rocketsandcheese.humsterbot.repository.PhraseRepository
import com.rocketsandcheese.humsterbot.service.PhraseService
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class PhraseServiceImpl(private var phraseRepository: PhraseRepository): PhraseService {

    override fun getPhrases(): String {
        val phrases = phraseRepository.findAll().toString()
        return phrases;
    }

    override fun addPhrase(phraseValue: String): String {
        val phrase = Phrase(0, phraseValue)
        phraseRepository.save(phrase)
        return "Phrase \"$phraseValue\"successfully created"
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

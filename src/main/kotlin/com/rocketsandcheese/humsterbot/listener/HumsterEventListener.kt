package com.rocketsandcheese.humsterbot.listener

import com.rocketsandcheese.humsterbot.entity.TargetWord
import com.rocketsandcheese.humsterbot.repository.TargetWordRepository
import com.rocketsandcheese.humsterbot.service.HumsterBotService
import com.rocketsandcheese.humsterbot.service.PhraseService
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class HumsterEventListener : ListenerAdapter() {

    @Autowired
    lateinit var humsterBotService: HumsterBotService

    @Autowired
    lateinit var phraseService: PhraseService

    @Autowired
    lateinit var targetWordRepository: TargetWordRepository

    @Value("\${helpMessage}")
    lateinit var helpMessage: String

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author?.isBot == true) return
        val message = event.message
        val messageValue = message.contentDisplay.toLowerCase()
        if (event.isFromType(ChannelType.PRIVATE)) {
            handlePrivateMessage(event)
        } else if (containsHatedWords(messageValue)) {
            event.channel.sendMessage(phraseService.getRandomPhrase()).queue()
        }
        if (message?.mentionedUsers != null && message.mentionedUsers.contains(event.jda.selfUser)) {
            handleSelfMention(event)
        }
    }

    private fun containsHatedWords(message: String): Boolean {
        val hatedWords = targetWordRepository.findAll().stream().map { targetWord -> targetWord.value }.collect(Collectors.toList())
        hatedWords.forEach { word -> if (message.contains(word)) return true }
        return false
    }

    private fun handlePrivateMessage(event: MessageReceivedEvent) {
        val message = event.message.contentDisplay
        val args = message.split(" ")

        if (humsterBotService.isPaused()) {
            if (args[0].equals("unpause")) {
                humsterBotService.setPaused(false)
            } else {
                return
            }
        }

        when (args[0]) {
            "me" -> humsterBotService.broadcastMessage(args[1].toLong(), message.substring(message.indexOf(args[2])))
            "help" -> event.channel.sendMessage(helpMessage).queue()
            "phrase" -> when (args[1]) {
                "add" -> event.channel.sendMessage(phraseService.addPhrase(message.substring(message.indexOf(args[2])))).queue()
                "rm" -> event.channel.sendMessage(phraseService.deletePhrase(args[2].toLong())).queue()
                "list" -> event.channel.sendMessage(phraseService.getPhrases()).queue()
            }
            "target" -> when (args[1]) {
                "add" -> {
                    val savedWord = targetWordRepository.save(TargetWord(0, message.substring(message.indexOf(args[2]))))
                    event.channel.sendMessage("Target word" + savedWord.value + "saved").queue()
                }
                "rm" -> {
                    targetWordRepository.deleteById(args[2].toLong())
                    event.channel.sendMessage("Target word successfully removed").queue()
                }
                "list" -> event.channel.sendMessage(targetWordRepository.findAll().toString()).queue()
            }
            "reboot" -> System.exit(0)
            "pause" -> humsterBotService.setPaused(true)
        }
    }

    private fun handleSelfMention(event: MessageReceivedEvent) {
        val message = event.message.contentDisplay.toLowerCase()
        when {
            message.contains("сколько до дембеля") -> event.channel.sendMessage(humsterBotService.getReturnDate()).queue()
            message.contains("посоветуй аниме") -> event.channel.sendMessage("https://www.youtube.com/watch?v=16q43J-dBFg").queue()
            else -> event.channel.sendMessage("ди нах").queue()
        }
    }
}

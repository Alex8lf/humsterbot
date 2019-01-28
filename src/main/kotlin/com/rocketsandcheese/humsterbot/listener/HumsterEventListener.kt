package com.rocketsandcheese.humsterbot.listener

import com.rocketsandcheese.humsterbot.repository.TargetWordRepository
import com.rocketsandcheese.humsterbot.service.CategoryService
import com.rocketsandcheese.humsterbot.service.HumsterBotService
import com.rocketsandcheese.humsterbot.service.PhraseService
import com.rocketsandcheese.humsterbot.service.TargetWordService
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class HumsterEventListener : ListenerAdapter() {

    @Value("\${helpMessage}")
    private lateinit var helpMessage: String

    @Autowired
    private lateinit var humsterBotService: HumsterBotService

    @Autowired
    private lateinit var phraseService: PhraseService

    @Autowired
    private lateinit var targetWordRepository: TargetWordRepository

    @Autowired
    private lateinit var categoryService: CategoryService

    @Autowired
    private lateinit var targetWordService: TargetWordService

    //todo use positive numbers indexes
    @Transactional //todo move transactional to specific service methods
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return
        val message = event.message
        val messageText = message.contentDisplay.toLowerCase()

        if (event.isFromType(ChannelType.PRIVATE)) {
            handlePrivateMessage(event)
        } else if (message?.mentionedUsers != null && message.mentionedUsers.contains(event.jda.selfUser)) {
            if (humsterBotService.isPaused()) return
            handleSelfMention(event)
        } else {
            if (humsterBotService.isPaused()) return
            sendMessage(event.channel, phraseService.getReaction(messageText))
        }
    }

    private fun sendMessage(channel: MessageChannel, message: String) = channel.sendMessage(message).queue()

    private fun handlePrivateMessage(event: MessageReceivedEvent) {
        val channel = event.channel
        val messageText = event.message.contentDisplay
        val args = messageText.split(" ")
        args.forEach { it.toLowerCase() }

        if (humsterBotService.isPaused()) {
            if (args[0].equals("unpause")) {
                humsterBotService.setPaused(false)
            } else {
                return
            }
        }

        when (args[0]) {
            "me" -> humsterBotService.broadcastMessage(
                args[1].toLong(), messageText.substring(messageText.indexOf(args[2]))
            )
            "help" -> sendMessage(channel, helpMessage)

            "category" -> when (args[1]) {
                "list" -> sendMessage(channel, categoryService.getCategories())
                "rm" -> sendMessage(channel, categoryService.removeCategory(args[2].toLong()))
                "add" -> sendMessage(
                    channel, categoryService.createCategory(messageText.substring(messageText.indexOf(args[2])))
                )
            }

            "phrase" -> when (args[1]) {
                "add" -> sendMessage(
                    channel,
                    phraseService.addPhrase(args[2].toLong(), messageText.substring(messageText.indexOf(args[3])))
                )
                "rm" -> event.channel.sendMessage(phraseService.deletePhrase(args[2].toLong())).queue()
                "list" -> event.channel.sendMessage(phraseService.getPhrases(args[2].toLong())).queue()
            }

            "target" -> when (args[1]) {
                "add" -> {
                    sendMessage(
                        channel, targetWordService.createWord(
                            args[2].toLong(), messageText.substring(messageText.indexOf(args[3]))
                        )
                    )
                }
                "rm" -> {
                    targetWordRepository.deleteById(args[2].toLong())
                    event.channel.sendMessage("Target word successfully removed").queue()
                }
                "list" -> sendMessage(channel, targetWordService.getWords(args[2].toLong()))
            }

            "reboot" -> System.exit(0)
            "pause" -> humsterBotService.setPaused(true)
        }
    }

    private fun handleSelfMention(event: MessageReceivedEvent) {
        val message = event.message.contentDisplay.toLowerCase()
        when {
            message.contains("сколько до дембеля") -> event.channel.sendMessage(
                humsterBotService.getReturnDate()
            ).queue()
            message.contains("посоветуй аниме") -> event.channel.sendMessage(
                "https://www.youtube.com/watch?v=16q43J-dBFg"
            ).queue()
            else -> event.channel.sendMessage("ди нах").queue()
        }
    }
}

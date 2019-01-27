package com.rocketsandcheese.humsterbot.service.impl

import com.rocketsandcheese.humsterbot.listener.HumsterEventListener
import com.rocketsandcheese.humsterbot.service.HumsterBotService
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.hooks.EventListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.*
import javax.annotation.PostConstruct

@Service
class HumsterBotServiceImpl(private val humsterEventListener: HumsterEventListener) : HumsterBotService {

    private var paused = false

    lateinit var jda: JDA

    override fun getJdi(): JDA = jda

    @Value("\${token}")
    private lateinit var token: String

    @Value("\${returnDate}")
    private lateinit var returnDateRaw: String

    //TODO refactor with constructor
    @PostConstruct
    override fun startBot() {
        jda = JDABuilder(token)
                .addEventListeners(humsterEventListener)
                .build()
    }

    override fun addListeners(vararg listener: EventListener) {
        jda.addEventListener(listener)
    }

    override fun sendMessage(guild: Long, channel: Long, message: String) {
        jda.getGuildById(guild)
                .getTextChannelById(channel)
                .sendMessage(message)
                .queue()
    }

    override fun broadcastMessage(channelId: Long, message: String) {
        jda.getTextChannelById(channelId).sendMessage(message).queue()
    }

    override fun getReturnDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.parse(returnDateRaw)
        val today = Date()
        return "Я вернусь через " + ChronoUnit.DAYS.between(today.toInstant(), date.toInstant()).toString() + " дней"
    }

    override fun setPaused(paused: Boolean) {
        this.paused = paused
    }

    override fun isPaused(): Boolean {
        return paused
    }
}


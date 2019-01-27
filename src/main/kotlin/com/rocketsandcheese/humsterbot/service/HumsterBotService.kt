package com.rocketsandcheese.humsterbot.service

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.hooks.EventListener

interface HumsterBotService {
    fun sendMessage(guild: Long, channel: Long, message: String)
    fun addListeners(vararg listener: EventListener)
    fun startBot()
    fun getJdi(): JDA
    fun broadcastMessage(channelId: Long, message: String)
    fun getReturnDate() : String
    fun setPaused(paused: Boolean)
    fun isPaused() : Boolean
}


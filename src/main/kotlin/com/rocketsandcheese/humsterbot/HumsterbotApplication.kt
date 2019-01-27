package com.rocketsandcheese.humsterbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@PropertySource(value = ["classpath:/application.properties"], encoding = "US-ASCII")
class HumsterbotApplication

fun main(args: Array<String>) {
	runApplication<HumsterbotApplication>(*args)
}


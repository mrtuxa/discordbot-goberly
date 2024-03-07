package com.goberly.system

import com.goberly.system.messages.Handbuch
import com.goberly.system.messages.Welcome
import com.goberly.system.messages.PreisAlarm
import com.goberly.system.messages.TwitterPush

import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import net.dv8tion.jda.api.JDA

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

val activityExamples = listOf(
    "Server einrichten",
    "Netzwerk Ping Pong",
    "Kabelsalat",
    "Minecraft",
    "Kunden beraten...",
    "Einen Kaffee trinken",
    "Datenbanken verwalten",
    "Server bauen",
    "Server verwalten",
    "Server verkaufen",
    "Server einrichten",
    "Server konfigurieren",
    "Server updaten",
    "Server sichern",
    "Auf Kunden warten",
    "Büro aufräumen",
    "Aufträge bearbeiten",
)

fun updateActivityStatus(jda: JDA) {
    val guildSize = jda.guilds.size
    val status = if (guildSize == 1) {
        "${jda.guilds.size}"
    } else {
        "$guildSize"
    }

    val randomActivity = activityExamples.random()
    jda.presence.activity = Activity.playing("$randomActivity")
}

class OnReady : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {

        val guild = event.jda.getGuildById(1076995283466068120L)

        guild!!.updateCommands().addCommands(
            SlashCommmands().setWelcome(),
            SlashCommmands().setHandbuch(),
            SlashCommmands().PreisAlarm(),
            SlashCommmands().TwitterPush()
        ).queue(
            { println("Slash Commands registered") },
            { println("Slash Commands registration failed") }
        )

        event.jda.addEventListener(Welcome())
        event.jda.addEventListener(Handbuch())
        event.jda.addEventListener(PreisAlarm())
        event.jda.addEventListener(TwitterPush())

        // Erstes Update des Aktivitätsstatus
        updateActivityStatus(event.jda)

        // Aktivitätsstatus alle 5 Minuten aktualisieren
        val scheduler = Executors.newScheduledThreadPool(1)
        scheduler.scheduleAtFixedRate({ updateActivityStatus(event.jda) }, 5, 5, TimeUnit.MINUTES)
        /*
        val guildSize = event.jda.guilds.size

        if(guildSize == 1) {
            event.jda.presence.activity = Activity.playing("on ${event.jda.guilds.size} guild")
        } else {
            event.jda.presence.activity = Activity.playing("on $guildSize guilds")
        }*/
    }

    class SlashCommmands {
        fun setWelcome(): SlashCommandData {
            return Commands.slash("setwelcome", "Set the welcome channel")
        }
        fun setHandbuch(): SlashCommandData {
            return Commands.slash("sethandbuch", "Set the handbuch channel")
        }
        fun PreisAlarm(): SlashCommandData {
            return Commands.slash("preisalarm", "Set the handbuch channel")
                .addOption(OptionType.STRING, "preis", "Der Preis", true)
                .addOption(OptionType.STRING, "name", "Der Name", true)
                .addOption(OptionType.STRING, "beschreibung", "Die Beschreibung", true)
                .addOption(OptionType.STRING, "link", "Der Link", true)
        }
        fun TwitterPush(): SlashCommandData {
              return Commands.slash("twitterpush", "Sendet den Twitter-Link als Card von Discord generiert")
                .addOption(OptionType.STRING, "link", "Der Twitter-Link, der gesendet werden soll", true)
        }
    }
}
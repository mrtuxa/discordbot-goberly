package com.goberly.system.messages

import com.goberly.system.utils.WEBSITE
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel

class PreisAlarm : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name == "preisalarm") {

            val preis = event.getOption("preis")!!.asString
            val name = event.getOption("name")!!.asString
            val beschreibung = event.getOption("beschreibung")!!.asString
            val link = event.getOption("link")!!.asString
            val channel = event.jda.getNewsChannelById (1083399555116175420) //NewsChannel von Preisalarm (1083399555116175420)

            //val channel = guildChannel as? TextChannel

            val embed = EmbedBuilder()

            embed.setTitle("🚨 Preisalarm")
            embed.setDescription(beschreibung + "\n\n")

            embed.addField("Name", name, true)
            embed.addField("Preis", preis, true)
            embed.addField("Link", link, true)

            embed.setAuthor(event.jda.selfUser.name, WEBSITE, event.jda.selfUser.avatarUrl)
            embed.setFooter(event.jda.selfUser.name, event.jda.selfUser.avatarUrl)

            if (channel != null) {
                channel.sendMessageEmbeds(embed.build()).queue()
                event.reply("Preisalarm wurde gesetzt").queue()
            } else {
                    event.reply("Der Ankündigungskanal konnte nicht gefunden werden. Bitte überprüfe die Konfiguration. Aktuelle ID: " + channel + " "+ channel?.type)
                    .setEphemeral(true).queue()
            }
        }
    }
}
package com.goberly.system.messages
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class TwitterPush : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {

        if (event.name == "TwitterPush") {

                val link = event.getOption("link") !!.asString
                val channel = event.jda.getNewsChannelById(1083408525507710976)

            event.reply("Tweet wird gepusht").queue()

            if (channel != null) {
                println("Sending message to channel: $channel") // Debug output
                channel.sendMessage(link).queue { message ->
                    println("Message sent: $message") // Debug output
                    event.reply("Neuer Tweet wurde gepusht: ${message.jumpUrl}").queue()
                }
            } else {
                println("Channel not found") // Debug output
                event.reply("Der Textkanal konnte nicht gefunden werden. Bitte überprüfe die Konfiguration. Aktuelle ID: " + channel )
                    .setEphemeral(true).queue()
            }


        }
    }
}

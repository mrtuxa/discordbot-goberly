package com.goberly.system.messages

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.Button
import com.goberly.system.utils.WELCOME
class Welcome : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if(event.name == "setwelcome") {
            val channel = event.jda.getTextChannelById(WELCOME)

            val embed = EmbedBuilder()
                .setTitle(Locale.title())
                .setDescription(Locale.description())
                .setAuthor(Locale.Companion.SetAuthor.name(), Locale.Companion.SetAuthor.discordUrl(), Locale.Companion.SetAuthor.iconUrl())
                .setFooter(Locale.Companion.SetAuthor.name(), Locale.Companion.SetAuthor.iconUrl())

            channel!!.sendMessageEmbeds(embed.build()).addComponents(buttons()).queue()
        }
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        if (event.componentId == "german") {
            val role = event.guild!!.getRoleById(1086323527843594312)
            val englishrole = event.guild!!.getRoleById(1086323510789546115)
            if (event.member!!.roles.contains(englishrole)) {
                event.reply("You are already verified as english speaking user").setEphemeral(true).queue()
            } else if (event.member!!.roles.contains(role)) {
                event.reply("Du bist bereits als deutschsprachiger Nutzer verifiziert").setEphemeral(true).queue()
            } else {
                event.guild!!.addRoleToMember(event.member!!, role!!).queue()
                event.reply("Du bist nun als deutschsprachiger Nutzer verifiziert").setEphemeral(true).queue()
            }
        } else if (event.componentId == "english") {
            val germanrole = event.guild!!.getRoleById(1086323527843594312)
            val role = event.guild!!.getRoleById(1086323510789546115)
            if (event.member!!.roles.contains(germanrole)) {
                event.reply("Du bist bereits als deutschsprachiger Nutzer verifiziert").setEphemeral(true).queue()
            } else if (event.member!!.roles.contains(role)) {
                event.reply("You are already verified as english speaking user").setEphemeral(true).queue()
            } else {
                event.guild!!.addRoleToMember(event.member!!, role!!).queue()
                event.reply("You are now verified as english speaking user").setEphemeral(true).queue()
            }
        } else if (event.componentId == "removeLanguage") {
            val germanRole = event.guild!!.getRoleById(1086323527843594312)
            if (event.member!!.roles.contains(germanRole)) {
                event.guild!!.removeRoleFromMember(event.member!!, germanRole!!).queue()
                event.reply("Deutschsprachiger Nutzer wurde entfernt").setEphemeral(true).queue()
            } else {
                val englishRole = event.guild!!.getRoleById(1086323510789546115)
                if (event.member!!.roles.contains(englishRole)) {
                    event.guild!!.removeRoleFromMember(event.member!!, englishRole!!).queue()
                    event.reply("English speaking user was removed").setEphemeral(true).queue()
                } else {
                    event.reply("You are not verified as english or german speaking user").setEphemeral(true).queue()
                }
            }
        }
    }

    class Locale {
        companion object {
            fun title(): String {
                return "Welcome to GoBerly Community Discord!"
            }
            fun description(): String {
                return "We are thrilled to have you here and excited that you have decided to join our community. You have arrived at the entrance area of our Discord, where you can explore all that our server has to offer. Currently, you are considered unverified and have limited access to our channels and content.\n" +
                        "\n" +
                        "To unlock more features, simply select your preferred language using the interactive buttons located below this message. Please note that you can only choose one language and any previously selected languages will be removed.\n" +
                        "\n" +
                        "Once you have selected your language, you will gain full access to our Discord server and can start chatting and connecting with other members.\n" +
                        "\n" +
                        "We hope you have a great time here and look forward to seeing you in our chats!"
            }
            class SetAuthor {
                companion object {
                    fun name(): String {
                        return "GoBerly Community"
                    }
                    fun discordUrl(): String {
                        return "https://discord.gg/goberly"
                    }
                    fun iconUrl(): String {
                        return "https://raw.githubusercontent.com/goberly/designs/main/goberly_avatar.png"
                    }

                }
            }
        }
    }

    private fun buttons(): ActionRow {
        fun german(): Button {
            return Button.primary("german", "German")
        }
        fun english(): Button {
            return Button.primary("english", "English")
        }
        fun removeLanguage(): Button {
            return Button.danger("removeLanguage", "Remove Language")
        }
        return ActionRow.of(german(), english(), removeLanguage())
    }
}

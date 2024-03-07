package com.goberly.system.messages

import com.goberly.system.utils.HANDBUCH
import com.goberly.system.utils.STELLDICHVOR
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.text.TextInput
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle
import net.dv8tion.jda.api.interactions.modals.Modal
import java.time.Instant

class Handbuch : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name == "sethandbuch") {
            event.deferReply().queue()
            val channel = event.jda.getTextChannelById(HANDBUCH)

            val embed = EmbedBuilder()
                .setTitle("GoBerly Community - Informationen Panel")
                .setDescription(locale().description())
                .setAuthor("GoBerly", "https://goberly.com", event.jda.selfUser.avatarUrl)
                .setFooter("GoBerly", event.jda.selfUser.avatarUrl)

            event.reply("Handbuch wurde erfolgreich gesetzt").setEphemeral(true).queue()
            channel!!.sendMessageEmbeds(embed.build()).addComponents(Buttons()).queue()
        }
    }

    class locale {
        fun description(): String {
            return "Willkommen auf unserem GoBerly Discord! Hier findest du alle wichtigen Informationen zu unserem Server. Verwende die interaktiven Buttons unter dieser Nachricht, um unsere Regeln einzusehen, einen kurzen Guide durchzulesen, deine Rollen anzupassen und dich vorzustellen.\n" +
                    "\n" +
                    "Um alles übersichtlich zu halten, haben wir verschiedene Channels eingerichtet. Achte darauf, welche Themen in welchen Channel gehören.\n" +
                    "\n" +
                    "Hinweis:\n" +
                    ":small_blue_diamond: Bitte beachte, dass unser Team auf dem GoBerly Discord keinen offiziellen Support anbietet! Für allgemeine Fragen und vor allem für den Community-Support steht dir unser Helpdesk zur Verfügung.\n" +
                    "\n" +
                    ":small_blue_diamond: Deine Sicherheit hat für uns oberste Priorität. Bitte schaue dir den Infobereich an, um dich selbst zu schützen."
        }

    }

    /*
    feedback
    guide (not implemented)
    rollen
    selbstschutz
    stelldichvor
    feedback
     */


    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        if(event.componentId == "regelwerk") {
            val embed = EmbedBuilder()
                .setTitle("GoBerly Community - Regelwerk")
                .addField("1: Verhaltensgrundsatz", "Es wird immer ein freundlicher und respektvoller Umgang mit anderen Usern gefordert. Negativität ist hier unerwünscht. Unter keinen Umständen erlauben wir NSFW & politische Inhalte. Jede Art von Beleidigung oder Spam ist verboten.", true)
                .addField("2: Pingen von Teammitgliedern", "Das Anpingen von Teammitgliedern (GoBerly Teammitgliedern, Administratoren, Moderatoren und Community Helpern) ist nicht erlaubt. Das Anpingen aufgrund einer Supportanfrage zum eigenen Server ist nicht erlaubt.", true)
                .addField("3: Profil", "Dein Nutzername muss pingbar und leserlich sein. Zudem ist Hoisting durch etwaige Sonderzeichen vor eurem Nutzernamen untersagt. Nutzernamen/Profilbilder dürfen keine Beleidigungen, Provokationen, pornografische oder rassistische Inhalte enthalten und sind angemessen zu wählen. Wir behalten uns das Recht vor, diese gegebenenfalls anzupassen.", true)
                .addField("4: Leaks", "Private Daten von sich selbst oder einer anderen Person dürfen nicht ohne Zustimmung öffentlich geteilt werden.", true)
                .addField("5: Werbung", "Das Teilen von Discord-Einladungen ist im öffentlichen Bereich verboten. Werbung ist in den dafür vorgesehenen Kanälen gestattet! Das Werben per DM ist strengstens verboten!", true)
                .addField("6: Moderation", "Anweisungen der Teammitglieder sind ohne Widerspruch Folge zu leisten. Wir behalten uns vor, die Regeln jederzeit zu ändern oder anzupassen!", true)
                .setAuthor("GoBerly", "https://goberly.com", event.jda.selfUser.avatarUrl)
                .setFooter("GoBerly", event.jda.selfUser.avatarUrl)

            event.replyEmbeds(embed.build()).addComponents(regelwerkButtons()).setEphemeral(true).queue()
        } else if(event.componentId == "stelldichvor") {
            val text = TextInput.create("stelldichvortext", "Erzähle uns etwas über dich!", TextInputStyle.PARAGRAPH).setPlaceholder("Hallo, ich bin ...")
                .setMinLength(20)
                .setMaxLength(2048)
                .build()
            val contact = TextInput.create("stelldichvorcontact", "Wo kann man dich finden", TextInputStyle.SHORT).setPlaceholder("https://discord.gg/goberly")
                .setMinLength(5)
                .setMaxLength(64)
                .build()

            val modal = Modal.create("stelldichvormodal", "Stell dich vor!")
                .addComponents(ActionRow.of(text), ActionRow.of(contact))
            event.replyModal(modal.build()).queue()
        } else if(event.componentId == "selbstschutz") {
            val embed = EmbedBuilder()
                .setTitle("GoBerly Community - Selbstschutz")
                .setDescription("Hey ${event.member!!.effectiveName},\n" +
                        "\n" +
                        "wir wissen, wie nervig es sein kann, wenn irgendwelche Fremden unerwünschte Links in eurem Discord-Chat posten. Deshalb möchten wir euch zeigen, wie ihr Direktnachrichten von Servermitgliedern deaktivieren könnt, um solche Nachrichten zu vermeiden.\n" +
                        "\n" +
                        "Wenn ihr am Computer seid, klickt einfach mit der rechten Maustaste auf das Serversymbol in eurer Serverliste und wählt \"Privatsphäre-Einstellungen\". Dort könnt ihr dann die Option \"Direktnachrichten von Servermitgliedern zulassen\" ausschalten und bestätigen.\n" +
                        "\n" +
                        "Wenn ihr am Handy seid, wählt ihr einfach die \"drei Punkte [...]\" neben dem Servernamen aus und deaktiviert die Option \"Direktnachrichten zulassen\".\n" +
                        "\n" +
                        "Und denkt immer daran, gebt niemals persönliche Daten preis oder klickt auf verdächtige Links. Wenn euch jemand danach fragt, meldet ihn bitte direkt und ignoriert ihn.\n" +
                        "\n" +
                        "Lasst uns zusammen einen sicheren und angenehmen Discord-Chat haben! \uD83D\uDCAC")

            event.replyEmbeds(embed.build()).setEphemeral(true).queue()
        }
    }

    override fun onModalInteraction(event: ModalInteractionEvent) {
        if(event.modalId == "stelldichvormodal") {
            val text = event.getValue("stelldichvortext")!!.asString
            val contact = event.getValue("stelldichvorcontact")!!.asString

            val channel = event.jda.getTextChannelById(STELLDICHVOR)

            val embed = EmbedBuilder()
                .setTitle("GoBerly Community - Stell dich vor!")
                .setDescription(text)
                .addField("Kontakt", contact, true)
                .setAuthor(event.member!!.effectiveName, contact, event.user.avatarUrl)
                .setTimestamp(
                    Instant.now())
                .setFooter("GoBerly", event.jda.selfUser.avatarUrl)

            channel!!.sendMessageEmbeds(embed.build()).queue()

            event.reply("Deine Vorstellung wurde erfolgreich gesendet!").setEphemeral(true).queue()
        }
    }

    private fun regelwerkButtons() : ActionRow {
        return ActionRow.of(
            Button.link("https://dis.gd/tos", "Discord Nutzungsbedingungen"),
            Button.link("https://dis.gd/guidelines", "Discord Gemeinschaftsrichtlinien"),
        )
    }


    private fun Buttons(): ActionRow {
        return ActionRow.of(
            Button.primary("regelwerk", "Regelwerk"),
            // Button.success("guide", "Guide"),
            Button.danger("selbstschutz", "Selbstschutz"),
            Button.success("stelldichvor", "Stell dich vor!"),
            // Button.secondary("feedback", "Feedback")
        )
    }
}
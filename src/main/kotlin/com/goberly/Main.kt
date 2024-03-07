package com.goberly

import com.goberly.system.OnReady
import io.github.cdimascio.dotenv.dotenv
import net.dv8tion.jda.api.JDABuilder


class Main {
    companion object {
        val dotenv = dotenv()
    }
}

fun main() {
    JDABuilder.createDefault(Main.dotenv["TOKEN"])
        .addEventListeners(OnReady())
        .build()
}
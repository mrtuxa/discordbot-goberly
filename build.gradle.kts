plugins {
    kotlin("jvm") version "1.8.0"
    application
    id("io.ktor.plugin") version "2.2.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation("net.dv8tion:JDA:5.0.0-beta.5")
    implementation("org.ktorm:ktorm-core:3.6.0")
    implementation("mysql:mysql-connector-java:8.0.32")
    implementation("ch.qos.logback:logback-classic:1.2.9")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("com.goberly.MainKt")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.goberly.Main"
    }
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    id("se.thinkcode.cucumber-runner") version "0.0.8"
}
group = "me.rakeshprabhakaran"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("junit:junit:4.12")
    testImplementation ("io.cucumber:cucumber-java:6.8.1")
    testImplementation("io.rest-assured:rest-assured:4.3.2")
    testImplementation("com.jayway.jsonpath:json-path:2.4.0")
}
configurations {
    cucumber{
        featurePath = "src/test/resources"
        plugin= arrayOf("pretty")
        glue="weather"
        main = "io.cucumber.core.cli.Main"

    }
}

plugins {
    `maven-publish`
    id("java")
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "com.cosine.library"
version = "1.0"


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.cosine.library"
            artifactId = "CosineLibrary"
            version = "1.0"
            from(components["java"])
        }
    }
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("org.bukkit:craftbukkit:1.12.2-R0.1-SNAPSHOT")
    compileOnly("com.destroystokyo.paper:paper-api:1.12.2-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.20")
}

tasks {
    shadowJar {
        archiveFileName.set("CosineLibrary-${project.version}.jar")
        destinationDirectory.set(File("D:\\서버\\1.12.2 - 개발\\plugins"))
    }
}
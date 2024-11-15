plugins {
	id("java")
	id("application")
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.0"
	kotlin("plugin.spring") version "1.8.0"
}

application {
	mainClass.set("App.TestingClass")
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

version = "1.0"

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.google.api-client:google-api-client:2.0.0")
	implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
	implementation("com.google.apis:google-api-services-calendar:v3-rev20240705-2.0.0")
	implementation("dev.langchain4j:langchain4j-open-ai:0.34.0")
	implementation("dev.langchain4j:langchain4j:0.34.0")
	implementation("dev.langchain4j:langchain4j-spring-boot-starter:0.34.0")
	implementation("dev.langchain4j:langchain4j-open-ai-spring-boot-starter:0.34.0")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter:3.2.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql:42.6.0")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks.named<JavaExec>("run") {
	standardInput = System.`in`
}

springBoot {
	mainClass.set("App.TestingClass")
}
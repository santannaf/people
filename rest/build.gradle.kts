import org.gradle.kotlin.dsl.getByName
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    application
    java
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.graalvm.buildtools.native") apply true
}

group = "santannaf.people.rest"

extra["springAiVersion"] = "1.0.0"

dependencyManagement {
    imports {
        mavenBom("io.opentelemetry:opentelemetry-bom:1.48.0")
        mavenBom("io.opentelemetry.instrumentation:opentelemetry-instrumentation-bom:2.14.0")
        mavenBom("io.micrometer:micrometer-tracing-bom:1.4.4")
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation(project(":core"))

    // local analyse query
    implementation("santannaf:analyse-queries:0.0.1")
    implementation("org.springframework.ai:spring-ai-starter-model-openai")
    implementation("p6spy:p6spy:3.7.0")
}

application {
    mainClass.set("santannaf.people.rest.RestApplicationKt")
}

tasks.getByName<BootJar>("bootJar") {
    mainClass.set("santannaf.people.rest.RestApplicationKt")
}

tasks.register<Exec>("runCustomJar") {
    group = "application"
    dependsOn("bootJar")
    val app = "rest.jar"
    val appAddress = "./build/libs/$app"
//    commandLine(
//        "jar",
//        "-xfv",
//        appAddress
//    )
    commandLine(
        "java",
        "-agentlib:native-image-agent=config-merge-dir=./src/main/resources/META-INF/native-image/",
        "-jar",
        appAddress
    )

//    commandLine(
//        "java",
//        "-agentlib:native-image-agent=config-merge-dir=./src/main/resources/META-INF/native-image/",
//        "-cp",
//        "BOOT-INF/classes:BOOT-INF/lib/*",
//        "santannaf.customer.rest.ApplicationKt"
//    )
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("meuapp")
            configurationFileDirectories.from(file("src/main/resources/META-INF/native-image"))
            mainClass.set("santannaf.people.rest.RestApplicationKt")
            buildArgs.add("--color=always")
            buildArgs.add("--report-unsupported-elements-at-runtime")
            buildArgs.add("--allow-incomplete-classpath")
            buildArgs.add("--enable-preview")
            buildArgs.add("--verbose")
            buildArgs.add("-g")
            buildArgs.add("-march=native")
        }
    }
}

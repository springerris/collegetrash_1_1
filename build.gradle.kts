
plugins {
    id("java")
}

group = "com.github.springerris"
version = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    // MySQL JDBC Driver
    implementation("com.mysql:mysql-connector-j:8.0.33")

    // JetBrains IDE Annotations
    compileOnly("org.jetbrains:annotations:25.0.0")

    // Unit Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.1")
    testImplementation("org.hamcrest:hamcrest:3.0")
}

tasks.compileJava {
    sourceCompatibility = "21"
    targetCompatibility = "21"
}

// Makes the JAR file produced by Gradle executable
tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "${project.group}.Main"
        )
    }
}

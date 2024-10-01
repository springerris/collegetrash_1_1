
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

    // These seem unused, but were present in the original .iml
    // They'll be used if you ever add JUnit tests, though explicitly
    // requiring hamcrest is not necessary at all.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.1")
    testImplementation("org.hamcrest:hamcrest:3.0")
}

// Makes the JAR file produced by Gradle executable,
// e.g. java -jar lab2.jar
tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "${project.group}.Main"
        )
    }
}

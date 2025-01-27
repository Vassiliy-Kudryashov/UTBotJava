val kotlinLoggingVersion: String by rootProject
val junit4Version: String by rootProject
val junit5Version: String by rootProject
val sootVersion: String by rootProject
val mockitoVersion: String by rootProject

dependencies {
    implementation(project(":utbot-framework-api"))
    implementation("org.soot-oss:soot:${sootVersion}") {
        exclude(group="com.google.guava", module="guava")
    }
    implementation(project(":utbot-java-fuzzing"))
//    implementation(project(":utbot-instrumentation"))
    implementation(group = "com.github.haifengl", name = "smile-kotlin", version = "2.6.0")
    implementation(group = "com.github.haifengl", name = "smile-core", version = "2.6.0")
    implementation(group = "io.github.microutils", name = "kotlin-logging", version = kotlinLoggingVersion)
    implementation("com.github.javaparser:javaparser-core:3.22.1")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$junit5Version")
}

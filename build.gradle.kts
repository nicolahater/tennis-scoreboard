plugins {
    id("java")
}

//group = "dev.backend"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    // Web
    implementation("org.springframework:spring-webmvc:7.0.8")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")


    // DB
    implementation("org.postgresql:postgresql:42.7.13")
    implementation("org.hibernate.orm:hibernate-core:7.4.5.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    // HikariCPI as connection pool library
//    implementation("com.zaxxer:HikariCP:7.1.0")
//    implementation("org.hibernate.orm:hibernate-hikaricp:7.4.5.Final")

    // Test
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
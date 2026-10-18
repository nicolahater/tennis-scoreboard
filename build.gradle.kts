plugins {
    id("java")
}

//group = "dev.backend"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
//    implementation("org.springframework.data:spring-data-jpa:4.1.0")

    // Web
    implementation("org.springframework:spring-webmvc:7.0.8")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")

    // AOP
//    implementation("org.springframework:spring-aop:7.0.8")
//    compileOnly("org.aspectj:aspectjweaver:1.9.25.1")
////    implementation("org.aspectj:aspectjweaver:1.9.25.1")

    // JSON mapper
    implementation("tools.jackson.core:jackson-databind:3.2.3")

    // DB
    implementation("org.postgresql:postgresql:42.7.13")
    implementation("org.hibernate.orm:hibernate-core:7.4.5.Final")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
//    implementation("org.apache.logging.log4j:log4j-core:2.25.4") // to show sql parameters
    // HikariCPI as connection pool library
//    implementation("com.zaxxer:HikariCP:7.1.0")
//    implementation("org.hibernate.orm:hibernate-hikaricp:7.4.5.Final")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.20")
    implementation("ch.qos.logback:logback-classic:1.5.18")

    // Test
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
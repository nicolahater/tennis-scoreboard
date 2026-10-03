package dev.backend.config;

import dev.backend.entity.Player;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.tool.schema.Action;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:/application.properties")
public class HibernateConfig {
    @Value("${db.driver}")
    private String databaseDriver;

    @Value("${db.url}")
    private String databaseUrl;

    @Value("${db.username}")
    private String databaseUsername;

    @Value("${db.password}")
    private String databasePassword;

    @Value("${hibernate.schema_generation_action}")
    private String schemaGenerationAction;

    @Value("${hibernate.show_sql}")
    private String hibernateShowSQL;

    @Value("${hibernate.highlight_sql}")
    private String hibernateHighlightSQL;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        Map<String, String> properties = getPersistenceProperties();

        EntityManagerFactory emf = new PersistenceConfiguration("persistence-unit")
                .managedClass(Player.class)
                .properties(properties)
                .createEntityManagerFactory();

        return emf;
    }

    private Map<String, String> getPersistenceProperties(){
        Map<String, String> properties = new HashMap<>();
        // PostgreSQL
        properties.put(PersistenceConfiguration.JDBC_URL, databaseUrl);
        // Credentials
        properties.put(PersistenceConfiguration.JDBC_USER, databaseUsername);
        properties.put(
                PersistenceConfiguration.JDBC_PASSWORD,
                databasePassword
        );
        // Automatic schema action
        properties.put(
                PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION,
                Action.ACTION_UPDATE
        );
        // SQL statement logging
        properties.put(JdbcSettings.SHOW_SQL, hibernateShowSQL);
        properties.put(JdbcSettings.HIGHLIGHT_SQL, hibernateHighlightSQL);

        // HikariCP config
//        properties.put("hibernate.connection.provider_class", "hikaricp");
//        properties.put(HikariCPSettings.HIKARI_MAX_SIZE, "20");

        return properties;
    }
}

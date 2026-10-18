package dev.backend.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@Configuration
@EnableWebMvc
@ComponentScan("dev.backend")
@PropertySource("classpath:/application.properties")
//public class WebConfig implements WebMvcConfigurer {
public class WebConfig {
}

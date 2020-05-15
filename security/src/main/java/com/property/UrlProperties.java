package com.property;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.security")
@Configuration
@Data
public class UrlProperties {
    private final Map<String, String> mappingaccess = new HashMap<>();
}

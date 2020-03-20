package com.zoom.security;

import com.zoom.security.utils.DbInitRuner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ZoomSecurity {

    @ConditionalOnProperty(name = "zoom.security.db-init", havingValue = "true")
    @Bean
    public DbInitRuner dbInitRuner() {
        return new DbInitRuner();
    }

}

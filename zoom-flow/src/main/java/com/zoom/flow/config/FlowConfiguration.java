package com.zoom.flow.config;

import org.flowable.common.engine.impl.cfg.IdGenerator;
import org.flowable.common.engine.impl.persistence.StrongUuidGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowConfiguration {

    @Bean
    public IdGenerator strongUuidGenerator() {
        return new StrongUuidGenerator();
    }
}

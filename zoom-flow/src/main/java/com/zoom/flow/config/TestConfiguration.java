package com.zoom.flow.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.zoom.tools.controller"})
//@ComponentScan("org.flowable.rest.service.api")
public class TestConfiguration {
}

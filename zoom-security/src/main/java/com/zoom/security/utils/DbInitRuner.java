package com.zoom.security.utils;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

public class DbInitRuner implements CommandLineRunner {

    //当配置文件开启zoom.security.db-init: true时，则必须包含下面3个配置
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public void run(String... args) {
        Flyway flyway = Flyway.configure()
//                .dataSource("jdbc:mysql://localhost:3306/zoom", "zoom", "zoom")
                .dataSource(url, username, password)
                .locations("db/zoom/security")  //todo 暂时只考虑mysql，后期可以加上根据driver-class-name区分oracle或mysql
                .table("flyway_zoom_security_history")
                .baselineOnMigrate(true)    //设置版本从1开始，所以文件要从V1_1开始，不能从V1.0开始
                .load();
        flyway.migrate();
    }
}

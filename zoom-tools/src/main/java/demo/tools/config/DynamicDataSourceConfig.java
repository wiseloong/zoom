package demo.tools.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zoom.tools.jdbc.DynamicDataSource;
import com.zoom.tools.jdbc.DynamicDataSourceContextHolder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置事例
 */
@Configuration
public class DynamicDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSourceProperties slaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave.hikari")
    public DataSource slaveDataSource() {
        return slaveDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    // 这种配置使用 HikariDataSource 时，application.yml里不能使用url，需要使用jdbc-url
    /*@Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }*/

    /**
     * 多数据源配置，primary代表默认主数据源
     */
    @Bean
    public DynamicDataSource myDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("primary", dataSource());
        targetDataSources.put("slave", slaveDataSource());
        return DynamicDataSourceContextHolder.dynamicDataSource(targetDataSources, "primary");
    }

}

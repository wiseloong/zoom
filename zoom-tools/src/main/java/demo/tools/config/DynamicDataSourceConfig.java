package demo.tools.config;

import com.zoom.tools.jdbc.DynamicDataSource;
import com.zoom.tools.jdbc.DynamicDataSourceContextHolder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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

    /**
     * 主数据源，需要@Primary注解
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 从数据源，可配置多个
     */
    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DynamicDataSource myDataSource(@Qualifier("primaryDataSource") DataSource primaryDataSource,
                                          @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("primary", primaryDataSource);
        targetDataSources.put("slave", slaveDataSource);
        return DynamicDataSourceContextHolder.dynamicDataSource(targetDataSources, "primary");
    }

}

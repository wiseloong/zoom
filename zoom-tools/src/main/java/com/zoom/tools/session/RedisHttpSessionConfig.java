package com.zoom.tools.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.Session;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

//@Configuration
@EnableRedisHttpSession //可以设置过期时间和保存路径
@ConditionalOnClass({Session.class, RedisOperations.class})
public class RedisHttpSessionConfig {

    /**
     * Security使用时需要这个配置，参考下面事例，不然解析json时报错。
     * <p>
     * &#064;Bean
     * &#064;Profile("dev")
     * public RedisHttpSessionConfig config(){
     *   return new RedisHttpSessionConfig(objectMapper());
     * }
     * <p>
     * private ObjectMapper objectMapper() {
     *   ObjectMapper mapper = new ObjectMapper();
     *   ClassLoader loader = getClass().getClassLoader();   //这句跟null没啥区别？
     *   mapper.registerModules(SecurityJackson2Modules.getModules(loader));
     *   return mapper;
     * }
     */
    private final ObjectMapper mapper;

    @Autowired(required = false)
    public RedisHttpSessionConfig(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * 序列化为json格式，方便查看，性能不好，生产环境建议关闭
     */
    @Profile("dev")
    @Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    /**
     * cookie-id去掉Base64编码，生产环境建议关闭
     */
    @Profile("dev")
    @Bean
    public CookieSerializer defaultCookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setUseBase64Encoding(false);
        return cookieSerializer;
    }
}

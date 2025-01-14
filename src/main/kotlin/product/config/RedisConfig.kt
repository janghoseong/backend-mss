package product.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.net.InetSocketAddress
import java.net.Socket

@Configuration
@ConditionalOnProperty(name = ["use-embedded-redis"], havingValue = "true", matchIfMissing = true)
class RedisConfig(
    private val embeddedRedisConfig: EmbeddedRedisConfig,
    @Value("\${spring.data.redis.port:6379}") val redisPort: Int,
    @Value("\${use-embedded-redis:false}") val useRedis: Boolean
) {
    @PostConstruct
    fun startRedis() {
        if(!isRedisRunning("localhost", redisPort) && useRedis) {
            embeddedRedisConfig.startRedis()
        }
    }

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val standaloneConfiguration = RedisStandaloneConfiguration().apply {
            hostName = "localhost"
            port = embeddedRedisConfig.redisPort
            username = ""
            password = RedisPassword.none()
        }
        return LettuceConnectionFactory(standaloneConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            connectionFactory = redisConnectionFactory()
            keySerializer = StringRedisSerializer()
            valueSerializer = GenericJackson2JsonRedisSerializer(
                ObjectMapper().registerModule(
                    KotlinModule.Builder()
                        .withReflectionCacheSize(512)
                        .configure(KotlinFeature.NullToEmptyCollection, false)
                        .configure(KotlinFeature.NullToEmptyMap, false)
                        .configure(KotlinFeature.NullIsSameAsDefault, false)
                        .configure(KotlinFeature.SingletonSupport, false)
                        .configure(KotlinFeature.StrictNullChecks, false)
                        .build()
                )
            )
        }
    }

    // 애플리케이션 종료 시 Redis 서버 종료
    @PreDestroy
    fun stopRedis() {
        if(useRedis) {
            embeddedRedisConfig.stopRedis()
        }
    }

    fun isRedisRunning(host: String, port: Int): Boolean {
        return try {
            Socket().use {
                it.connect(InetSocketAddress(host, port), 2000)
                true
            }
        } catch (e: Exception) {
            false
        }
    }
}
package product.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import redis.embedded.RedisServer

@Configuration
class EmbeddedRedisConfig(
    @Value("\${spring.data.redis.port:6379}") val redisPort: Int,
) {

    private var redisServer: RedisServer? = null

    fun startRedis() {
//        val redisExecProvider = ExecutableProvider.newFileThenJarResourceProvider(OsArchitecture.UNIX_x86, "src/main/redis/redis-server")
        if(redisServer == null || (redisServer != null && !redisServer!!.isActive)) {
            redisServer = RedisServer.newRedisServer()
//            .executableProvider(redisExecProvider)
                .setting("bind 127.0.0.1") // 로컬 바인딩
                .setting("maxmemory 64mb") // 메모리 제한 설정
                .port(redisPort)
                .build()

            redisServer?.start()
        }
    }

    fun stopRedis() {
        redisServer?.stop()
    }
}
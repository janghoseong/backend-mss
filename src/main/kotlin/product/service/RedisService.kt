package product.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(

){
    @Autowired(required = false)
    private var redisTemplate: RedisTemplate<String, Any>? = null

    // redis 값 셋팅
    fun setValue(key: String, value: Any){
        redisTemplate?.opsForValue()?.set(key, value)
    }

    // redis 값 조회
    fun getValue(key: String): Any? {
        return redisTemplate?.opsForValue()?.get(key)
    }
}
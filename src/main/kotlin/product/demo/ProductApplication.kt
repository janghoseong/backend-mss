package product.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication(exclude = [RedisAutoConfiguration::class, RedisRepositoriesAutoConfiguration::class])
@ComponentScan(basePackages = ["product"])
class ProductApplication

fun main(args: Array<String>) {
    runApplication<ProductApplication>(*args)
}


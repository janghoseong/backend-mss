package product.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(basePackages = ["product.jpa.repo"], entityManagerFactoryRef = "h2EntityManager", transactionManagerRef = "h2TransactionManager")
class DataSourceConfig(
    private val env: Environment,
) {

    @Bean
    fun h2DataSource(): DataSource {
        val config = HikariConfig()
        config.jdbcUrl = env.getProperty("spring.datasource.url")
        config.username = env.getProperty("spring.datasource.username")
        config.password = env.getProperty("spring.datasource.password")
        config.driverClassName = env.getProperty("spring.datasource.driver-class-name")
        config.connectionTimeout = env.getProperty("spring.datasource.connectionTimeout")?.toLong() ?: 30000
        config.validationTimeout = env.getProperty("spring.datasource.validationTimeout")?.toLong() ?: 5000
        config.idleTimeout = env.getProperty("spring.datasource.idleTimeout")?.toLong() ?: 600000
        config.maximumPoolSize = env.getProperty("spring.datasource.maximumPoolSize")?.toInt() ?: 5
        config.minimumIdle = env.getProperty("spring.datasource.minimumIdle")?.toInt() ?: 5
        return HikariDataSource(config)
    }

    @Bean
    fun h2EntityManager(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = h2DataSource()
        em.setPackagesToScan("product.jpa.entity")
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
        em.setJpaProperties(jpaProperties())
        em.persistenceUnitName = "h2PU"
        return em
    }

    @Bean
    fun h2TransactionManager(): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = h2EntityManager().getObject()
        return transactionManager
    }

    private fun jpaProperties(): Properties {
        val properties = Properties()
        properties["hibernate.dialect"] = "org.hibernate.dialect.H2Dialect"
        properties["hibernate.format_sql"] = env.getProperty("spring.datasource.jpa.properties.hibernate.format_sql")
        return properties
    }
}
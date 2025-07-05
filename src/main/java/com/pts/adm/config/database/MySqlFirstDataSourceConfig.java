package com.pts.adm.config.database;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.pts.module.firstMysql.repository", // repository FirstMySQL
        entityManagerFactoryRef = "firstMysqlEntityManagerFactory",
        transactionManagerRef = "firstMysqlTransactionManager"
)
public class MySqlFirstDataSourceConfig {
    @Bean(name = "firstMysqlDataSource")
    @Qualifier("firstMysqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mysql.first")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "firstMysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("firstMysqlDataSource") DataSource mysqlDataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.hbm2ddl.auto", "none"); // hoặc none, validate tùy
        properties.put("hibernate.show_sql", true);
        return builder
                .dataSource(mysqlDataSource)
                .packages("com.pts.entity.firstMysql") // entity FirstMySQL
                .persistenceUnit("firstMysqlEntityManager")
                .properties(properties)
                .build();
    }

    @Bean(name = "firstMysqlTransactionManager")
    public PlatformTransactionManager mysqlTransactionManager(
            @Qualifier("firstMysqlEntityManagerFactory") EntityManagerFactory mysqlEntityManagerFactory) {
        return new JpaTransactionManager(mysqlEntityManagerFactory);
    }
}

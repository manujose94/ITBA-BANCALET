package ar.edu.itba.paw.persistence.hibernate;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@ComponentScan({ "ar.edu.itba.paw.persistence", })
@Configuration
public class TestConfig {
	@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(org.postgresql.Driver.class);

		String url = "jdbc:postgresql://localhost/bancaletTest";
		String pass = "mypass";
		String user = "myuser";

		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(pass);
		return ds;
	}

	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory em) {
		return new JpaTransactionManager(em);
	}

	@Bean(name = "bancalet")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("ar.edu.itba.paw.models");

		factoryBean.setDataSource(dataSource());

		final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);

		final Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");

		factoryBean.setJpaProperties(properties);
		return factoryBean;
	}
}
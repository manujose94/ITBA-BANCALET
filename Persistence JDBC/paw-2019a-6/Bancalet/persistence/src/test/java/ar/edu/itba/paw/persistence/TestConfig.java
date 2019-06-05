package ar.edu.itba.paw.persistence;

import java.sql.Driver;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@ComponentScan({ "ar.edu.itba.paw.persistence", })
@Configuration
public class TestConfig {
	@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass((Class<? extends Driver>) JDBCDriver.class);
		ds.setUrl("jdbc:hsqldb:mem:paw");
		ds.setUsername("ha");
		ds.setPassword("");
		return ds;
	}
}
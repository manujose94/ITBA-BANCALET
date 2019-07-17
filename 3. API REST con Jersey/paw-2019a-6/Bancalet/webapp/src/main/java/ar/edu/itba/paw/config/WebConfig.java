package ar.edu.itba.paw.config;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@ComponentScan({ "ar.edu.itba.paw.service", "ar.edu.itba.paw.rest", "ar.edu.itba.paw.persistence",
		"ar.edu.itba.paw.models", "ar.edu.itba.paw.dto" })
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:config/application.propierties")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Value("classpath:schema.sql")
	private Resource schemaSql;

	private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

	@Autowired
	Environment env;

	@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(org.postgresql.Driver.class);

		String url = env.getProperty("datasource.url");
		String pass = env.getProperty("datasource.password");
		String user = env.getProperty("datasource.username");

		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(pass);
		LOGGER.debug("Se inicia el dataSource en " + ds.getUrl());
		return ds;
	}

	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory em) {
		return new JpaTransactionManager(em);
	}

	private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
		dbp.addScript(schemaSql);
		LOGGER.debug("Se inicia el DatabasePopulator");
		return dbp;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
		final DataSourceInitializer dsi = new DataSourceInitializer();
		dsi.setDataSource(ds);
		dsi.setDatabasePopulator(databasePopulator());
		LOGGER.debug("Se inicia el DataSourceInitializer");
		return dsi;
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
		// Si ponen esto en prod, hay tabla!!!
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("format_sql", "true");
		factoryBean.setJpaProperties(properties);
		return factoryBean;
	}

	@Bean
	public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		messageSource.setCacheSeconds(5);
		LOGGER.debug("Se inicia el MessageSource");
		return messageSource;
	}

	@Bean
	public JavaMailSender getJavaMailSender() {

		JavaMailSenderImpl ms = new JavaMailSenderImpl();
		ms.setHost(env.getProperty("mailing.host"));
		ms.setPort(Integer.valueOf(env.getProperty("mailing.port")));
		ms.setUsername(env.getProperty("mailing.mail"));
		ms.setPassword(env.getProperty("mailing.password"));
		Properties mailProperties = ms.getJavaMailProperties();
		mailProperties.put("mail.transport.protocol", "smtp");
		mailProperties.put("mail.smtp.auth", "true");

		mailProperties.put("mail.smtp.starttls.enable", "true");
		mailProperties.put("mail.smtp.debug", "true");
		LOGGER.debug("Se inicia el JavaMailSender");
		return ms;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		LOGGER.debug("Se inicia el addResourceHandlers");
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(10485760); // 10MB
		multipartResolver.setMaxUploadSizePerFile(1048576 * 2); // 2MB
		return multipartResolver;
	}
}
package ar.edu.itba.paw.config;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@ComponentScan({ "ar.edu.itba.paw.service", "ar.edu.itba.paw.controllers", "ar.edu.itba.paw.persistence",
		"ar.edu.itba.paw.models" })
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
	public ViewResolver viewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		LOGGER.debug("Creado el view resolver.");
		return viewResolver;
	}

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
	public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		messageSource.setCacheSeconds(5);
		LOGGER.debug("Se inicia el MessageSource");
		return messageSource;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
		final DataSourceInitializer dsi = new DataSourceInitializer();
		dsi.setDataSource(ds);
		dsi.setDatabasePopulator(databasePopulator());
		LOGGER.debug("Se inicia el DataSourceInitializer");
		return dsi;
	}

	@Bean
	public JavaMailSender getJavaMailSender() {

		JavaMailSenderImpl ms = new JavaMailSenderImpl();
		ms.setHost("smtp.gmail.com");
		ms.setPort(587);

		ms.setUsername(env.getProperty("mailing.mail"));
		ms.setPassword(env.getProperty("mailing.password"));

		Properties mailProperties = ms.getJavaMailProperties();
		mailProperties.put("mail.transport.protocol", "smtp");
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.debug", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
		mailProperties.put("mail.smtp.debug", true);
		LOGGER.debug("Se inicia el JavaMailSender");
		return ms;
	}

	private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
		dbp.addScript(schemaSql);
		LOGGER.debug("Se inicia el DatabasePopulator");
		return dbp;
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		LOGGER.debug("Se inicia el addResourceHandlers");
	}
	// classe 29/04/2019 HIBERNATE
	// hibernate no puede tener atributos final porque pide constructores vacios
	// entity manager no es threadsafe en hibernate
	// CLASSE USER
	// en hubernate la clase user se pondria la etiqueta @Entity para indicar a
	// hibernate que tiene que mapearla a la base de datos
	// @Table(name="users") hay que indicar como se llamara la tabla a usar en la
	// base de datos
	// @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="users_id_seq")
	// @SequenceGenerator(name="users_id_seq",sequencename="users_id_seq",allocationSize=1)

	// @Column(length=100,nullable=false,unique=true)
	// private String username;

	// @Column(length=100,nullable=false)
	// private String password;
}
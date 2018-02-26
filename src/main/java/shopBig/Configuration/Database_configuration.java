package shopBig.Configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@ComponentScan("shopBig")
@EnableTransactionManagement
@Component
public class Database_configuration {

	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://localhost/~/shopBig");
		dataSource.setUsername("shopBig");
		dataSource.setPassword("shopBig");
		System.out.println("Datasource");
		return dataSource;

	}


	private Properties getHibernateProperties() 
	{
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.put("hibernate.hbm2ddl.auto", "update");
		System.out.println("Hibernate Properties");
		return properties;

	}

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.addProperties(getHibernateProperties());
		
/*		sessionBuilder.addAnnotatedClass(User.class);*/
		
		sessionBuilder.scanPackages("shopBig.*");
		System.out.println("Session");
		
		
		return sessionBuilder.buildSessionFactory();
		
	}
	
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
		System.out.println("Transaction");
		return transactionManager;
	}
	
	/*
	@Autowired
	@Bean(name = "userDAO")
	public UserDAO getUserDAO(SessionFactory sessionFactory)
	{
		return new UserDAOImpl(sessionFactory);
	}*/
}

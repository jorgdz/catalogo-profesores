package com.hardcode.catalogoprofesores.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement /*Permite que las acciones a la base de adtos sean transaccional*/
public class DataBaseConfiguration {
	
	@Bean
	public LocalSessionFactoryBean sesionFactory(){
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		sfb.setDataSource(dataSource());
		sfb.setPackagesToScan("com.hardcode.catalogoprofesores.model");
		sfb.setHibernateProperties(hibernateProperties());
		
		return sfb;
	}
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/sistema_profesores");
		dataSource.setUsername("sistema_profesores");
		dataSource.setPassword("p@ssw0rd");
		return dataSource;
	}
	
	public Properties hibernateProperties(){
		Properties properties = new Properties();
		properties.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
		properties.put("show_sql","true");
		
		return properties;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(){		
		HibernateTransactionManager htmanager = new HibernateTransactionManager();
		htmanager.setSessionFactory(sesionFactory().getObject());
		return htmanager;
	}
}

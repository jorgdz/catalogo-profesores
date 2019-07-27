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
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://ec2-54-243-193-59.compute-1.amazonaws.com:5432/dacfpr5mol7nk4");
		dataSource.setUsername("qcejkcuyprajuj");
		dataSource.setPassword("3396cb669d51a5db92fd4c16545d3149f59f7a6b78e1e084a5173ea7ae8d0d3a");
		/*dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/sistema_profesores");
		dataSource.setUsername("sistema_profesores");
		dataSource.setPassword("p@ssw0rd");*/
		
		return dataSource;
	}
	
	public Properties hibernateProperties(){
		Properties properties = new Properties();
		//properties.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
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

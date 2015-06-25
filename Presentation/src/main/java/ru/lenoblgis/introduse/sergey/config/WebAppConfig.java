package ru.lenoblgis.introduse.sergey.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries;
import ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLServerQueries;
import ru.lenoblgis.introduse.sergey.domen.mappers.EventRowMapper;
import ru.lenoblgis.introduse.sergey.domen.mappers.OrganizationRowMapper;
import ru.lenoblgis.introduse.sergey.domen.mappers.PassportRowMapper;
import ru.lenoblgis.introduse.sergey.domen.mappers.UserRowMapper;
import ru.lenoblgis.introduse.sergey.services.EventService;
import ru.lenoblgis.introduse.sergey.services.OwnerService;
import ru.lenoblgis.introduse.sergey.services.PassportService;
import ru.lenoblgis.introduse.sergey.services.UserDetailsServiceImpl;
import ru.lenoblgis.introduse.sergey.services.UserService;
import ru.lenoblgis.introduse.sergey.validation.OrganizationValidator;
import ru.lenoblgis.introduse.sergey.validation.PassportValidator;
import ru.lenoblgis.introduse.sergey.validation.RegistrationValidator;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

@Configuration
@EnableWebMvc
@ComponentScan({"ru.lenoblgis.introduse.sergey.controllers",
					"ru.lenoblgis.introduse.sergey.data.dao",
					"ru.lenoblgis.introduse.sergey.domen.mappers"})
public class WebAppConfig extends WebMvcConfigurerAdapter {
 
    /**
     * ��������� ������ ��� ������� � ����� views, ����� ��� ��������, ����� � �.�.
     */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/views/**").addResourceLocations("/views/");
    }
 
	/**
	 * ���� ��� �������������� View ������ ������� (������������ � mvc-dispatcher-servlet.xml)
	 * @return
	 */
    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
 
        return resolver;
    }
    
    
    /**
     * ��������� ���� DAO
     * @return - DAO
     */
    @Bean
    public DAO getDAO() {
        return new DAO();
    }
    
    /**
     * ��������� ���� ���������������� sql-������� ��� DAO
     * @return - ��� ��������������� sql-������� ��� DAO
     */
    @Bean
    public SQLQueries getSQLQueries(){
    	return new SQLServerQueries();
    }
    
    /**
     * ������ ��� ����������� ��������� �� �� � ����������� �������������� (������������ DAO)
     * @return - ������ ��� ��������
     */
    @Bean
    public PassportRowMapper getPassportRowMapper(){
    	return new PassportRowMapper();
    }
    
    /**
     * ������ ��� ����������� ������� �� �� � ����������� �������������� (������������ DAO)
     * @return - ������ ��� �������
     */
    @Bean
    public EventRowMapper getEventRowMapper(){
    	return new EventRowMapper();
    }
    
    /**
     * ������ ��� ����������� ����������� �� �� � ����������� �������������� (������������ DAO)
     * @return - ������ ��� �����������
     */
    @Bean
    public OrganizationRowMapper getOrganizationRowMapper(){
    	return new OrganizationRowMapper();
    }
    
    /**
     * ������ ��� ����������� ������������� �� �� � ����������� �������������� (������������ DAO)
     * @return - ������ ��� �������������
     */
    @Bean
    public UserRowMapper getUserRowMapper(){
    	return new UserRowMapper();
    }
    
    /**
     * ��������� ���� ������� ������ � �������������
     * @return - ������ ������ � �������������
     */
    @Bean
    public OwnerService getOwnerService() {
        return new OwnerService();
    }
    
    /**
     * ��������� ���� ������� ������ � ���������� �����
     * @return - ������ ������ � ���������� �����
     */
    @Bean
    public PassportService getPassportService(){
    	return new PassportService();
    }
    
    /**
     * ��������� ���� ������� ������ � �������� �������
     * @return - ������ ������ � �������� �������
     */
    @Bean
    public EventService getEventService(){
    	return new EventService();
    }
    
    /**
     * ��������� ���� ������� ������ � ��������������
     * @return - ������ ������ � ��������������
     */
    @Bean
    public UserService getUserService(){
    	return new UserService();
    }
    
    /**
     * ��������� ���� ���������� � ��
     * @return - ���������� � ��
     */
    @Bean
	public DataSource getDataSource() {
    	SQLServerDataSource ds = new SQLServerDataSource();
		ds.setPortNumber(1433);
		ds.setHostNameInCertificate("localhost");
		ds.setDatabaseName("passport_agricultural");
		ds.setUser("adminAgricultural");
		ds.setPassword("admin123");
		return ds;
    }
    
    /**
     * ��������� ���� ������� ������ � �������������� - ��� �������������� ����� spring security
     * @return - ������ ������ � �������������� - ��� �������������� ����� spring security
     */
    @Bean
    public UserDetailsService getUserDetailsService(){
        return new UserDetailsServiceImpl();
    }
    
    /**
     * ��������� ���� ���������� �������� ������ � ���������, ��� ��� �������� ��� ���������
     * @return - ��������� ��������
     */
    @Bean
    @Qualifier("passportValidator")
    public Validator getPassportValidator(){
    	return new PassportValidator();
    }

    /**
     * ��������� ���� ���������� �������� ������ �� �����������, ��� � ���������
     * @return - ��������� �����������
     */
    @Bean
    @Qualifier("organizationValidator")
    public Validator getOrganizationValidator(){
    	return new OrganizationValidator();
    }
    
    /**
     * ��������� ���� ���������� �������� ������ ��� ����������� (� ������������ � �����������)
     * @return - ��������� ���������� ��� �����������
     */
    @Bean
    @Qualifier("registrationValidator")
    public Validator getRegistrationValidator(){
    	return new RegistrationValidator();
    }
    
    /**
     * ���������� ���� ��� ���������� ��������� �� ������� �� ����� pecources/WEB-INF/messeges/erormessages.properties
     * @return - ������ ��� ������ � ����������� �� �������
     */
    @Bean
    public ResourceBundleMessageSource getResourceBundleMessageSource(){
    	ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
    	rbms.setBasename("messages/erormessages");
    	rbms.setDefaultEncoding("Cp1251");
    	return rbms;
    }
}

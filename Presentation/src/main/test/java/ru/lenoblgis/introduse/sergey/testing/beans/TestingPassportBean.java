package ru.lenoblgis.introduse.sergey.testing.beans;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.lenoblgis.introduse.sergey.config.WebAppConfig;
import ru.lenoblgis.introduse.sergey.services.PassportService;


public class TestingPassportBean {

	/**
	 * �������� ���������
	 */
	@Test
	@Ignore
	public void testPassportServiceBean(){
		ApplicationContext context = new AnnotationConfigApplicationContext(WebAppConfig.class);
        PassportService passportService = (PassportService) context.getBean("passportService");
        Map<String, String> passportInfo = new HashMap<String, String>();
		passportInfo.put("id_organization", "8");
		passportInfo.put("region", "������������ �-�");
		passportInfo.put("cadastr_number", "25");
		passportInfo.put("area", "33");
		passportInfo.put("type_field", "�������������������� ������������");
		passportInfo.put("comment", "Hello world!");
        passportService.createPassport(passportInfo);
	}
	
	/**
	 * �������� ���������
	 */
	@Test
	@Ignore
	public void testReviewPassport() {
		ApplicationContext context = new AnnotationConfigApplicationContext(WebAppConfig.class);
        PassportService passportService = (PassportService) context.getBean("passportService");
            
	}
	
	/**
	 * �������������� ��������
	 */
	@Test
	@Ignore
	public void testEditPassport(){
		ApplicationContext context = new AnnotationConfigApplicationContext(WebAppConfig.class);
        PassportService passportService = (PassportService) context.getBean("passportService");
        Map<String, String> passportInfo = new HashMap<String, String>();
		passportInfo.put("id", "37");
		passportInfo.put("id_organization", "24");
		passportInfo.put("region", "������������ �-�");
		passportInfo.put("cadastr_number", "23");
		passportInfo.put("area", "40");
		passportInfo.put("type_field", "�������������������� ������������");
		passportInfo.put("comment", "���� ���������������!");
        passportService.editPassport(passportInfo);
	}
	
	/**
	 * ������������ ��������� ���� ���������
	 */
	@Test
	@Ignore
	public void testReviewAllPassports(){
		ApplicationContext context = new AnnotationConfigApplicationContext(WebAppConfig.class);
        PassportService passportService = (PassportService) context.getBean("passportService");
        List<Map<String, String>> passports = passportService.reviewAllPassport();
        Assert.assertEquals(passports.size(), 24);
	}
	
	/**
	 * ������������ ������ ���������
	 */
	@Test
	@Ignore
	public void testFindPassports(){
		ApplicationContext context = new AnnotationConfigApplicationContext(WebAppConfig.class);
        PassportService passportService = (PassportService) context.getBean("passportService");
        Map<String, String> passportInfo = new HashMap<String, String>();
		passportInfo.put("id_organization", "8");
        List<Map<String, String>> passports = passportService.findPassports(passportInfo);
        Assert.assertEquals(passports.size(), 23);
	}
	
	/**
	 * ������������ �������� ���������
	 */
	@Test
	@Ignore
	public void testDeletePassport(){
		ApplicationContext context = new AnnotationConfigApplicationContext(WebAppConfig.class);
        PassportService passportService = (PassportService) context.getBean("passportService");
        Map<String,String> resultInfo = passportService.deletePassport(38);
        Assert.assertEquals("false", resultInfo.get("success"));
	}
	
	
}

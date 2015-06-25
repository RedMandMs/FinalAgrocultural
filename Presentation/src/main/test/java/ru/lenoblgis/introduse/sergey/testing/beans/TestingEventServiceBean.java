package ru.lenoblgis.introduse.sergey.testing.beans;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.lenoblgis.introduse.sergey.config.WebAppConfig;
import ru.lenoblgis.introduse.sergey.services.EventService;

/**
 * ������������ ���� ������� ������� (EventService)
 * @author Sergey
 *
 */
public class TestingEventServiceBean {
	
	/**
	 * ������������ ��������� ���� ������� � �������
	 */
	@Test
	@Ignore
	public void testReviewAllEvents(){
		ApplicationContext context = new AnnotationConfigApplicationContext(WebAppConfig.class);
        EventService eventService = (EventService) context.getBean("eventService");
        List<Map<String, String>> events = eventService.getAllEvents();
        Assert.assertEquals(26, events.size());
	}
	
	/**
	 * ������������ ��������� ���� ������� ������ ���������
	 */
	@Test
	@Ignore
	public void testReviewAllOwnerEvents(){
		ApplicationContext context = new AnnotationConfigApplicationContext(WebAppConfig.class);
        EventService eventService = (EventService) context.getBean("eventService");
        List<Map<String, String>> events = eventService.getAllOwnerEvents(24);
        Assert.assertEquals(12, events.size());
	}
	
}

package ru.lenoblgis.introduse.sergey.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.lenoblgis.introduse.sergey.datatransferobject.event.EventInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.services.EventService;
import ru.lenoblgis.introduse.sergey.services.OwnerService;
import ru.lenoblgis.introduse.sergey.validation.OrganizationValidator;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	/**
	 * ������ ������ � �������������
	 */
	@Autowired
	private OwnerService ownerService;

	/**
	 * ����� ������ � ���������
	 */
	@Autowired
	private EventService eventService;
	
	/**
	 * ���������� ���������������� ��������������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "managing", method = RequestMethod.GET)
    public String showAdminPage(ModelMap model) {	
		return "admin/managing";
	}
	
	/**
	 * ���������� ����� ��� ������ ��������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/findingCompany", method = RequestMethod.GET)
    public String showFindingOranizationForm(ModelMap model) {	
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		OrganizationInfo serchingOrganization = (OrganizationInfo) session.getAttribute("serchingOrganization");
		
		if(serchingOrganization == null){
			serchingOrganization = new OrganizationInfo();
		}
		
		model.addAttribute("serchingOrganization", serchingOrganization);
		
		return "admin/findOrganization";
	}
	
	/**
	 * ����� �������� �� �������� ����������
	 * @param serchingOrganization - ������ � ��������� ������ �����������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/findingCompany", method = RequestMethod.POST)
    public String findOranization(OrganizationInfo serchingOrganization) {
		
		OrganizationValidator.encodeOrganization(serchingOrganization);
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		List<OrganizationInfo> findingOrganizations = ownerService.findOrganizations(serchingOrganization);
		
		session.setAttribute("serchingOrganization", serchingOrganization);
		session.setAttribute("findingOrganizations", findingOrganizations);		
		
		return "redirect:/admin/findingCompany";
	}
	
	/**
	 * �������� ����� � ������� ������� �������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/allEvents", method = RequestMethod.GET)
    public String showAllEventsForm(ModelMap model) {	
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		EventInfo serchingEvent = (EventInfo) session.getAttribute("serchingEvent");
		if(serchingEvent == null){
			serchingEvent = new EventInfo();
		}
		
		model.addAttribute("serchingEvent", serchingEvent);
				
		return "admin/allevents";
	}
	
	/**
	 * ����� ������ �������
	 * @param serchingEvent - ������, ���������� ��������� ������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/allEvents", method = RequestMethod.POST)
    public String findEvents(EventInfo serchingEvent, ModelMap model) {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		List<EventInfo> findingEvents = eventService.findEvents(serchingEvent);
		
		session.setAttribute("serchingEvent", serchingEvent);
		session.setAttribute("findingEvents", findingEvents);		
		
		return "redirect:/admin/allEvents";
	}
	
	/**
	 * ������� �������
	 * @param idEvent - id �������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/deleteEvent/{idEvent}", method = RequestMethod.GET)
    public String deleteEvent(@PathVariable Integer idEvent, ModelMap model) {
		
		eventService.deleteEvent(idEvent);
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		@SuppressWarnings("unchecked")
		List<EventInfo> findingEvents = (List<EventInfo>) session.getAttribute("findingEvents");
		
		for (int i = 0; i < findingEvents.size(); i++) {
			if(findingEvents.get(i).getId().equals(idEvent)){
				findingEvents.remove(findingEvents.get(i));
			}
		}
		
		return "redirect:/admin/allEvents";
	}
}

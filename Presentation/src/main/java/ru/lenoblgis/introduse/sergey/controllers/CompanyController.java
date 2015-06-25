package ru.lenoblgis.introduse.sergey.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.lenoblgis.introduse.sergey.datatransferobject.event.EventInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.passportinfo.PassportInfo;
import ru.lenoblgis.introduse.sergey.services.EventService;
import ru.lenoblgis.introduse.sergey.services.OwnerService;
import ru.lenoblgis.introduse.sergey.services.PassportService;
import ru.lenoblgis.introduse.sergey.services.UserService;

@Controller
@RequestMapping(value="/organization")
public class CompanyController {
	
	/**
	 * �����
	 */
	 private static final Logger log = Logger.getLogger(OwnerService.class);

	/**
	 * ������ ��� ������ � �������������
	 */
	@Autowired
	private OwnerService ownerService;
	
	/**
	 * ������ ��� ������ � ��������������
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * ������ ��� ������ � ����������
	 */
	@Autowired
	private PassportService passportService;
	
	/**
	 * ������ ��� ������ � ���������
	 */
	@Autowired
	private EventService eventService;
	
	/**
	 * ��������� ��� �������� �������� ������ ��� ��������� ���������� � ��������
	 */
	@Autowired
    @Qualifier("organizationValidator")
    private Validator validator;
	
	/**
	 * ����������� ���������� ��� ��������� ������
	 * @param binder - webDaStaBinder
	 */
	@InitBinder
	private void initBinder(WebDataBinder binder) {
	    binder.setValidator(validator);
	}
	
	/**
	 * ����� ������������ ������ � ���������� ��������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/company/{organizationId}", method = RequestMethod.GET)
    public String showOrganization(@PathVariable Integer organizationId, ModelMap model) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		if((boolean) session.getAttribute("isAdmin")){
			
			setMyCompany(session, organizationId);
			
			return "organization/company";
		}else{
			return "403";
		}
	}
	
	/**
	 * ����� ������������ ������ � ����� �������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/company/mycompany", method = RequestMethod.GET)
    public String showMyCompany(ModelMap model) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		model.addAttribute("reviewingCompany", session.getAttribute("myCompany"));
		
		model.addAttribute("isMyCompany", true);
		
		return "organization/company";
	}
	
	/**
	 * ����� ������������ ����� ��� ��������� ������ � ����� ��������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/company/change_organization_info", method = RequestMethod.GET)
    public String showChangeInfoOrganization(ModelMap model) {
		
		HttpSession session = getSession();
		
		OrganizationInfo myCompany;
		
		String message = "";
		if(session.getAttribute("incorrectCompany") != null){
			myCompany = (OrganizationInfo) session.getAttribute("incorrectCompany");
			session.removeAttribute("incorrectCompany");
		}else{
			session.removeAttribute("editOrganizationErors");
			message = "�������� ����������� ������";
			myCompany = (OrganizationInfo) session.getAttribute("myCompany");
		}
		
		model.addAttribute("message", message);
		
		model.addAttribute("myCompany", myCompany);
		
		OrganizationInfo changedCompany = new OrganizationInfo();
		
		model.addAttribute("changedCompany", changedCompany);
		
		return "organization/change_info_organization";
	}
	
	/**
	 * ����� �������������� ��������� � ���������� �� �����������
	 * @param organizationInfo - ����� ���������� �� �����������
	 * @return - ����������� �������� ����� ��������� (���������������)
	 * @throws UnsupportedEncodingException - ������ � �� ���������� ������������
	 */
	@RequestMapping(value = "/company/change_organization_info", method = RequestMethod.POST)
    public String �hangeInfoOrganization(@Valid OrganizationInfo organizationInfo, BindingResult result) throws UnsupportedEncodingException {
		
		log.log(Level.INFO, DateTime.now() + "	User trying chenge information about organization(" + organizationInfo + ")");
		
		HttpSession session = getSession();
		
		//�������� ��������� �� �������, ���� ��� ����
		List<ObjectError> erorList = result.getAllErrors();
		List<String> erorMessageList = new ArrayList<>();
		try{
			if( ! erorList.isEmpty()){
				log.log(Level.INFO, DateTime.now() + "	User can not change the information about organization("+organizationInfo+"), becouse to errors in filling fields");
				erorMessageList = getListMessageForEror(erorList);
				session.setAttribute("incorrectCompany", organizationInfo);
				session.setAttribute("editOrganizationErors", erorMessageList);
				return "redirect:/organization/company/change_organization_info";
			}
	
			OrganizationInfo myCompany = (OrganizationInfo) session.getAttribute("myCompany");
			organizationInfo.setId(myCompany.getId());
			
			//�������� �������� �� �������������� �������
			ownerService.editOwner(organizationInfo);
			setMyCompany(session, myCompany.getId());
			return "redirect:/organization/company/mycompany";
			
		}catch (Exception e) {
			session.setAttribute("incorrectCompany", organizationInfo);
			erorMessageList.add("��������� ������!");
			session.setAttribute("editOrganizationErors", erorMessageList);
			return "redirect:/organization/company/change_organization_info";
		}
	}

	/**
	 * �������� ������ ������� ��������� � �����������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/company/events", method = RequestMethod.GET)
    public String showLogEvents( ModelMap model) {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		OrganizationInfo myCompany = (OrganizationInfo) session.getAttribute("myCompany");
		List<EventInfo> events = eventService.getAllOwnerEvents(myCompany.getId());
		
		session.setAttribute("events", events);
		
		
		return "organization/events_company";
	}
	
	/**
	 * �������� ������ ��������� �� �������
	 * @param listEror - ������ ������
	 * @return - ������ ��������� �� �������
	 */
	public static List<String> getListMessageForEror(List<ObjectError> listEror){
		List<String> messages = new ArrayList<>();
		for(ObjectError error : listEror){
			
			//���� ���������� ������� �������� ����
			if(error.getCode().equals("typeMismatch")){
				String code = error.getCodes()[1];
				String[] partsEror = code.split("\\.");
				String field = partsEror[partsEror.length-1];
				if(field.equals("inn")){
					messages.add("����������� ������ ��� ��������!");
				}
			}else{
				messages.add(error.getDefaultMessage());
			}
		}
		
		return messages;
	}
	
	/**
	 * ���������� ������������ �������������� � ��������
	 * @param session - ������
	 * @param idOrganization - id �����������, � ������� ��������� ������������
	 */
	private void setMyCompany(HttpSession session, Integer idOrganization) {
		OrganizationInfo myCompany = ownerService.reviewOwner(idOrganization);
		session.setAttribute("myCompany", myCompany);

		//������� �� �������� �� ���� ��� �������� ����������� (������� ������ id ���������)
		PassportInfo ownPassports = new PassportInfo();
		ownPassports.setIdOwner(myCompany.getId());
		List<PassportInfo> myPassportsList = passportService.findPassports(ownPassports);
		session.setAttribute("myPassportsList", myPassportsList);
		session.setAttribute("lastList", "mylistpassports");
		List<Integer> myIdPassports = new ArrayList<Integer>();
		for(PassportInfo passportInfo : myPassportsList){
			myIdPassports.add(passportInfo.getId());
		}
		session.setAttribute("myIdPasports", myIdPassports);

	}
	
	/**
	 * �������� ������
	 * @return - ������
	 */
	private HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}
}

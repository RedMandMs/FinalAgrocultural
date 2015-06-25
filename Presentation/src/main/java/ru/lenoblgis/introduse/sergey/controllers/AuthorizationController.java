package ru.lenoblgis.introduse.sergey.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.RegistrationInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.passportinfo.PassportInfo;
import ru.lenoblgis.introduse.sergey.domen.actionevent.TypeEvent;
import ru.lenoblgis.introduse.sergey.domen.passport.RegionField;
import ru.lenoblgis.introduse.sergey.domen.passport.TypeField;
import ru.lenoblgis.introduse.sergey.domen.user.UserRole;
import ru.lenoblgis.introduse.sergey.services.OwnerService;
import ru.lenoblgis.introduse.sergey.services.PassportService;
import ru.lenoblgis.introduse.sergey.services.UserService;

@Controller
@RequestMapping("/login")
public class AuthorizationController {
	
	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PassportService passportService;
	
	/**
	 * �������� ����� �����������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(method = RequestMethod.GET)
    public String showAuthorizationForm(ModelMap model) {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		if(session.getAttribute("invalidateAuthorization")!=null){
			session.removeAttribute("invalidateAuthorization");
			String message = "�� ����� ����������� ������ �����!";
			model.addAttribute("message", message);
		}else{
			String message = "������� ����� � ������ ��� ���������� �����������:";
			model.addAttribute("message", message);
		}
		
		RegistrationInfo userOrganization = new RegistrationInfo();
		model.addAttribute("userOrganization", userOrganization);
		
		return "login";
	}
	
	/**
	 * ����� ����������� ����� �����������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/after_autorithation", method = RequestMethod.GET)
    public String afterAutorithation(ModelMap model) {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		if(session.getAttribute("myCompany") == null){
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			RegionField[] regions = RegionField.values();
			session.setAttribute("regions", regions);
			
			TypeField[] types = TypeField.values();
			session.setAttribute("types", types);
			
			TypeEvent[] typesEvent = TypeEvent.values();
			session.setAttribute("typesEvent", typesEvent);
			
			//��� �����?
			if(isAdmin(session, user)){
				setMyCompany(session, userService.getUserByLogin(user.getUsername()).getOrganizationId());
				return "redirect:/admin/managing";
			}else{
				setMyCompany(session, userService.getUserByLogin(user.getUsername()).getOrganizationId());
				return "redirect:/organization/company/mycompany";
			}
		}else{
			return "redirect:/organization/company/mycompany";
		}
	}
	
	/**
	 * ����� ������������ ���� � ������� ����� �����
	 * @param session - ������
	 * @param user - ������������
	 * @return - true - �����, false - ������� ������������
	 */
	private boolean isAdmin(HttpSession session, User user){
		Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
		if(isAdmin == null){
			Iterator<GrantedAuthority> authoritieIterator = user.getAuthorities().iterator();
			String role = authoritieIterator.next().getAuthority();
			if(role.equals(UserRole.ADMIN.getName())){
				OrganizationInfo adminCompany = ownerService.reviewOwner(userService.getUserByLogin(user.getUsername()).getOrganizationId());
				session.setAttribute("adminCompany", adminCompany);
				session.setAttribute("isAdmin", true);
				return true;
			}else{
				session.setAttribute("isAdmin", false);
				return false;
			}
		}else{
			return isAdmin;
		}
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
}

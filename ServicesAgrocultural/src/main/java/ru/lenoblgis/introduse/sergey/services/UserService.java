package ru.lenoblgis.introduse.sergey.services;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.RegistrationInfo;
import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;
import ru.lenoblgis.introduse.sergey.domen.user.User;
import ru.lenoblgis.introduse.sergey.domen.user.UserRole;

@Service
public class UserService implements Serializable{

	/**
	 * ���������� ��-���������
	 */
	public UserService() {
	}

	/**
	 * DAO ��� ������ � ����� ������
	 */
	@Autowired
	private DAO dao;
	
	/**
	 * ������ ������ � �������������
	 */
	@Autowired
	private OwnerService ownerService;
	
	/**
	 * ���������������� ������������ � ��������
	 * @param registrationInfo - ������ �������������� ������������ � �����������, ��������� ��� �����������
	 * @return - ���������� � ��������� �����������
	 */
	public OrganizationInfo registration(RegistrationInfo registrationInfo){
		
		OrganizationInfo organizationInfo = new OrganizationInfo();
		
		User user = new User(registrationInfo.getLogin(), registrationInfo.getPassword(), UserRole.USER);
			
		Owner organization = new Organization(registrationInfo.getOrganizationName(), 
					registrationInfo.getInn(), registrationInfo.getAddress());
			
		//��������� ������
		String coddingPassword = new Md5PasswordEncoder().encodePassword(user.getPassword(), "");
		user.setPassword(coddingPassword);
	
		user = dao.registration(user, organization);
		
		organization = dao.reviewOwner(user.getOrganizationId());
			
		organizationInfo = OwnerService.convertDomainToDTO(organization);
				
		return organizationInfo;
	}
	
	/**
	 * �������� ������������ �� ������
	 * @param login - �����
	 * @return - ������������
	 */
	public User getUserByLogin(String login) {
		return dao.findUserByLogin(login);
	}
	
	/**
	 * �������� ����������� �� ������
	 * @param login - �����
	 * @return - ��������� �����������
	 */
	public OrganizationInfo getMyOrganizationByLogin(String login){
		User user = getUserByLogin(login);
		
		OrganizationInfo myOwnerInfo = ownerService.reviewOwner(user.getOrganizationId());
		
		return myOwnerInfo;
	}

	/**
	 * UID ��� ������������
	 */
	private static final long serialVersionUID = -4563959505303068679L;
}

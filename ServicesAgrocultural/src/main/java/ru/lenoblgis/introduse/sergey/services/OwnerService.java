package ru.lenoblgis.introduse.sergey.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;

@Component("organizationService")
public class OwnerService implements Serializable{
	 
	/**
	 * DAO ��� ������ � ����� ������
	 */
	@Autowired
	private DAO dao;
	
	/**
	 * ������������� ���������
	 * @param organizationInfo - ���������� � �������������� ���������("id" - id ���������, "name" - ��� �����������, inn - ���, address_org - ����� �����������)
 	 * @return - �������� �� ��� �������������� ��������
	 */
	public boolean editOwner(OrganizationInfo organizationInfo){
		
		Owner editingOwner = convertDTOToDomain(organizationInfo);
		
		dao.editOwner(editingOwner);
		
		return true;
	}
	
	/**
	 * ����������� ���������
	 * @param ownerId - id ���������
	 * @return - ���������� � ��������������� ��������� ("isExist" - ���������� �� ����� ��������, "id" - id ���������, "name" - ��� �����������, inn - ���, address_org - ����� �����������)
	 */
	public OrganizationInfo reviewOwner(int ownerId){
		
		try{
			Owner reviewOwner = dao.reviewOwner(ownerId);
			OrganizationInfo organizationInfo = convertDomainToDTO(reviewOwner);
			return organizationInfo;
		}
		catch (IndexOutOfBoundsException ex) {
			log.log(Level.ERROR, DateTime.now() + "		Organization(id="+ownerId+") was not been reviwed, because it wasn't found. Exeption: " + ex);
			throw ex;
		}
	}
	
	/**
	 * ������� ���������
	 * @param ownerId - id ���������
	 * @return - true - ������� �����/ false - �� �����
	 */
	public boolean deleteOwner(int ownerId){
		try{
			dao.deleteOwner(ownerId);
			return true;
		}
		catch (IndexOutOfBoundsException ex) {
			log.log(Level.ERROR, DateTime.now() + "		Organization(id="+ownerId+") was not been deleted, because organization with id like this wasn't found. Exeption: " + ex);
			throw ex;
		}
	}
	
	/**
	 * ����� ����������� �� �������� ����������
	 * @param serchingOrganization - ������, ���������� � ���� �������� ��� ������ ���������
	 * @return - ������ ��������� �����������
	 */
	public List<OrganizationInfo> findOrganizations(OrganizationInfo serchingOrganization){
		List<OrganizationInfo> findInfo = new ArrayList<OrganizationInfo>();
		
		Organization findingOrganization = convertDTOToDomain(serchingOrganization);
		
		List<Organization> findOrganizations = dao.findOwners(findingOrganization);
		
		for(Organization organization : findOrganizations){
			findInfo.add(convertDomainToDTO(organization));
		}
		
		return findInfo;
	}
	
	/**
	 * ������������� ����������� �� Data Transfer Object � �������� �����
	 * @param organization - ����������� � ����� DTO
	 * @return - ����������� � �������� �����
	 */
	public static Organization convertDTOToDomain(OrganizationInfo organization){
		return new Organization(organization.getId(), organization.getName(), organization.getInn(), organization.getAddress());
	}
	
	/**
	 * ������������� ����������� �� Data Transfer Object � �������� �����
	 * @param organization - ����������� � ����� DTO
	 * @return - ����������� � �������� �����
	 */
	public static OrganizationInfo convertDomainToDTO(Owner organization){
		return new OrganizationInfo(organization.getId(), organization.getName(), organization.getInn(), organization.getAddress());
	}
	
	/**
	 * UID ��� ������������
	 */
	private static final long serialVersionUID = 3916222068090518352L;
	
	/**
	 * �����
	 */
	 private static final Logger log = Logger.getLogger(OwnerService.class);
}

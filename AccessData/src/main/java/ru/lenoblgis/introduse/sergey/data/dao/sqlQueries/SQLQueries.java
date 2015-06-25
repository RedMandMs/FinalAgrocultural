package ru.lenoblgis.introduse.sergey.data.dao.sqlQueries;

import java.io.Serializable;

import ru.lenoblgis.introduse.sergey.domen.actionevent.PassportEvent;
import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;
import ru.lenoblgis.introduse.sergey.domen.passport.Passport;
import ru.lenoblgis.introduse.sergey.domen.user.User;

public interface SQLQueries extends Serializable {

	/**
	 * �������� ���������
	 * @param owner 
	 */
	public String createOwner(Owner owner);
	
	/**
	 * �������� ���������
	 */
	public String deleteOwner();
	
	/**
	 * ������������� ���������
	 */
	public String editOwner();
	
	/**
	 * ����������� ���������
	 */
	public String reviewOwner();
	
	/**
	 * �������� ��������
	 */
	public String createPassport(Passport passport);
	
	/**
	 * �������� ��������
	 */
	public String deletePassport();
	
	/**
	 * �������������� ��������
	 */
	public String editPassport();
	
	/**
	 * �������� ��������
	*/
	public String reviewPassport();
	
	
	/**
	 * ����� ��������
	 */
	public String findPassports(Passport serchingPassport);
	
	/**
	 * ������������ ������ ��� ������� �������
	 * @return - ������ ������� ������� ���������
	 */
	public String createPassportEvent();
	
	/**
	 * ������������ ������ ��� �������� �������
	 * @return - ������ �������� ������� ���������
	 */
	public String deletePassportEvent();
	
	
	/**
	 * ������������ ������ ��� �������� ������ ������������ ������� (�����������)
	 * @return - ������
	 */
	public String createUser(User user);
	
	/**
	 * ������������ ������ ��� ��������� ������������ �� ������
	 * @return - ������
	 */
	public String reviewUserByLogin();

	/**
	 * ������� ������ ��� ������ ���� ����������� ��������������� ���������� ��������
	 * @param findingOrganization - ������, ���������� � ���� ������� ��� ������ �����������
	 * @return - ������
	 */
	public String findOwners(Organization findingOrganization);

	/**
	 * ������� ������ ��� ������ ���� ������� ��������������� ���������� ��������
	 * @param findingEvent - ������, ���������� � ���� ������� ��� ������ �������
	 * @return - ������
	 */
	public String findEvents(PassportEvent findingEvent);
}

package ru.lenoblgis.introduse.sergey.domen.user;

import java.io.Serializable;

/**
 * ������������ ����� �������������
 * @author VILGODSKIY
 *
 */
public enum UserRole implements Serializable{

	/**
	 * ������������������ ������������
	 */
	USER("USER"),
	/**
	 * �������������
	 */
	ADMIN("ADMIN");
	

	/**
	 * ����������� � ������� ����
	 * @param name - ��� ����
	 */
	private UserRole(String name) {
		this.name = name;
	}

	/**
	 * ��� ����
	 */
	private String name;

	/**
	 * �������� ��� ����
	 * @return - ��� ����
	 */
	public String getName() {
		return name;
	}
	
}

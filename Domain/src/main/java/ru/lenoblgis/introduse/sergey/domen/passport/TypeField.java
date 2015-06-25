package ru.lenoblgis.introduse.sergey.domen.passport;

import java.io.Serializable;

/**
 * ������������ ����� �����
 * @author VILGODSKIY
 *
 */
public enum TypeField implements Serializable{
	
	/**
	 * �� ������� (��� ������)
	 */
	NULL(""),
	/**
	 * �� ������� (��� ��������)
	 */
	UNKNOWN("����������"),
	/**
	 * ���������� ���������
	 */
	FARM("���������� ���������"),
	/**
	 * �������-������������� ������������
	 */
	AGROCULTURAL("�������-������������� ������������"),
	/**
	 * ������������ ���������
	 */
	COLLECTIVE_FARM("������������ ���������"),
	/**
	 * ��� ����������� ������������� ���������
	 */
	COLLECTIVE_AGROCULTURAL("��� ����������� ������������� ���������");
	
	/**
	 * ������� �����������
	 * @param type - ��� ����
	 * @param view - ��� ��� �����������
	 */
	private TypeField(String type) {
		this.type = type;
	}

	/**
	 * ��������� ��������� ������������ ���� ���� �� ��������
	 * @param title - �������� ���� ����
	 * @return - ��������� ������� ������������
	 */
	public static TypeField getTypeOf(String title){
		if(title == null || title.equals("")){
			return NULL;
		}
		TypeField[] values = TypeField.values();
		for (int i = 0; i < values.length; i++) {
			if(title.equals(values[i].type)) return values[i];
		}
		return null;
	}

	/**
	 * ��� ���� (�������)
	 */
	private String type;
	
	/**
	 * �������� ��� ����
	 * @return - ��� ���� (�������)
	 */
	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return type;
	}
	
	
}

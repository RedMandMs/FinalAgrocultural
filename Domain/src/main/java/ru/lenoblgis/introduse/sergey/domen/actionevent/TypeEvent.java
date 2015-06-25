package ru.lenoblgis.introduse.sergey.domen.actionevent;

import java.io.Serializable;

/**
 * ������������ ����� ������� ���������
 * @author VILGODSKIY
 *
 */
public enum TypeEvent implements Serializable{
	
	UNKNOWN(null, "�� ��������", "���������� ��� �������"),
	ADDITION("ADDITION", "���������� ����", "��������"),
	DELETION("DELETION", "�������� ����", "�������"),
	EDITION("EDITION", "�������������� ����", "���������������"),
	REVIEW("REVIEW", "�������� ����", "�����������");
	
	TypeEvent(String type, String view, String wordForMassege){
		this.view = view;
		this.type = type;
		this.wordForMassege = wordForMassege;
	}
	
	/**
	 * ��������� ��������� ������������ ���� �� ��������
	 * @param title - ��� �������� � ��
	 * @return - ��������� ������������, ��������������� �������� ����
	 */
	public static TypeEvent getTypeEvent(String title){
		if(title == null || title.equals("")){
			return UNKNOWN;
		}
		TypeEvent[] values = TypeEvent.values();
		for (int i = 0; i < values.length; i++) {
			if(title.equals(values[i].type)) return values[i];
		}
		return null;
	}
	
	/**
	 * ��� ������� ��� ����������� �� ������
	 */
	private String view;
	
	/**
	 * ��� �������
	 */
	private String type;

	
	/**
	 * ����� ��� ���������� � ����� �������
	 */
	private String wordForMassege;
	
	/**
	 * �������� ��� �������
	 * @return - ��� �������
	 */
	public String getType() {
		return type;
	}

	/**
	 * ��������� �����, ������������� � ��������� ��������� � �������
	 * @return - ����� ��� ���������� ���������
	 */
	public String getWordForMassege() {
		return wordForMassege;
	}
	
	/**
	 * ��������� ��� ������ ��� ����������� ��� �� ������
	 * @return - ��� ������� ��� ����������� �� ������
	 */
	public String getView() {
		return view;
	}


	
	

}

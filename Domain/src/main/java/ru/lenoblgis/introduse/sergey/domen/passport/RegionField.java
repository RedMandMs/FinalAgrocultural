package ru.lenoblgis.introduse.sergey.domen.passport;

import java.io.Serializable;

/**
 * ������������ ��������
 * @author Vilgodskiy
 *
 */
public enum RegionField implements Serializable{
	
	/**
	 * ������ �� ������ (��� ������)
	 */
	NULL(""),
	/**
	 * ������ �� ������ (��� ��������)
	 */
	UNKNOWN("����������"),
	/**
	 * ������������ �����
	 */
	VSEVOLOGSKIY("������������ �����"),
	/**
	 * ����������� �����
	 */
	PRIZEMSKIY("����������� �����"),
	/**
	 * ����������� �����
	 */
	VOLOSOVSKIY("����������� �����"),
	/**
	 * ���������� �����
	 */
	VOLHOVSKIY("���������� �����"),
	/**
	 * ���������� �����
	 */
	VIBORSKIY("���������� �����"),
	/**
	 * ���������� �����
	 */
	GATCHINSKIY("���������� �����"),
	/**
	 * ������������� �����
	 */
	KINGISEPPSKIY("������������� �����"),
	/**
	 * ��������� �����
	 */
	KIRISHSKIY("��������� �����"),
	/**
	 * ��������� �����
	 */
	KIROVVSKIY("��������� �����"),
	/**
	 * ������������� �����
	 */
	LOMONOSOVSKIY("������������� �����"),
	/**
	 * ������� �����
	 */
	LUZHSKIY("������� �����"),
	/**
	 * ������������ �����
	 */
	PODPOROZHSKIY("������������ �����"),
	/**
	 * ����������� �����
	 */
	SLANCEVSKIY("����������� �����"),
	/**
	 * ���������� �����
	 */
	TIHOVSKIY("���������� �����"),
	/**
	 * ���������� �����
	 */
	TOSNENSKIY("���������� �����"),
	/**
	 * �������������� �����
	 */
	BOKSITOGORSKIY("�������������� �����");
	
	
	/**
	 * ������� �����������
	 * @param region - ������
	 * @param view - ������ ��� �����������
	 */
	private RegionField(String region){
		this.region = region;
	}
	
	/**
	 * ��������� ��������� ������������ ������� �� ��������
	 * @param title - ��� �������� � ��
	 * @return - ��������� ������������, ��������������� �������� �������
	 */
	public static RegionField getRegion(String title){
		if(title == null || title.equals("")){
			return NULL;
		}
		RegionField[] values = RegionField.values();
		for (int i = 0; i < values.length; i++) {
			if(title.equals(values[i].region)) return values[i];
		}
		return null;
	}

	/**
	 * ��� �������
	 */
	private String region;
	
	/**
	 * �������� �������� �������
	 * @return - �������� �������
	 */
	public String getRegion() {
		return region;
	}

	@Override
	public String toString() {
		return region;
	}
}

package ru.lenoblgis.introduse.sergey.domen.owner;


/**
 * ��������� ��� ���������� ���������� ���������� ��������� (�����������, ������� ��� � �.�.)
 * @author VILGODSKIY
 *
 */
public interface Owner{

	/**
	 * �������� id ���������
	 * @return - id ���������
	 */
	Integer getId();
	
	/**
	 * ���������� id ���������
	 * @param id - ����� id ���������
	 */
	public void setId(Integer id);
	
	/**
	 * �������� ��� ���������
	 * @return - ��� ���������
	 */
	public String getName();
	
	/**
	 * ���������� ��� ���������
	 * @param name - ����� ���
	 */
	public void setName(String name);

	/**
	 * �������� ��� ���������
	 * @return - ��� ���������
	 */
	public Integer getInn();

	/**
	 * ���������� ��� ���������
	 * @param iNN - ����� ���
	 */
	public void setInn(Integer iNN);

	/**
	 * �������� ����� ���������
	 * @return - ����� ���������
	 */
	public String getAddress();
	
	/**
	 * ���������� ����� ���������
	 * @param addres - ����� �����
	 */
	public void setAddres(String addres);

	
}

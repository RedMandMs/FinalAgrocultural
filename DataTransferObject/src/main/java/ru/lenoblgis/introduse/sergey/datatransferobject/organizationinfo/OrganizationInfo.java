package ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo;


public class OrganizationInfo{
	
	
	
	/**
	 * ����������� ��-���������
	 */
	public OrganizationInfo() {
		
	}

	/**
	 * ����������� ��� ����������� ��� ���������� ��������� � ��
	 * @param id - id ��������
	 * @param name - ��� �����������
	 * @param inn - ����� ��� �����������
	 * @param address - ����� �����������
	 */
	public OrganizationInfo(int id, String name, int inn, String address) {
		this.id = id;
		this.name = name;
		this.inn = inn;
		setAddress(address);
	}
	
	/**
	 * ����������� ��� ����������� � �� ��� �� ������������ �����������
	 * @param name - ��� �����������
	 * @param inn - ����� ��� �����������
	 * @param address - ����� �����������
	 */
	public OrganizationInfo(String name, int inn, String address) {
		this.name = name;
		this.inn = inn;
		setAddress(address);
	}



	/**
	 * id �����������
	 */
	private Integer id;
	
	/**
	 * �������� �����������
	 */
	private String name;
	
	
	/**
	 * ��� �����������
	 */
	private Integer inn;
	
	
	/**
	 * ������ �����������
	 */
	private String address;

	/**
	 * �������� id �����������
	 * @return - id �����������
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * ���������� id �����������
	 * @param id - ����� �������� id �����������
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * �������� ��� �����������
	 * @return - ��� �����������
	 */
	public String getName() {
		return name;
	}

	/**
	 * ���������� ��� �����������
	 * @param name - ����� ��� �����������
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * �������� ��� �������������
	 * @return - ��� �����������
	 */
	public Integer getInn() {
		return inn;
	}

	/**
	 * ���������� ��� �������������
	 * @param inn - ����� ��� �����������
	 */
	public void setInn(Integer inn) {
		this.inn = inn;
	}

	/**
	 * �������� ����� �����������
	 * @return - ����� �����������
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * ���������� ����� �����������
	 * @param address - ����� ����� �����������
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OrganizationInfo that = (OrganizationInfo) o;

		if (id == that.id) return true;
		if (inn != that.inn) return false;
		return name.equals(that.name);

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + name.hashCode();
		if(inn != null){
			result = 31 * result + inn;
		}
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(getClass().getName());
		sb.append("{id=").append(getId());
		sb.append(", name='").append(getName()).append('\'');
		sb.append(", inn=").append(getInn());
		sb.append('}');
		return sb.toString();
	}
}

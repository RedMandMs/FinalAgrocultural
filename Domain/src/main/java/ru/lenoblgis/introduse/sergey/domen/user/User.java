package ru.lenoblgis.introduse.sergey.domen.user;

/**
 * ����� �������������� ������������� �������
 * @author VILGODSKIY
 *
 */
public class User {
	
	/**
	 * id ������������
	 */
	private int id;
	
	/**
	 * ����� ������������
	 */
	private String login;

	/**
	 * ������ ������������
	 */
	private String password;
	
	/**
	 * id �����������, ������� ������� ������������
	 */
	private int organizationId;
	
	/**
	 * ���� ������������ � �������
	 */
	private UserRole role;
		
	
	/**
	 * ����������� ��-���������
	 */
	public User() {
	}
	
	/**
	 * ����������� ������������ ��������� ������ ������������ � ��
	 * @param id - id ������������
	 * @param userLogin - ����� ������������
	 * @param userPassword - ������ ������������
	 */
	public User(String userLogin, String userPassword) {
		super();
		this.login = userLogin;
		this.password = userPassword;
	}
	
	/**
	 * ����������� ������������ ��������� ������ ������������ � ��
	 * @param id - id ������������
	 * @param userLogin - ����� ������������
	 * @param userPassword - ������ ������������
	 * @param role - ���� ������������
	 */
	public User(String userLogin, String userPassword, UserRole role) {
		super();
		this.login = userLogin;
		this.password = userPassword;
		this.role = role;
	}

	/**
	 * ����������� ������������ ��� ������� ���� �� ��
	 * @param id - id ������������
	 * @param userLogin - ����� ������������
	 * @param userPassword - ������ ������������
	 * @param organizationId - id �����������, ����������, ������� ��-�� ������������
	 */
	public User(int id, String userLogin, String userPassword,
			int organizationId) {
		super();
		this.id = id;
		this.login = userLogin;
		this.password = userPassword;
		this.organizationId = organizationId;
		
	}


	/**
	 * �������� id ������������
	 * @return - id ������������
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * ���������� id ������������
	 * @param id - ����� id ������������
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * �������� ����� ������������
	 * @return - ����� ������������
	 */
	public String getLogin() {
		return login;
	}
	
	/**
	 * ���������� ����� ������������
	 * @param userLogin - ����� ����� ������������
	 */
	public void setLogin(String userLogin) {
		this.login = userLogin;
	}
	
	/**
	 * �������� ������ ������������
	 * @return - ������ ������������
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * ���������� ����� ������ ������������
	 * @param userPassword - ������ ������������
	 */
	public void setPassword(String userPassword) {
		this.password = userPassword;
	}
	
	/**
	 * �������� id �����������, ������� ������� ������������
	 * @return - id ����������� �����������
	 */
	public int getOrganizationId() {
		return organizationId;
	}
	
	/**
	 * ���������� id �����������, ������� ����� ������� ������������
	 * @param organizationId - ����� id �����������
	 */
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * �������� ���� ������������
	 * @return - ���� ������������
	 */
	public UserRole getRole() {
		return role;
	}
	
	/**
	 * �������� ���� ������������ - ������
	 * @return - ���� ������������
	 */
	public String getRoleStr() {
		return role.getName();
	}

	/**
	 * ���������� ���� ������������
	 * @param role - ����� ���� ������������
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id == user.id) return true;
		if (organizationId != user.organizationId) return false;
		return login.equals(user.login);
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + login.hashCode();
		result = 31 * result + organizationId;
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(getClass().getName());
		sb.append("{id=").append(getId());
		sb.append(", login='").append(getLogin()).append('\'');
		sb.append(", organizationId=").append(getOrganizationId());
		sb.append(", role=").append(getRole().getName());
		sb.append('}');
		return sb.toString();
	}
}

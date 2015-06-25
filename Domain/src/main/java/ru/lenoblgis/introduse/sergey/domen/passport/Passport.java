package ru.lenoblgis.introduse.sergey.domen.passport;

import ru.lenoblgis.introduse.sergey.domen.owner.Owner;

/**
 * ����� ��� ���������� ���������
 * @author VILGODSKIY
 *
 */
public class Passport{
	
	/**
	 * ����������� ��� ����������� �������� �� ��
	 * @param id - id ���������
	 * @param idOwner - id ���������
	 * @param region - ������
	 * @param cadastrNumber - ����������� �����
	 * @param area - �������
	 * @param type - ��� ����
	 * @param comment - �����������
	 */
	public Passport(Integer id, Integer idOwner, String region, Integer cadastrNumber,
			Float area, String type, String comment) {
		setId(id);
		setIdOwner(idOwner);
		setRegion(region);
		setCadastrNumber(cadastrNumber);
		setArea(area);
		setType(type);		
		setComment(comment);
		setOwner(owner);
	}
	
	/**
	 * ����������� ��� ������ ��������� � �� (��� id)
	 * @param idOwner - id ���������
	 * @param region - ������
	 * @param cadastrNumber - ����������� �����
	 * @param area - �������
	 * @param type - ��� ����
	 * @param comment - �����������
	 */
	public Passport(Integer idOwner, String region, Integer cadastrNumber,
			Float area, String type, String comment) {
		setIdOwner(idOwner);
		setRegion(region);
		setCadastrNumber(cadastrNumber);
		setArea(area);
		setType(type);		
		setComment(comment);
		setOwner(owner);
	}
	
	/**
	 * ����������� ��-���������
	 */
	public Passport() {
		setRegion(null);
		setType(null);
	}
	
	
	/**
	 * Id ����
	 */
	private Integer id;
	
	 /**
	  * �����������-�������� ����
	  */
	private Integer idOwner;
	
	/**
	 * ������ �� ��������� ���������
	 */
	private Owner owner;
	
	/**
	 * ������� ������������ ����
	 */
	private RegionField region;
	
	/**
	 * ����������� ����� ����
	 */
	private Integer cadastrNumber;
	
	/**
	 * ������� ����
	 */
	private Float area;
	
	/**
	 * ��� ����
	 */
	private TypeField type;
	
	/**
	 * ����������� � ����
	 */
	private String comment;
	
	
	/**
	 * ��������� id ��������
	 * @return - id ��������
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * ��������� id ��������
	 * @param id - id ��������
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * ��������� id ���������
	 * @return - id ���������
	 */
	public Integer getIdOwner() {
		return idOwner;
	}
	
	/**
	 * ��������� id ���������
	 * @param idOwner - id ���������
	 */
	public void setIdOwner(Integer idOwner) {
		this.idOwner = idOwner;
	}
	
	/**
	 * ��������� �������
	 * @return - ������
	 */
	public String getRegion() {
		return region.getRegion();
	}
	
	/**
	 * ��������� �������
	 * @param region - ������
	 */
	public void setRegion(String region) {
		this.region = RegionField.getRegion(region);
	}
	
	/**
	 * ��������� ������������ ������
	 * @return - ����������� �����
	 */
	public Integer getCadastrNumber() {
			return cadastrNumber;
	}
	
	/**
	 * ��������� ������������ ������
	 * @param cadastrNumber - ����������� �����
	 */
	public void setCadastrNumber(Integer cadastrNumber) {
		this.cadastrNumber = cadastrNumber;
	}
	
	/**
	 * ��������� ������� ����
	 * @return - ������� ����
	 */
	public Float getArea() {
		return area;
	}
	
	/**
	 * ��������� ������� ����
	 * @param area - ������� ����
	 */
	public void setArea(Float area) {
		this.area = area;
	}
	
	/**
	 * ��������� ���� ����
	 * @return - ��� ����
	 */
	public String getType() {
		return type.getType();
	}
	
	/**
	 * ��������� ���� ����
	 * @param type - ��� ����
	 */
	public void setType(String type) {
		this.type = TypeField.getTypeOf(type);
	}
	
	/**
	 * ��������� ����������� � ���������
	 * @return - ����������� � ��������
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * �������� ��������� ��������
	 * @return - �������� ��������
	 */
	public Owner getOwner() {
		return owner;
	}

	/**
	 * ���������� ��������� ��������
	 * @param owner - ����� �������� ��������
	 */
	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	/**
	 * ��������� ����������� � ���������
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cadastrNumber == null) ? 0 : cadastrNumber.hashCode());
		result = prime * result + id;
		result = prime * result + idOwner;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passport other = (Passport) obj;
		if (id == other.id){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(getClass().getName());
		sb.append("{id=").append(getId());
		sb.append(", idOwner=").append(getIdOwner());
		sb.append(", cadastrNumber=").append(getCadastrNumber());
		sb.append('}');
		return sb.toString();
	}
}

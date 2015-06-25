package ru.lenoblgis.introduse.sergey.domen.actionevent;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.passport.Passport;

/**
 * ����� ������������ ������� ���������
 * @author VILGODSKIY
 *
 */
public class PassportEvent{
	
	/**
	 * Id �������
	 */
	private Integer  id;
	
	/**
	 * ���� � ����� �������
	 */
	private DateTime dateTime;
	
	/**
	 * ��� �������
	 */
	private TypeEvent typeEvent;	
	
	/**
	 * ID ������ �������
	 */
	private Integer idAuthor;
	
	
	/**
	 * ����� �������
	 */
	private Owner auther;
	
	/**
	 * ID ��������, ��� ������� ���� ��������� �������
	 */
	private Integer idPassport;
	
	/**
	 * �������, ��� ������� ���� ��������� �������
	 */
	private Passport passport;
	
	/**
	 * ��������� ������� ���������
	 */
	private String message;
	
	/**
	 * ����������� ��-���������
	 */
	public PassportEvent(){
		setType("");
	}
	
	/**
	 * ����������� ��� ����������� ������� � ����������� �������������
	 * @param id - id �������
	 * @param id_passport - id �������� ��� ������� ���� ��������� �������
	 * @param id_organization - id ����������� ����������� �������� �� ������� �������
	 * @param message_event - ��������� ��������� �������
	 * @param date_time_event - ���� � ����� �������
	 * @param type_event - ��� �������
	 */
	public PassportEvent(Integer id, Integer id_passport, Integer id_organization, String message, DateTime date_time_event,  String type_event){
		this.id = id;
		this.idPassport = id_passport;
		this.idAuthor = id_organization;
		this.message = message;
		setDataTime(date_time_event);
		setType(type_event);
	}
	
	/**
	 * ����������� ������������ ��� �������� �������, ����� ���������� ��� � ��
	 * @param passport - �������, ��� ������� ����������� �������
	 * @param eventType - ��� ������� (��� ��������� � �������������)
	 * @param dao - DAO ��������� ������ ������� (����� ��� ���������� �� �� ������ �������)
	 */
	public PassportEvent(Passport passport, Owner owner, String eventType){
		setType(TypeEvent.valueOf(eventType));
		this.passport = passport;
		this.idPassport = passport.getId();
		this.idAuthor = owner.getId();
		this.auther = owner;
	}
	
	/**
	 * ���������� id �������
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * ���������� ���� � ����� �������
	 */
	public void setDataTime(DateTime dateTime){
		this.dateTime = dateTime;
	}
	
	/**
	 * ���������� ��� �������
	 */
	public void setType(TypeEvent typeEvent){
		this.typeEvent = typeEvent;
	}
	
	/**
	 * ���������� ��� �������
	 */
	public void setType(String typeEvent){
		this.typeEvent = TypeEvent.getTypeEvent(typeEvent);
	}
	
	/**
	 * ���������� id ������ �������
	 */
	public void setIdAuthor(Integer idAuthor){
		this.idAuthor = idAuthor;
	}
	
	/**
	 * ���������� id ������� ��� ������� ���� ��������� �������
	 */
	public void setIdPassport(Integer idPassport){
		this.idPassport = idPassport;
	}
	
	/**
	 * �������� id �������
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * �������� ���� � ����� �������
	 */
	public DateTime getDataTime(){
		return dateTime;
	}
	
	/**
	 * ��������� ����� �������
	 */
	public LocalTime getTime(){
		return dateTime.toLocalTime();
	}
	
	public LocalDate getDate(){
		return dateTime.toLocalDate();
	}
	
	/**
	 * �������� ��� �������
	 */
	public TypeEvent getTypeEvent(){
		return typeEvent;
	}
	
	/**
	 * �������� ��� ������� (�������)
	 */
	public String getType(){
		return typeEvent.getType();
	}
	
	
	/**
	 * �������� id ������� ��� ������� ���� ��������� �������
	 */
	public Integer getIdPassport(){
		return idPassport;
	}
	
	/**
	 * ��������������� ������ ��������� ��������� ������� - �������� ������, ����� ����� �� ������ �� ��
	 * @return - ����� �������
	 */
	public Owner getAuther() {
		return auther;
	}

	/**
	 * ���������� ������ �������
	 * @param auther - �����
	 */
	public void setAuther(Owner auther) {
		this.auther = auther;
	}
	
	/**
	 *  ��������������� ������ �������� ������� ��� �������� ������� �������
	 * @return - �������
	 */
	public Passport getPassport() {
		return passport;
	}

	/**
	 * �������� id ������
	 * @return - id ������
	 */
	public Integer getIdAuthor() {
		return idAuthor;
	}

	/**
	 * �������� ����� ��������� � �������
	 * @return - ����� ��������� � �������
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * ���������� ����� ��������� � �������
	 * @param message - ����� ����� ��������� � �������
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + idAuthor;
		result = prime * result + idPassport;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PassportEvent other = (PassportEvent) obj;
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
		sb.append(", typeEvent=").append(getType());
		sb.append(", idAuthor=").append(getIdAuthor());
		sb.append(", idPassport=").append(getIdPassport());
		sb.append('}');
		return sb.toString();
	}
}

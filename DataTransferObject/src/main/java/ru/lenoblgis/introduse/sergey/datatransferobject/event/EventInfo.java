package ru.lenoblgis.introduse.sergey.datatransferobject.event;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class EventInfo{

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
	private String typeEvent;	
	
	/**
	 * ID ������ �������
	 */
	private Integer idAuthor;
	
	
	/**
	 * ����� �������
	 */
	private String nameAuthor;
	
	/**
	 * ID ��������, ��� ������� ���� ��������� �������
	 */
	private Integer idPassport;
	
	/**
	 * ��������� ������� ���������
	 */
	private String message;
	
	/**
	 * ����������� ��-���������
	 */
	public EventInfo(){
	}
	
	/**
	 * ����������� ��� ����������� ������� � ����������� �������������
	 * @param id - id �������
	 * @param id_passport - id �������� ��� ������� ���� ��������� �������
	 * @param id_organization - id ����������� ����������� �������� �� ������� �������
	 * @param message - ��������� ��������� �������
	 * @param date_time_event - ���� � ����� �������
	 * @param type_event - ��� �������
	 */
	public EventInfo(Integer id, Integer id_passport, Integer id_organization, String message, DateTime date_time_event,  String type_event){
		this.id = id;
		this.idPassport = id_passport;
		this.idAuthor = id_organization;
		this.message = message;
		setDataTime(date_time_event);
		setTypeEvent(type_event);
	}

	/**
	 *  ����������� ������������ ��� �������� �������, ����� ���������� ��� � ��
	 * @param idPassport - id ��������, ��� ������� ��������� �������
	 * @param idAuthor - id ������ �������
	 * @param nameAuther - ��� ������ ������� (�����������)
	 * @param eventType - ��� ������� (��� ��������� � �������������)
	 */
	public EventInfo(Integer idPassport, Integer idAuthor, String nameAuther, String eventType){
		setTypeEvent(eventType);
		this.idPassport = idPassport;
		this.idAuthor = idAuthor;
		this.nameAuthor= nameAuther;
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
	public String getTypeEvent(){
		return typeEvent;
	}
	
	/**
	 * �������� id ������� ��� ������� ���� ��������� �������
	 */
	public Integer getIdPassport(){
		return idPassport;
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

	/**
	 * �������� ���� �������
	 * @return - �������� ���� �������
	 */
	public DateTime getDateTime() {
		return dateTime;
	}

	/**
	 * ���������� ���� �������
	 * @param dateTime - ���� �������
	 */
	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * �������� ��� ���������
	 * @return - ��� ���������
	 */
	public String getNameAuthor() {
		return nameAuthor;
	}

	/**
	 * ���������� ����� ��� ���������
	 * @param nameAuther - ����� ��� ���������
	 */
	public void setNameAuthor(String nameAuther) {
		this.nameAuthor = nameAuther;
	}

	/**
	 * ���������� ��� �������
	 * @param typeEvent - ����� ��� �������
	 */
	public void setTypeEvent(String typeEvent) {
		if(typeEvent.trim().equals("")){
			this.typeEvent = null;
		}else{
			this.typeEvent = typeEvent;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EventInfo eventInfo = (EventInfo) o;

		if (id == eventInfo.id){
			return true;
		}else{
			return false;
		}

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + idAuthor;
		result = 31 * result + idPassport;
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(getClass().getName());
		sb.append("{id=").append(getId());
		sb.append(", typeEvent='").append(getTypeEvent()).append('\'');
		sb.append(", idAuthor=").append(getIdAuthor());
		sb.append(", idPassport=").append(getIdPassport());
		sb.append('}');
		return sb.toString();
	}
}

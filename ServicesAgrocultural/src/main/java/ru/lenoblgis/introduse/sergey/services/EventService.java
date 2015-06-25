package ru.lenoblgis.introduse.sergey.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.event.EventInfo;
import ru.lenoblgis.introduse.sergey.domen.actionevent.PassportEvent;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;

@Service("eventService")
public class EventService implements Serializable{
	
	/**
	 * DAO ��� ������ � ����� ������
	 */
	@Autowired
	private DAO dao;

	/**
	 * �������� ��� �������, ������� �������� ���������� ��������
	 * @param idOwner - id ���������
	 * @return - ������ �������
	 */
	public List<EventInfo> getAllOwnerEvents(int idOwner){
		List<EventInfo> listEvents = new ArrayList<EventInfo>();
		
		PassportEvent passportEvent = new PassportEvent();
		passportEvent.setIdAuthor(idOwner);
		
		List<PassportEvent> events = dao.findEvents(passportEvent);
		
		for(PassportEvent event : events){
			listEvents.add(convertDomainToDTO(event));
		}
		
		return listEvents;
	}

	/**
	 * ����� ������� �� �������� ����������
	 * @param serchingEvent - ������ � ��������� ��� ������ �����������
	 * @return - ������ �������� �������
	 */
	public List<EventInfo> findEvents(EventInfo serchingEvent) {
	
		List<EventInfo> findInfo = new ArrayList<EventInfo>();
		
		PassportEvent findingEvent = convertDTOToDomain(serchingEvent);

		String nameAuthor = serchingEvent.getNameAuthor();
		
		//���� ��� ������ ���� ������� ��� ������ �������
		if(nameAuthor != null && !nameAuthor.trim().equals("")){
			Organization organization = new Organization();
			organization.setName(nameAuthor);
			List<Organization> organizations = dao.findOwners(organization);
			
			//���� ����������� � �������� ������ ������� �� ����, �� ���������� ������ ������
			//
			if(organizations.isEmpty()){
				return findInfo;
			//����� ���� ���������� ����������� - ��� ������ � ������������ � ������, �.�. ����� ����������� ���������
			}else{
				organization = organizations.get(0);
			}
			
			//���� id ������ (���� ��� �� ����� null) ��������� ��� ������ �� ������������� id,
			//������� �������� ��� ������ �� ����� ����� - ����� ���������� ������ ������
			if(serchingEvent.getIdAuthor() != organization.getId() && serchingEvent.getIdAuthor() != null){
				return findInfo;
			}
			
			//���� id ������ ������� �� ����, �� ���� ������� ���, �� ����������� id � �������
			if(serchingEvent.getIdAuthor() == null){
				findingEvent.setIdAuthor(organization.getId());
			}
			
		}
		
		List<PassportEvent> findEvents = dao.findEvents(findingEvent);
		
		for(PassportEvent event : findEvents){
			findInfo.add(convertDomainToDTO(event));
		}
		
		return findInfo;
	}

	/**
	 * ������������� ������� �� Data Transfer Object � �������� �����
	 * @param event - ������� � ����� DTO
	 * @return - ������� � �������� �����
	 */
	private EventInfo convertDomainToDTO(PassportEvent event) {
		return new EventInfo(event.getId(), event.getIdPassport(), event.getIdAuthor(), event.getMessage(), event.getDataTime(), event.getType());
	}

	/**
	 * ������������� ������� �� �������� ����� � Data Transfer Object
	 * @param event - ������� � �������� �����
	 * @return - ������� � ����� DTO
	 */
	private PassportEvent convertDTOToDomain(EventInfo event) {
		return new PassportEvent(event.getId(), event.getIdPassport(), event.getIdAuthor(), event.getMessage(), event.getDataTime(), event.getTypeEvent());
	}

	/**
	 * ������� �������
	 * @param idEvent - id �������
	 */
	public void deleteEvent(Integer idEvent) {
		dao.deletePassportEvent(idEvent);
	}
	
	/**
	 * UID ��� ������������
	 */
	private static final long serialVersionUID = -7362792691829662617L;
}

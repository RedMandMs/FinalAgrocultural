package ru.lenoblgis.introduse.sergey.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.passportinfo.PassportInfo;
import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.passport.Passport;

@Component("passportService")
public class PassportService implements Serializable {
	
	/**
	 * �����
	 */
	 private static final Logger log = Logger.getLogger(PassportService.class);
	
	/**
	 * ����������� ��-���������
	 */
	public PassportService() {

	}
	
	/**
	 * DAO ��� ������ � ����� ������
	 */
	@Autowired
	private DAO dao;
	
	/**
	 * �������� ����� �������� � ��
	 * @param passportInfo - ����������� �������� � ��
	 * @return - ��� �� ��������, �� ��� � id � ��
	 */
	public PassportInfo createPassport(PassportInfo passportInfo){
		
		Passport passport = converDTOtoDomain(passportInfo);
		try{
			Integer id = dao.createPassport(passport);
			passportInfo.setId(id);
			return passportInfo;
		}catch(DuplicateKeyException duplicateEx){
			log.log(Level.ERROR, DateTime.now() + "		Passport(" + passportInfo + ") was not been created, because they were duplicated unique fields. Exeption: " + duplicateEx);
			throw duplicateEx;
		}catch(DataIntegrityViolationException ex){
			log.log(Level.ERROR, DateTime.now() + "		Passport(" + passportInfo + ") was not been created, because of a conflict foreign key. Exeption: " + ex);
			throw ex;
		}
	}
	
	
	/**
	 * ������������� ��������
	 * @param passportInfo - ���������� ������� � ������ �����������
	 * @return - ������ �� �������������� �������
	 */
	public boolean editPassport(PassportInfo passportInfo){
		
		Passport passport = converDTOtoDomain(passportInfo);
		try{
			dao.editPassport(passport);;
			return true;
		}catch(DuplicateKeyException duplicateEx){
			log.log(Level.ERROR, DateTime.now() + "		Passport(" + passportInfo + ") was not been edited, because they were duplicated unique fields. Exeption: " + duplicateEx);
			throw duplicateEx;
		}catch(DataIntegrityViolationException ex){
			log.log(Level.ERROR, DateTime.now() + "		Passport(" + passportInfo + ") was not been edited, because of a conflict foreign key. Exeption: " + ex);
			throw ex;
		}
	}
	
	/**
	 * ����������� �������
	 * @param passportId - id ��������
	 * @param myCompany - ��� ������������� �������
	 * @return - ������������� �������
	 */
	public PassportInfo reviewPassport(int passportId, OrganizationInfo myCompany){
		
		Owner browsing = OwnerService.convertDTOToDomain(myCompany);
		PassportInfo passportInfo = null;
		try{
			Passport passport = dao.reviewPassport(passportId, browsing);
			passportInfo = converDomainToDTO(passport);
		}catch(IndexOutOfBoundsException indexOutOfBoundEx){
			log.log(Level.ERROR, DateTime.now() + "		Passport(" + passportInfo + ") was not been reviwed, because it wasn't found. Exeption: " + indexOutOfBoundEx);
			throw indexOutOfBoundEx;
		}
		return passportInfo;
	}
	
	/**
	 * ������� ��������
	 * @param passportId -id ���������� ���������
	 * @return - true - �������� �����, false - �������� ��������
	 */
	public boolean deletePassport(int passportId){
		
		try{
			dao.deletePassport(passportId);
			return true;
		}catch(IndexOutOfBoundsException indexOutOfBoundEx){
			log.log(Level.ERROR, DateTime.now() + "		Passport(id="+passportId+") was not been deleted, because passport with id like this wasn't found. Exeption: " + indexOutOfBoundEx);
			throw indexOutOfBoundEx;
		}
	}
	
	/**
	 * ����� ��������� �� �������� ����������
	 * @param serchingPassport - ������ �������� ���������� ���������� ��������� ��� ������
	 * @return - ������, ��������� ���������
	 */
	public List<PassportInfo> findPassports(PassportInfo serchingPassport) {
		List<PassportInfo> listPasportsInfo = new ArrayList<PassportInfo>();
		
		Passport serchinDomainPassport = converDTOtoDomain(serchingPassport);
		
		List<Passport> passports = dao.findPassports(serchinDomainPassport);
		for(Passport passport : passports){
			listPasportsInfo.add(converDomainToDTO(passport));
		}
		return listPasportsInfo;
	}
	
	private static Passport converDTOtoDomain(PassportInfo passport){
		return new Passport(passport.getId(), passport.getIdOwner(), passport.getRegion(), passport.getCadastrNumber(), passport.getArea(), passport.getType(), passport.getComment());
	}
	
	private static PassportInfo converDomainToDTO(Passport passport){
		return new PassportInfo(passport.getId(), passport.getIdOwner(), passport.getRegion(), passport.getOwner().getName(), passport.getCadastrNumber(), passport.getArea(), passport.getType(), passport.getComment());
	}
	
	/**
	 * UID ��� ������������
	 */
	private static final long serialVersionUID = -8295916090862287108L;
}

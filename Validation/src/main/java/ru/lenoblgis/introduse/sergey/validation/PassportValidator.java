package ru.lenoblgis.introduse.sergey.validation;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.passportinfo.PassportInfo;
import ru.lenoblgis.introduse.sergey.domen.passport.Passport;

/**
 * ����� ��� �������� �������� ������ �������� � ��������� ��������
 * @author vilgodskiy
 *
 */
public class PassportValidator implements Validator{

	/**
	 * �����
	 */
	 private static final Logger log = Logger.getLogger(PassportValidator.class);
	
	/**
	 * DAO ��� ������ � ����� ������
	 */
	@Autowired
	DAO dao;
	
	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return PassportInfo.class.equals(clazz);
	}

	/**
	 * ��������� ��������:
	 * �������� �� ��������������� ������������ ������
	 * �������� �� ����� ������������ ������
	 * �������� �� ���� �� ���� ������� ��������� ������
	 * �������� �� ��������������� �������
	 * �������� ������ ���� ����
	 * �������� ������ ������ ����
	 * 
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
	        PassportInfo passportInfo = (PassportInfo) target;

	      //�������������� �������� ������
	        passportInfo = encodeAllPassportFields(passportInfo);
	        
	        //-------------�������� �� ��������������� ������������ ������
	        if (passportInfo.getCadastrNumber() != null) {
	        	if(passportInfo.getCadastrNumber() <= 0){
	            errors.rejectValue("cadastrNumber", "cadastrNumber.isNegative", "����������� ����� ������ ����� ������������� ��������");
	        	}else{
	        	
		        	//-------------�������� �� ����� ������������ ������
			        //���������, ���� �� � �� �������� � �����  ����������� �������
		        	Passport serchingPassport = new Passport();
		        	serchingPassport.setCadastrNumber(passportInfo.getCadastrNumber());
		        	List<Passport> findingPasports = dao.findPassports(serchingPassport);
		        	//���� ����
		        	if( ! findingPasports.isEmpty()){
		        		//���� � ��������� ���� id - ������ ���� ������� �������������, ����� - ��������
		    	        if(passportInfo.getId() != null){
		    	        	//���� ������� �������������, �� ���������� ��������� �� ��� �� �� ��� ������� (��� �������� ����������� ����� �� ���������)
		    	        	if( ! passportInfo.getId().equals(findingPasports.get(0).getId())){
		    	        		//���� ��� ������ ��������, �� ��������� ������
		    	        		errors.rejectValue("cadastrNumber", "cadastrNumber.copy", "������� � ����� ����������� ������� ��� ���������������!");
		    	        	}
		    	        }else{
		    	        	//���� ��������, �� ��� ������
		    	        	errors.rejectValue("cadastrNumber", "cadastrNumber.copy", "������� � ����� ����������� ������� ��� ���������������!");
		    	        }
		        	}
		        }
	        }
	      
	        
        	//-------------�������� �� ���� �� ���� ������� ��������� ������
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "area", "area.empty", "���������� ������� ������� ����!");
        	
        	
        	//-------------�������� �� ��������������� �������
        	if (passportInfo.getArea() != null && passportInfo.getArea() <= 0) {
	            errors.rejectValue("area", "area.isNegative", "������� ���� ������ ����� ������������� ��������");
	        }
        	
        	
        	//-------------�������� ������ ���� ����
        	if(passportInfo.getType().trim().equals("")){
        		errors.rejectValue("type", "type.isNotChoose", "���������� ������� ��� ����!");
        	}
        	
        	
        	//-------------�������� ������ ������ ����
        	if(passportInfo.getRegion().trim().equals("")){
        		errors.rejectValue("region", "region.isNotChoose", "���������� ������� ������!");
        	}
	}
	
	/**
	 * ������������ ��� ���� �������� (�� ������ ������� ��������)
	 * @param passport - ������������ �������
	 * @return - ������� � ���������������� (���������������) ������
	 * @throws UnsupportedEncodingException - ��������� ������������� ����� 
	 */
	public static PassportInfo encodeAllPassportFields(PassportInfo passport){
		if(passport.getNameOwner() != null){
			passport.setNameOwner(encodeToCp1251(passport.getNameOwner()));
		}
		if(passport.getComment() != null){
			passport.setComment(encodeToCp1251(passport.getComment()));
		}
		if(passport.getRegion() != null){
			passport.setRegion(encodeToCp1251(passport.getRegion()));
		}
		if(passport.getType() != null){
			passport.setType(encodeToCp1251(passport.getType()));
		}
		return passport;
	}
	
	/**
	 * ������������� ����� � JSP ������� �� ISO-8859-1 � Cp1251 (��� ������ ��������)
	 * @param origin - ������������ ������
	 * @return - ��������������� ������
	 * @throws UnsupportedEncodingException - ��������� ������������� ����� 
	 */
	private static String encodeToCp1251(String origin){
		String converted = null;
		try {
			converted = new String(origin.getBytes("ISO-8859-1"), "Cp1251");
		} catch (UnsupportedEncodingException e) {
			log.log(Level.ERROR, "unsuccessful attempt to decode text. Exeption: " + e);
		}
		return converted;
	}

}

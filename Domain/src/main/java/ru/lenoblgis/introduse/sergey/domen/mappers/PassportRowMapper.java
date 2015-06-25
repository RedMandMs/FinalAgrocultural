package ru.lenoblgis.introduse.sergey.domen.mappers;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.lenoblgis.introduse.sergey.domen.passport.Passport;

/**
 * ����� ��� ����������� ��������� �� �� � ���������� �������������
 * @author VILGODSKIY
 *
 */
public class PassportRowMapper implements RowMapper<Passport>, Serializable {

	/**
	 * UID ��� ������������
	 */
	private static final long serialVersionUID = 9202383766579652978L;

	/**
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Passport mapRow(ResultSet rs, int rowNum) throws SQLException {		
		Passport passport = new Passport(rs.getInt("id"), rs.getInt("id_organization"), rs.getString("region"), 
				rs.getInt("cadastr_number"), rs.getFloat("area"), rs.getString("type_field"), rs.getString("comment"));
		if(passport.getCadastrNumber() == 0){
			passport.setCadastrNumber(null);
		}
		return passport;
	}

}

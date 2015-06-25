package ru.lenoblgis.introduse.sergey.data.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries;
import ru.lenoblgis.introduse.sergey.domen.actionevent.PassportEvent;
import ru.lenoblgis.introduse.sergey.domen.mappers.EventRowMapper;
import ru.lenoblgis.introduse.sergey.domen.mappers.OrganizationRowMapper;
import ru.lenoblgis.introduse.sergey.domen.mappers.PassportRowMapper;
import ru.lenoblgis.introduse.sergey.domen.mappers.UserRowMapper;
import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;
import ru.lenoblgis.introduse.sergey.domen.passport.Passport;
import ru.lenoblgis.introduse.sergey.domen.user.User;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
 * ����� DAO ��� ������ � ����� ������, ���������� spring, ������� ��������������� � �� ����� JDBC
 * @author VILGODSKIY
 *
 */
@Component("dao")
public class DAO implements Serializable {

	/**
	 * �����
	 */
	 private static final Logger log = Logger.getLogger(DAO.class);
	 
	
	/**
	 * ��������� ����� ������������ ��� ���������� ������� - ���������� ����
	 */
	private static final String ADD_EVENT = "ADDITION";
	
	/**
	 * ��������� ����� ������������ ��� ���������� ������� - �������������e ����
	 */
	private static final String EDIT_EVENT = "EDITION";
	
	/**
	 * ��������� ����� ������������ ��� ���������� ������� - �������� ����
	 */
	private static final String DELETE_EVENT = "DELETION";
	
	/**
	 * ��������� ����� ������������ ��� ���������� ������� - �������� ����
	 */
	private static final String REVIEW_EVENT = "REVIEW";
	
	/**
	 * ����������� ��-���������
	 */
	public DAO(){
		postConstruct();
	}
	
	/**
	 * ������ ��� ��������� ������ ��������
	 */
	@Autowired
	private SQLQueries sqlQueries;
	/**
	 * ������ DataSource
	 */
	private SQLServerDataSource ds;
	/**
	 * ������ ������ ��� �������������� ���� ������
	 */
	private JdbcTemplate jdbcTemplate = null;
	/**
	 * ������, ������������ �������� � ����������� ������ �� ��
	 */
	@Autowired
	private PassportRowMapper passportRowMapper;
	/**
	 * ������, ������������ ����������� � ����������� ������ �� ��
	 */
	@Autowired
	private OrganizationRowMapper organizationRowMapper;
	/**
	 * ������, ������������ ������� � ����������� ������ �� ��
	 */
	@Autowired
	private EventRowMapper eventRowMapper;
	/**
	 * ������, ������������ ������������ � ����������� ������ �� ��
	 */
	@Autowired
	private UserRowMapper userRowMapper;
	
	/**
	 * ����������� DataSource � ���� ������ � �������� jdbcTemplate
	 */
	public void postConstruct(){
		try{
			ds = new SQLServerDataSource();
			ds.setPortNumber(1433);
			ds.setHostNameInCertificate("localhost");
			ds.setDatabaseName("passport_agricultural");
			ds.setUser("adminAgricultural");
			ds.setPassword("admin123");
			jdbcTemplate = new JdbcTemplate(ds);
		}catch(Exception ex){
			log.log(Level.ERROR, "DAO unable to connect to database");
		}
	}
	
	/**
	 * ������� ���������
	 * @param owner - ����� ��������
	 * @return - id ������ ���������
	 */
	private int createOwner(Owner owner) {
		String sqlQuery = sqlQueries.createOwner(owner);
		PreparedStatementCreator psc = new PrepereStmCreater(sqlQuery);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		int id = keyHolder.getKey().intValue();
		owner.setId(id);
		log.log(Level.INFO, DateTime.now() + "    Created organization: " + owner);
		return id;
	}

	/**
	 * ������� �����������
	 * @param idOwner - id �����������
	 */
	public void deleteOwner(int idOwner) {
		Object [] values = new Object[]{idOwner};
		jdbcTemplate.update(sqlQueries.deleteOwner(), values);
		log.log(Level.INFO, DateTime.now() + "    Deleted organization with id = " + idOwner);
	}

	/**
	 * ������������� ������ �� �����������
	 * @param owner - �����������
	 */
	public void editOwner(Owner owner) {
		Object [] values = new Object[]{owner.getName(), owner.getInn(), owner.getAddress(), owner.getId()};
		String sqlQuery = sqlQueries.editOwner();
		jdbcTemplate.update(sqlQuery, values);
		log.log(Level.INFO, DateTime.now() + "    Edited organization: " + owner);
	}

	/**
	 * ����������� �����������
	 * @param id - id �����������
	 * @return - ����������� ��� ���������
	 */
	public Owner reviewOwner(int id) {
		Object [] values = new Object[]{id};
		List<Organization> resultSet = jdbcTemplate.query(sqlQueries.reviewOwner(), values, organizationRowMapper);
		Owner owner = resultSet.get(0);
		log.log(Level.INFO, DateTime.now() + "    Reviwed organization: " + owner);
		return  owner;
	}
	
	/**
	 * ����� ����������� �� �������� ��������
	 * @param findingOrganization - ������ � ����������� ���������
	 * @return - ������ �������� �����������
	 */
	public List<Organization> findOwners(Organization findingOrganization) {
		List<Organization> listOrganizations = new ArrayList<Organization>();
		
		String sql = sqlQueries.findOwners(findingOrganization);
		
		listOrganizations = jdbcTemplate.query(sql, organizationRowMapper);
		
		return listOrganizations;
	}

	/**
	 * ������� ������� � ��
	 * @param passport - ����������� �������
	 * @return - id ��������
	 */
	public int createPassport(Passport passport) {
		String sqlQuery = sqlQueries.createPassport(passport);
		PreparedStatementCreator psc = new PrepereStmCreater(sqlQuery);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		
		int id = keyHolder.getKey().intValue();
		
		//������� ������ ��� ����������� ������� �� ������������� Id ��������� ������ �����������
		passport.setId(id);
		
		Owner owner = reviewOwner(passport.getIdOwner());
		passport.setOwner(owner);
		
		//������������ ������� ���������� ����
		addPassportEvent(passport, owner, ADD_EVENT);
		
		log.log(Level.INFO, DateTime.now() + "    Created passport: " + passport);
		
		return id;
	}

	/**
	 * ������� �������
	 * @param id - id ���������� ��������
	 */
	public void deletePassport(int id) {
		Object [] values = new Object[]{id};
		Passport passport = reviewPassportWithoutWrite(id);
		jdbcTemplate.update(sqlQueries.deletePassport(), values);
		
		Owner owner = reviewOwner(passport.getIdOwner());
		passport.setOwner(owner);
		
		//������������ ������� �������� ����
		addPassportEvent(passport, owner, DELETE_EVENT);
		
		log.log(Level.INFO, DateTime.now() + "    Deleted passport: " + passport);
	}
	
	/**
	 * ����������� ������� (�� �������� ������� � ��������� ��������)
	 * @param id - id ���������������� ��������
	 * @return - ����������� �������
	 */
	public Passport reviewPassportWithoutWrite(int id){
		Object[] values = new Object[] {id};
		List<Passport> resultSet = jdbcTemplate.query(sqlQueries.reviewPassport(), values , passportRowMapper);

		Passport passport = resultSet.get(0);
		Owner owner = reviewOwner(passport.getIdOwner());
		passport.setOwner(owner);
		
		return passport;
	}

	/**
	 * ������������� �������
	 * @param passport - ����������� �������
	 */
	public void editPassport(Passport passport) {
		Object [] values = new Object[]{passport.getIdOwner(), passport.getRegion(), passport.getCadastrNumber(), passport.getArea(), passport.getType(), passport.getComment(), passport.getId()};
		jdbcTemplate.update(sqlQueries.editPassport(), values);

		Owner owner = reviewOwner(passport.getIdOwner());
		passport.setOwner(owner);
		
		//������������ ������� �������������� ����
		addPassportEvent(passport, owner, EDIT_EVENT);
		
		log.log(Level.INFO, DateTime.now() + "    Edited passport: " + passport);
	}

	/**
	 * ����������� �������
	 * @param id - id ���������������� ��������
	 * @param browsing - �����������, ��������������� �������
	 * @return - ������������� �������
	 */
	public Passport reviewPassport(int id, Owner browsing) {

		Passport passport = reviewPassportWithoutWrite(id);
		
		//������������ ������� ��������� ����
		addPassportEvent(passport, browsing, REVIEW_EVENT);
		
		log.log(Level.INFO, DateTime.now() + "    Reviwed passport: " + passport);
		
		return passport;
	}

	/**
	 * ����� ������� �� �������� �������� (���� ������� �� ������ ���������� ��� ��������)
	 * @param info - ���������� �������
	 * @return - ������ �������� ���������
	 */
	public List<Passport> findPassports(Passport serchingPassport) {
		List<Passport> resaltList = jdbcTemplate.query(sqlQueries.findPassports(serchingPassport), passportRowMapper);
		
		for(Passport passport : resaltList){
			Owner owner = reviewOwner(passport.getIdOwner());
			passport.setOwner(owner);
		}
		
		log.log(Level.INFO, DateTime.now() + "    Executed requare for finding passports");
		
		return resaltList;
	}
	
	/**
	 * �������� ������ ������� � ���� ������
	 * @param passport - ��������, � ������ ������� �������
	 * @param owner - �����������, ������� ��������� �������
	 * @param typeEvent - ��� �������
	 */
	private void addPassportEvent(Passport passport, Owner owner, String typeEvent) {
		
		PassportEvent event = new PassportEvent(passport, owner, typeEvent);
		
		//������������ ���������, ������� ��������� � ��
		String message = "����������� \"" + owner.getName() + "\" (id = " + owner.getId() + ") " + event.getTypeEvent().getWordForMassege() 
								+ " �������� � id = " + passport.getId();
		event.setMessage(message);
		
		Object[] values = new Object[] {event.getIdPassport(), event.getIdAuthor(), message, event.getType()};
		String sqlQuery = sqlQueries.createPassportEvent();
		jdbcTemplate.update(sqlQuery, values);
		log.log(Level.INFO, DateTime.now() + "    Added event ("+ typeEvent +") about passport: " + passport);
	}
	
	/**
	 * �������� ������ ������� �� ��
	 * @param idEvent - id ���������� �������
	 */
	public void deletePassportEvent(int idEvent){
		jdbcTemplate.update(sqlQueries.deletePassportEvent(), new Object[]{idEvent});
		log.log(Level.INFO, DateTime.now() + "    Event with id="+idEvent+" deleted from data base!");
	}

	/**
	 * ����� ������� �� �������� ��������
	 * @param findingEvent - ������ � ��������� ���������
	 * @return - ������ ��������� �������
	 */
	public List<PassportEvent> findEvents(PassportEvent findingEvent) {
		List<PassportEvent> listEvents = new ArrayList<PassportEvent>();
		
		String sql = sqlQueries.findEvents(findingEvent);
		
		listEvents = jdbcTemplate.query(sql, eventRowMapper);
		
		return listEvents;
	}

	/**
	 * ���������������� ������������ � ������� ��� �����������
	 * @param user - ������������
	 * @param organization - ����������� ������������
	 * @return - ������������ (� id)
	 */
	public User registration(User user, Owner organization) {
		int organizationId = createOwner(organization);
		user.setOrganizationId(organizationId);
		int userId = createUser(user);
		user.setId(userId);
		log.log(Level.INFO, DateTime.now() + "    Registration user: "+user+ " was execute successful!");
		return user;
	}
	
	/**
	 * �������� ������������ � �������
	 * @param user - ������������
	 * @return - id ������������
	 */
	private int createUser(User user){
		String sqlQuery = sqlQueries.createUser(user);
		PreparedStatementCreator psc = new PrepereStmCreater(sqlQuery);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		int userId = keyHolder.getKey().intValue();
		log.log(Level.INFO, DateTime.now() + "    User: "+user+" was created successfuk in data base!");
		return userId;
		
	}

	/**
	 * ����� ������������ �� ������
	 * @param login - �����
	 * @return - ������������
	 */
	public User findUserByLogin(String login){
		Object[] values = new Object[] {login};
		List<User> resultSet = jdbcTemplate.query(sqlQueries.reviewUserByLogin(), values , userRowMapper);
		if(resultSet.isEmpty()){
			log.log(Level.INFO, DateTime.now() + "    Executed finding user by login='"+login+"', but he wasn't find!");
			return null;
		}else{
			log.log(Level.INFO, DateTime.now() + "    Executed finding user by login='"+login+"' and he was find!");
		}
		return resultSet.get(0);
	}
	
	/**
	 * ����� ��� ���������� sql ��������, ��� �������� ����, ������� ������ ������������ ��� �������
	 * @author VILGODSKIY
	 */
	private class PrepereStmCreater implements PreparedStatementCreator{

		String sqlQuery = null;

		public PrepereStmCreater(String sqlQuery) {
			super();
			this.sqlQuery = sqlQuery;
		}

		@Override
		public PreparedStatement createPreparedStatement(Connection con)
				throws SQLException {
			PreparedStatement pstm = con.prepareStatement(sqlQuery, new String [] {"id"});
			
			return pstm;
		}
		 
	}
	
	/**
	 * UID ��� ������������
	 */
	private static final long serialVersionUID = 5453332340428759258L;
}

<%@ page language="java" contentType="text/html; charset=Cp1251"
    pageEncoding="Cp1251"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
<title>����������� ������ ������������</title>
</head>
<body>
	<div align="center" >
		<br>
		<form method="GET" action="/Presentation/">
			<input type="submit" name="goToMainPageBtn" value="����� �� �������� ��������"/>
		</form>
		<br>
		<h1>����������� ������ ������������:</h1>
		<sec:authorize access="!isAuthenticated()">
			<c:forEach var="message" items="${listErorRegistration}">
				<h4>${message}</h4>
			</c:forEach>
			<sf:form method="POST" modelAttribute="userOrganization" acceptCharset="Cp1251">
				<fieldset>
					<table>
						<tr>
							<th><label for="user_login">������� �����:</label></th>
							<td>
								<sf:input path="login" size="20" id="user_login" value="${reviwingOrganization.getLogin()}"/>
								<small id ="login_msg">����� ������ �������� �� ��������� �������� � ����. ������������ ������ ������ 15 ��������, ����������� - 5</small>
							</td>
						</tr>
						
						<tr>
							<th><label for="user_password">������� ������:</label></th>
							<td>
								<sf:password path="password" size="20" id="user_password"/>
								<small id ="password_msg">������ ������ �������� �� ��������� �������� � ����. ������������ ������ ������ 15 ��������, ����������� - 5</small>
							</td>
						</tr>
						
						<tr>
							<th><label for="user_repassword">������� ������ ��������:</label></th>
							<td>
								<sf:password path="repassword" size="20" id="user_repassword"/>
							</td>
						</tr>
						
						<tr>
							<th><label for="name_organization">������� ��� ��������: </label></th>
							<td><sf:input path="organizationName" size="20" id="name_organization" value="${reviwingOrganization.getOrganizationName()}"/></td>
						</tr>
						
						<tr>
							<th><label for="inn">������� ��� ��������: </label></th>
							<td><sf:input path="inn" size="20" id="inn" value="${reviwingOrganization.getInn()}"/></td>
						</tr>
						
						<tr>
							<th><label for="organization_address">������� ����� ��������: </label></th>
							<td><sf:input path="address" size="50" id="organization_address" value="${reviwingOrganization.getAddress()}"/></td>
						</tr>
						
						<tr>
							<td><input type="submit" name="regestrationBtn" value="������������������"></td>
						</tr>
					</table>
				</fieldset>
			</sf:form>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<H1><a>�� ��� ����� � �������, ��� �����: <sec:authentication property="principal.username" /></a></H1>
			<form name="logOutForm" action="<c:url value='/logout' />" method='GET'>
				<input type="submit" name="logOutBtn" value="����� �� �������">
			</form>
		</sec:authorize>
	</div>
</body>
</html>
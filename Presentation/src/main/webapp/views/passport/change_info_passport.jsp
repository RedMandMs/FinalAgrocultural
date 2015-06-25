<%@ page language="java" contentType="text/html; charset=Cp1251"
    pageEncoding="Cp1251"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
	<title>��������� ���������� � ���������</title>
	</head>
	<body>
		<div align="center" >
			<form method="GET" action="/Presentation/passport/${lastList}" >
				<input type="submit" name="backToPassportsListBtn" value="����� � ������ ���������"/>
			</form>
			<br>
			<form method="GET" action="/Presentation/passport/${changedPassport.getId()}" >
				<input type="submit" name="backToPassportsListBtn" value="����� � ���������� � ��������"/>
			</form>
			<h1>��������� ������ � ���������:</h1>
			<c:forEach var="message" items="${erorMessagesEditPassport}">
				<h4>${message}</h4>
			</c:forEach>
			<a>${message}</a>
			<sf:form method="POST" modelAttribute="changedPassport" acceptCharset="Cp1251">
				<fieldset>
					<table>
						<tr>
							<td><label for="id"></label>Id ���������: </td>
							<td><sf:input readonly="true" path="id" size="10" id="id" value = "${changedPassport.getId()}"/></td>
						</tr>
						
						
						<tr>
							<td><label for="id_owner"></label>Id ��������-��������� ��������: </td>
							<td><sf:input readonly="true" path="idOwner" size="10" id="id_owner" value = "${changedPassport.getIdOwner()}"/></td>
						</tr>
					
						<tr>
							<td><label for="name_owner"></label>��� ��������-��������� ��������: </td>
							<td><sf:input readonly="true" path="nameOwner" size="10" id="name_owner" value = "${changedPassport.getNameOwner()}"/></td>
						</tr>
					
						<tr>
							<th><label for="region">������� ������ ����: </label></th>
							<td>
								<sf:select path="region" id="region">
									<c:forEach var="region" items="${regions}">
										<sf:option value="${region.getRegion()}"/>
									</c:forEach>
								</sf:select>
							</td>
						</tr>
						
						<tr>
							<th><label for="area">������� ������� ����: </label></th>
							<td><sf:input path="area" size="10" id="area" value="${changedPassport.getArea()}"/></td>
						</tr>
						
						<tr>
							<th><label for="cadastrNumber">������� ����������� ����� ��������: </label></th>
							<td><sf:input path="cadastrNumber" size="20" id="cadastrNumber" value="${changedPassport.getCadastrNumber()}"/></td>
						</tr>
						
						<tr>
							<th><label for="type">������� ��� ����: </label></th>
							<td>
								<sf:select path="type" id="type">
									<c:forEach var="type" items="${types}">
										<sf:option value="${type.getType()}"/>
									</c:forEach>
								</sf:select>
							</td>
						</tr>
						
						<tr>
							<th><label for="comment">������� ����������� � ���������: </label></th>
							<td><sf:input path="comment" size="50" id="comment" value="${changedPassport.getComment()}"/></td>
						</tr>
						
						<tr>
							<td><input type="submit" name="changedBtn" value="�������� ���������� � ���������"></td>
						</tr>
					</table>
				</fieldset>
			</sf:form>
		</div>
	</body>
</html>
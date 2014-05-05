<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>  
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>  
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Examenes generados</title>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/angular.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/SeniorityController.js"></script>
</head>

<body ng-controller="SeniorityCtrl">
	<table width="70%">
		<tr>
			<td width="25%">Name</td>
			<td valign="top">
				<textarea ng-model="nameText" rows="6" cols="20"></textarea>
			</td>
		</tr>
		<tr>
			<td>
				<INPUT value="Save Seniority" type="button" ng-click="saveSeniority()">
			</td>
		</tr>
	</table>
</body>
</html>
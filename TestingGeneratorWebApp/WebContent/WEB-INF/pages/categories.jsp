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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/CategoriaController.js"></script>
</head>

<body ng-controller="LoginCtrl">
	<table width="70%">
		<tr>
			<td width="25%">Name</td>
			<td valign="top">Description</td>
			<td width="8%">Active</td>
			<td width="8%">&nbsp</td>
		</tr>
		<tr>
			<td valign="top">
				<input type="text" ng-model="queryName" size="20"></textarea>
			</td>
			<td valign="top">
				<input type="text" ng-model="queryDescription" size="20"></textarea>
			</td>
			<td valign="top">&nbsp</td>
			<td valign="top">&nbsp</td>
		</tr>
		<tr ng-repeat="category in categories | filter:{name:queryName,description:queryDescription}">
			<td width="25%">
				<a href="${pageContext.request.contextPath}/spring/updateCategory/{{categoryname}}" >{{category.name}}</a>
			</td>
			<td valign="top">{{category.description}}</td>
			<td width="8%">{{category.active}}</td>
			<td width="8%">
				<input type="checkbox">
			</td>
		</tr>
	</table>
</body>
</html>
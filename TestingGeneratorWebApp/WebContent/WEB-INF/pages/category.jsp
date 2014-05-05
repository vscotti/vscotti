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

<body ng-controller="CategoryCtrl">
	<table width="70%">
		<tr>
			<td width="25%">Name</td>
			<td valign="top">
				<c:if test="${category != null}">
					<input type="text" ng-init="nameText='${category.name}'" readonly="readonly" ng-model="nameText" />
				</c:if>
				<c:if test="${category == null}">
					<input type="text" ng-init="nameText='${category.name}'" ng-model="nameText" />
				</c:if>
			</td>
		</tr>
		<tr>
			<td width="25%">Description</td>
			<td valign="top">
				<textarea ng-model="descriptionText" rows="6" cols="20" ng-init="descriptionText='${category.description}'" ></textarea>
			</td>
		</tr>
		<c:if test="${category != null}">
			<tr>
				<td width="25%">Active</td>
				<td valign="top">${category.active}</td>
			</tr>
			<tr>
				<td>
					<INPUT value="Update Category" type="button" ng-click="updateCategory()">
				</td>
			</tr>
		</c:if>
		<c:if test="${category == null}">
			<tr>
				<td>
					<INPUT value="Save Category" type="button" ng-click="saveCategory()">
				</td>
			</tr>
		</c:if>
	</table>
</body>
</html>
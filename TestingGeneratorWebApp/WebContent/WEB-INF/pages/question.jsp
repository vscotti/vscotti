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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/PreguntaController.js"></script>
</head>

<body ng-controller="QuestionCtrl">
	<table width="70%">
		<tr>
			<td width="25%">Category</td>
			<td valign="top">
				<select ng-model="categoryNameSelected">
    				<option value="">Select</option>
			        <option ng-repeat="category in categories" value="{{category.name}}">
			          {{category.name}}
			        </option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="25%">Seniority</td>
			<td valign="top">
				<select ng-model="seniorityNameSelected">
    				<option value="">Select</option>
			        <option ng-repeat="seniority in seniorities" value="{{seniority.name}}">
			          {{seniority.name}}
			        </option>
				</select>
			</td>
		</tr>
		<tr>
			<td width="25%">Question</td>
			<td valign="top">
				<textarea ng-model="questionText" rows="6" cols="20"></textarea>
			</td>
		</tr>
		<tr>
			<td width="25%">Answer</td>
			<td valign="top">
				<textarea ng-model="answerText" rows="6" cols="20"></textarea>
			</td>
		</tr>
		<tr>
			<td>
				<INPUT value="Save Question" type="button" id="saveQuestion" ng-click="saveQuestion()">
			</td>
		</tr>
	</table>
</body>
</html>
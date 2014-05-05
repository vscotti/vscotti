<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" ng-app="phonecatApp">
<head>
	<script src="../bower_components/angular/angular.js"></script>
	<script src="../bower_components/angular-route/angular-route.js"></script>
	<script src="js/app.js"></script>
	<script src="js/controllers.js"></script>

	<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/angular.js"></script>
	<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/LoginController.js"></script>
    <script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/RcSubmit.js"></script>
    <script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/App.js"></script>

</head>
<body>
	<div ng-view></div>
</body>
</html>
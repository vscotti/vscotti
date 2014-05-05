<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
	<title>Login Page</title>

	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
	
	<script language="javascript" type="text/javascript" src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

	<!--script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script-->
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.8/angular.min.js"></script>

	<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/LoginController.js"></script>
	
	<style>
		.error {
			padding: 15px;
			margin-bottom: 20px;
			border: 1px solid transparent;
			border-radius: 4px;
			color: #a94442;
			background-color: #f2dede;
			border-color: #ebccd1;
		}
		
		.msg {
			padding: 15px;
			margin-bottom: 20px;
			border: 1px solid transparent;
			border-radius: 4px;
			color: #31708f;
			background-color: #d9edf7;
			border-color: #bce8f1;
		}
		
		#login-box {
			width: 300px;
			padding: 20px;
			margin: 100px auto;
			background: #fff;
			-webkit-border-radius: 2px;
			-moz-border-radius: 2px;
			border: 1px solid #000;
		}
	</style>
	
</head>
<body ng-app="myApp">

	<div>

		<div ng-controller="LoginController">
			<div class="container">
				
				<div class="row">
					<div class="col-sm-10">
				
						<form id="userFormLogin" name="userFormLogin" class="simple_form form-horizontal" role="form" novalidate action="<c:url value='/j_spring_security_check' />" method="POST">
							<c:if test="${not empty error}">
								<div class="error">${error}</div>
							</c:if>
							<c:if test="${not empty msg}">
								<div class="msg">${msg}</div>
							</c:if>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">&nbsp;</label> 
								<div class="col-sm-6">
									<h3>Login</h3>
								</div>
								<div class="col-sm-3">&nbsp;</div>
							</div>
							
							<div class="form-group" ng-class="{ 'has-error' : userFormLogin.username.$error.required }">
								<label class="col-sm-3 control-label">Username</label> 
								<div class="col-sm-5">
									<input type="text" name="username" class="form-control" ng-model="username" required>
								</div>
								<div class="col-sm-4">
									<p ng-show="userFormLogin.username.$error.required" class="help-block">Username is required.</p>
								</div>
							</div>
							<div class="form-group" ng-class="{ 'has-error' : userFormLogin.password.$error.required }">
								<label class="col-sm-3 control-label">Password</label> 
								<div class="col-sm-5">
									<input type="password" name="password" class="form-control" ng-model="password" required>
								</div>
								<div class="col-sm-4">
									<p ng-show="userFormLogin.password.$error.required" class="help-block">Password is required.</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 control-label">&nbsp;</label> 
								<div class="col-sm-5">
									<button type="button" class="btn btn-primary" ng-disabled="userFormLogin.$invalid" ng-click="validatePresubmitLogin()">Sign In</button>
									<BR>
									<BR>
									<div>Not an User? Please <a href="register">Register</a></div>
								</div>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
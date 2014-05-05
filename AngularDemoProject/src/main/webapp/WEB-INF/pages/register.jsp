<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>  

<%@page session="true"%>
<html>

<head>
	<title>Registration Page</title>
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
	
	<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.8/angular.min.js"></script>

	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

	<script src="${pageContext.request.contextPath}/js/LoginController.js"></script>
</head>

<body ng-app="myApp">

	<div>

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>

		<div ng-controller="LoginController">
			<div class="container">
				<div class="row">
					<div class="col-sm-10">
						<form id="userForm" name="userForm" class="simple_form form-horizontal" role="form" novalidate action="${pageContext.request.contextPath}/register">
							<div class="form-group" ng-class="{ 'has-error' : userForm.username.$error.required }">
								<label class="col-sm-3 control-label">&nbsp;</label> 
								<div class="col-sm-5">
									<h3>Register new User</h3>
								</div>
								<div class="col-sm-4">
								</div>
							</div>
							<div class="form-group" ng-class="{ 'has-error' : userForm.username.$error.required }">
								<label class="col-sm-3 control-label">Username</label> 
								<div class="col-sm-5">
									<input type="text" name="username"class="form-control" ng-model="username" required>
								</div>
								<div class="col-sm-4">
									<p ng-show="userForm.username.$error.required" class="help-block">Username is required.</p>
								</div>
							</div>
							<div class="form-group" ng-class="{ 'has-error' : userForm.email.$error.required || userForm.email.$error.email}">
								<label class="col-sm-3 control-label">Email</label> 
								<div class="col-sm-5">
									<input type="email" name="email"class="form-control" ng-model="email" required>
								</div>
								<div class="col-sm-4">
									<p ng-show="userForm.email.$error.required" class="help-block">Email is required.</p>
									<p ng-show="userForm.email.$error.email" class="help-block">Not valid email!</p>
								</div>
							</div>
							<div class="form-group" ng-class="{ 'has-error' : userForm.password.$error.required || userForm.password.$error.equalvalues }">
								<label class="col-sm-3 control-label">Password</label> 
								<div class="col-sm-5">
									<input type="password" name="password" class="form-control" ng-model="password" required>
								</div>
								<div class="col-sm-4">
									<p ng-show="userForm.password.$error.required" class="help-block">Password is required.</p>
								</div>
							</div>
							<div class="form-group" ng-class="{ 'has-error' : userForm.retypepassword.$error.required }">
								<label class="col-sm-3 control-label">Retype Password</label> 
								<div class="col-sm-5">
									<input type="password" name="retypepassword" class="form-control" ng-model="retypepassword" required>
								</div>
								<div class="col-sm-4">
									<p ng-show="userForm.retypepassword.$error.required" class="help-block">Retype Password is required.</p>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">&nbsp;</label> 
								<div class="col-sm-5">
									<button type="button" class="btn btn-primary" ng-disabled="userForm.$invalid" ng-click="validatePresubmit()">Submit</button>
									<BR>
									<BR>
									<div>Already an User? Please <a href="login">Log In</a></div>
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
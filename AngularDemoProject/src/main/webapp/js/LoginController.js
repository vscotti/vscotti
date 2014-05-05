
var myApp = angular.module('myApp', []);

myApp.controller('LoginController', ['$scope', '$http', function($scope, $http) {
	$scope.username = '';
	$scope.email = '';
	$scope.password = '';
	$scope.retypepassword = '';
	
	$scope.validatePresubmitLogin = function() {
		$http.get('/AngularDemoProject/restnosecurity/getUserForLogin?username=' + $scope.username+"&password="+$scope.password)
			.success(function(data) {
				if(data === ''){
					alert("Invalid Username/Passsword");
				} else {
					document.getElementById ('userFormLogin').submit();
				}
			})
			.error(function(data, status, headers, config) {
			});

	};
	
	$scope.validatePresubmit = function() {
		var formRegister = angular.element(document.querySelector('#userForm'));
		$http.get('/AngularDemoProject/restnosecurity/getUser?username=' + $scope.username)
			.success(function(data) {
				if(data === ''){
					if($scope.password == $scope.retypepassword) {
						formRegister.submit();
					} else {
						alert("Both passwords are not equals.");
					}
				} else {
					alert("Username already exist in the system. Please enter a different name.");
				}
			})
			.error(function(data, status, headers, config) {
			});

	};
}]);

myApp.directive('ngEqualvalues', function() {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, elem, attr, ngModel) {
			
			ngModel.$parsers.unshift(function(value) {
				var valid = value === scope.retypepassword;
				ngModel.$setValidity('equalvalues', valid);
				return valid ? value : undefined;
			});

			ngModel.$formatters.unshift(function(value) {
				var valid = value === scope.retypepassword;
				ngModel.$setValidity('equalvalues', valid);
				return value;
			});
		}
	};
});

myApp.directive('ngAlreadyexist', function($http) {
	return {
		restrict: 'A',
	    require: 'ngModel',
		link: function(scope, elem, attr, ngModel) {
			
			ngModel.$parsers.unshift(function(value) {
				var valid = true;
				$http.get('/AngularDemoProject/restnosecurity/getUser?username=' + value)
					.success(function(data) {
						if(data === ''){
							valid = false;
						}
					});
				ngModel.$setValidity('alreadyexist', valid);
				return valid ? value : undefined;
			});

			ngModel.$formatters.unshift(function(value) {
				var valid = true;
				$http.get('/AngularDemoProject/restnosecurity/getUser?username=' + value)
					.success(function(data) {
						if(myVar === ''){
							valid = false;
						}
					});
				ngModel.$setValidity('alreadyexist', valid);
				return value;
			});
		}
	};
});

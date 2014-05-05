
var myApp = angular.module('myApp', []);

myApp.controller('MainController', ['$scope', '$http', function($scope, $http) {
	$scope.id = '';
	$scope.description = '';
	$scope.priority = '-1';
	$scope.status = '1';
	$scope.duedate = '';
	$scope.createdate = '';
	$scope.items;
	$scope.itemsCompleted;
	$scope.priorities;
	$scope.statuses;
	$scope.loggedUser;
	$scope.isModify = false;
	$scope.isCompleted = false;
	
    $scope.sort = {
        	column: 'priority',
            descending: false
        };

    $scope.sortcompleted = {
        	column: 'priority',
            descending: false
        };

    $scope.selectedCls = function(column) {
        return (column == $scope.sort.column)? 'sort-' + $scope.sort.descending : '';
    };

    $scope.selectedClsCompleted = function(column) {
        return (column == $scope.sortcompleted.column)? 'sort-' + $scope.sortcompleted.descending : '';
    };
    
    $scope.changeSorting = function(column) {
        var sort = $scope.sort;
        if (sort.column == column) {
            sort.descending = !sort.descending;
        } else {
            sort.column = column;
            sort.descending = false;
        }
    };
    
    $scope.changeSortingCompleted = function(column) {
        var sort = $scope.sortcompleted;
        if (sort.column == column) {
            sort.descending = !sort.descending;
        } else {
            sort.column = column;
            sort.descending = false;
        }
    };
        
	$http.get('/AngularDemoProject/rest/getPriorities')
		.success(function(data) {
			$scope.priorities = data;
		});
	
	$http.get('/AngularDemoProject/rest/getStatuses')
	.success(function(data) {
		$scope.statuses = data;
	});
	
	$http.get('/AngularDemoProject/rest/getLoggedUser')
	.success(function(data) {
		$scope.loggedUser = data;
	});

	$scope.getItems = function() {
		$http.get('/AngularDemoProject/rest/getAllItems')
			.success(function(data) {
				$scope.items = data;
			});
	};

	$scope.getItemsCompleted = function() {
		$http.get('/AngularDemoProject/rest/getItemsCompleted')
			.success(function(data) {
				$scope.itemsCompleted = data;
			});
	};
		
	$scope.addItem = function() {
		$http.get('/AngularDemoProject/rest/addNewItem?desc='+$scope.description+'&prior='+$scope.priority+'&dueDate='+$scope.getTimestamp($scope.duedate))
			.success(function(data) {
				alert("Item Saved Sucessfuly.");
				$scope.id = '';
				$scope.description = '';
				$scope.priority = '-1';
				$scope.status = '1';
				$scope.duedate = '';
				$scope.createdate = '';
				$scope.isModify = false;
				$scope.isCompleted = false;
				$scope.getItems();
			});
	};
	
	$scope.loadItem = function(id) {
		$http.get('/AngularDemoProject/rest/getItem?id='+id)
			.success(function(data) {
				$scope.description = data.description;
				$scope.priority = data.priority;
				$scope.status = data.status;
				$scope.duedate = $scope.getDate(data.dueDate);
				$scope.createdate = $scope.getDate(data.creationDate);
				$scope.id = data.id;
				$scope.isModify = true;
				$scope.isCompleted = false;
				if($scope.status == 0) {
					$scope.isCompleted = true;
				}
			});
	};
	
	$scope.deleteItem = function(id) {
		$http.get('/AngularDemoProject/rest/deleteItem?id='+id)
			.success(function(data) {
				$scope.getItems();
			});
	};
	
	$scope.modifyItem = function() {
		 var params = 'id='+$scope.id;
		 params += '&desc='+$scope.description;
		 params += '&priority='+$scope.priority;
		 params += '&status='+$scope.status;
		 params += '&dueDate='+$scope.getTimestamp($scope.duedate);
		 $http.get('/AngularDemoProject/rest/modifyItem?'+params)
			.success(function(data) {
				$scope.id = '';
				$scope.description = '';
				$scope.priority = '-1';
				$scope.status = '1';
				$scope.duedate = '';
				$scope.createdate = '';
				$scope.isModify = false;
				$scope.isCompleted = false;
				$scope.getItems();
				$scope.getItemsCompleted();
			});
	};
	
	$scope.getItems();
	$scope.getItemsCompleted();
	
	$scope.getTimestamp = function(str) {
		var d = str.match(/\d+/g);
		return +new Date(d[2], d[1] - 1, d[0], 0, 0, 0);
	};
	
	$scope.getDate = function(timestamp) {
		var date = new Date(timestamp);
		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var day = date.getDate();
		return day+"/"+month+"/"+year;
	};
	
	$scope.clearDate = function() {
		$scope.id = '';
		$scope.description = '';
		$scope.priority = '-1';
		$scope.status = '1';
		$scope.duedate = '';
		$scope.createdate = '';
		$scope.isModify = false;
		$scope.isCompleted = false;
	};
	
	$scope.getPriorityDesc = function(id) {
		for (var key in $scope.priorities) {
			if($scope.priorities[key].id == id) {
				return $scope.priorities[key].name;
			}
		}
		return id;
	};
	
	$scope.getSatusDesc = function(id) {
		for (var key in $scope.statuses) {
			if($scope.statuses[key].id == id) {
				return $scope.statuses[key].name;
			}
		}
		return id;
	};
}]);

myApp.directive('ngSelectedvalue', function() {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, elem, attr, ngModel) {
			ngModel.$parsers.unshift(function(value) {
				var valid = value === '-1';
				ngModel.$setValidity('selectedvalue', !valid);
				return !valid ? value : undefined;
			});

			ngModel.$formatters.unshift(function(value) {
				var valid = value === '-1';
				ngModel.$setValidity('selectedvalue', !valid);
				return value;
			});
		}
	};
});

myApp.directive('ngDategreaterthantoday', function() {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, elem, attr, ngModel) {
			
			ngModel.$parsers.unshift(function(value) {
				var valid = true;
				if(value.length === 10) {
					var timestamp = scope.getTimestamp(value);
					var date = new Date(timestamp);
					
					var today = new Date();
					var year = today.getFullYear();
					var month = today.getMonth() + 1;
					var day = today.getDate();
					var strToday = day+"/"+month+"/"+year;
					
					var todatTimestamp = scope.getTimestamp(strToday);
					today = new Date(todatTimestamp);
					
					valid = +date >= +today;
				}
				ngModel.$setValidity('dategreaterthantoday', valid);
				return value;
			});
		}
	};
});

myApp.directive('ngValidatedate', function() {
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, elem, attr, ngModel) {
			
			ngModel.$parsers.unshift(function(value) {
				var re = new RegExp(/^(0?[1-9]|[12][0-9]|3[01])[\/\-\.](0?[1-9]|1[012])[\/\-\.](\d{4})$/);
				var valid = (value === '' || value === null || value === undefined) || re.test(value);
				ngModel.$setValidity('validatedate', valid);
				return value;
			});
		}
	};
});

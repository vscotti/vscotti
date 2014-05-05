'use strict';

function CategoryListCtrl($scope, $http) {
	$scope.queryName = '';
	$scope.queryDescription = '';

	$http.get('getAllCategories')
		.success(function(data) {
			$scope.categories = data;
		});
}

function CategoryCtrl($scope, $http) {
	
	$scope.saveCategory = function() {
		if($scope.nameText != '' &&
				$scope.descriptionText != '') {
			var xsrf = {name : $scope.nameText, 
						description : $scope.descriptionText};
			$http.post('saveCategoryAjax', xsrf)
				.success(function(data) {
					if(data == "SUCCESS") {
						alert("Categoria guarda con existo.");
						$scope.nameText = '';
						$scope.descriptionText = '';
					} else {
						alert("Ya existe una categoria con ese nombre. Por favor ingrese una nueva.");
					}
			});
		} else {
			alert("Por favor complete el campo nombre y descripcion.");
		}
	};

	$scope.updateCategory = function() {
		if($scope.descriptionText != '') {
			var xsrf = {name : $scope.nameText, 
						description : $scope.descriptionText};
			$http.post('updatedCategoriaAjax', xsrf)
				.success(function(data) {
					if(data == "SUCCESS") {
						alert("Categoria actualizada con existo.");
					} else {
						alert("Ha ocurrido un error.");
					}
			});
		} else {
			alert("Por favor complete el campo descripcion.");
		}
	};
	
}

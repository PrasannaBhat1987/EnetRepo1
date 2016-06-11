


var app = angular.module("homeApp", ['ngCookies']);
	app.controller("homeController", function($scope, $window, $cookies, $http) {
		
		$scope.username='';
		$scope.password='';
		$scope.error = false;
		$scope.processing = false;
		
		$scope.login = function() {
			$scope.processing = true;
			$http.post('http://localhost:8083/Enet3/rest/user/login',{
	    		"username" : $scope.username,
	    		"password" : $scope.password
	    	}).success( function(response) {
	    		$scope.error = false;
	    		$cookies.Auth = response.auth;
	    		$cookies.userId = response.id;
	    		$cookies.role = response.role;
	    		$scope.processing = false;
	    		$window.location.href = 'http://localhost:8083/Enet3/dashboard.html';
	    	}).error(function(errorinfo){
	    		$scope.processing = false;
	    		$scope.username='';
	    		$scope.password='';
	    		$scope.error = true;
	    	});
		}
	});
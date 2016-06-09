var app = angular.module("homeApp", ['ngCookies']);
	
	app.controller("homeController", function($scope, $window, $cookies, $http) {
		
		$scope.username='';
		$scope.password='';
		$scope.mess="test";
		
		$scope.login = function() {
			$http.post('http://localhost:8083/Enet3/rest/user/login',{
	    		"username" : $scope.username,
	    		"password" : $scope.password
	    	}).success( function(response) {
//	    		var auth = response.substring(response.indexOf(':')+2);
//	    		
//	    		console.log('Auth: ' + response);
//	    		console.log('auth: ' + auth);
	    		$cookies.Auth = response.auth;
	    		$cookies.userId = response.id;
	    		$cookies.role = response.role;
	    		$window.location.href = 'http://localhost:8083/Enet3/dashboard.html';
	    	});
		}
	});
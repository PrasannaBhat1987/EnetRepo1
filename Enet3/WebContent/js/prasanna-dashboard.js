/**
 * 
 */
(function(){

		
var sampleApp = angular.module('enetDashboardApp', []);
  
sampleApp .config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/users', {
        templateUrl: 'templates/show-users.html',
        controller: 'ShowUsersController'
      }).
      when('/websites', {
        templateUrl: 'templates/show-websites.html',
        controller: 'ShowWebsitesController'
      }).
      when('/customers', {
          templateUrl: 'templates/show-customers.html',
          controller: 'ShowCustomersController'
        }).
      otherwise({
        redirectTo: '/dashboard'
      });
  }]);

sampleApp.controller('ShowUsersController', function($scope, $http) {
	$scope.message = 'This is Add new order screen';
	$scope.users = [{
        "name": "Prasanna",
        "password": "prasannna",
        "email": "prasannaEmail",
        "contact": "prasContact",
        "address": "PrasannaAddress",
        "role": null,
        "managerId": 0
    }];

	$http.defaults.headers.common['Auth'] = 'UHJhc2FubmE6Qmhhc3NzdA==';
	$http.get('http://localhost:8083/Enet3/rest/representative/all').success( function(response) {
	      $scope.users = response; 
	});
    
    
     
});
 
 
sampleApp.controller('ShowCustomersController', function($scope) {
	
    $scope.message = 'This is Show orders screen';
 
});

sampleApp.controller('LogoutController', function($scope) {
	 
    $scope.message = 'This is Show orders screen';
 
});

sampleApp.controller('LogoutController',function($scope, $window, $http) {
    $scope.count = 0;
    $scope.logout = function() {
    	$window.location.href = 'http://localhost:8083/Enet3';
    	$http.defaults.headers.common['Auth'] = '';
    }
    $scope.tab = 1;
	 $scope.selectTab = function(setTab) {
		 $scope.tab = setTab;
	 };
	 $scope.isSelected = function(checkTab) {
		 return $scope.tab === checkTab;
	 };
});
})();
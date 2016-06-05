/**
 * 
 */
(function(){

		
var sampleApp = angular.module('enetDashboardApp', ['ngCookies','ngResource']);
var jq = $.noConflict();
  
sampleApp .config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/users', {
        templateUrl: 'templates/show-users.html',
        controller: 'ShowUsersController'
      }).
      when('/createrao', {
          templateUrl: 'templates/show-createrao.html',
          controller: 'CreateRaoController'
        }).
      when('/websites', {
        templateUrl: 'templates/show-websites.html',
        controller: 'ShowWebsitesController'
      }).
      when('/customers', {
          templateUrl: 'templates/show-customers.html',
          controller: 'CreateCustomersController'
        }).
     when('/branches', {
        templateUrl: 'templates/show-branches.html',
        controller: 'BranchController'
      }).
      otherwise({
        redirectTo: '/dashboard'
      });
  }]);

sampleApp.controller('ShowUsersController', function($scope, $http, $cookies, $resource) {
	
	$scope.tab = 1;
	$scope.name = '';
	$scope.email = '';
	$scope.contact = '';
	$scope.address = '';
	$scope.role = '';
	$scope.manager = '';
	var table;
	
	$scope.selectTab = function(setTab) {
		$scope.tab = setTab;
	}
	
	$scope.isSelected = function(checkTab) {
		return $scope.tab === checkTab;
	}
	
	$http.defaults.headers.common['Auth'] = $cookies.Auth;
	
	$scope.addUser = function() {
		$http.post('http://localhost:8083/Enet3/rest/representative/add',{
    		"name" : $scope.name,
    		"email" : $scope.email,
    		"contact" : $scope.contact,
    		"address" : $scope.address,
    		"role" : $scope.role,
    		"managerId" : $scope.manager
    	}).success( function(response) {
    		$scope.name = '';
    	    $scope.email = '';
    	    $scope.contact = '';
    	    $scope.address = '';
    	    $scope.role = '';
    	    $scope.manager = '';
    		$scope.message = 'User added !!'; 
    	});
	};
	
	$scope.showUsers = function() {
		$http.get('http://localhost:8083/Enet3/rest/representative/all').success( function(response) {
		      $scope.users = response; 
		      table = jq('#usersTable').DataTable( {
			        "data": $scope.users,
			        destroy: true,
			        "columns": [
			            { "data": "id" },
			            { "data": "name" },
			            { "data": "email" },
			            { "data": "contact" },
			            { "data": "role" },
			            { "data": "managerId" }
			        ]
			    } );
		});
	      
	      jq('#usersTable tbody').on( 'click', 'tr', function () {
		        if ( jq(this).hasClass('selected') ) {
		            jq(this).removeClass('selected');
		        }
		        else {
		            jq('tr.selected').removeClass('selected');
		            jq(this).addClass('selected');
		        }
		    } );
			 
	};
	
	jq('#deleteuser').click( function () {
    	var d = table.rows('.selected').data()[0];
    	$http.delete('http://localhost:8083/Enet3/rest/representative/' + d.id)
	        .success(function (data, status, headers) {
	        	table.row('.selected').remove().draw( false );
	        })
	        .error(function (data, status, header, config) {
	        });
    });
	
});
 
sampleApp.controller('CreateRaoController', function($scope, $http, $cookies) {
	
	jq('.custominfo').hide();
	jq('.repdetails').hide();
	$scope.tab = 1;
	$scope.showme  = false;
	$scope.customerid='';
	$scope.branchId='';
	$scope.serviceCharge = 200;
	$scope.lineItems = [{
		"item" : "Television",
		"itemDescription" : "Item1",
		"quantity" : 3,
		"unitPrice" : 40
	},
	{
		"item" : "Refrigeratorr",
		"itemDescription" : "Item2",
		"quantity" : 10,
		"unitPrice" : 120
	},
	{
		"item" : "Washing Machine",
		"itemDescription" : "Item3",
		"quantity" : 5,
		"unitPrice" : 1200
	}];
	$scope.itemDescription='';
	$scope.item=';'
	$scope.quantity = '';
	$scope.unitPrice= '';
	$scope.representativeid = '';
	$scope.websites = [];
	$scope.branches = [];
	$scope.sameaddress=true;
	$scope.shippingaddress = '';
	$scope.description = '';
	$scope.orderNumber='';
	$scope.orderDate = new Date();
	$scope.websiteName = '';
	
	$scope.getWebsiteName = function() {
		angular.forEach($scope.websites, function(website, index){
            if (website.id === $scope.websiteId) {
            	$scope.websiteName = website.name;
            }
        });
	};
	
	$scope.getTotal = function() {
		var total1 = 0;
		angular.forEach($scope.lineItems, function(lineItem, index){
            total1 = total1 + lineItem.quantity * lineItem.unitPrice;
        });
		$scope.total = total1;
		return total1;
	};
	
	$scope.getGrandTotal = function() {
		return $scope.total + $scope.serviceCharge;
	};
	
	
	$scope.copyAddress = function() {
		if($scope.sameaddress) {
			$scope.shippingaddress = $scope.customeraddress;
			jq('#shipping').hide();
		} else {
			$scope.shippingaddress = '';
			jq('#shipping').show();
		}
	};
	
	$scope.serviceCharge = 200;
		
	$scope.saveLineItem = function(){
		$scope.showme  = false;
		$scope.lineItems.push({
			"item" : $scope.item,
			"itemDescription" : $scope.itemDescription,
			"quantity" : $scope.quantity,
			"unitPrice" : $scope.unitPrice
		});
		$scope.total = parseFloat($scope.total) + ($scope.quantity * parseFloat($scope.unitPrice));
		$scope.itemDescription='';
		$scope.quantity = '';
		$scope.unitPrice= '';
	};
	
	$scope.selectTab = function(setTab) {
		$scope.tab = setTab;
	}
	
	$scope.isSelected = function(checkTab) {
		return $scope.tab === checkTab;
	}
	
	$http.defaults.headers.common['Auth'] = $cookies.Auth;
	$http.get('http://localhost:8083/Enet3/rest/website/all').success( function(response) {
	      $scope.websites = response; 
	});  
	$http.get('http://localhost:8083/Enet3/rest/branch/all').success( function(response) {
	      $scope.branches = response; 
	});
	
	$scope.createRao = function() {
		$http.post('http://localhost:8083/Enet3/rest/rao/add',{
    		"websiteId" : $scope.websiteId,
    		"customerId" : $scope.customerid,
    		"branchId" : $scope.branchId,
    		"userId" : $scope.representativeid,
    		"orderDate" : $scope.orderDate,
    		"status" : "Created",
    		"description" : $scope.description,
    		"lineItems" : $scope.lineItems,
			"deliveryAddress" : $scope.shippingaddress,
			"orderNumber" : $scope.orderNumber
    	}).success( function(response) {
    		
    		$scope.message = 'Rao created !!'; 
    	});
	}
	
	$scope.customeremail = '';
    $scope.customernumber = '';
    $scope.customername = '';
    $scope.customeraddress = '';
    
	$scope.fetchCustomer = function(){
		$http.get('http://localhost:8083/Enet3/rest/customer/' + $scope.customerid).success( function(response) {
		      $scope.customer = response;
		      jq('.custominfo').show();
		      $scope.customeremail = response.email;
		      $scope.customernumber = response.contact;
		      $scope.customername = response.name;
		      $scope.customeraddress = response.address;
		      $scope.shippingaddress =  response.address;
		});
	};
 
	$scope.representativecontact = '';
	$scope.representativeemail = '';
	$scope.representativename = '';
	$scope.representative;
	
	$scope.fetchRepresentative = function(){
		$http.get('http://localhost:8083/Enet3/rest/representative/' + $scope.representativeid).success( function(response) {
		      $scope.representative = response;
		      jq('.repdetails').show();
		      $scope.representativecontact = response.contact;
		      $scope.representativeemail = response.email;
		      $scope.representativename = response.name;
		});
	};
});

sampleApp.controller('CreateCustomersController', function($scope, $http, $cookies) {
	
    $scope.message = 'This is Show orders screen';
    $scope.customerid = '';
    $scope.customername = '';
    $scope.customeremail = '';
    $scope.customernumber = '';
    $scope.customeraddress = '';
    $scope.tab = 1;
    $scope.customers = [];
    var table;
    
    $scope.selectTab = function(setTab) {
		$scope.tab = setTab;
	}
	
	$scope.isSelected = function(checkTab) {
		return $scope.tab === checkTab;
	}
	
    $http.defaults.headers.common['Auth'] = $cookies.Auth;
    $scope.createCustomer = function() {
    	$http.post('http://localhost:8083/Enet3/rest/customer/add',{
    		"name" : $scope.customername,
    		"email" : $scope.customeremail,
    		"contact" : $scope.customernumber,
    		"address" : $scope.customeraddress
    	}).success( function(response) {
    		$scope.customername = '';
    	    $scope.customeremail = '';
    	    $scope.customernumber = '';
    	    $scope.customeraddress = '';
    		$scope.message = 'User added !!'; 
    	});
    }
    
    $scope.showCustomers = function() {
		
		$http.get('http://localhost:8083/Enet3/rest/customer/all').success( function(response) {
		      $scope.customers = response; 
		      
		      table = jq('#customerTable').DataTable( {
			        "data": $scope.customers,
			        destroy: true,
			        "columns": [
					            { "data": "id" },
					            { "data": "name" },
					            { "data": "email" },
					            { "data": "contact" },
					            { "data": "address" }
					        ]
			    } );
		      
		      jq('#customerTable tbody').on( 'click', 'tr', function () {
			        if ( jq(this).hasClass('selected') ) {
			            jq(this).removeClass('selected');
			        }
			        else {
			        	jq('tr.selected').removeClass('selected');
			            jq(this).addClass('selected');
			            var d = table.rows('.selected').data()[0];
			            $scope.customerid = d.id;
			            $scope.customername = d.name;
			    	    $scope.customeremail = d.email;
			    	    $scope.customernumber = d.contact;
			    	    $scope.customeraddress = d.address;
			        }
			    } );
			 
		});
	}
	
	jq('#deletecustomer').click( function () {
    	var d = table.rows('.selected').data()[0];
    	$http.delete('http://localhost:8083/Enet3/rest/customer/' + d.id)
	        .success(function (data, status, headers) {
	        	table.row('.selected').remove().draw( false );
	        })
	        .error(function (data, status, header, config) {
	        });
    });
	
	$scope.updateCustomer = function() {
		$http.put('http://localhost:8083/Enet3/rest/customer/' + $scope.customerid, {
			"name" : $scope.customername,
    		"email" : $scope.customeremail,
    		"contact" : $scope.customernumber,
    		"address" : $scope.customeraddress
		})
	        .success(function (data, status, headers, config) {
	        	jq('#modalClose').click();
	        	$scope.showCustomers();
	        })
	        .error(function (data, status, header, config) {
	        });
	};
    
 
});

sampleApp.controller('ShowWebsitesController', function($scope, $cookies, $http) {
	var table;
	$scope.tab = 1;
	$scope.websitename = '';
	$scope.websiteid = '';
	$scope.websites={};
	$scope.selectTab = function(setTab) {
		$scope.tab = setTab;
	}
	
	$scope.isSelected = function(checkTab) {
		return $scope.tab === checkTab;
	}
	$http.defaults.headers.common['Auth'] = $cookies.Auth;
	$scope.createWebsite = function() {
		$http.post('http://localhost:8083/Enet3/rest/website/add',{
    		"name" : $scope.websitename,
    	}).success( function(response) {
    		$scope.websitename = '';
    		$scope.message = 'Website added !!'; 
    	});
	};
	$scope.showWebsites = function() {
		
		$http.get('http://localhost:8083/Enet3/rest/website/all').success( function(response) {
		      $scope.websites = response; 
		      
		      table = jq('#websiteTable').DataTable( {
			        "data": $scope.websites,
			        destroy: true,
			        "columns": [
			            { "data": "id" },
			            { "data": "name" }
			        ]
			    } );
		      
		      jq('#websiteTable tbody').on( 'click', 'tr', function () {
			        if ( jq(this).hasClass('selected') ) {
			            jq(this).removeClass('selected');
			        }
			        else {
			            jq('tr.selected').removeClass('selected');
			            jq(this).addClass('selected');
			            $scope.websitename = table.rows('.selected').data()[0].name;
			            $scope.websiteid = table.rows('.selected').data()[0].id;
			        }
			    } );
			 
		});
	}
	
	jq('#deletewebsite').click( function () {
    	var d = table.rows('.selected').data()[0];
    	$http.delete('http://localhost:8083/Enet3/rest/website/' + d.id)
	        .success(function (data, status, headers) {
	        	table.row('.selected').remove().draw( false );
	        })
	        .error(function (data, status, header, config) {
	        });
    });
	
	$scope.updateWebsite = function() {
		$http.put('http://localhost:8083/Enet3/rest/website/' + $scope.websiteid, {
			"name" : $scope.websitename,
		})
	        .success(function (data, status, headers, config) {
	        	jq('#modalClose').click();
	        	$scope.showWebsites();
	        })
	        .error(function (data, status, header, config) {
	        });
	};
 
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

sampleApp.controller('BranchController',function($scope, $http, $cookies) {
   
    $scope.tab = 1;
	 $scope.selectTab = function(setTab) {
		 $scope.tab = setTab;
	 };
	 $scope.isSelected = function(checkTab) {
		 return $scope.tab === checkTab;
	 };
	 
	 $scope.place = '';
	 $scope.address = '';
	 $scope.pincode = '';
	 $scope.contact = '';
	 $scope.managerid = '';
	 $scope.branches = [];
	 $scope.branchid = '';
	 
	 $http.defaults.headers.common['Auth'] = $cookies.Auth;
	 $scope.addBranch = function() {
		 $http.post('http://localhost:8083/Enet3/rest/branch/add',{
	    		"place" : $scope.place,
	    		"address" : $scope.address,
	    		"pincode" : $scope.pincode,
	    		"contact" : $scope.contact,
	    		"branchmanagerId" : $scope.managerid
	    	}).success( function(response) {
	    		$scope.place = '';
	    		$scope.address = '';
	    		$scope.pincode = '';
	    		$scope.contact = '';
	    		$scope.managerid = '';
	    		$scope.message = 'Branch added !!'; 
	    	});
	 };
	 
	 $scope.updateBranch = function() {
		 $http.put('http://localhost:8083/Enet3/rest/branch/' + $scope.branchid, {
			 "place" : $scope.place,
	    		"address" : $scope.address,
	    		"pincode" : $scope.pincode,
	    		"contact" : $scope.contact,
	    		"branchmanagerId" : $scope.managerid
			})
		        .success(function (data, status, headers, config) {
		        	jq('#modalClose').click();
		        	$scope.showBranches();
		        })
		        .error(function (data, status, header, config) {
		        });
	 };
		
		$scope.showBranches = function() {
			
			$http.get('http://localhost:8083/Enet3/rest/branch/all').success( function(response) {
			      $scope.branches = response; 
			      
			      table = jq('#branchTable').DataTable( {
				        "data": $scope.branches,
				        destroy: true,
				        "columns": [
				            { "data": "id" },
				            { "data": "place" },
				            { "data": "pincode" },
				            { "data": "contact" },
				            { "data": "address" },
				            { "data": "branchmanagerId" }
				        ]
				    } );
			      
			});
			
			jq('#branchTable tbody').on( 'click', 'tr', function () {
		        if ( jq(this).hasClass('selected') ) {
		            jq(this).removeClass('selected');
		        }
		        else {
		            jq('tr.selected').removeClass('selected');
		            jq(this).addClass('selected');
		            var d = table.rows('.selected').data()[0];
		            $scope.place = d.place;
		    		$scope.address = d.address;
		    		$scope.pincode = d.pincode;
		    		$scope.contact = d.contact;
		    		$scope.managerid = d.branchmanagerId;
		    		$scope.branchid = d.id;
		        }
		    } );
		}
		
		
		jq('#deletebranch').click( function () {
	    	var d = table.rows('.selected').data()[0];
	    	$http.delete('http://localhost:8083/Enet3/rest/branch/' + d.id)
		        .success(function (data, status, headers) {
		        	table.row('.selected').remove().draw( false );
		        	$scope.place = '';
		    		$scope.address = '';
		    		$scope.pincode = '';
		    		$scope.contact = '';
		    		$scope.managerid = '';
		        })
		        .error(function (data, status, header, config) {
		        });
	    });
	 
});
})();
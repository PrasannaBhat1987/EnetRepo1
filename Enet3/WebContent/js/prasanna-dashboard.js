/**
 * 
 */
(function(){

		
var sampleApp = angular.module('enetDashboardApp', ['ngCookies','ngResource']);
var jq = $.noConflict();
var selectedRaoId;
var viewRaoClicked;
  
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
      when('/dashboard', {
          templateUrl: 'templates/show-dashboard.html',
          controller: 'DashboardController'
	  }).
	  when('/rao', {
	      templateUrl: 'templates/show-rao.html',
	      controller: 'ShowRaoController'
	  }).  
      otherwise({
        redirectTo: '/dashboard'
      });
  }]);

sampleApp.controller('ShowUsersController', function($scope, $http, $cookies, $resource, $window) {
	
	$scope.tab = 1;
	$scope.name = '';
	$scope.email = '';
	$scope.contact = '';
	$scope.address = '';
	$scope.role = '';
	$scope.manager = '';
	$scope.assignbranch = false;
	$scope.branchId = '';
	$scope.branches = [];
	$scope.id = '';
	
	$scope.managerName = '';
    $scope.managerEmail = '';
    $scope.managerContact = '';
    $scope.managerAddress = '';
	 
	var table;
	
	$scope.selectTab = function(setTab) {
		$scope.tab = setTab;
	}
	
	$scope.isSelected = function(checkTab) {
		return $scope.tab === checkTab;
	}
	
	if(!$cookies.Auth) {
		$window.location.href = 'http://localhost:8083/Enet3/#login';
	} 
	
	$http.defaults.headers.common['Auth'] = $cookies.Auth;
	
	$scope.getUserDetails = function() {
		$http.get('http://localhost:8083/Enet3/rest/representative/'+$scope.id+'/details').success( function(response) {
		      var manager = response.manager;
		      if(manager) {
		    	  $scope.managerName = manager.name;
			      $scope.managerEmail = manager.email;
			      $scope.managerContact = manager.contact;
			      $scope.managerAddress = manager.address;
		      }
		     
		      var branch = response.branch;
		      if(branch) {
		    	  $scope.branchPlace = branch.place;
			      $scope.branchPincode = branch.pincode;
			      $scope.branchContact = branch.contact;
			      $scope.branchAddress = branch.address;
		      }
		      
		      
		      $scope.raos = response.raos;
		      
		});
	};
	
	
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
    	}).error(function(errorInfo) {
    		jq('#error').click();
			$scope.errorCode = errorInfo.status;
			$scope.errorMessage = errorInfo.message;
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
		            var d = table.rows('.selected').data()[0];
		            $scope.id = d.id;
		            $scope.name = d.name;
		    	    $scope.email = d.email;
		    	    $scope.contact = d.contact;
		    	    $scope.address = d.address;
		    	    $scope.role = d.role;
		    	    $scope.manager = d.managerId;
		    	    $scope.branchId = d.branchId;
		        }
		    } );
			 
	};
	
	jq('#deleteuser').click( function () {
    	var d = table.rows('.selected').data()[0];
    	$http.delete('http://localhost:8083/Enet3/rest/representative/' + d.id)
	        .success(function (data, status, headers) {
	        	table.row('.selected').remove().draw( false );
	        })
	        .error(function(errorInfo){
	        	jq('#error').click();
				$scope.errorCode = errorInfo.status;
				$scope.errorMessage = errorInfo.message;
	    	});
    });
	
	
	$scope.updateUser = function() {
		$scope.assignbranch = false;
		
		$http.put('http://localhost:8083/Enet3/rest/representative/' + $scope.id, {
			"name" : $scope.name,
    		"email" : $scope.email,
    		"contact" : $scope.contact,
    		"address" : $scope.address,
    		"role" : $scope.role,
    		"managerId" : $scope.manager,
    		"branchId" : $scope.branchId
		})
	        .success(function (data, status, headers, config) {
	        	jq('#modalClose').click();
	        	$scope.showUsers();
	        })
	        .error(function(errorInfo){
	        	alert(errorInfo.message);
//	        	jq('#modalClose').click();
//	    		jq('#error').click();
//				$scope.errorCode = errorInfo.status;
//				$scope.errorMessage = errorInfo.message;
	    	});
	};
	
	$scope.assignBranchToUser = function() {
		if($scope.assignbranch) {
			$http.get('http://localhost:8083/Enet3/rest/branch/all').success( function(response) {
			      $scope.branches = response; 
			});
			jq('.assignBranch').show();
		} else {
			jq('.assignBranch').hide();
		}
	};
});
 
sampleApp.controller('CreateRaoController', function($scope, $http, $cookies, $window) {
	
	if(!$cookies.Auth) {
		$window.location.href = 'http://localhost:8083/Enet3/#login';
	} 
	
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
	$scope.item='';
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
	$scope.raoid = '';
	$scope.total = 0;
	$scope.disableConfirm = false;
	$scope.status = '';
	jq('#raoid').hide();
	
	$http.defaults.headers.common['Auth'] = $cookies.Auth;
	$http.get('http://localhost:8083/Enet3/rest/website/all').success( function(response) {
	      $scope.websites = response; 
	});  
	$http.get('http://localhost:8083/Enet3/rest/branch/all').success( function(response) {
	      $scope.branches = response; 
	});
	
	if(selectedRaoId) {
		$http.get('http://localhost:8083/Enet3/rest/rao/' + selectedRaoId).success( function(response) {
			$scope.websiteId = response.websiteId;
			$scope.customerid = response.customerId;
			 $scope.branchId = response.branchId;
			 $scope.representativeid = response.userId;
			$scope.orderDate = response.orderDate;
			 $scope.description = response.description;
			$scope.lineItems = response.lineItems;
			 $scope.shippingaddress = response.deliveryAddress;
			$scope.orderNumber = response.orderNumber;
			$scope.raoid = selectedRaoId; 
			jq('#raoid').show();
			$scope.fetchCustomer();
			$scope.fetchRepresentative();
			if(viewRaoClicked) {
				$scope.tab = 6;
				$scope.disableConfirm = true;
			}
			$scope.getWebsiteName();
			$scope.status = response.status;
			selectedRaoId = null;
		});
	}
	
	
	
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
	
	
	
	$scope.clearFields = function() {
		$scope.websiteId = '';
		$scope.customerid = '';
		 $scope.branchId = '';
		 $scope.representativeid = '';
		$scope.orderDate = '';
		 $scope.description = '';
		$scope.lineItems.splice(0, $scope.lineItems.length);
		 $scope.shippingaddress = '';
		$scope.orderNumber = '';
		$scope.raoid = '';
		
		jq('.custominfo').hide();
		jq('.repdetails').hide();
	}
	
	$scope.createRao = function() {
		if($scope.raoid && $scope.raoid >0) {
			$scope.updateRao();
		} else {
			$scope.createNewRao();
		}
	}
	
	$scope.createNewRao = function() {
		$scope.disableConfirm = true;
		$http.post('http://localhost:8083/Enet3/rest/rao/add',{
    		"websiteId" : $scope.websiteId,
    		"customerId" : $scope.customerid,
    		"branchId" : $scope.branchId,
    		"userId" : $scope.representativeid,
    		"orderDate" : $scope.orderDate,
    		"status" : "New",
    		"description" : $scope.description,
    		"lineItems" : $scope.lineItems,
			"deliveryAddress" : $scope.shippingaddress,
			"orderNumber" : $scope.orderNumber,
			"total" : $scope.total
    	}).success( function(response) {
    		
			alert("New RAO created with ID : " + response);
			$scope.raoid = response;
			jq('#raoid').show();
    	}).error( function(errorInfo) {    		
    		$scope.disableConfirm = false;
    	});
	}
	
	$scope.updateRao = function() {
		$scope.disableConfirm = true;
		$http.put('http://localhost:8083/Enet3/rest/rao/'+$scope.raoid,{
    		"websiteId" : $scope.websiteId,
    		"customerId" : $scope.customerid,
    		"branchId" : $scope.branchId,
    		"userId" : $scope.representativeid,
    		"orderDate" : $scope.orderDate,
    		"status" : $scope.status,
    		"description" : $scope.description,
    		"lineItems" : $scope.lineItems,
			"deliveryAddress" : $scope.shippingaddress,
			"orderNumber" : $scope.orderNumber,
			"total" : $scope.total
    	}).success( function(response) {
    		
			alert("RAO updated");
    	}).error( function(errorInfo) {    		
    		$scope.disableConfirm = false;
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
		}).error(function (errorInfo){
			jq('#error').click();
			$scope.errorCode = errorInfo.status;
			$scope.errorMessage = errorInfo.message;
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
		}).error(function (errorInfo){
			jq('#error').click();
			$scope.errorCode = errorInfo.status;
			$scope.errorMessage = errorInfo.message;
		});
	};
});

sampleApp.controller('CreateCustomersController', function($scope, $http, $cookies, $window) {
	
	
	if(!$cookies.Auth) {
		$window.location.href = 'http://localhost:8083/Enet3/#login';
	} 
	
	
    $scope.message = 'This is Show orders screen';
    $scope.customerid = '';
    $scope.customername = '';
    $scope.customeremail = '';
    $scope.customernumber = '';
    $scope.customeraddress = '';
    $scope.tab = 1;
    $scope.customers = [];
    $scope.raos = [];
    var table;
    
    $scope.selectTab = function(setTab) {
		$scope.tab = setTab;
	}
	
	$scope.isSelected = function(checkTab) {
		return $scope.tab === checkTab;
	}
	
    $http.defaults.headers.common['Auth'] = $cookies.Auth;
    
    $scope.getCustomerDetails = function() {
    	$http.get('http://localhost:8083/Enet3/rest/customer/'+$scope.customerid +'/details').success( function(response) {
		      $scope.raos = response.raos; 
    	});
	};
    
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
			            if(d) {
			            	$scope.customerid = d.id;
				            $scope.customername = d.name;
				    	    $scope.customeremail = d.email;
				    	    $scope.customernumber = d.contact;
				    	    $scope.customeraddress = d.address;
			            }
			            
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

sampleApp.controller('ShowWebsitesController', function($scope, $cookies, $http, $window) {
	
	if(!$cookies.Auth) {
		$window.location.href = 'http://localhost:8083/Enet3/#login';
	} 
	
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
	
	$scope.getWebsiteDetails = function() {
		$http.get('http://localhost:8083/Enet3/rest/website/'+ $scope.websiteid + '/details').success( function(response) {
		      $scope.raos = response.raos; 
		});
	}
	
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
		jq('#modalClose').click();
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

sampleApp.controller('LogoutController',function($scope, $window, $http, $cookies) {
        
    if(!$cookies.Auth) {
		$window.location.href = 'http://localhost:8083/Enet3/#login';
	}
    
    $scope.logout = function() {
    	$window.location.href = 'http://localhost:8083/Enet3';
    	$cookies.Auth = '';
    }
    $scope.tab = 1;
	 $scope.selectTab = function(setTab) {
		 $scope.tab = setTab;
	 };
	 $scope.isSelected = function(checkTab) {
		 return $scope.tab === checkTab;
	 };
});

sampleApp.controller('BranchController',function($scope, $http, $cookies, $window) {
   
	if(!$cookies.Auth) {
		$window.location.href = 'http://localhost:8083/Enet3/#login';
	}
	
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
	 
	 $scope.branchManagerName = '';
	 $scope.branchManagerEmail = '';
	 $scope.branchManagerContact = '';
	 $scope.branchManagerAddress = '';
	 $scope.branchEmployees = [];
	 $scope.raos = [];
	 
	 
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
	    	}).error(function(errorInfo){
	    		jq('#error').click();
				$scope.errorCode = errorInfo.status;
				$scope.errorMessage = errorInfo.message;
	    	});
	 };
	 
	 $scope.getBranchDetails = function() {
		 $http.get('http://localhost:8083/Enet3/rest/branch/'+$scope.branchid+'/details').success( function(response) {
			 
			 var manager = response.branchmanager;
			 if(manager) {
				 $scope.branchManagerName = manager.name;
				 $scope.branchManagerEmail = manager.email;
				 $scope.branchManagerContact = manager.contact;
				 $scope.branchManagerAddress = manager.address;
			 }
			 
			 $scope.branchEmployees = response.branchEmployees;
			 if(!$scope.branchEmployees) {
				 $scope.branchEmployees = [];
			 }
			 
			 $scope.raos = response.raos;
			 if(!$scope.raos) {
				 $scope.raos = [];
			 }
			 
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
		        .error(function(errorInfo){
		        	alert(errorInfo.message);
//		        	jq('#modalClose').click();
//		    		jq('#error').click();
//					$scope.errorCode = errorInfo.status;
//					$scope.errorMessage = errorInfo.message;
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
		        .error(function(errorInfo){
		    		jq('#error').click();
					$scope.errorCode = errorInfo.status;
					$scope.errorMessage = errorInfo.message;
		    	});
	    });
	 
});



sampleApp.controller('EditProfileController',function($scope, $http, $cookies, $window) {
	$scope.message='hellooooooooo';
	$scope.showpass = false;
	$scope.newPassword = '';
	$scope.password='';
	$scope.confirmPassword = '';
	$scope.invalidPassword = false;
	$scope.name = '';
	$scope.email = '';
    $scope.contact = '';
    $scope.address = '';
	
	$scope.canShow = function(){
		return $scope.newPassword !== $scope.confirmPassword;
	};
	
	$http.defaults.headers.common['Auth'] = $cookies.Auth;
	
	$scope.editProfile = function() {
			$http.put('http://localhost:8083/Enet3/rest/representative/' + $cookies.userId, {
				"name" : $scope.name,
	    		"contact" : $scope.contact,
	    		"address" : $scope.address,
	    		"password" : $scope.password,
	    		"newPassword" : $scope.newPassword,
			})
		        .success(function (data, status, headers, config) {
		        	$scope.newPassword = '';
		        	$scope.password='';
		        	$scope.confirmPassword = '';
		        	$scope.showpass = false;
		        	jq('#modalCloseMain').click();
		        })
		        .error(function (data, status, header, config) {
		        	$scope.invalidPassword = true;
		        	alert('Unable to update data. Please try again later.')
		        });
	};
	
		$http.get('http://localhost:8083/Enet3/rest/representative/'+ $cookies.userId).success( function(response) {
		      $scope.name = response.name;
		      $scope.email = response.email;
		      $scope.contact = response.contact;
		      $scope.address = response.address;
		      
		});
});






sampleApp.controller('DashboardController',function($scope, $http, $cookies, $window) {
	
	$http.defaults.headers.common['Auth'] = $cookies.Auth;
	
	$http.get('http://localhost:8083/Enet3/rest/representative/getNumbers').success( function(response) {
	      $scope.numbers = response; 
	      
	      jq("#chartContainer").CanvasJSChart({ 
	  		title: { 
	  			text: "Users" 
	  		}, 
	  		data: [ 
	  		{ 
	  			type: "doughnut", 
	  			toolTipContent: "{label}: {y}",
	  			dataPoints: [ 
	  				{ label: "Admin",       y: $scope.numbers.admins}, 
	  				{ label: "Manager",        y: $scope.numbers.managers}, 
	  				{ label: "Representatives",y: $scope.numbers.reps} 
	  			] 
	  		} 
	  		] 
	  	});
	});
	
	
	$http.get('http://localhost:8083/Enet3/rest/rao/getNumbers').success( function(response) {
	      $scope.numbers = response; 
	
	jq("#chartContainer1").CanvasJSChart({ 
  		title: { 
  			text: "Orders" 
  		}, 
  		data: [ 
  		{ 
  			type: "doughnut", 
  			toolTipContent: "{label}: {y}",
  			dataPoints: [ 
  				{ label: "In Progress",       y: $scope.numbers.inProgress}, 
  				{ label: "Completed",        y: $scope.numbers.completed}, 
  				{ label: "New",			y: $scope.numbers.created} 
  			] 
  		}]
	});
	});
	
	
});


sampleApp.controller('ShowRaoController',function($scope, $window, $http, $cookies) {
    var table;
    selectedRaoId = null;
    
    var options = {
    	   year: "numeric", month: "short",  day: "numeric"
    	};
    
    
    $scope.showRaos = function() {
    	$http.get('http://localhost:8083/Enet3/rest/rao/all').success( function(response) {
	  	      $scope.raos = response; 
	  	      
	  	      table = jq('#raoTable').DataTable( {
	  		        "data": $scope.raos,
	  		        destroy: true,
	  		        "columns": [
	  		            { "data": "id" },
	  		            { "data": "orderNumber" },
	  		            { "data": "orderDate", 
	  		            	"render": function (data) {
	  		                    return new Date(data).toLocaleDateString("en-US", options);
	  		                }
	  		            },
	  		            { "data": "status" },
	  		            { "data": "total" }
	  		        ]
	  		    } );
	  	      
	  	      jq('#raoTable tbody').on( 'click', 'tr', function () {
	  		        if ( jq(this).hasClass('selected') ) {
	  		            jq(this).removeClass('selected');
	  		        }
	  		        else {
	  		            jq('tr.selected').removeClass('selected');
	  		            jq(this).toggleClass('selected');
	  		            var d = table.row( this ).data();
	  		            //var d = table.rows('.selected').data()[0];
	  		            selectedRaoId = d.id;
	  		        }
	  		    } );
	  	});
    };
    
    if(!$cookies.Auth) {
		$window.location.href = 'http://localhost:8083/Enet3/#login';
	} 
    
    $scope.editRao = function() {
    	if(selectedRaoId) {
    		viewRaoClicked = false;
        	$window.location.href = 'http://localhost:8083/Enet3/dashboard.html#/createrao';
    	}
    };
    
    $scope.viewRao = function() {
    	if(selectedRaoId) {
    		viewRaoClicked = true;
        	$window.location.href = 'http://localhost:8083/Enet3/dashboard.html#/createrao';
    	}
    };
    
    jq('#deleteRao').click( function () {
    	var d = table.rows('.selected').data()[0];
    	$http.delete('http://localhost:8083/Enet3/rest/rao/' + d.id)
	        .success(function (data, status, headers) {
	        	table.row('.selected').remove().draw( false );
	        	selectedRaoId = null;
	        	
	        })
	        .error(function (data, status, header, config) {
	        });
    });
   
});

})();
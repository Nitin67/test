var app = angular.module("app", ["ngResource", "ngRoute"])
	.constant("apiUrl", "/api")
	.config(["$routeProvider", function($routeProvider) {
		return $routeProvider.when("/patient/:id", {
			templateUrl: "/views/patient_detail",
			controller: "PatientDetailCtrl"
		}).when("/doctor/:id", {
			templateUrl: "/views/doc_detail",
			controller: "DoctorDetailCtrl"
	    }).when("/pharmacist/:id", {
			templateUrl: "/views/pharmacist_detail",
			controller: "PharmacistDetailCtrl"
	    }).when("/patient", {
			templateUrl: "/views/patient_form",
			controller: "PatientCtrl"
		}).when("/doctor", {
			templateUrl: "/views/doctor_form",
			controller: "DoctorCtrl"
	    }).when("/pharmacist", {
			templateUrl: "/views/pharmacist_form",
			controller: "PharmacistCtrl"
	    }).otherwise({
			templateUrl: "/views/doctor_form",
			controller: "DoctorCtrl"
		});
	}
	]).config([
	"$locationProvider", function($locationProvider) {
		return $locationProvider.html5Mode({
			enabled: true,
			requireBase: false
		}).hashPrefix("!"); // enable the new HTML5 routing and history API
		// return $locationProvider.html5Mode(true).hashPrefix("!"); // enable the new HTML5 routing and history API
	}
]);

// the global controller
app.controller("AppCtrl", ["$scope", "$location", function($scope, $location) {
	// the very sweet go function is inherited by all other controllers
	$scope.go = function (path) {
		$location.path(path);
	};
}]);

// the list controller
app.controller("ListCtrl", ["$scope", "$resource", "apiUrl", function($scope, $resource, apiUrl) {
	var Celebrities = $resource(apiUrl + "/celebrities"); // a RESTful-capable resource object
	$scope.celebrities = Celebrities.query(); // for the list of celebrities in public/html/main.html
}]);



// the create pharmacist controller
app.controller("PharmacistCtrl", ["$scope", "$resource", "$timeout", "apiUrl", function($scope, $resource, $timeout, apiUrl) {
	// to save a celebrity
	$scope.save = function() {
		var CreateCelebrity = $resource(apiUrl + "/pharmcist"); // a RESTful-capable resource object
		CreateCelebrity.save($scope.pharmcist); // $scope.celebrity comes from the detailForm in public/html/detail.html
		$timeout(function() { $scope.go('/'); }); // go back to public/html/main.html
	};
}]);

// the create patient controller
app.controller("PatientCtrl", ["$scope", "$resource", "$timeout", "apiUrl", function($scope, $resource, $timeout, apiUrl) {
	$scope.save = function() {
		var CreatePatient = $resource(apiUrl + "/patient"); // a RESTful-capable resource object
		CreatePatient.save($scope.patient); // $scope.celebrity comes from the detailForm in public/html/detail.html
		$timeout(function() { $scope.go('/'); }); // go back to public/html/main.html
	};
}]);

// the create doctor controller
app.controller("DoctorCtrl", ["$scope", "$resource", "$timeout", "apiUrl", function($scope, $resource, $timeout, apiUrl) {
	$scope.save = function() {
		var CreateDoctor = $resource(apiUrl + "/doctor"); // a RESTful-capable resource object
		CreateDoctor.save($scope.doctor); // $scope.doctor comes from the detailForm in public/html/detail.html
		$timeout(function() { $scope.go('/'); }); // go back to public/html/main.html
	};
}]);

// the patient get controller
app.controller("PatientDetailCtrl", ["$scope", "$resource", "$routeParams", "$timeout", "apiUrl", function($scope, $resource, $routeParams, $timeout, apiUrl) {
	var ShowPatient = $resource(apiUrl + "/patient/:id", {id:"@id"}); // a RESTful-capable resource object
	if ($routeParams.id) {
		$scope.patient = ShowPatient.get({id: $routeParams.id});
	}

}]);

// the doctor get controller
app.controller("DoctorDetailCtrl", ["$scope", "$resource", "$routeParams", "$timeout", "apiUrl", function($scope, $resource, $routeParams, $timeout, apiUrl) {
	var ShowDoctor = $resource(apiUrl + "/doctor/:id", {id:"@id"}); // a RESTful-capable resource object
	if ($routeParams.id) {
		$scope.patient = ShowDoctor.get({id: $routeParams.id});
	}

}]);
// the pharmacist get controller
app.controller("PharmacistDetailCtrl", ["$scope", "$resource", "$routeParams", "$timeout", "apiUrl", function($scope, $resource, $routeParams, $timeout, apiUrl) {
	var ShowPharmacist = $resource(apiUrl + "/pharmacist/:id", {id:"@id"}); // a RESTful-capable resource object
	if ($routeParams.id) {
		$scope.patient = ShowPharmacist.get({id: $routeParams.id});
	}

}]);
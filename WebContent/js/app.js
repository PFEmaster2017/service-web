	var app = angular.module("Myapp", ['pascalprecht.translate']);
	app.config(function ($translateProvider  ) {
	 
		  //default language
		  $translateProvider.preferredLanguage('en');
			//fallback language if entry is not found in current language
			//$translateProvider.fallbackLanguage('es');
		  //load language entries from files
			$translateProvider.useStaticFilesLoader({
				prefix: 'traduction/', //relative path Eg: /assets/languages/
				suffix: '.json' //file extension
			});
		});
	app.run(function($rootScope, Language) {
		   //make the service available     
		   $rootScope.Language = Language;
		});
	
	
	app.controller("VoitureController", function($scope, $http, $translate) {

   
		 $scope.changeLanguageparam = function (key) {
			    $translate.use(key);
			  };

		
			
		//$scope.navTitle = 'All Voitures';
		$scope.operation="";
		$scope.isSaveDisabled = true;
		$scope.isDeleteDisabled = true;
		
		//pour la liste des voitures
		var response = $http.get('http://localhost:8585/Fin/rest/voiture/findAll');
		response.success(function(data) {
			$scope.voitures = data;
			console.log("[main] # of items: " + data.length)
			angular.forEach(data, function(element) {
				console.log("[main] voiture: " + element.name);
			});
		})
		response.error(function(data, status, headers, config) {
			alert("AJAX failed to get data, status=" + status);
		})
		
		//pour obtenir la voiture selectioner suivant l id
		$scope.getvoiture = function(numEnrg) {
			var response = $http.get('http://localhost:8585/Fin/rest/voiture/find/'+ numEnrg );
			
			response.success(function(data) {
				console.log("getvoiture data: " + angular.toJson(data, false));
				$scope.voiture = data;
				$scope.operation="update";
				$scope.isSaveDisabled = false;
				$scope.isDeleteDisabled = false;
		    })
			
			response.error(function(data, status, headers, config) {
				alert("AJAX failed to get data, status=" + status);
			})
		};
		
		//pour rechercher voiture depuis liste
		$scope.searchvoiture = function(descriptionVoiture) {
			$scope.navTitle = 'Search Critére';
			
			var response = $http.get('http://localhost:8585/Fin/rest/voiture/findDes/' + descriptionVoiture);
			response.success(function(data) {
				$scope.voitures = data;
				console.log("[main] # of items: " + data.length)
				angular.forEach(data, function(element) {
					console.log("[main] voiture: " + element.name);
				});
			})
			
			response.error(function(data, status, headers, config) {
				alert("AJAX failed to get data, status=" + status);
			})
		};
		
		//lors de click resetsearch et apres le save
		$scope.clearForm = function() {
			$scope.voiture = {
					personName:'',
					personPrenom:'',
					personCin:'',
					depot:'',
					descriptionVoiture:'',
				source:''
			};
		}
		//lors de click addnew
		$scope.addNewVoiture = function(element) {
			$scope.operation="create";
			$scope.clearForm();
			
			$scope.isSaveDisabled = false;
			$scope.isDeleteDisabled = true;
		}
		//le button save
		$scope.savevoiture = function(numEnrg) {
			$scope.jsonObj = angular.toJson($scope.voiture, false);
			console.log("[update] data: " + $scope.jsonObj);

			if ($scope.operation == "update") {
				var response = $http.put('http://localhost:8585/Fin/rest/voiture/edit' , $scope.jsonObj);
				response.success(function(data, status) {
					console.log("Inside update operation..." + angular.toJson(data, false) + ", status=" + status);
					$scope.resetSearch();
			    });
				
				response.error(function(data, status) {
					alert("AJAX failed to get data, status=" + status);
				})
			} else if ($scope.operation == "create") {
				var response = $http.post('http://localhost:8585/Fin/rest/voiture/plus', $scope.jsonObj);
				response.success(function(data, status) {
					console.log("Inside create operation..." + angular.toJson(data, false) + ", status=" + status);
					$scope.resetSearch();
			    });
				
				response.error(function(data, status) {
					alert("AJAX failed to get data, status=" + status, $scope);
				})	
			}
		};
		//le button delete
		$scope.deletevoiture = function(numEnrg) {
			 var result = confirm('vous etes suur???');
	    	 if (result==true){

			var response = $http.delete('http://localhost:8585/Fin/rest/voiture/delet/' +numEnrg);
			response.success(function(data, status) {
				console.log("Inside delete operation..." + angular.toJson(data, false) + ", status=" + status);
				$scope.resetSearch();
			});
				
			response.error(function(data, status) {
				alert("AJAX failed to get data, status=" + status);
			})
	    	 }
		};
		
		//le button reset search
		$scope.resetSearch = function(name) {
			//var app = this;
			var response = $http.get('http://localhost:8585/Fin/rest/voiture/findAll');
			response.success(function(data) {
				$scope.voitures = data;
				//$scope.$apply();
				console.log("[resetSearch] # of items: " + data.length)
		    });
			
			response.error(function(data, status, headers, config) {
				alert("AJAX failed to get data, status=" + status);
			})
			$scope.operation="";
			$scope.isSaveDisabled = true;
			$scope.isDeleteDisabled = true;
			$scope.navTitle = 'All Voitures';
			$scope.descriptionVoiture = '';
			$scope.clearForm();
		};
	});	
	app.factory('Language', function ($translate) {
	    //add the languages you support here. ar stands for arabic
	    var rtlLanguages = ['ar'];

	    var isRtl = function() {
	        var languageKey = $translate.proposedLanguage() || $translate.use();
	        for (var i=0; i<rtlLanguages.length; i+=1) {
	            if (languageKey.indexOf(rtlLanguages[i])>-1) 
	                return true;
	        }
	        return false; 
	    };

	    //public api
	    return {
	        isRtl: isRtl
	    };
});
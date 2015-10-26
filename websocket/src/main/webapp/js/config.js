'use strict';
angular.module('iot')
       .config(['$stateProvider','$urlRouterProvider',
                function ($stateProvider, $urlRouterProvider) {
    	   			$urlRouterProvider.otherwise( function($injector, $location) {
    	   				var $state = $injector.get("$state");
                            $state.go("iot");
                        }); 
                        $stateProvider.state('iot', {
	                        url: "/iot",
	                        views : { 
                                main: {
                                	templateUrl:"views/home.html"
                                }
	                        },  
	                        data: {
	                        	pageTitle: 'Internet das Coisas'
	                        }
                        }) 
                }   
       ]);
'use strict';

angular.module('iot')
	   .controller('HMeterController', ['$scope', '$state', '$websocket', function($scope, $state, $websocket){
			$scope.sendCalibrate = function (){
            	$websocket.restartCalibration();
            };
		    var callback = {
				beginCalibrate: function() {
	            	$scope.title = "Calibrando...";
	            	$scope.message = "Aguarde...";
	            	$scope.cssColor = "yellow";
	            	$scope.$apply();
	            },
	            calibrate: function(height) {
	            	$scope.title = "Equipamento calibrado!";
	            	$scope.message = "Altura Máxima: " + height;
	            	$scope.cssColor = "blue";
	            	$scope.$apply();
	            },
	            keepAlive: function(height) {
	            	$scope.title = "Olá visitante!";
	            	$scope.message = "Sua altura: " + height;
	            	$scope.cssColor = "green";
	            	$scope.$apply();
	            },
	            hwLost: function(){
	            	$scope.title = "Impossível medir a altura";
	            	$scope.message = "Fora de Serviço";
	            	$scope.cssColor = "red";
	            	$scope.$apply();
	            },
	            connectionClosed: function() {
	            	$scope.title = "Impossível medir a altura!";
	            	$scope.message = "Fora de Serviço";
	            	$scope.cssColor = "red";
	            	$scope.$apply();
	            },
	            waitForVisitors: function() {
	            	$scope.title = "Aguardando por visitantes!";
	            	$scope.message = "Aguardando...";
	            	$scope.cssColor = "gray";
	            	$scope.$apply();
	            }
			};
		    angular.element(document).ready(function() {
		    	$scope.title = "Procurando o serviço...";
            	$scope.message = "Aguarde...";
            	$scope.cssColor = "yellow";
				try {
					$websocket.init(callback);
				} catch(e) {
					console.log(e);
					$scope.title = e.message;
	            	$scope.message = "#ERRO";
	            	$scope.cssColor = "red";
				}
				$scope.$apply();
			});
	   }]);
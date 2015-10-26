'use strict';

angular.module('iot')
	   .service('$websocket', [function(){
			return {
				hmeter: null,
				callbacks: null,
				calibration: new Number("0.0"),
				restartCalibration: function() {
					this.calibration = new Number("0.0");
				},
				init: function(callbacks) {
					this.callbacks = callbacks;
					this.hmeter = new WebSocket("ws://localhost:8080/websocket/hmeter");
					this.hmeter.onopen = this.onOpen;
					this.hmeter.onclose = this.onClose;
					this.hmeter.onmessage = this.onMessage;
					this.hmeter.socketHandler = this;
					var _this = this;
					angular.element(document).bind("HSHC", function(evt) {
						_this.hmeter.send("CLIENT:WEB");
					});
					angular.element(document).bind("BGNC", function(evt) {
						_this.sendCalibrateMessage();
					});
				},
	            beginCalibrate: function() {
	            	this.callbacks.beginCalibrate();
	            },
	            calibrate: function(height) {
	            	this.callbacks.calibrate(height);
	            	this.calibration = new Number(height.replace(",", "."));
	            },
	            keepAlive: function(height) {
	            	if(new Number(height.replace(",", ".")) < this.calibration - 0.03) {
	            		this.callbacks.keepAlive((this.calibration - new Number(height.replace(",", "."))).toFixed(2));
	            	} else {
	            		this.callbacks.waitForVisitors();
	            	}
	            },
	            hwLost: function(){
	            	this.callbacks.hwLost();
	            },
	            connectionClosed: function() {
	            	this.callbacks.connectionClosed();
	            },
	            sendCalibrateMessage: function() {
	            	this.hmeter.send("WEB:Calibrate");
	            },
				onOpen: function() {
					console.log("conexão aberta...");
					angular.element(document).trigger('HSHC');
	            },
	            onClose: function() {
	            	console.log("conexão encerrada...");
	            	this.socketHandler.connectionClosed();
	            },
	            onMessage: function(evt) {
	            	console.log("mensagem recebida: " + evt.data);
	            	var protocol = evt.data.split(":");
	            	if("BeginCalibrate" == protocol[0]) {
	            		this.socketHandler.beginCalibrate();
	            	} else if("Calibrate" == protocol[0]) {
	            		this.socketHandler.calibrate(protocol[1]);
	            	} else if("KeepAlive" == protocol[0]) {
	            		if(this.socketHandler.calibration == null || this.socketHandler.calibration == 0) {
	            			angular.element(document).trigger('BGNC');
	            		} else {
	            			this.socketHandler.keepAlive(protocol[1]);
	            		}
	            	} else if("HWLost" == protocol[0]) {
	            		this.socketHandler.hwLost();
	            	} else if("HWAvailable" == protocol[0]) {
	            		this.socketHandler.sendCalibrateMessage();
	            	}
	            }
			};  
	   }]);
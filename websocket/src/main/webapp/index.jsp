<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<!DOCTYPE html>
<html ng-app="iot">
    <head>
        <title page-title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="stylesheet" href="css/style.css" />
        
        <!-- bower:js -->
        <script src="bower_components/jquery/dist/jquery.js"></script>
        <script src="bower_components/angular/angular.js"></script>
        <script src="bower_components/angular-ui-router/release/angular-ui-router.js"></script>
        <!-- endbower -->
        
        <script src="js/script.js"></script>
        <script src="js/config.js"></script>
        <script src="js/services.js"></script>
        <script src="js/controllers.js"></script>
    </head>
    
    <body  ng-controller="HMeterController">
    	<!--[if lt IE 7]>
		<p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade
		    your browser</a> to improve your experience.</p>
		<![endif]-->
		
		<!-- Wrapper-->
		
		<div ui-view="main"></div>
		
		
		<!-- End wrapper-->
    </body>
</html>
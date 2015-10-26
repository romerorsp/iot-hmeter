# IOT HMeter
This project was created to proof the concept of the IoT paper written to the Brazilian Java Magazine. It relies on a maven main module wich contains two important chindren:
  - hmeter - The standalone application that will likely run in a Raspberry PI in comunication with an Arduino to control an Ultrassonic sensor
  - websocket - The view layer that will connect the device to the web interface that will show the informations as an Height Meter device. It should be deployed as an webapp on either tomcat, jetty, jboss or whatever Java web container you may try.

### Version
1.0.0-SNAPSHOT

### Tech

This project makes youse of:

* [AngularJS] - The presentation layer makes high use of AngularJS!
* [WebSocket] - The Java WebSocket Specification was also used in order to connect the device to the web clients.
* [JArduino] - This is a super cool framework that turns your Arduino into a Java micro controller.

### Installation

In order to build this application you need Maven:

```sh
$ mvn clean install
```
Then you'll have two artifacts on hands: hmeter.jar and websocket.war. You can run the hmeter in whatever JVM you want by using:

```sh
$ java -jar hmeter.jar
```

Finally you can deploy the websocket.war in any Java web container. It should be running at http://localhost:8080/websocket

### Development

Want to contribute? Great! You can help me to turn the build a more configurable environment and make some code review.

License
----

The Unlicensed


**This code was originally written in order to demonstrate how nice IoT can be in Java! There is a papper written to the Brazilian Java Magazine that explains the entire code in this project.**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does it's job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [WebSocket]: <https://jcp.org/en/jsr/detail?id=356>
   [JArduino]: <https://github.com/romerorsp/JArduino.git>
   [AngularJS]: <http://angularjs.org>
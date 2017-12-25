# radius
A futile attempt on RADIUS server for school work. It makes use of TinyRadius-1.0, in fact, a little bit too much.
All credit goes TinyRadius project despite its flaws.

## Compiling
Compile the TestServer.java and TestClient.java as below:-
```shell
$ javac -cp classpath/*:. TestServer.java
$ javac -cp classpath/*:. TestClient.java
```
## Running
```shell
$ java -cp classpath/*:. org.tinyradius.test.TestServer
$ java -cp classpath/*:. org.tinyradius.test.TestClient hostname sharedsecret username password
```

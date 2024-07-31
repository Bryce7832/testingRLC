<h1>Introduction</h1>

I am using the resource leak checker on an open source project to document and fix any resource leaks. Credit to open source project: https://drive.google.com/file/d/1U06FWrkHdsU9t8vuzGmlwzEqjrFp8Qu2/view?usp=sharing

## Reported Errors:
```
src/stv6/http/HttpSocket.java:31: error: [required.method.not.called] @MustCall method close may not have been invoked on new
Socket(host, port) or any of its aliases.
                this(new Socket(host, port));
                     ^
  The type of object is: java.net.Socket.
  Reason for going out of scope: regular method exit
src/stv6/http/HttpServer.java:93: error: [required.method.not.called] @MustCall method close may not have been invoked on sock
or any of its aliases.
                        Socket sock = getSocket();
                               ^
  The type of object is: java.net.Socket.
  Reason for going out of scope: regular method exit
src/stv6/http/HttpServer.java:124: error: [required.method.not.called] @MustCall method close may not have been invoked on
new ServerSocket(port) or any of its aliases.
                serverskt = new ServerSocket(port);
                            ^
  The type of object is: java.net.ServerSocket.
  Reason for going out of scope: regular method exit
src/stv6/database/MysqlDatabase.java:727: error: [required.method.not.called] @MustCall method close may not have been invoked
on getConnection() or any of its aliases.
                        conn = getConnection();
                                            ^
  The type of object is: java.sql.Connection.
  Reason for going out of scope: regular method exit
src/stv6/episodes/managers/UpnpManager.java:104: error: [required.method.not.called] @MustCall method close may not have been
invoked on new FileReader(pmsConf) or any of its aliases.
                BufferedReader r = new BufferedReader(new FileReader(pmsConf));
                                                      ^
  The type of object is: java.io.FileReader.
  Reason for going out of scope: possible exceptional exit due to r.readLine() with exception type java.io.FileNotFoundException
src/stv6/episodes/managers/FileSystemManager.java:156: error: [required.method.not.called] @MustCall method close may not have
been invoked on new FileInputStream(cfgFile.getCanonicalFile()) or any of its aliases.
                                    new FileInputStream(cfgFile.getCanonicalFile())
                                    ^
  The type of object is: java.io.FileInputStream.
  Reason for going out of scope: possible exceptional exit due to reader.readLine() with exception type java.io.IOException
src/stv6/STHandlerManager.java:37: warning: [synchronization] attempt to synchronize on an instance of a value-based class
                synchronized(isStatic) {
                ^
src/stv6/STHandlerManager.java:59: warning: [synchronization] attempt to synchronize on an instance of a value-based class
                synchronized(isStatic) {
                ^
src/stv6/templating/TemplateReader.java:24: error: [required.method.not.called] @MustCall method close may not have been invoked
on new FileReader(source) or any of its aliases.
                        new FileReader(source)
                        ^
  The type of object is: java.io.FileReader.
  Reason for going out of scope: regular method exit
src/stv6/handlers/FileHandler.java:77: error: [required.method.not.called] @MustCall method close may not have been invoked on
new FileReader(theFile) or any of its aliases.
                                BufferedReader reader = new BufferedReader(new FileReader(theFile));
                                                                           ^
  The type of object is: java.io.FileReader.
  Reason for going out of scope: possible exceptional exit due to reader.readLine() with exception type java.io.FileNotFoundException
src/stv6/handlers/FileHandler.java:108: error: [required.method.not.called] @MustCall method close may not have been invoked on is
or any of its aliases.
        InputStream is = new FileInputStream(file);
                    ^
  The type of object is: java.io.InputStream.
  Reason for going out of scope: regular method exit
src/stv6/handlers/util/CoverHandler.java:140: error: [required.method.not.called] @MustCall method close may not have been invoked
on is or any of its aliases.
        InputStream is = new FileInputStream(file);
                    ^
  The type of object is: java.io.InputStream.
  Reason for going out of scope: regular method exit
src/stv6/Profile.java:490: warning: [synchronization] attempt to synchronize on an instance of a value-based class
                synchronized(reloading) {
                ^
src/stv6/Profile.java:509: warning: [synchronization] attempt to synchronize on an instance of a value-based class
                        synchronized(reloading) {
                        ^
src/stv6/Profile.java:581: warning: [synchronization] attempt to synchronize on an instance of a value-based class
                synchronized(reloading) {
                ^
src/stv6/Profile.java:352: error: [required.method.not.called] @MustCall method close may not have been invoked on
new FileReader(file) or any of its aliases.
                        BufferedReader in = new BufferedReader(new FileReader(file));
                                                               ^
  The type of object is: java.io.FileReader.
  Reason for going out of scope: possible exceptional exit due to in.readLine() with exception type java.io.FileNotFoundException
11 errors
5 warnings
```
### Documentation of Fixed Errors
https://docs.google.com/document/d/1znZtlyAOC_G_SvF5lBX0GZlY-msnTnDdj0YDyql4C8I/edit?usp=sharing

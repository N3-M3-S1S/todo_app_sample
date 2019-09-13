# Todo app sample

A sample todo app with a  REST server written in Java and a Android client written in Kotlin.

Server uses Maven, Spring-webmvc, Spring-data-jpa, Hibernate and MySQL as a database.
Android client uses Retrofit, Architecture Components and coroutines.

## Usage

#### Server

Go to [app.properties](todo_server/src/main/resources/app.properties) file and change values of this variables to to match your MySQL server's setup.
<pre>
jdbc.url
jdbc.username
jdbc.password
</pre>
Then build the server with your IDE or manually using `mvn package` command and install it to a web server (i.e. Tomcat). 

#### Client
Go to [TodoApi.kt](todo_client_android/data/src/main/java/com/nemesis/todo_client/data/api/TodoApi.kt) and change value of `serverUrl`  variable to match app's server url. Then build and install the client to your device with Android Studio.




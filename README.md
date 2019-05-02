# BCM Test interview
## Install the program

Maven and JDK 1.8+ are required.

Clone the project and run:
```
mvn clean install
```

## Launch the program

First write a database.properties file with the following content:

```
MYSQL_DB_URL=
MYSQL_DB_USERNAME=
MYSQL_DB_PASSWORD=
```

Then launch the compiled jar:
```
java -cp pam-1.0-SNAPSHOT.jar bcm.test.batch.Launcher
```


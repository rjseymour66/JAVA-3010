# JAVA-3010

# Establishing a Connection To A Database
As you know, there are many ways to connect different data sources to an application. A data source can be a file system, (which we have been working with for the past couple of weeks),  piped output from another application, or a database (which we will work with this week)

There are several ways of connecting to a relational database base using just Java class methods:

- `DriverManager`: This fully implemented class connects an application to a data source, which is specified by a database URL. When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within the class path. 

- `DataSource`: This interface is allows details about the underlying data source to be transparent to your application. A `DataSource` object's properties are set so that it represents a particular data source. 

**Note**: We will be using the `DriverManager` class instead of the `DataSource` class because it is easier to use and will allow you to get your database connection up and running with (hopefully) a minimum of effort.

Keep in mind that connecting directly to a database using Java native calls restricts database access to the desktop application.  There are other architectures that support distributed databases that are accessed via web browsers.  However, because the focus of this course is desktop applications, it is appropriate to cover the basics of connecting using native database connection classes.  

#  Using the DriverManager Class

The DriverManager class is used to manage more than one database driver and database connection.  In fact, it can handle many connections to different databases in a single application.  If this type of class sounds familiar to you, it's because it is a utility class and as such, contains static utility methods to manage database connections.  Several other Java classes are used in support of the DriverManager class.  These classes are instantiated for each individual database connection.

The `Connection` class will contain a single database connection url. It will store the name of the database server, the connection port, and several other connection values.

The `Properties` class will contain the connection properties such as user name and password for a single database `Connection` class object.  

Connecting to a relational database with the DriverManager class starts with calling the static method, `DriverManager.getConnection`:

The following method, `getConnection`, establishes a database connection to a MySQL database using properties from the `Properties` class instance:

```java
public Connection getConnection() throws SQLException {

// Create a Properties object which contains a user name and password to access the database
Properties connectionProps = new Properties();
    connectionProps.put("user", this.userName);
    connectionProps.put("password", this.password);
   
// Create a database Connection object which used the connection properties object created above
Connection dbConnection = 
               DriverManager.getConnection("jdbc:" + this.dbms + "://" + this.serverName +
                   ":" + this.portNumber + "/", connectionProps);
 
    System.out.println("Connected to database");
    return dbConnection;
}
```
The method `DriverManager.getConnection` establishes a database connection. This method requires a database URL, which varies depending on your DBMS. The following is an examples ofa  database URL:

1. MySQL: jdbc:mysql://localhost:3306/, where localhost is the name of the server hosting your database, and 3306 is the port number

2. **Note**: This URL establishes a database connection with the Java DB Embedded Driver. Java DB also includes a Network Client Driver, which uses a different URL.

This method specifies the user name and password required to access the DBMS with a `Properties` object.


# Setting Up The Database
In this section we will look at the code to work with the tables for the relational database.

## Creating the tables
There are a number of ways you can implement the code to create the tables directly within your application.  For our application, we will have a utility class with several static methods that initialize the tables, as well as dropping tables and resetting tables.

Intializing the tables refers to creating the table structure - i.e. what columns and data types will the tables contain.  For our application, we will want to match up the columns and data types to the data members in the data model classes. The method below will create 4 tables with the following names:

- CLASSROOMS
- COURSES
- STUDENTS
- FACULTY

The method uses a loop to loop through an array of pre-defined database statements which are passed as commands, to the database.  The pre-defined command strings are stores in the database initialization class along with the methods that will use them.  The strings are listed here for completeness but please refer to the sample code for better readability.

```sql
 CREATE TABLE classrooms (roomnumber varchar(5), roomtype varchar(10), capacity integer, PRIMARY KEY (roomnumber)),
        CREATE TABLE courses (courseid varchar(7), coursename varchar(100), room varchar(5), PRIMARY KEY (courseid)),
        CREATE TABLE students (studentid integer, name varchar(100), address varchar(100), dateofbirth DATE , dataofgraduation DATE, gpa float, PRIMARY KEY (studentid)),
        CREATE TABLE faculty (facultyid varchar(9), name varchar(100), address varchar(100), dateofbirth DATE , dataofhire DATE salary float, PRIMARY KEY (facultyid))
```

## Executing the intialization method:
The code snippet below is from the sample project and demonstrates creating the tables.  Refer to the sample project for the complete method implementation.

```sql
for (int i = 0; i < createTables.length; i++) {
            try {
                st.execute(createTables[i]);
                LOGGER.info("Table: " + createTables[i] + " created");
            } catch (SQLException sqlException) {
                LOGGER.severe(sqlException.getMessage());
            }
        }
```

## Other Methods
There are several other methods in the database initialization class with detailed comments that describe what the methods do.  Please read through the sample code and come with questions to the chat!


# Storing Data InThe Database
The following code snippet demonstrates how to store the data from the classroom data container, into the database. As you can see, the command string has several sections.

The first section is referred to as the "insert into" section.  Here is where you specify the table in the database that you will be inserting data.  

The second second is the list of columns which will be updated with new data from the object.

The third section is the "values" section which contains the actual data from the object. Note that there should be exactly a one to one correspondence between the columns listes and the data values.

SQL commands must be structured in a very formal way and the format must match what the database is expecting or the command will fail. The statement below may look somewhat complicated and can be which can lead to errors in the format. Fortunately there are ways to ensure that the format is always correct.  However, since this isn't a database course and we are only touching on the topic, we will leave that to the database courses.  For now, just be sure that your format is correct.  As you can see, the column names and column values are separated by commas and each section is enclosed in parentheses.

```java
String command = "INSERT INTO uml.classrooms (roomnumber, roomtype, capacity)"
                        + "VALUES ('" + room.getRoomNumber() + "','"
                        + room.getTypeOfRoom() + "','" + room.getCapacity() + "')";
// Execute the statement object
insertStatement.executeUpdate(command);
```

Please refer to the updated ClassroomIO.java in the sample project for the complete code that will store the data from our data containers into the database.


# Retrieving Data From The Database
In this section we will look at the SQL statements to retrieve the data from the Database and populate the array lists for dsiplay in the application.

There are many formats a database query can take.  How you format the query depends on what data you want to retrieve.  Below is a small sample of some database retrieval querys.

The  code snippet below demonstrates how to retrieve all columns of data for all rows from the database classroom table.   The asterisk means "all columns".  

`SELECT * FROM CLASSROOM;`

The  code snippet below demonstrates how to retrieve all columns of data from the database classroom table but limit the results to only those entries that have a room capacity greater than 30.

`SELECT * FROM CLASSROOM WHERE ROOM_CAPACITY > 30;`

The code snippet below  demonstrates how to retrieve a single columns of data from the database classroom table and limit the results to only those entries that have a room capacity greater than 30.

`SELECT ROOM_NUMBER  FROM CLASSROOM WHERE ROOM_CAPACITY > 30;`

The code snippet below  demonstrates how to retrieve two columns of data from the database classroom table, limit the results to only those entries that have a room capacity greater than 30, and sort the results by room capacity in ascending order.

`SELECT ROOM_NUMBER, ROOM_CAPACITY  FROM CLASSROOM WHERE ROOM_CAPACITY > 30 ORDER BY ROOM_CAPACITY ASC;`

Retrieving data from the database is done in a similar fashion to inserting the data.  We can create a command string and then execute a database query command:

```java
String command = " SELECT ROOM_NUMBER, ROOM_CAPACITY  FROM CLASSROOM WHERE ROOM_CAPACITY > 30 ORDER BY ROOM_CAPACITY ASC;";
```

Once the command to retrieve the data is stored as a string, execute the query command to retrieve the data:
```java
results = queryStatement.executeQuery(query);
```

### Storing the Data In the Array List

After the query runs and the results are stored in the result set, loop through the result set and store the data into the data container array list.  The following code snippet shows how to loop through the result set and store the data.

```java
// Parse the result set and recreate the classroom           
while (results.next()) {               
     String roomnumber = results.getString("roomnumber");               
     String roomtype = results.getString("roomtype");               
     int capacity = Integer.parseInt(results.getString("capacity"));                               
     Classroom room = new Classroom();               
      room.setRoomNumber(roomnumber);               
      room.setTypeOfRoom(roomtype);               
      room.setCapacity(capacity);
      // Store in the data container               
      classroomDataContainer.getListOfClassrooms().add(room);           
}
```
Please refer to the updated ClassroomIO.java in the sample project for the complete code that will store the data from our data containers into the database.

# Setting the Path to the Java Libraries

## Library Requirements

Downloading and installing the Java Developers Toolkit (the JDK) using the default installation procedure provided by Oracle, will automatically register the Java compile time and run time libraries in the registry of computers running Microsoft Windows and most versions of Linux.  (This class doesn't support work on Apple products but most likely it works the same way as on a MS Windows OS).    However, the environment variables that need to be set to locate the libraries after installation, is not done automatically.  In order to locate the libraries when running from a command window, a path to the root of the JDK must be set.  The root is commonly referred to as "JAVA_HOME".  

## Linux

On a computer running a Linux OS, JAVA_HOME and a path to the libraries can be set several ways, depending on the version of Linux.  Typically, a pair of commands is used to set these variables, a set command and an export command.  You will commonly see these commands combined into a single "export" command as follows:

`export JAVA_HOME=/usr/java/jdk1.5.0_07/bin/java`

`export PATH=$PATH:/usr/java/jdk1.5.0_07/bin`

On a Linux OS running inside a bash shell, these commands are usually part of a login profile.  Each user that logs in to the OS, has their own copy of a login script which runs at login and executes all the commands they will need for their session, including those that are necessary to locate the Java libraries. However, if the commands aren't in this login script, they can be entered for a single command window session using a format similar to the above.

## Windows

On a computer running a MS Windows OS, JAVA_HOME and a path to the libraries can be set using environment variables.  Different versions of the Windows OS will set these environment variables in different ways but the most common way is through the system properties menu. From there, clicking the "Advanced" button (or tab dedending on the version of the OS),  will allow you to specify the JAVA_HOME and PATH locations.

# Compiling and Running From the Command Line 

## Compiling An Application

To compile all of the source code required to run an application from a Java .class file, enter the javac command and the name of the file.  If there is more than one file in the application (which is most commonly the case), you can use the wildcard "*" to compile all of the files from the root of the source code folder:

`>javac *.java`

## Running An Application

To run an application from a Java .class file, enter the java command and the name of the file containing the main method.  If there are arguments to the application, they are also entered on the command line:

`>java MyApp arg1 arg2`

Note that when a class contains a package statement, you must specify the path to the package as part of the class name. For example, if HelloWorld.java is in the helloworld package, you would use this format:

`>java helloworld.HelloWorld`

# Creating an Executable JAR File 

## Creating an Executable JAR File

The most common way to distribute an application written in Java is to create an executable Java Archive File (JAR).  When we compile our application in Netbeans, it automatically creates this file for us and places it in the dist folder of our project.  It builds the file using a script which is contained in the project called "build.xml".
To create an executable JAR file when compiling from the command line, we need to provide a file called a "Manifest" file and include the Main Class (the class with the main method), in the Manifest file. When you create a jar file, the  jar command also creates the manifest file inside the META-INF folder as MANIFEST.MF but doesn't create the Main-Class entry which is required to make the jar executable. You can add this functionality in two ways, by  either providing a self created Manifest file or by specifying an entry point into the application using the "-e" jar option during compile time. Here is an example of creating an executable jar file specifying the main entry point with the -e option:

>`java jar -cvfe HelloWorld.jar HelloWorld HelloWorld.class`

If you provide an external Manifest file than you need to use the jar -m option to include that manifest file inside the jar.  Here is an example of adding a class with a main method, to the manifest file:

>`java jar -cvfm HelloWorld.jar MANIFEST.MF HelloWorld.class`

When Netbeans creates an executable jar file, by default, it inserts the entry point into the application based on what we have specified as the class containing the main method, into the Manifest file.  You can overrride this behavior in Netbeans by customizing the default build script which Netbeans uses to build the executable jar file.   However, this is beyond the scope of our class so for this semester, we will stick with the default build script.

## Explanation of the other command line options

`-c` Create a new jar file

`-v` Create a new jar file using verbose output. This option will list all the files going into the jar as they are added.

`-f` The name of the jar file being created

## Running an Application From an Executable JAR File

Here is an example of running HelloWorld from the jar file:

>`java -jar HelloWorld.jar`

# Writing Use Documentation 

Your application came out great, has all the features your users need, is well-written and hopefully,  totally bug free. But if your users don't know how it works or how to navigate the various functions and windows, it won't get used!  Although you are not required to submit documentation for this application, keep in mind the following:

Some guidelines:

- Don't assault your new user with an unwieldy use document.  If you do, it won’t be read. Remember, this is a user driven, event driven application. And although you wrote great, bug free code, chances are, you didn't cover all possibilities.  And leave it to a user to find just that one thing that you didn't plan for!
- Do provide "cookbook" style instructions for navigating through the menus.  If you provide a useful help panel built into the application,  a user can refer to it for helpful guidance directly inside the app. 
- A picture is worth a thousand words!  If users can see what they should be seeing on the display, it can be very helpful!

# CLASSPATH and $PATH env variable

`${CLASSPATH}`  is a way to point to java classes when running an application in the JVM.  The JVM automatically knows where to look for the Java built in language classes but it can't find user defined classes or third party libraries without a pointer.  If you are on a linux system and execute the following command, "echo `$CLASSPATH`",  you will get a listing of all the classes contained in jar files that the path currently can find.  When you create a new application jar file, you can add it to the classpath by appending the location to the `CLASSPATH` environment variable.  There are several ways to refer to the classpath variable. `${CLASSPATH}`  is used inside scripts and denotes a script variable called CLASSPATH.  Outside of scripts, you can omit the curly braces. When appending your jar path to the classpath variable, you can use %CLASSPATH% as well. 

The PATH environment contains the locations for executables that you want to use. It's a convenience variable so that no matter where you are in the file system, if you execute an application, you don't have to specify the complete path to the executable. For example, when you install the Java JDK, it automatically adds the location of the Java executables to your path variable so you can build and run from anywhere. Usually whenever you install an application, the installation will put the path into the path variable automatically.  If it doesn't, then you have to add it manually.  $PATH works the same way as `$CLASSPATH`.  It's a way to access the value of PATH - sort of like a variable. In the example above, the export statement is adding your path to the current value of PATH and making it globally available to the operating system.  

An easy way to remember the differene between the two is - the PATH is the path to executables, the CLASSPATH is the path to Java classes that have been put into executable jar files.

Actually these are all on topic!

#Java Server

This project is an implementation of a simple web server in Java, designed to pass a suite of acceptance tests, [Cob Spec](https://github.com/8thlight/cob_spec).

##Why did I make this?

Writing this Java server is one of several projects normally completed by 8th Light apprentices (of which I am currently one). Although there are too many objectives to list out, the more significant takeaways include:
- a better understanding of the HTTP protocol, especially its formatting requirements
- the importance of SOLID and package principles for software design
- an opportunity to learn and use a new language (this was my first time working with Java)

##Installation

To install and run the server:
```
git clone [clone-url]
cd java-server
mvn package
java -jar target/javaserver-1.0-SNAPSHOT.jar -d [public directory]
```

*the public directory option is required and must exist on your system

##Tests

Unit tests for this project are written with JUnit and can be run in an IDE with support for JUnit or using maven with `mvn test`.

There is also a suite of acceptance tests, available in a separate repository [here](https://github.com/8thlight/cob_spec).

##Issues

If you run into any problems, please open an issue on GitHub with the relevant details.

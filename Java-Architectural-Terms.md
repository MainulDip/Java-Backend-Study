### Servlet and Servlet Container (Web Server):
Servlet is a generic interface, implementer of this interface handles `requests`, processes them and reply back with a `response`. It runs on web server (servlet container). Collection input from a user through an HTML form, query records from a database, and create web pages dynamically all are done using Servlet in Java.

With HttpServlet, other web technologies like JSP (`JavaServer Pages`), Spring MVC, etc. also relay on `Servlet` interface.

```java
// an HttpServlet implementation to handle post request
@WebServlet(name = "FormServlet", urlPatterns = "/calculateServlet")
public class FormServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, 
      HttpServletResponse response)
      throws ServletException, IOException {

        String height = request.getParameter("height");
        String weight = request.getParameter("weight");

        try {
            double bmi = calculateBMI(
              Double.parseDouble(weight), 
              Double.parseDouble(height));
            
            request.setAttribute("bmi", bmi);
            response.setHeader("Test", "Success");
            response.setHeader("BMI", String.valueOf(bmi));

            request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
        } catch (Exception e) {
           request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
        }
    }

    private Double calculateBMI(Double weight, Double height) {
        return weight / (height * height);
    }
}
```
Guide: https://www.baeldung.com/intro-to-servlets

### Web Server vs Application Server:
application servers have full support for the Java EE spec, whereas web servers (a.k.a Servlet Container) support a small subset of that functionality

<img src="./images/java-web-server-vs-application-server.jpg"/>

* Application Servers Can Contain
    - Servlets
    - JSP (Java Server Pages)
    - Expression Language
    - WebSocket
    - JTA (Java Transaction API)
    - Batch
    - CDI (Contexts and Dependency Injection) 
    - Bean Validation
    - EJB (Jakarta Enterprise Beans)
    - JPA (Java Persistence API )
    - JMS (Java Message Service)
    - JSON-P
    - JavaMail
    - JAX-RS (Java/Jakarta RESTful API Web Services )
    - JAX-WS (creating and consuming Simple-Object-Access-Protocol/SOAP API for XML Web Services )

* Web Server (aka Servlet Container) Contains a Subset of the Application Server Specification
    - Servlets
    - JSP (Java Server Pages)
    - Expression Language
    - WebSocket
    - JTA
    - JASPIC

* Application Server Example
    - `Apache TomEE` (open source), full application server built on top of the standard Apache Tomcat
    - `Oracle WebLogic`
    - `WebSphere` by IBM
    - `WildFly` (Open Source) by Red Hat
    - `Apache Geronimo` (Open Source)
    - `GlassFish` (Open Source) sponsored by Oracle

* Web Server Example
    - `Apache Tomcat` (Open Source) most popular
    - `Jetty` (Open Source) developed by Eclipse Foundation, lightweight and fast. it’s so lightweight, it can be easily embedded in devices, frameworks, and application servers. Naturally, the project is open-source and you can contribute to it here. Used by Apache ActiveMQ, Eclipse, Google App Engine, Apache Hadoop and Atlassian Jira.


Guide: https://www.baeldung.com/java-servers.


### Applet ():
Java applets were small applications written in the Java, or another programming language that compiles to Java bytecode, and delivered to users in the form of Java bytecode. The user launched the Java applet from a web page, and the applet was then executed within a Java virtual machine (JVM) in a process (sandbox) separate from the web browser itself. A Java applet could appear in a frame of the web page, a new application window, a program from Sun called `appletviewer`, or a stand-alone tool for testing applets. Later Applet Was Deprecated in Java 9 and onwards and dropped support form all browsers around 2015.

History: After alpha release in 1995, JDK 1.0 was released in `23rd January 1996`

When Internet (became available in 1993 for General Public) is gaining popularity, Browser wars started. Netscape (later Mozilla) has 86% and IE has 10% market share as users can choose/install their own browser. Then Microsoft began integrating IE within it's Windows OS and bundling deals with OEMs. So by 1999 it had 99% of the market. That time IE was also available for Apple's OS. Later IE was announced Windows only support.

Back to Applet, SUN Micro-System (maker of Java) and Netscape (later Mozilla) jointly developed `SWING`, this `SWING` components are produced by Java itself and are called `Applet` is platform-independent and provie some additional features which were not in AWT (Abstract Window Toolkit, an interface for limited GUI implementation in Java) technology.


### JDK, Java SE, OpenJDK, Java EE to Jakarta EE:
Java's first 2 release (1.0 and 1.1) was only JDK (1996/97). Then J2SE from 1.2/1998 to 5.0/2004. From Java 6 to Current (21) are `Java SE`.

JDK: `Java Development Kit`, which includes an implementation of the `Java SE` specs, plus tools to write Java apps and a `JVM` to run/test. These tools include a compiler called `Javac`.

JRE: `Java Runtime Environment`, a subset of JDK without development tools and compiler (`javac`). It provides JVM and other components to run compiled java application.

Note => Every JDK and every JRE contains a JVM

Java SE: `Java Platform, Standard Edition`, which provide A set of specifications published by Oracle Corp. This is the core Java programming platform. It contains all of the libraries and APIs `specification` that any Java programmer should learn (java.lang, java.io, java.math, java.net, java.util, etc...). It was formerly known as Java 2 Platform, Standard Edition (J2SE)

OpenJDK: it is an open-source implementation of the JDK. The OpenJDK project published `source code` to an implementation of the `Java SE` specifications. The `project` doesn't provide binaries or installers. Based on the `OpenJDK source code` several vendors publish `binaries or installers`, ie, Amazon, Microsoft, Red Hat, Oracle, BellSoft, IBM.

java ME: Micro Edition for Old Mobile Devices and embedded systems such as set-top boxes.

Java EE: Enterprise Edition (mainly for Web). Built on top of Java SE and extends functionality. Java EE provides libraries for database access (JDBC, JPA), remote method invocation (RMI), messaging (JMS), web services, XML processing, and defines standard APIs for Enterprise JavaBeans, servlets, portlets, Java Server Pages, etc...

Jakarta EE: After Oracle transfer Java SE to `Eclipse Foundation`, they change its name as `Jakarta EE` to bypass Oracle's copyright issue [Jakarta is the capital of Indonesia, which is in the Java Island]  



https://stackoverflow.com/questions/2857376/difference-between-java-se-ee-me
https://stackoverflow.com/questions/73902305/whether-java-se-is-the-same-as-jdk

### Spring @Been:
A bean is an object that is instantiated, assembled, and managed by a Spring IoC container (` IoC: Inversion of Control`). It's like seed in other terms, but rather than we creating, we supplied the requirements and Spring will do the rest of job, like creating the object/s in a specific point where everything is ready and manage it's lifecycle. 
```java
@Bean
CommandLineRunner initDatabase(EmployeeRepository repository) {
    return args -> {
        log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
        log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
    };
}
```

https://stackoverflow.com/questions/17193365/what-in-the-world-are-spring-beans

### Inversion-of-Control (IoC) and Dependency Injection (DI):
The Dependency-Injection (DI) pattern is a more specific version of IoC pattern.

The term Inversion of Control (IoC) originally meant any sort of programming style where an overall framework or run-time controlled the program flow (lifecycle of everything inside).

Inversion of Control (IoC) means that objects do not create other objects on which they rely to do their work. Instead, they get the objects that they need, from an outside source (for example, an xml configuration file or DI).

Dependency Injection (DI) means that this is done without the object intervention, usually by a framework component that passes constructor parameters and set properties.

https://stackoverflow.com/questions/6550700/inversion-of-control-vs-dependency-injection
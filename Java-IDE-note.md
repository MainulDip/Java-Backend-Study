### Overviews:
Let's work with free version of Intellij IDEA (community edition). As Idea community has no support for Spring or the STS (Spring Tool Suite), vs-code can be used to utilize the missing part.

### IntelliJ IDEA
Generate class methods based on declared properties.

### VSCode
To work in vs-code for spring boot development, we need
=> Spring Boot Extension Pack (contains below extensions and some other helper extensions)
    - Spring initializer
    - Spring Boot Tools
    - Spring Boot Dashboard
=> Java Development Kit (JDK)
=> Extension Pack for Java

=> New project can be created using STS extension of vs-code through `command palette` -> `Spring Initializer` to generate Maven or Gradle project. Modules/Dependencies can be added later down the road form pom.xml or build.gradle file. There will be an ide suggestion just right of the dependencies tag in the pom.xml saying `add spring boot starters ...`. Click there and add new dependencies.

Docs => https://code.visualstudio.com/docs/java/java-spring-boot


### Spring ResponseEntity<?>
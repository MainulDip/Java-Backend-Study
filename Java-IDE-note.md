### Overviews:
Let's work with free version of Intellij IDEA (community edition). As Idea community has no support for Spring or the STS (Spring Tool Suite), vs-code can be used to utilize the missing part.

### IntelliJ IDEA :
Generate class methods based on declared properties.
=> JPA Buddy 
=> JPA Support

### VSCode:
To work in vs-code for spring boot development, we need
=> Spring Boot Extension Pack (contains below extensions and some other helper extensions)
    - Spring initializer
    - Spring Boot Tools
    - Spring Boot Dashboard
=> Java Development Kit (JDK)
=> Extension Pack for Java

=> New project can be created using STS extension of vs-code through `command palette` -> `Spring Initializer` to generate Maven or Gradle project. Modules/Dependencies can be added later down the road form pom.xml or build.gradle file. There will be an ide suggestion just right of the dependencies tag in the pom.xml saying `add spring boot starters ...`. Click there and add new dependencies.

Docs => https://code.visualstudio.com/docs/java/java-spring-boot


### Spring ResponseEntity<?>:


### Eclipse:
- AutoComplete => add `abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.` at preference -> java -> editor -> content assist -> auto activation trigger for java

- Terminal => stop color `invert` form window -> preference -> terminal
- Project Explorer => Hide `bloating` elements, follow https://stackoverflow.com/questions/60962386/why-is-eclipse-removing-the-navigator-view.
- Database Connection =>
    - first convert the project to a JPA project (from project `properties` => `project facets`)
    - Use no library and when connecting, add database jdbc driver from the `external-resource` build jar files (will will be available upon build and need to be in pom.xml)
- From project directory, right click and use `run -> maven install` to build the project and run `maven clean` + `maven install` if there is some build error, sometimes cleaning resolves building success  

### Eclipse Themes:
Most used theme in Eclipse is `Dev Style Dark Theme` and `Eclipse Color Theme`.
DevStyle them provide full IDE customization which color theme lacking (only support editor theming, not workbench theming). And color theme has some good theming option.
Its better to customize `Dev Style` theme based on downloaded `color theme`
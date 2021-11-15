Series of folders creating a simple spring security for a web project.
Based on https://www.youtube.com/watch?v=PhG5p_yv0zs&ab_channel=JavaBrains

* SEcurity1
    Empty Web app. goto http://localhost:8080/hello
    - Using controller annoation
    - Requires @ResponseBody for each line in order to return explicit html (Can impreove with @RestControler)
    - TODO add custome error page
    - Build mvn spring-boot:run
    - Test: http://localhost:8080/hello
    - TEst http://localhost:8080/hello/who%20are%20You
    - Test param : http://localhost:8080/hello/p?p=myParam

* Security 1
     Just add actuator / security dependency / developer tools (active restarts on config change)
    - Want to have actuator as a permitAll while the other urls are authentiaced
    - Changed from Controller to RestController to simplify things/. (No @REsponseBody)

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

* Just add security dependency

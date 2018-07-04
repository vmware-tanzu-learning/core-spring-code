# Spring Boot Resources

By default, Spring Boot looks for resources in `classpath:static`, which is
this location.  However `if src/main/webapp` exists it will look in there
first.

Spring Boot is also conveniently configured to map '/' to `index.html`.
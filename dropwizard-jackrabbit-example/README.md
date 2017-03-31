dropwizard-jackrabbit-example
========================

This is an example of using Dropwizard with:

 * Static Resources 
 * Jackrabbit

The example is lent on [Conejo-Example](https://developer.jboss.org/wiki/JBossJackrabbitOracleDBAndRestEasyAnExample).

* mvn clean package
* java -jar target/hello-jackrabbit-*.jar server hello-world.yml


Jackrabbit and Dropwizard
--------------------------------
Create JCR-Node and store data by PUT [http://localhost:8082/api/jcr/var?name=Test](http://localhost:8082/api/jcr/var?name=Test) (put the data to store into the body)

Get JCR-Node and stored data by GET [http://localhost:8082/api/jcr/var?name=Test](http://localhost:8082/api/jcr/var?name=Test) (body should accord to your stored content)
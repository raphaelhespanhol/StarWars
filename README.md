# StarWars
# Created by Raphael Hespanhol - raphael.hespanhol@gmail.com
# Date: 09/29/2019

1. Program Language and tools.
   Java 8.
   Spring Framework 4.2.3
   Spring Boot 2.1.8
   Gradle
   
2. Comilation.
   cd C:\Users\raphahes\git\StarWars
   
   gradle build -x test
   
   Then verify in the folder
   C:\Users\raphahes\git\StarWars\build\libs

3. Deploy.

   Copy the Jar StarWars-X.X.X.jar to following directory.
   
4. Profile configuration.
   
   Spring boot come several way to run jar.
   
   java -jar StarWars-1.0.0.jar
   
   java -Xmx32m -Xss256k -jar StarWars-0.0.1-SNAPSHOT.jar

This is a command line Spring Boot application. It is built using gradle which is part of the source code ditribution.

To build it execute this command from the project root folder (I am using MacOS, so UNIX syntax):

```
./gradlew
```

It will build an uber jar in `build/libs` folder.

To run the application execute this command:

```
java -jar tetris.jar [<input file name>]
```

where `<input file name>` is the desired input file. If you don't put an argument, it will be defaulted to `input.txt` file packed inside the archive. I assume that java 8 is installed on the computer and the `java` executable is in the PATH. Also, I assume that your current folder contains the `tetris.jar` file. If the the jar is located in some other folder, you need to provide path to it.

The program is written in Java and built by Gradle as I mentioned before. All required libraries (Spring Boot and dependencies) are loaded from central maven repository by Gradle and packaged inside uber jar by the Spring Boot plugin.

I used functional patterns in the code where it was critical. I ran it on MacBook Pro. The entire process executed in under 2 seconds including reading/writing from/to file and output to a console. So, I decided that it is unnecessary to further refine the code to remove non functional loops, although it was not optimal.# tetris

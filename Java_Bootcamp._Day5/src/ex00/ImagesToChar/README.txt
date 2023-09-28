Project working directory: ImagesToChar

-To create a directory where the build will be executed, enter the command:
mkdir target

-To build the project, enter the command:
javac -d target -sourcepath src/java src/java/edu/school21/printer/app/Program.java

-To run the program, enter:
java -classpath target edu.school21.printer.app.Program . 0 /Users/aramil/Desktop/APJ/Java_Bootcamp._Day04-1/src/ex00/it.bmp
where:
1. "." - symbol for white pixels,
2. "0" - symbol for black pixels,
3. "/Users/aramil/Desktop/APJ/Java_Bootcamp._Day04-1/src/ex00/it.bmp" - is the absolute path to the file to read.

-To remove the build directory, enter the command
rm -rf target

-Do all
mkdir target
javac -d target -sourcepath src/java src/java/edu/school21/printer/app/Program.java
java -classpath target edu.school21.printer.app.Program . 0 /Users/aramil/Desktop/APJ/Java_Bootcamp._Day04-1/src/ex00/it.bmp
rm -rf target

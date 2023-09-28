Project working directory: ImagesToChar

-To create a directory where the build will be executed, enter the command:
mkdir target

-To build the project, enter the command:
javac -d target -sourcepath src/java src/java/edu/school21/printer/app/Program.java

-Copy resource directory:
cp -R src/resources target/.

-To build the jar file, enter the command:
jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target .

-To run the program, enter:
java -jar target/images-to-chars-printer.jar . 0
where:
1. "." - symbol for white pixels,
2. "0" - symbol for black pixels.

-To remove the build directory, enter the command
rm -rf target


-Do all
mkdir target
javac -d target -sourcepath src/java src/java/edu/school21/printer/app/Program.java
cp -R src/resources target/.
jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target .
java -jar target/images-to-chars-printer.jar . 0
rm -rf target

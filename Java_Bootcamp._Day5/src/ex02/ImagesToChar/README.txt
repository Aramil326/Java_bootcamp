Project working directory: ImagesToChar

-To create a directory in which to build and a directory in which external libraries will be downloaded, enter the command:
mkdir target lib

-To download the libraries, enter the command
curl https://repo1.maven.org/maven2/com/beust/jcommander/1.72/jcommander-1.72.jar -o lib/jcommander-1.72.jar
curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.0/JCDP-4.0.0.jar -o lib/JCDP-4.0.0.jar

-To extract the files and move them from to the target directory, enter the command
cd target && jar xf ../lib/jcommander-1.72.jar && jar xf ../lib/JCDP-4.0.0.jar && rm -rf META-INF && cd ..

-To compile files into target directory
javac -d target -sourcepath src/java -cp lib/\* src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/*.java

-Copy resource directory:
cp -r src/resources ./target/resources

-To build the jar file, enter the command:
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

-To run the program, enter:
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN

-To remove the build directorys, enter the command
rm -rf target lib

-Do all
mkdir target lib
curl https://repo1.maven.org/maven2/com/beust/jcommander/1.72/jcommander-1.72.jar -o lib/jcommander-1.72.jar
curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.0/JCDP-4.0.0.jar -o lib/JCDP-4.0.0.jar
cd target && jar xf ../lib/jcommander-1.72.jar && jar xf ../lib/JCDP-4.0.0.jar && rm -rf META-INF && cd ..
javac -d target -sourcepath src/java -cp lib/\* src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/*.java
cp -r src/resources ./target/resources && \
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN
rm -rf target lib

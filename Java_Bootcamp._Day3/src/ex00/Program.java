import java.util.Scanner;

public class Program {
    public static void main(String[] argc) {

        Signature s = new Signature();
        Scanner scanner = new Scanner(System.in);
        String path = "";

        s.getAllSignatureSamples();
        while (!path.equals("42")) {
            path = scanner.nextLine().trim();
            if (!path.equals("42")) {
                s.run(path);
            }
        }
    }
}
///Users/azathotp/Desktop/image.png
///Users/aramil/Desktop/vcs.xml
///Users/azathotp/Desktop/LOOK_AT_ME_06_03.pdf


///Users/azathotp/Desktop/7 principles of structural programming.md
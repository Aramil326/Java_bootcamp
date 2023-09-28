import java.util.HashMap;

public class Program {
    public static void main(String[] argc) {
        if (argc.length == 2) {
            String filePath1 = "/Users/aramil/Desktop/APJ/Java_Bootcamp._Day02-1/src/ex01/" + argc[0];
            String filePath2 = "/Users/aramil/Desktop/APJ/Java_Bootcamp._Day02-1/src/ex01/" + argc[1];
            FileSimilarity facade = new FileSimilarity();
            System.out.println("Similarity = " + facade.run(filePath1, filePath2));
        }
    }
}

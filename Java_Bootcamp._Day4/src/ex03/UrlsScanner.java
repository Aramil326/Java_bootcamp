import java.io.File;
import java.io.FileNotFoundException;
import java.io.PipedReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class UrlsScanner {
    private final HashMap<Integer, String> urls = new HashMap<>();
    private int[] keys;

    public void readFilesUrls() {
        String filesUrlsPath = "/Users/aramil/Desktop/APJ/Java_Bootcamp._Day03-1/src/ex03/files_urls.txt";
        File filesUrls = Paths.get(filesUrlsPath).toFile();
        try {
            Scanner scanner = new Scanner(filesUrls);
            while (scanner.hasNextLine()) {
                int fileNum = scanner.nextInt();
                String filePath = scanner.nextLine();
                urls.put(fileNum, filePath);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        keys = new int[urls.size()];
        int i = 0;
        for (Integer key : urls.keySet()) {
            keys[i++] = key;
        }
    }

    public HashMap<Integer, String> getUrls() {
        return urls;
    }

    public int[] getKeys() {
        return keys;
    }
}

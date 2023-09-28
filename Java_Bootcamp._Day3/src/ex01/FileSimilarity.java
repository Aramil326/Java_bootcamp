import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;


public class FileSimilarity {
    private final HashMap<String, String> dictionary = new HashMap<>();
    private String[] textFromFile1;
    private String[] textFromFile2;

    private int dictionaryLen = 0;

    public String run(String path1, String path2) {
        getDictionary(path1, path2);
        writeDictionaryToFile();
        return String.valueOf(getSimilarityForTwoFile()).substring(0, 4);
    }

    public void getDictionary(String path1, String path2) {
        textFromFile1 = getTextFromFile(path1).split(" ");
        textFromFile2 = getTextFromFile(path2).split(" ");

        String[] textToDictionary = new String[textFromFile1.length + textFromFile2.length];

        System.arraycopy(textFromFile1, 0, textToDictionary, 0, textFromFile1.length);
        System.arraycopy(textFromFile2, 0, textToDictionary, textFromFile1.length, textFromFile2.length);


        for (String elem : textToDictionary) {
            dictionary.put(elem, "");
        }
    }

    public String getTextFromFile(String path) {
        try {
            File allSignaturesFile = new File(path);
            Scanner scanner = new Scanner(allSignaturesFile);
            String result = "";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim() + " ";
                result = result + line;
            }
            return result;
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(-1);
        }
        return "";
    }

    public void writeDictionaryToFile() {
        try (FileOutputStream outputStream = new FileOutputStream("/Users/aramil/Desktop/APJ/Java_Bootcamp._Day02-1/src/ex01/Dictionary.txt", false)) {
            final PrintStream printStream = new PrintStream(outputStream);

            for (Map.Entry entry : dictionary.entrySet()) {
                printStream.print(entry.getKey().toString() + " ");
                dictionaryLen++;
            }

            printStream.close();
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
    }

    public double getSimilarityForTwoFile() {
        Vector<Integer> frequency1 = getFrequency(textFromFile1);
        Vector<Integer> frequency2 = getFrequency(textFromFile2);

        int numerator = 0;
        for (int i = 0; i < dictionaryLen; i++) {
            numerator += frequency1.elementAt(i) * frequency2.elementAt(i);
        }

        double temp1 = 0;
        double temp2 = 0;

        for (int i = 0; i < dictionaryLen; i++) {
            temp1 += Math.pow(frequency1.elementAt(i), 2);
            temp2 += Math.pow(frequency2.elementAt(i), 2);
        }

        System.out.println(numerator / (Math.sqrt(temp1) * Math.sqrt(temp2)));

        return numerator / (Math.sqrt(temp1) * Math.sqrt(temp2));
    }

    public Vector<Integer> getFrequency(String[] textFromFile) {
        Vector<Integer> frequency = new Vector<>(dictionaryLen);

        for (int i = 0; i < dictionaryLen; i++) {
            frequency.add(0);
        }

        int i = 0;
        for (Map.Entry entry : dictionary.entrySet()) {
            for (String s : textFromFile) {
                if (s.equals(entry.getKey())) {
                    frequency.set(i, frequency.elementAt(i) + 1);
                }
            }
            i++;
        }

        return frequency;
    }
}

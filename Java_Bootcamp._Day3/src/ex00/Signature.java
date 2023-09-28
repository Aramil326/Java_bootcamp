import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Signature {
    private final HashMap<String, String> signatureSamples = new HashMap<>();
    private final String pathToSignatureSamples = "/Users/aramil/Desktop/APJ/Java_Bootcamp._Day02-1/src/ex00/signal.txt";
    private final String pathToResults = "/Users/aramil/Desktop/APJ/Java_Bootcamp._Day02-1/src/ex00/result.txt";
    private String currentSignature;
    private int maxLenOfSignatures = 0;

    public void run(String path) {
        getSignatureFromFile(path);
        writeResult(pathToResults);
    }

    public void getAllSignatureSamples() {
        try {
            File allSignaturesFile = new File(pathToSignatureSamples);
            Scanner scanner = new Scanner(allSignaturesFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] temp = line.split(",");
                signatureSamples.put(temp[0].trim(), temp[1].trim());
                int bytesCount = getBytesCountFromString(temp[1].trim());
                if (maxLenOfSignatures < bytesCount) {
                    maxLenOfSignatures = bytesCount;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(-1);
        }
    }

    private int getBytesCountFromString(String str) {
        String[] temp = str.split(" ");
        return temp.length;
    }

    public void getSignatureFromFile(String path) {
        try (FileInputStream inputStream = new FileInputStream(path)) {
            byte[] bytesFromInputFile = new byte[maxLenOfSignatures];
            inputStream.read(bytesFromInputFile, 0, maxLenOfSignatures);
            String result = "";
            for (byte elem : bytesFromInputFile) {
                String oneByteStr = (Integer.toHexString(elem & 0xFF) + " ").toUpperCase();
                if (oneByteStr.length() == 2) {
                    oneByteStr = "0" + oneByteStr;
                }
                result = result.concat(oneByteStr);
            }
            result = result.trim();
            currentSignature = result;
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
    }

    public String getSignatureMatches() {
        for (Map.Entry entry : signatureSamples.entrySet()) {
            String tmp = currentSignature.substring(0, entry.getValue().toString().length());
            if (entry.getValue().equals(tmp)) {
                return entry.getKey().toString();
            }
        }
        return "";
    }

    public void writeResult(String path) {
        try (FileOutputStream outputStream = new FileOutputStream(path, true)) {
            final PrintStream printStream = new PrintStream(outputStream);
            String fileType = getSignatureMatches();
            if (fileType.length() > 0) {
                printStream.println(fileType);
                System.out.println("PROCESSED");
            } else {
                System.out.println("UNDEFINED");
            }
            printStream.close();
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
    }
}

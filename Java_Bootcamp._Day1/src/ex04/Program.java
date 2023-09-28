import java.util.Scanner;

public class Program {
    private static char[] inputArr;
    private static int[][] inputNumbersArr;
    private static int[][] outputNumbersArr;
    private static int outputArrIndex;
    private static String[][] resultStrArr;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        if (str.length() > 0) {
            fillInputArr(str);
            fillInputNumbersArr();
            fillOutputNumbersArr();
            sortOutputNumbersArr();
            fillElemsFrequency();
            getResultStrArr();
            outputResultStrArr();
        }
    }

    private static void fillInputArr(String str) {
        inputArr = str.toCharArray();
    }

    private static void fillInputNumbersArr() {
        inputNumbersArr = new int[65535][3];

        for (char c : inputArr) {
            inputNumbersArr[c][0]++;
            inputNumbersArr[c][1] = c;
        }
    }

    private static void fillOutputNumbersArr() {
        outputNumbersArr = new int[10][3];

        outputArrIndex = 0;
        for (int[] elem : inputNumbersArr) {
            if (elem[0] != 0) {
                if (outputArrIndex < 10) {
                    outputNumbersArr[outputArrIndex] = elem;
                    outputArrIndex++;
                } else {
                    insertIntoSmallestElem(elem);
                }
            }
        }
    }

    private static void insertIntoSmallestElem(int[] elem) {
        int[] smallestElem = outputNumbersArr[0];
        int index = 0;
        for (int i = 0; i < outputNumbersArr.length; i++) {
            for (int j = 0; j < outputNumbersArr[i].length; j++) {
                if (smallestElem[0] > outputNumbersArr[i][0]) {
                    smallestElem = outputNumbersArr[i];
                    index = i;
                    break;
                }
            }
        }
        outputNumbersArr[index] = elem;
    }

    private static void sortOutputNumbersArr() {
        for (int i = 0; i < outputNumbersArr.length; i++) {
            for (int j = i; j < outputNumbersArr.length; j++) {
                if (i + 1 != outputNumbersArr.length && outputNumbersArr[i][0] < outputNumbersArr[j][0]) {
                    int[] temp = outputNumbersArr[i];
                    outputNumbersArr[i] = outputNumbersArr[j];
                    outputNumbersArr[j] = temp;
                }
            }
        }
    }

    private static void fillElemsFrequency() {
        int maxFrequency = outputNumbersArr[0][0];
        for (int i = 0; i < outputNumbersArr.length; i++) {
            outputNumbersArr[i][2] = outputNumbersArr[i][0] * 100 / maxFrequency / 10;
        }
    }

    private static void getResultStrArr() {
        int numberOfGrids = 10;
        if (outputArrIndex == 10) {
            resultStrArr = new String[12][10];
        } else {
            resultStrArr = new String[12][outputArrIndex];
        }

        for (int i = 0; i < resultStrArr.length; i++) {
            for (int j = 0; j < resultStrArr[i].length; j++) {
                if (numberOfGrids == outputNumbersArr[j][2]) {
                    resultStrArr[i][j] = getStringFromInt(outputNumbersArr[j][0]);
                } else if (i == 11) {
                    resultStrArr[i][j] = new String(new char[]{(char) outputNumbersArr[j][1]});
                } else if (numberOfGrids < outputNumbersArr[j][2]) {
                    resultStrArr[i][j] = "#";
                } else {
                    resultStrArr[i][j] = " ";
                }
            }
            numberOfGrids--;
        }
    }

    private static String getStringFromInt(int num) {
        int numberOfDigits = getNumberOfDigits(num);
        char[] temp = new char[numberOfDigits];
        for (int i = 0; i < numberOfDigits; i++) {
            temp[i] = (char) (num % 10 + '0');
            num /= 10;
        }
        char[] res = new char[numberOfDigits];
        for (int i = temp.length - 1, j = 0; i >= 0; i--, j++) {
            res[j] = temp[i];
        }
        return new String(res);
    }

    private static int getNumberOfDigits(int num) {
        int i = 0;
        while (num > 0) {
            num = num / 10;
            ++i;
        }
        return i;
    }

    private static void outputResultStrArr() {
        for (String[] strings : resultStrArr) {
            for (String string : strings) {
                System.out.print(string + "\t");
            }
            System.out.print("\n");
        }
    }
}

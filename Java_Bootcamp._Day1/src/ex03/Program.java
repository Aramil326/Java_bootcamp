import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    private static boolean stopper = false;
    private static int digit = 0;

    public static void main(String[ ] args) {
        Scanner scanner = new Scanner(System.in);
        long allTestGrades = getAllTestGrades(scanner);
        int temp = 0;
        for (int i = digit; i != 0; i--) {
            long testGrade;
            if (i == digit) {
                testGrade = allTestGrades % (long)10;
            } else {
                testGrade = allTestGrades / (long)Math.pow(10, temp) % (long)10;
            }
            printCurWeek(testGrade, temp + 1);
            temp++;
        }
    }

    private static int getWeekNum(Scanner scanner) {
        String week = scanner.next();
        int weekNum = 0;
        if (week.equals("Week")) {
            try {
                weekNum = scanner.nextInt();
            } catch (InputMismatchException e) {
                throwError();
            }
            if (weekNum > 18) {
                throwError();
            }
        } else if (week.equals("42")) {
            stopper = true;
        }
        return weekNum;
    }

    private static int getTestGrade(Scanner scanner) {
        int minNum = 9;
        for (int i = 0; i < 5; i++) {
            int curNum = 0;
            try {
                curNum = scanner.nextInt();
            } catch (InputMismatchException e) {
                throwError();
            }
            if ((curNum <= 0) || (curNum > 9)) {
                throwError();
            } else {
                if (minNum > curNum) {
                    minNum = curNum;
                }
            }
        }
        return minNum;
    }

    private static long getAllTestGrades(Scanner scanner) {
        int weekNum;
        long minTestGrade;
        long temp = 1;
        long resultNum = 0;
        for (int i = 0; i < 18 && !stopper; i++, temp *= 10) {
            weekNum = getWeekNum(scanner);
            if (!stopper) {
                if (weekNum != i + 1) {
                    throwError();
                }
                minTestGrade = getTestGrade(scanner);
                resultNum += minTestGrade * temp;
                digit++;
            }
        }
        return resultNum;
    }

    private static void printCurWeek(long testGrade, int weekNum) {
        System.out.print("Week " + weekNum + " ");
        for (long i = 0; i < testGrade; i++) {
            System.out.print("=");
        }
        System.out.println(">");
    }
    private static void throwError() {
        System.err.println("IllegalArgument");
        System.exit(1);
    }
}


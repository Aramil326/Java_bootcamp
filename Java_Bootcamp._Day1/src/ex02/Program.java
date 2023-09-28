import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    public static void main(String[ ] args) {
        Scanner scanner = new Scanner(System.in);

        int num = 0;
        int counter = 0;
        while (num != 42) {
            try {
                num = scanner.nextInt();
            } catch (InputMismatchException e) {
                throwError();
            }

            if ((is_prime(getNumberDigitSum(num))) && (num != 42)) {
                counter++;
            }
        }

        System.out.print("Count of coffee-request – " + counter);
    }

    private static boolean is_prime(int num) {
        boolean is_prime = true;
        if (num < 2) {
            throwError();
        } else {
            int i = 2;
            for (; i < num; i++) {
                if ((num % i == 0) && (num != i + 1)) {
                    is_prime = false;
                    break;
                }
                if (i * i > num) {
                    break;
                }
            }
        }
        return is_prime;
    }

    private static int getNumberDigitSum(int num) {
        int result = 0;
        for (int i = 0; i < getNumberOfDigits(num); i++) {
            result += num % 10;
            num /= 10;
        }
        return result;
    }

    private static int getNumberOfDigits(int num) {
        int i = 0;
        while(num > 0) {
            num = num / 10;
            i++;
        }
        return i;
    }

    private static void throwError() {
        System.err.println("Illegal Argument");
        System.exit(1);
    }
}


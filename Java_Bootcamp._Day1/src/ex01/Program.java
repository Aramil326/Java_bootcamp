import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = 0;

        try {
            num = scanner.nextInt();
        } catch (InputMismatchException e) {
            throwError();
        }

        if (num < 2) {
            throwError();
        } else {
            boolean is_prime = true;
            int i = 2;
            for (; i < num; i++) {
                if (num % i == 0 && num != i + 1) {
                    is_prime = false;
                    break;
                }
                if (i * i > num) {
                    break;
                }
            }
            System.out.print(is_prime + " " + (i - 1));
            System.exit(0);
        }
    }

    private static void throwError() {
        System.err.println("Illegal Argument");
        System.exit(1);
    }
}

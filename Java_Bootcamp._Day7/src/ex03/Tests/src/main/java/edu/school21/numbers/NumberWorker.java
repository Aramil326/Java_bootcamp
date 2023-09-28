package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number < 2) {
            throw new IllegalNumberException("Number must be greater than 1");
        } else {
            boolean is_prime = true;
            int i = 2;
            for (; i < number; i++) {
                if (number % i == 0 && number != i + 1) {
                    is_prime = false;
                    break;
                }
                if (i * i > number) {
                    break;
                }
            }
            return is_prime;
        }
    }

    public int digitsSum(int number) {
        int res = 0;
        while (number > 0) {
            res += number % 10;
            number /= 10;
        }
        return res;
    }


    public static class IllegalNumberException extends RuntimeException {
        public IllegalNumberException(String s) {
            super(s);
        }
    }
}

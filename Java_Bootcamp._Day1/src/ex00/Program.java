public class Program {
    public static void main(String[] args) {
        int num = 479598;
        int res = 0;
        res += num % 10;
        res += num / 10 % 10;
        res += num / 100 % 10;
        res += num / 1000 % 10;
        res += num / 10000 % 10;
        res += num / 100000 % 10;
        System.out.println(res);
    }
}
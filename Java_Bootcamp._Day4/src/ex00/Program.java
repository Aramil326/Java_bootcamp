public class Program {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].regionMatches(0, "--count=", 0, 8)) {
            try {
                int count = Integer.parseInt(args[0].substring(8));

                Thread thread1 = new Thread(new Hen(count));
                Thread thread2 = new Thread(new Egg(count));

                thread1.start();
                thread2.start();

                try {
                    thread1.join();
                    thread2.join();
                } catch (InterruptedException e) {
                    System.err.println(e);
                }

                for (int i = 0; i < count; i++) {
                    System.out.println("Human");
                }

            } catch (NumberFormatException e) {
                System.err.println(e);
                System.exit(-1);
            }
        } else {
            System.err.println("Wrong argument");
            System.exit(-1);
        }
    }
}


















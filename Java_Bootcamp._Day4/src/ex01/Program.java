public class Program {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].regionMatches(0, "--count=", 0, 8)) {
            try {
                int count = Integer.parseInt(args[0].substring(8));

                OutMessage msg = new OutMessage();
                Thread henMessage = new Thread(new Hen(count, msg));
                Thread eggMessage = new Thread(new Egg(count, msg));
                henMessage.start();
                eggMessage.start();

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


















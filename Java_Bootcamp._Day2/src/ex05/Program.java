public class Program {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start(args.length > 0 && args[0].equals("--profile=dev"));
    }
}

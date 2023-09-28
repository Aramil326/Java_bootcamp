import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        if (args.length == 1) {
            if (args[0].regionMatches(0, "--current-folder=", 0, 17)) {
                String inputPath = args[0].substring(17);
                FileManager manager = new FileManager(inputPath);
                Scanner scanner = new Scanner(System.in);
                String command = "";
                while (!command.equals("exit")) {
                    command = scanner.nextLine().trim();
                    String[] commandParams = command.split(" ");
                    if (commandParams[0].equals("ls")) {
                        manager.lsCommand();
                    } else if (commandParams[0].equals("cd")) {
                        if (commandParams.length == 1) {
                            manager.cdCommand("");
                        } else if (commandParams.length == 2) {
                            manager.cdCommand(commandParams[1]);
                        }
                    } else if (commandParams[0].equals("mv") && commandParams.length == 3) {
                        manager.mvCommand(commandParams[1], commandParams[2]);
                    } else if (!command.equals("exit")) {
                        System.out.println("command not found: " + command);
                    }
                }
            }
        }

    }


}
//--current-folder=/Users/aramil/Desktop/APJ/test/out
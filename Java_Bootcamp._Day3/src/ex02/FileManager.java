import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private Path currentPath;

    FileManager(String inputPath) {
        if (inputPath.charAt(inputPath.length() - 1) != '/') {
            inputPath += "/";
        }

        this.currentPath = Paths.get(inputPath);
        if (!currentPath.toFile().exists()) {
            System.err.println("no such file or directory: " + inputPath);
            System.exit(-1);
        }
    }

    public void lsCommand() {
        File[] files = currentPath.toFile().listFiles();
        if (files != null) {
            for (File elem : files) {
                System.out.println(elem.getName() + " " + String.format("%.0f", getSize(elem) / 1000.0) + " KB");
            }
        }
    }

    private long getSize(File file) {
        long fileLength = 0;
        if (file.isDirectory()) {
            fileLength = getDirectorySize(file);
        } else if (file.isFile()) {
            fileLength = getFileSize(file);
        }
        return fileLength;
    }

    private long getDirectorySize(File file) {
        long fileLength = 0;
        File[] files = file.listFiles();
        if (files != null) {
            for (File elem : files) {
                if (elem.isDirectory()) {
                    fileLength += getDirectorySize(elem);
                } else {
                    fileLength += getFileSize(elem);
                }
            }
        }
        return fileLength;
    }

    private long getFileSize(File file) {
        return file.length();
    }

    public void cdCommand(String pathToGo) {
        if (pathToGo.equals("..") && currentPath.getParent() != null && currentPath.getParent().toFile().exists()) {
            currentPath = currentPath.getParent().normalize();
            if (!currentPath.toString().equals("/")) {
                System.out.println(currentPath.normalize());
            }
        } else if (!currentPath.toString().equals("/") && !pathToGo.equals(".") && !pathToGo.equals("~") && !pathToGo.equals("")) {
            String tempPath = currentPath.normalize() + "/" + pathToGo;
            File goDirectory = Paths.get(tempPath).toFile();
            if (!goDirectory.exists()) {
                System.err.println("cd: no such file or directory: " + pathToGo);
            } else if (goDirectory.isFile()) {
                System.err.println("cd: not a directory: " + pathToGo);
            } else {
                currentPath = goDirectory.toPath().normalize();
                System.out.println(currentPath.normalize());
            }
        } else if (pathToGo.equals("~") || pathToGo.equals("")) {
            currentPath = currentPath.getRoot();
        }
        if (currentPath.toString().equals("/")) {
            System.out.println(currentPath.normalize());
        }
    }

    public void mvCommand(String what, String where) {
        try {
            Path whatPath = Paths.get(what);
            Path wherePath = Paths.get(where);

            if (isNotAbsolutePath(whatPath)) {
                whatPath = Paths.get(currentPath.normalize() + "/" + whatPath);
            }
            if (isNotAbsolutePath(wherePath)) {
                wherePath = Paths.get(whatPath.getParent().toString() + "/" + wherePath);
            }

            Files.move(whatPath, whatPath.resolveSibling(wherePath));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean isNotAbsolutePath(Path path) {
        return !path.toFile().toString().equals(path.toFile().getAbsolutePath());
    }
}

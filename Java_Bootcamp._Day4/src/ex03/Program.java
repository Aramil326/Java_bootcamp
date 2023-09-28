public class Program {
    private static ThreadToDownload[] myThreads;

    public static void main(String[] args) {
        String threads = "--threadsCount=";
        if (args.length == 1 && args[0].regionMatches(0, threads, 0, threads.length())) {
            try {
                int threadsCount = Integer.parseInt(args[0].substring(threads.length()));
                UrlsScanner urlsScanner = new UrlsScanner();
                urlsScanner.readFilesUrls();
                if (urlsScanner.getKeys().length >= threadsCount) {
                    String urlToDownload = "/Users/aramil/Desktop/APJ/Java_Bootcamp._Day03-1/src/ex03";
                    FileDownloader downloader = new FileDownloader(urlsScanner.getUrls(), urlsScanner.getKeys(), urlToDownload);

                    makeMyThreads(threadsCount, downloader);
                    makeThreads();
                    startThreads();
                } else {
                    System.err.println("Wrong argument");
                    System.exit(-1);
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

    private static void makeMyThreads(int threadsCount, FileDownloader downloader) {
        myThreads = new ThreadToDownload[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            myThreads[i] = new ThreadToDownload(i + 1, downloader);
        }
    }

    private static void makeThreads() {
        int size = myThreads.length;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new Thread(myThreads[i]);
        }
    }

    private static void startThreads() {
        for (ThreadToDownload myThread : myThreads) {
            myThread.start();
        }
    }
}

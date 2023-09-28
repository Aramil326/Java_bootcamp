public class ThreadToDownload extends Thread {
    private final int threadNum;
    private FileDownloader downloader;


    public ThreadToDownload(int threadNum, FileDownloader downloader) {
        this.threadNum = threadNum;
        this.downloader = downloader;
    }

    @Override
    public void run() {
        while (!downloader.isDone()) {
            downloader.download(threadNum);
        }
    }
}

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;
import java.util.HashMap;

public class FileDownloader extends Thread {
    private final HashMap<Integer, String> urls;
    private final int[] keysOfUrls;
    private final String pathToDownload;
    private int nextToDownload;
    private boolean downloadDone;

    public FileDownloader(HashMap<Integer, String> urls, int[] keysOfUrls, String pathToDownload) {
        this.urls = urls;
        this.keysOfUrls = keysOfUrls;
        this.pathToDownload = pathToDownload;
        nextToDownload = 0;
        downloadDone = false;
    }

    public void download(int threadNum) {
        try {
            int fileNum = nextToDownload;
            if (urls.size() == nextToDownload) {
                downloadDone = true;
            } else {
                nextToDownload++;
                String pathFromDownload = urls.get(keysOfUrls[fileNum]);
                System.out.println("Thread-" + threadNum + " start download file number " + keysOfUrls[fileNum]);
                downloadUsingNIO(pathFromDownload, pathToDownload + Paths.get(pathFromDownload).getFileName());
                System.out.println("Thread-" + threadNum + " finish download file number " + keysOfUrls[fileNum]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    public boolean isDone() {
        return downloadDone;
    }
}

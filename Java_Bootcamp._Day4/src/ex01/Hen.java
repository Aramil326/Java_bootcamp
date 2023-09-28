public class Hen implements Runnable {
    private final int count;
    private final OutMessage msg;

    public Hen(int count, OutMessage msg) {
        this.count = count;
        this.msg = msg;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            msg.outHen();
        }
    }
}

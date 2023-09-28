public class MyThread extends Thread {
    private final int threadNum;
    private final int posFrom;
    private final int posTo;
    private final SumArr sumArr;

    public MyThread(int threadNum, int posFrom, int posTo, SumArr sumArr) {
        this.threadNum = threadNum;
        this.posFrom = posFrom;
        this.posTo = posTo;
        this.sumArr = sumArr;
    }

    @Override
    public void run() {
        sumArr.printCurSum(threadNum, posFrom, posTo);
    }
}

public class SumArr {
    private final int[] inputArr;
    private int allThreadsSum = 0;

    public SumArr(int[] inputArr) {
        this.inputArr = inputArr;
    }

    public synchronized void printCurSum(int threadNum, int posFrom, int posTo) {
        int sum = 0;
        for (int i = posFrom; i <= posTo; i++) {
            sum += inputArr[i];
        }
        System.out.println("Thread " + threadNum + ": from " + posFrom + " to " + posTo + " sum is " + sum);
        allThreadsSum += sum;
    }

    public int getAllThreadsSum() {
        return allThreadsSum;
    }
}

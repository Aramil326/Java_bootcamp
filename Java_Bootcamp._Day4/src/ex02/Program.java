public class Program {
    private static MyThread[] allMyThreads;
    private static Thread[] allThreads;

    public static void main(String[] args) {
        String size = "--arraySize=";
        String threads = "--threadsCount=";
        if (args.length == 2 && args[0].regionMatches(0, size, 0, size.length()) && args[1].regionMatches(0, threads, 0, threads.length())) {
            try {
                int arrSize = Integer.parseInt(args[0].substring(size.length()));
                int threadsCount = Integer.parseInt(args[1].substring(threads.length()));
                if (arrSize > 2000000 || threadsCount > arrSize) {
                    System.err.println("Wrong argument");
                    System.exit(-1);
                }

                int[] inputArr = new int[arrSize];
                int mainSum = 0;
                for (int i = 0; i < inputArr.length; i++) {
                    inputArr[i] = random(-1000, 1000);
                    mainSum += inputArr[i];
                }

                System.out.println("Sum: " + mainSum);

                SumArr sumArr = new SumArr(inputArr);
                int lengthOfSubArrays = arrSize / threadsCount;
                makeMyThreads(threadsCount, lengthOfSubArrays, sumArr, inputArr.length);
                makeThreads();
                startThreads();

                System.out.println("Sum by threads: " + sumArr.getAllThreadsSum());
            } catch (NumberFormatException e) {
                System.err.println(e);
                System.exit(-1);
            }
        } else {
            System.err.println("Wrong argument");
            System.exit(-1);
        }
    }

    public static int random(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    private static void makeMyThreads(int threadsCount, int lengthOfSubArrays, SumArr sumArr, int inputArrLength) {
        allMyThreads = new MyThread[threadsCount];

        for (int i = 0, srcPos = 0; i < threadsCount; i++, srcPos += lengthOfSubArrays) {
            if (i + 1 != threadsCount) {
                allMyThreads[i] = new MyThread(i + 1, srcPos, srcPos + lengthOfSubArrays - 1, sumArr);
            } else {
                allMyThreads[i] = new MyThread(i + 1, srcPos, srcPos + (inputArrLength - srcPos) - 1, sumArr);
            }
        }
    }

    private static void makeThreads() {
        int size = allMyThreads.length;
        allThreads = new Thread[size];
        for (int i = 0; i < size; i++) {
            allThreads[i] = new Thread(allMyThreads[i]);
        }
    }

    private static void startThreads() {
        int size = allMyThreads.length;
        for (int i = 0; i < size; i++) {
            allThreads[i].start();
        }
        for (int i = 0; i < size; i++) {
            try {
                allThreads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

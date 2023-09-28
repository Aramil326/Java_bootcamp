public class OutMessage {
    private Category category = Category.EGG;

    public synchronized void outEgg() {
        if (category != Category.EGG) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Egg");
        category = Category.HEN;
        notify();
    }

    public synchronized void outHen() {
        if (category != Category.HEN) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Hen");
        category = Category.EGG;
        notify();
    }

    private enum Category {
        EGG, HEN
    }
}

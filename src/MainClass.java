import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MainClass {
    public static final int CARS_COUNT = 4;
    private static CyclicBarrier cb = new CyclicBarrier ( CARS_COUNT + 1 );
    private static CountDownLatch cdl = new CountDownLatch ( CARS_COUNT );
    private static String winner = null;

    public static void carIsReady ()
    {
        try {
            cb.await ();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void carHasFinished ( String name )
    {
        if (winner == null)
        {
            winner = name;
            System.out.println ( name + " - WIN" );
        }
        cdl.countDown ();
    }

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        cb.reset ();
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            cb.await ();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        try {
            cdl.await ();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
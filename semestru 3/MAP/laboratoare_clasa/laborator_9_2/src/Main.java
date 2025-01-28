import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {


        Muncitor m = new Muncitor();
        Thread t = new Thread(m);
        Runnable worker = new MuncitorNumarator();
        Counter counter = new Counter();
        t.start();
        Thread t2 = new Thread(() ->{
            for(int i =0 ; i<1000;i++){
                counter.increment();
            }
        });
        Thread t3 = new Thread(() ->{
            for(int i =0 ; i<1000;i++){
                counter.increment();

            }
        });
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for(int i = 0; i <3;i++){
            executor.execute(worker);
        }
        t2.start();
        t3.start();
        t2.join();
        t3.join();
        System.out.println(counter.getCount());
        Task task = new Task();
        ExecutorService exec2 = Executors.newFixedThreadPool(3);
        for(int i = 0 ; i<5;i++){
            exec2.submit(task);
        }
        exec2.shutdown();
    }
}
import decorator.DelayTaskRunner;
import decorator.PrinterTaskRunner;
import decorator.StrategyTaskRunner;
import decorator.TaskRunner;
import factory.Strategy;
import model.MessageTask;
import model.Task;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
//        int[] lista1 = {1,5,4,2,8,6,100,54,334,23};
//        int[] lista2 = {1,5,4,2,8,6,100,54,334,23};
//
//        Task bubble = new SortingTask("1","Sortare cu Bubble",lista1,new BubbleSort());
//        Task quick = new SortingTask("2","Sortare cu Quick",lista2,new QuickSort());
//        bubble.execute();
//        quick.execute();
        // 10

        Task m1 = new MessageTask("1","1","1","1","2", LocalDateTime.now());
        Task m2 = new MessageTask("2","2","2","1","2", LocalDateTime.now());
        Task m3 = new MessageTask("3","3","3","1","2", LocalDateTime.now());
        Task m4 = new MessageTask("4","4","4","1","2", LocalDateTime.now());
        TaskRunner tr = new StrategyTaskRunner(Strategy.valueOf(args[0].toUpperCase()));
        tr.addTask(m1);
        tr.addTask(m2);
        tr.addTask(m3);
        tr.addTask(m4);
        tr.executeAll();
        TaskRunner tr2 = new DelayTaskRunner(tr);
        TaskRunner tr3 = new PrinterTaskRunner(tr2);
        tr2.addTask(m1);
        tr2.addTask(m2);
        tr2.addTask(m3);
        tr2.addTask(m4);
        tr2.executeAll();
        tr3.addTask(m1);
        tr3.addTask(m2);
        tr3.addTask(m3);
        tr3.addTask(m4);
        tr3.executeAll();

    }
}


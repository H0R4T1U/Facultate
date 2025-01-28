package decorator;

import Utils.Util;
import model.Task;

import java.time.LocalDateTime;

public class PrinterTaskRunner extends  AbstractTaskRunner{
    public PrinterTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }

    @Override
    public void executeOneTask() {
        taskRunner.executeOneTask();
        System.out.println("Task Executat la "+ LocalDateTime.now().format(Util.formatter));
    }

    @Override
    public void executeAll() {
        while(taskRunner.hasTask()){
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task t) {
        taskRunner.addTask(t);
    }

    @Override
    public boolean hasTask() {
        return taskRunner.hasTask();
    }
}

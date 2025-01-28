package decorator;

import model.Task;

public class DelayTaskRunner extends AbstractTaskRunner {
    public DelayTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }

    @Override
    public void executeOneTask() {
        try{
            Thread.sleep(3000);
            taskRunner.executeOneTask();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

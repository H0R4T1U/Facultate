package decorator;

public abstract class AbstractTaskRunner  implements TaskRunner {
    TaskRunner taskRunner;

    public AbstractTaskRunner(TaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }

}

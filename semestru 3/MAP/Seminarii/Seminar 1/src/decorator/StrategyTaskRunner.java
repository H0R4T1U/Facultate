package decorator;

import factory.Strategy;
import factory.TaskContainerFactory;
import model.Container;
import model.Task;

public class StrategyTaskRunner implements TaskRunner {
    private Container container;
    public StrategyTaskRunner(Strategy strategy) {
        this.container = TaskContainerFactory.getInstance().createContainer(strategy);
    }

    @Override
    public void executeOneTask() {
        this.container.remove().execute();
    }

    @Override
    public void executeAll() {
        while(! this.container.isEmpty()){
            this.container.remove().execute();
        }
    }

    @Override
    public void addTask(Task t) {
        this.container.add(t);
    }

    @Override
    public boolean hasTask() {
        return !this.container.isEmpty();
    }
}

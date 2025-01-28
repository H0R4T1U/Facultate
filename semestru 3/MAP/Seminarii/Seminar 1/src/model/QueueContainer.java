package model;


public class QueueContainer extends ContainerClass {
    @Override
    public Task remove() {
        return list.removeFirst();
    }

    @Override
    public void add(Task task) {
        list.add(task);
    }

}

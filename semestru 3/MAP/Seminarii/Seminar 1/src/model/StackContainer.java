package model;


public class StackContainer extends ContainerClass {

    @Override
    public Task remove() {
        return list.removeLast();
    }

    @Override
    public void add(Task task) {
        list.add(task);
    }

}

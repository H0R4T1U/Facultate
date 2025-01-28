package model;

import java.util.ArrayList;

public abstract class ContainerClass implements Container {
    protected ArrayList<Task> list = new ArrayList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}

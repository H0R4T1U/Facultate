package model;

import java.util.Objects;

public abstract class Task {
    private String id;
    private String desc;


    public Task(String id, String desc) {
        this.desc = desc;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(desc, task.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, desc);
    }
    abstract public void execute();

}

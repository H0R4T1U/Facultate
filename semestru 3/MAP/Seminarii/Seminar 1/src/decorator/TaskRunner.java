package decorator;

import model.Task;

public interface TaskRunner {
    void executeOneTask(); // Executa un task din colectia de task uri de executat
    void executeAll(); // executa toate task urile din colectia de task uri
    void addTask(Task t); // adauga un task in colectia de tas kuri de executat
    boolean hasTask(); // verifica daca mai sunt task uri de executat
}

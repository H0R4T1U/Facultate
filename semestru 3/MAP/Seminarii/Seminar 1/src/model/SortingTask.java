package model;

import Utils.AbstractSorter;

public class SortingTask extends Task {
    int[] lista;
    AbstractSorter sorter;

    public SortingTask(String id, String desc, int[] lista, AbstractSorter sorter) {
        super(id, desc);
        this.lista = lista;
        this.sorter = sorter;
    }

    @Override
    public void execute() {
        System.out.println("Lista inainte de sortare:");
        for (int i = 0; i < lista.length; i++) {
            System.out.println(lista[i]);
        }
        sorter.sort(lista);
        System.out.println("Lista dupa sortare:");
        for (int i = 0; i < lista.length; i++) {
            System.out.println(lista[i]);
        }
    }
}

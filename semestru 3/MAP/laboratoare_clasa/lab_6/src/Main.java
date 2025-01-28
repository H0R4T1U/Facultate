import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<String> cuvinte = new ArrayList<String>();
        cuvinte.add("Cuvant1");
        cuvinte.add("Cuvant2");
        cuvinte.add("Cuvant3");
        cuvinte = cuvinte.stream()
                .map(String::toUpperCase)
                .toList();
        cuvinte.forEach(System.out::println);

        List<Dog> caini = new ArrayList<Dog>();
        caini.add(new Dog("Doru",15));
        caini.add(new Dog("Rexy",10));
        caini.add(new Dog("Ferdinand",5));
        caini.stream()
                .filter(dog -> dog.getAge() > 10)
                .forEach(System.out::println);

        caini.stream()
                .sorted((dog1,dog2)->dog1.getAge()-dog2.getAge())
                .forEach(System.out::println);
        caini.stream()
                .filter(dog -> "Sam".equals(dog.getName()))
                .forEach(System.out::println);
        caini.stream()
                .map(Dog::getName)
                .forEach(System.out::println);
        List<Integer> numere = new ArrayList<>();
        numere.add(1);
        numere.add(2);
        numere.add(3);
        List<Integer> patrate = numere.stream()
                .map(nr -> nr*nr)
                .toList();
        patrate.forEach(System.out::println);
        List<Mall> malluri = new ArrayList<>();
        malluri.add(new Mall("Vivo","x",2024,64543.253));
        malluri.add(new Mall("Iulius","y",2023,123123.263));
        malluri.add(new Mall("Lotus","z",2022,2152343.237));
        malluri.add(new Mall("ShoppingCity","w",2021,2121313.2123));
        malluri.stream()
                .map(mall -> new Mall(mall.getName(),mall.getManager(), mall.getYear(),mall.getProfit() *3 ))
                .max(Comparator.comparingDouble(Mall::getProfit))
                .ifPresent(System.out::println);

    }
}
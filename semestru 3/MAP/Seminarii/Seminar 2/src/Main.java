import Domain.InMemoryRepository;
import Domain.MyMap;
import Domain.Repository;
import Domain.Student;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Student s1 = new Student("Dan",4.5f);
        Student s2 = new Student("Ana",8.5f);
        Student s3 = new Student("Dan",4.5f);
        Student s4 = new Student("Dan",9.5f);

//        Set<Student> treeSet = new TreeSet<>(new Comparator<Student>() {
//
//            @Override
//            public int compare(Student o1, Student o2) {
//                return o1.compareTo(o2);
//            }
//        });
// Se poate si asa
        MyMap myMap = new MyMap();
        for(var stud : getList()){
            myMap.add(stud);
        }
        for(var x : myMap.getEntries()){
            System.out.println("Studentii cu media:"+ x.getKey() + " sunt:");
            Set<Student> studs = new TreeSet<>();
            for(var stud : x.getValue()){
                studs.add(stud);
            }
            for(var stud : studs){
                System.out.println(stud);
            }
        }
        Repository<Long, Student> studRepository = new InMemoryRepository<>();
        long id = 1;
        for(var stud : getList()){
            stud.setId(id);
            studRepository.save(stud);
            id++;
        }
        System.out.println("Studentii cu media:"+ id + " sunt:");
    }
    public static List<Student> getList(){
        List<Student> list = new ArrayList<Student>();
        list.add(new Student("1",3.5f));
        list.add(new Student("2",7.5f));
        list.add(new Student("3",4.5f));
        list.add(new Student("4",6.5f));
        list.add(new Student("5",9.5f));
        list.add(new Student("6",8.5f));
        return list;
    }
}
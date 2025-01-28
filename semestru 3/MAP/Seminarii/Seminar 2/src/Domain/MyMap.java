package Domain;

import java.util.*;

public class MyMap {
    private TreeMap<Integer, List<Student>> studentsMap;
    // trebuie Integer nu int ca sa nu fie primitiv

    public MyMap() {
        this.studentsMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        }
        );
    }
    public void add(Student student) {
        int medie=Math.round(student.getGrade());
        List<Student> list=studentsMap.get(medie);
        if(list==null) {
            list=new ArrayList<>();
            list.add(student);
            studentsMap.put(medie,list);
        }else{
            list.add(student);
        }

    }
    public Set<Map.Entry<Integer, List<Student>>> getEntries(){
        return studentsMap.entrySet();
    }
}

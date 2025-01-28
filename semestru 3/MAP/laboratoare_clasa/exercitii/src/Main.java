import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Circle> lst = new ArrayList<Circle>();
        double random = Math.random();
        while(random >= 0.01){

            lst.add(new Circle(random));
            random = Math.random();
        }
        EmployeeDict employees = new EmployeeDict();
        employees.addEmployee("A1","BOB");
        employees.addEmployee("A2","Bobitel");
        employees.addEmployee("A3","Bobita");

        for(String key : employees.getEmployees().keySet()){
            System.out.println(key + " " +employees.getEmployees().get(key));
        }

    }
}
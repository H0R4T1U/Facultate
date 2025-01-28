import java.util.HashMap;
import java.util.Map;

public class EmployeeDict  {
    private Map<String,String> employees;

    public EmployeeDict() {
        this.employees = new HashMap<String,String>();
    }

    public Map<String, String> getEmployees() {
        return employees;
    }

    public void addEmployee(String id, String name){
        this.employees.put(id.toLowerCase(),name);
    }
}

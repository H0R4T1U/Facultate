package Domain;

import java.util.Objects;

public class Student extends Entity<Long> implements Comparable<Student> {
    private String name;
    private Float grade;

    public Student(String name, Float grade) {
        this.name = name;
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) && Objects.equals(grade, student.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, grade);
    }

    @Override
    public int compareTo(Student o) {
        int res = this.name.compareTo(o.name);
        if (res == 0) {
            return o.grade.compareTo(grade);
        }
        return res;
    }

    public Float getGrade() {
        return grade;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", grade=" + grade;
    }
}

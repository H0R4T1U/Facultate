namespace Lab_11;

public class Application
{
    public void Run()
    {
        Person person = new Person();
        person.Greet();
        Student student = new Student();
        student.SetAge(21);
        student.Greet();
        student.ShowAge();
        Profesor prof = new Profesor();
        prof.SetAge(30);
        prof.Explain();
        prof.Greet();

    }
}
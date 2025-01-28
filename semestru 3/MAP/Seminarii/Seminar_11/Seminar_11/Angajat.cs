namespace Seminar_11;

public class Angajat : Entity<string> 
{
    public String Nume { get; set; }
    public double VenitPeOra { get; set; }
    public KnowledgeLevel Nivel { get; set; }
 
       
 
    public override string ToString()
    {
        return ID+" "+Nume+" "+VenitPeOra+" "+Nivel;
    }
}
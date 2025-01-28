
import domain.validators.Utilizator;
import domain.validators.UtilizatorValidator;
import domain.validators.ValidationException;
import repository.Repository;
import repository.file.UtilizatorRepository;
import repository.memory.InMemoryRepository;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Repository<Long, Utilizator> repo = new InMemoryRepository<Long, Utilizator>(new UtilizatorValidator());
        Repository<Long, Utilizator> repoFile = new UtilizatorRepository(new UtilizatorValidator(), "./data/utilizatori.txt");
        Utilizator u1 = new Utilizator("IONUT", "a");
        Utilizator u2 = new Utilizator("Mihai", "b");
        Utilizator u3 = null;
        u1.setId(1L);
        u2.setId(2L);
        try {
            repoFile.save(u1);
            repoFile.save(u2);
            repoFile.save(u3);
        }catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }catch(ValidationException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println();

    }
}
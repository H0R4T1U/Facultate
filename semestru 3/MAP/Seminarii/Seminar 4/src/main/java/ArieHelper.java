import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ArieHelper {

    public static void main(String[] args) {
        // cerinta 1
        List<Patrat> patrate = List.of(new Patrat(2), new Patrat(4));
        List<Cerc> cercuri = List.of(new Cerc(2), new Cerc(4));
//        Arie<Patrat> ariePatrat = x -> x.getLatura() * x.getLatura();
//        Arie<Cerc> arieCerc = x -> x.getRaza() * x.getRaza() * Math.PI;
//        patrate.forEach(x-> System.out.println(ariePatrat.calculate(x)));
//        cercuri.forEach(x-> System.out.println(arieCerc.calculate(x)));

        Arie<Patrat> ariePatrat = ArieHelper::ariePatrat;
        Arie<Cerc> arieCerc = ArieHelper::arieCerc;

        // cerinta 2
        Predicate<String> lungimePara = s -> s.length() % 2 == 0;
        List<String> lista = List.of("mancare", "pui", "zece");
        afiseazaCriteriu(lista, lungimePara);

        Predicate<String> incepeCuP = s -> s.startsWith("p");
        afiseazaCriteriu(lista, incepeCuP);

        // cerinta 3a
        Function<String, Integer> converterLambda = x -> Integer.valueOf(x);
        System.out.println(converterLambda.apply("14"));

        Predicate<Character> eVocala = x -> {
            String vocale = "aeiouAEIOU";
            return vocale.contains(String.valueOf(x));
        };
        Function<String, String> converterPasareasca = x-> {
            String rez = "";
            for (int i = 0; i < x.length(); i++) {
                rez += x.charAt(i);
                if (eVocala.test(x.charAt(i))) {
                    rez += 'p';
                    rez += x.charAt(i);
                }
            }
            return rez;
        };

        System.out.println(converterPasareasca.apply("Mama merge la piata"));

        // cerinta 3b
        Function<String, Integer> convertMethodReference = Integer::valueOf;
        System.out.println(convertMethodReference.apply("14"));

        // cerinta 4
        Supplier<Cerc> supplier = () -> new Cerc();
        Cerc c1 = supplier.get();
        System.out.println(c1);

        // cerinta streamuri
        List<String> list = List.of("asd", "bce", "asc", "bcr", "ccc");
        Stream<String> stream = list.stream();
        stream.filter(x -> {
            //System.out.println(x);
            return x.startsWith("b");
        }).map(x -> {
            //System.out.println(x);
            return x.toUpperCase();
        }).forEach(System.out::println);

        List<Integer> integerList = List.of(1, 10, 24, 15);
        Stream<Integer> stream2 = integerList.stream();
        stream2.reduce( (x,y) -> x + y).ifPresent(System.out::println);
       }



       public static <E> void afiseazaCriteriu(List<E> l, Predicate<E> criteriu) {
        l.forEach(element -> {
            if (criteriu.test(element))
                System.out.println(element);
        });

       }

    public static double ariePatrat(Patrat patrat) {
        return patrat.getLatura() * patrat.getLatura();
    }

    public static double arieCerc(Cerc cerc) {
        return cerc.getRaza() * cerc.getRaza() * Math.PI;
    }
}

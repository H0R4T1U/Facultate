import domain.ActionMovie;
import domain.HorrorMovie;
import domain.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Movie> movies = new ArrayList<>();
        Movie m1 = new Movie("A",2024);
        Movie m2 = new HorrorMovie("B",2020,"LOW");
        Movie m3 = new HorrorMovie("C",2024,"HIGH");
        Movie m4 = new ActionMovie("D",2024,true);
        movies.add(m1);
        movies.add(m2);
        movies.add(m3);
        movies.add(m4);
        for(Movie movie : movies) {
            if(movie instanceof HorrorMovie){
                System.out.println(movie);
            }
        }
        List<String> linii = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/Texte/linii.txt"));
            String linie = reader.readLine();
            while(linie  != null){
                linii.add(linie);
                linie = reader.readLine();
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        int i =1;
        for(String line : linii){
            if(line.contains("test")){
                System.out.println("linia:"+i);
            }
            i++;
        }
        List<String> cuvinte = new ArrayList<>();
        int len = -1;
        String cuvant_mare = "";
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/Texte/cuvinte.txt"));
            String cuvant = reader.readLine();
            while(cuvant  != null){
                cuvinte.add(cuvant);
                cuvant = reader.readLine();
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        for(String c : cuvinte){
            if(c.length() > len){
                len = c.length();
                cuvant_mare = c;
            }
        }
        System.out.println("Cuvantul cel mai lung este:"+cuvant_mare);
        List<String> elemente = new ArrayList<>();
        int nr = 10;
        while(nr > 0){
            nr-=1;
            elemente.add(Double.toString(abs(Math.random())));
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Texte/nr_random.txt"));
            for(String el : elemente){
                writer.write(el);
                writer.newLine();
            }
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
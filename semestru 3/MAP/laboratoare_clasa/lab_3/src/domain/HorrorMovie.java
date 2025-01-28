package domain;

public class HorrorMovie extends Movie {
    private String intensityFear;

    public HorrorMovie(String title, Integer year, String intensityFear) {
        super(title, year);
        this.intensityFear = intensityFear;
    }

}

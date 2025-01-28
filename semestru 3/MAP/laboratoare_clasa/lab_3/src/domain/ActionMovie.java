package domain;

public class ActionMovie extends Movie {
    private boolean isThriller;

    public ActionMovie(String title, Integer year, boolean isThriller) {
        super(title, year);
        this.isThriller = isThriller;
    }
}

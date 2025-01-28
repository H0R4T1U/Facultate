package domain;

public class Movie {
    private String title;
    private Integer year;

    public Movie(String title, Integer year) {
        this.title = title;
        this.year = year;
    }

    @Override
    public java.lang.String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
}

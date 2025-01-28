public class Mall {
    String name;
    String manager;
    Integer year;
    Double profit;

    @Override
    public String toString() {
        return "Mall{" +
                "name='" + name + '\'' +
                ", manager='" + manager + '\'' +
                ", year=" + year +
                ", profit=" + profit +
                '}';
    }

    public Mall(String name, String manager, Integer year, Double profit) {
        this.name = name;
        this.manager = manager;
        this.year = year;
        this.profit = profit;
    }

    public String getName() {
        return name;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getManager() {
        return manager;
    }

    public Integer getYear() {
        return year;
    }

    public Double getProfit() {
        return profit;
    }
}

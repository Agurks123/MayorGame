package oldGameFiles;

public class Mayor {

    //protected String name;
    protected int budget;
    protected double popularity;
    protected Card backpack;

    public Mayor(double popularity, int budget) {
        this.popularity = popularity;
        this.budget = budget;
        this.backpack = null;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
}

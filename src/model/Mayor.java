package model;

public class Mayor {

    private int budget;
    private double popularity;
    private Card backpack;

    public Mayor(double popularity, int budget) {
        this.popularity = popularity;
        this.budget = budget;
    }

    public int getBudget() {
        return budget;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public Card getBackpack() {
        return backpack;
    }

    public boolean canAfford(int amount) {
        return budget >= amount;
    }

    public void spend(int amount) {
        if (!canAfford(amount)) {
            throw new IllegalStateException("Not enough money");
        }
        budget -= amount;
    }

    public void addBudget(int amount) {
        budget += amount;
    }

    public void changePopularity(double delta) {
        popularity += delta;
    }

    public boolean hasBackpackCard() {
        return backpack != null;
    }

    public void putInBackpack(Card card) {
        if (backpack != null) throw new IllegalStateException("Backpack full");
        backpack = card;
    }

    public Card removeBackpackCard() {
        Card c = backpack;
        backpack = null;
        return c;
    }
}

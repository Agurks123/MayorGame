package model;

import java.util.List;
import java.util.Map;

public class Card {

    private final String name;
    private final int cost;
    private final Type type;
    private final int requirement;
    private final int duration;
    private final List<Map.Entry<Type, Integer>> effects;

    public Card(String name, int cost, Type type, int requirement, int duration, List<Map.Entry<Type, Integer>> effects) {

        this.name = name;
        this.cost = cost;
        this.type = type;
        this.requirement = requirement;
        this.duration = duration;
        this.effects = effects;
    }

    // ---- getters ----

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public Type getType() {
        return type;
    }

    public int getRequirement() {
        return requirement;
    }

    public int getDuration() {
        return duration;
    }

    public List<Map.Entry<Type, Integer>> getEffects() {
        return effects;
    }

    // ---- helper ----

    public boolean hasDuration() {
        return duration > 0;
    }
}

package model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static utils.Constants.MAX_CITY_STAT_VALUE;
import static utils.Constants.MIN_CITY_STAT_VALUE;

public class City {


    private final Map<Type, Integer> stats = new EnumMap<>(Type.class);
    private final Map<Type, Integer> prevStats = new EnumMap<>(Type.class);
    private final Map<Type, Integer> effectStrength = new EnumMap<>(Type.class);
    private final Map<Type, Integer> effectDuration = new EnumMap<>(Type.class);

    public City(int civilian, int business, int environment, int security) {
        stats.put(Type.CIVILIAN, civilian);
        stats.put(Type.BUSINESS, business);
        stats.put(Type.ENVIRONMENT, environment);
        stats.put(Type.SECURITY, security);

        setPrev();

        for (Type t : Type.values()) {
            effectStrength.put(t, 0);
            effectDuration.put(t, 0);
        }
    }

    public int get(Type type) {
        return stats.get(type);
    }

    public int getPrev(Type type) {
        return prevStats.get(type);
    }

    public void setPrev() {
        prevStats.clear();
        prevStats.putAll(stats);
    }

    // ---- stat changes ----

    public void change(Type type, int delta) {
        stats.put(type, clamp(stats.get(type) + delta));
    }


    public void addEffect(Type type, int strength, int duration) {
        effectStrength.put(type, strength);
        effectDuration.put(type, duration);
    }

    public void applyActiveEffects() {
        for (Type t : Type.values()) {
            int d = effectDuration.get(t);
            if (d > 0) {
                change(t, effectStrength.get(t));
                effectDuration.put(t, d - 1);
            }
        }
    }

    // ---- auto update per turn ----

    public void updateGroups() {
        List<Integer> sorted = stats.values().stream().sorted().toList();
        int min1 = sorted.get(0);
        int min2 = sorted.size() > 1 ? sorted.get(1) : min1;
        int max = sorted.get(sorted.size() - 1);

        for (Type t : Type.values()) {
            int value = stats.get(t);

            if (value == min1 || value == min2) {
                change(t, -(2 + value * 7 / 100));
            } else if (value == max && value <= 80) {
                change(t, value * 5 / 100);
            }
        }
    }


    public boolean isDead() {
        return stats.values().stream().anyMatch(v -> v <= 0);
    }


    private int clamp(int value) {
        return Math.max(MIN_CITY_STAT_VALUE, Math.min(MAX_CITY_STAT_VALUE, value));
    }
}

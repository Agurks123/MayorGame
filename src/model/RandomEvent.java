package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandomEvent {

    private final String name;
    private final String description;
    private final Map<Type, Integer> effects;

    public RandomEvent(String name, String description, Map<Type, Integer> effects) {
        this.name = name;
        this.description = description;
        this.effects = effects;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<Type, Integer> getEffects() {
        return effects;
    }

    public boolean isPositive() {
        return effects.values().stream().mapToInt(Integer::intValue).sum() > 0;
    }



    // custom

    public String[] getEffectDescriptions() {
        String[] result = new String[4];
        List<String> temp = new ArrayList<>();

        for (Map.Entry<Type, Integer> entry : effects.entrySet()) {
            int value = entry.getValue();
            String sign = value > 0 ? "+" : "";
            temp.add(entry.getKey().name() + ": " + sign + value);
        }

        // Fill
        for (int i = 0; i < 4; i++) {
            if (i < temp.size()) {
                result[i] = temp.get(i);
            } else {
                result[i] = ""; // empty line if no effect
            }
        }

        return result;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("!! ").append(name).append(" !!  ");

        for (Map.Entry<Type, Integer> entry : effects.entrySet()) {
            int value = entry.getValue();
            String sign = value > 0 ? "+" : "-";
            String abbrev = getTypeAbbreviation(entry.getKey());
            sb.append(sign).append(abbrev).append(" ");
        }

        return sb.toString().trim();
    }

    private String getTypeAbbreviation(Type type) {
        switch(type) {
            case CIVILIAN:   return "CIV";
            case BUSINESS:   return "BUS";
            case ENVIRONMENT:return "ENV";
            case SECURITY:   return "SEC";
            default:         return type.name().substring(0, 3); // fallback
        }
    }
}

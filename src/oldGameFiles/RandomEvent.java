package oldGameFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandomEvent {
    private String name;
    private String description;
    private Map<Type, Integer> pointsChanges; // type;value
    private boolean isPositive;

    public RandomEvent(String name, String description, Map<Type, Integer> pointsChanges) {
        this.name = name;
        this.description = description;
        this.pointsChanges = pointsChanges;
        int totalPoints = pointsChanges.values().stream().mapToInt(Integer::intValue).sum();
        this.isPositive = totalPoints > 0;

    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Map<Type, Integer> getPointsChanges() { return pointsChanges; }
    public boolean isPositive() { return isPositive; }

    @Override
    public String toString() {
        return name + ": " + description + " " + pointsChanges;
    }

    public String[] getEffectDescriptions() {
        String[] result = new String[4];
        List<String> temp = new ArrayList<>();

        for (Map.Entry<Type, Integer> entry : pointsChanges.entrySet()) {
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

        for (Map.Entry<Type, Integer> entry : pointsChanges.entrySet()) {
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

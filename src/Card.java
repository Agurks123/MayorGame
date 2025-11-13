import java.util.List;
import java.util.Map;

public class Card {
    protected String name;
    protected int cost;
    protected Type type;
    protected int requirement;
    protected int duration;
    protected List<Map.Entry<Type, Integer>> effects;


    public Card(String name, int cost, Type type, int requirement, int duration, List<Map.Entry<Type, Integer>> effects) {
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.requirement = requirement;
        this.duration = duration;
        this.effects = effects;
    }
}

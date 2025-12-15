package effect;

import model.City;
import model.RandomEvent;
import model.Type;

import java.util.Map;

public class EventEffect implements GameEffect {

    private final RandomEvent event;

    public EventEffect(RandomEvent event) {
        this.event = event;
    }

    @Override
    public void apply(City city) {

        for (Map.Entry<Type, Integer> entry : event.getEffects().entrySet()) {
            city.change(entry.getKey(), entry.getValue());
        }
    }
}

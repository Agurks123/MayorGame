package service;

import model.RandomEvent;

import java.util.List;
import java.util.Random;

public class RandomEventService {

    private final List<RandomEvent> events;
    private final Random random = new Random();

    public RandomEventService(List<RandomEvent> events) {
        this.events = events;
    }

    public RandomEvent nextEvent() {
        return events.get(random.nextInt(events.size()));
    }
}

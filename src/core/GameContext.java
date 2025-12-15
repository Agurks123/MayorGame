package core;

import model.Card;
import model.City;
import model.Deck;
import model.Mayor;
import render.GameRenderer;
import service.RandomEventService;
import strategy.EventChanceStrategy;

import java.util.List;

public class GameContext {

    public final City city;
    public final Mayor mayor;
    public final Deck deck;
    public List<Card> hand;
    public final RandomEventService eventService;
    public final EventChanceStrategy chanceStrategy;
    public final GameRenderer renderer;
    public final String[] info;

    public GameContext(City city, Mayor mayor, Deck deck, RandomEventService eventService, EventChanceStrategy chanceStrategy, GameRenderer renderer, String[] info) {
        this.city = city;
        this.mayor = mayor;
        this.deck = deck;
        this.eventService = eventService;
        this.chanceStrategy = chanceStrategy;
        this.renderer = renderer;
        this.info = info;

        if (deck !=null) this.hand = deck.draw();
    }
}

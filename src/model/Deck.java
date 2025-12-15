package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static utils.Constants.DRAW_COUNT;

public class Deck {

    private final List<Card> cards = new ArrayList<>();
    private final List<Card> discardPile = new ArrayList<>();

    public Deck(List<Card> initialCards) {
        cards.addAll(initialCards);
        shuffle();
    }

    public List<Card> draw() {
        int count = Math.min(DRAW_COUNT, cards.size());
        List<Card> drawn = new ArrayList<>(cards.subList(0, count));
        cards.subList(0, count).clear();
        return drawn;
    }

    public void discard(List<Card> usedCards) {
        discardPile.addAll(usedCards);
    }

    public void reshuffle() {
        cards.addAll(discardPile);
        discardPile.clear();
        shuffle();
    }

    public int size() {
        return cards.size();
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    public void addCards(List<Card> newCards) {
        cards.addAll(newCards);
    }
    public List<Card> getCards() {
        return cards;
    }
}

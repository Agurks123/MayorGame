package effect;

import model.Card;
import model.City;
import model.Type;

import java.util.Map;

/**
 * Represents the effect of playing a card on the city.
 * Applies instant or lasting effects based on the card's duration.
 */
public class CardEffect implements GameEffect {

    private final Card card;

    public CardEffect(Card card) {
        this.card = card;
    }

    @Override
    public void apply(City city) {
        for (Map.Entry<Type, Integer> effectEntry : card.getEffects()) {
            Type type = effectEntry.getKey();
            int value = effectEntry.getValue();

            if (card.hasDuration() && card.getDuration() > 0) {
                // Add lasting effect to city (duration > 0)
                city.addEffect(type, value, card.getDuration());
            } else {
                // Apply immediate effect
                city.change(type, value);
            }
        }
    }
}

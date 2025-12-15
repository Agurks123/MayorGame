package factory;

import loader.CardFileLoader;
import model.Card;

import java.util.Collections;
import java.util.List;

import static utils.Constants.CARD_FILE;

public class CardFactory {

    private final CardFileLoader loader = new CardFileLoader();

    public List<Card> createDefaultDeck() {
        return loader.load(CARD_FILE);
    }
    public List<Card> getNewFiveRandomCards(){
        List<Card> c = loader.load(CARD_FILE);
        Collections.shuffle(c);
        return c.subList(0, Math.min(5, c.size()));
    }
}

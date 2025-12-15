package oldGameFiles;

import java.io.*;
import java.util.*;

public class Deck {
    protected List<Card> cards;
    protected List<Card> discardPile;

    public Deck() {
        discardPile = new ArrayList<>();
        cards = new ArrayList<>();
        loadCardsFromFile(cards,"cards.txt");
        shuffle();
        //for (Card card : cards) {
        //    System.out.println(card.name + "  " + card.effects);
        //}
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards() {
        cards.addAll(discardPile);
        discardPile.clear();
        shuffle();
    }

    public int getCardsSize(){
        return cards.size();
    }
    public int getDiscardPileSize(){return discardPile.size();}


    public List<Card> getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(List<Card> discardPile) {
        this.discardPile = discardPile;
    }



    public List<Card> drawCards() { // 6
        int n = 6;
        if (cards.size() < n){
            List<Card> allCards = new ArrayList<>(cards);
            cards.clear();
            return allCards;
        }
        else {

            List<Card> allCards = new ArrayList<>(cards.subList(0, n));
            for (int i = 0; i < n; i++) {
                cards.remove(0);
            }
            return allCards;
        }
    }

    public void discardCard(Card card) {
        discardPile.add(card);
    }

    public void discardCards(List<Card> allCards) {
        if(allCards != null){
            discardPile.addAll(allCards);
        }

    }

    public void getFiveRandomCards() {
        List<Card> allCards = new ArrayList<>();

        loadCardsFromFile(allCards,"cards.txt");

        Collections.shuffle(allCards);

        // send only 5
        cards.addAll(allCards.subList(0, Math.min(5, allCards.size())));
    }


    private List<Card> loadCardsFromFile(List<Card> cards, String filePath) {

        InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
        if (is == null) {
            System.err.println("cards.txt resource not found!");
            return cards;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length < 6) {
                    System.err.println("Invalid card format: " + line);
                    continue;
                }

                String name = parts[0];
                int cost = Integer.parseInt(parts[1]);
                Type type = Type.valueOf(parts[2].toUpperCase());
                int requirement = Integer.parseInt(parts[3]);
                int duration = Integer.parseInt(parts[4]);

                // parse List<Map.Entry<Type, Integer>>
                List<Map.Entry<Type, Integer>> effects = new ArrayList<>();
                String[] effectsParts = parts[5].split(",");
                for (String effectStr : effectsParts) {
                    String[] effectPair = effectStr.split(";");
                    if (effectPair.length != 2) continue;

                    Type effectType = Type.valueOf(effectPair[0].toUpperCase());
                    int effectValue = Integer.parseInt(effectPair[1]);
                    effects.add(new AbstractMap.SimpleEntry<>(effectType, effectValue));
                }

                Card card = new Card(name, cost, type, requirement, duration, effects);
                cards.add(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // hardcoded i guess

        }

        return cards;

    }
}

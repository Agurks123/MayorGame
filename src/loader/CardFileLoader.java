package loader;

import model.Card;
import model.Type;

import java.io.*;
import java.util.*;

public class CardFileLoader {

    public List<Card> load(String filePath) {

        List<Card> cards = new ArrayList<>();

        InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
        if (is == null) {
            throw new IllegalStateException("cards file not found: " + filePath);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");

                String name = parts[0];
                int cost = Integer.parseInt(parts[1]);
                Type type = Type.valueOf(parts[2].toUpperCase());
                int requirement = Integer.parseInt(parts[3]);
                int duration = Integer.parseInt(parts[4]);

                List<Map.Entry<Type, Integer>> effects = new ArrayList<>();

                for (String e : parts[5].split(",")) {
                    String[] pair = e.split(";");

                    effects.add(
                            Map.entry(
                                    Type.valueOf(pair[0].toUpperCase()),
                                    Integer.parseInt(pair[1])
                            )
                    );
                }


                cards.add(new Card(name, cost, type, requirement, duration, effects));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return cards;
    }
}

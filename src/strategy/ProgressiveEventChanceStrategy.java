package strategy;

import java.util.Random;

public class ProgressiveEventChanceStrategy implements EventChanceStrategy {

    private final Random random = new Random();

    private static double randomEventChance = 0;

    @Override
    public boolean shouldTriggerEvent(int cityStatus ) {

        // random ahh function
        double chance = randomEventChance * (1 + (50 - cityStatus) / 100.0);
        if (random.nextDouble() < chance){
            randomEventChance = 0;
            return true;
        } else {
            randomEventChance += 0.1;
            return false;
            //if (randomEventChance > 1) randomEventChance = 1;
         }

    }
}
